package net.arver.oa.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.arver.oa.common.util.R;
import net.arver.oa.config.shiro.JwtUtil;
import net.arver.oa.exception.ServiceException;
import net.arver.oa.form.CheckinForm;
import net.arver.oa.pojo.User;
import net.arver.oa.service.CheckinService;
import net.arver.oa.service.FaceModelService;
import net.arver.oa.service.UserService;
import net.arver.oa.vo.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签到控制器.
 * @author leegvv
 */
@RequestMapping("/checkin")
@RestController
@Api("签到模块web接口")
@Slf4j
public class CheckinController {

    /**
     * 文件存放地址.
     */
    @Value("${oa.image-folder}")
    private String imageFolder;

    /**
     * jwt校验工具类.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 签到服务.
     */
    @Autowired
    private CheckinService checkinService;

    /**
     * 人脸模型服务.
     */
    @Autowired
    private FaceModelService faceModelService;

    /**
     * 用户服务.
     */
    @Autowired
    private UserService userService;

    /**
     * 常量.
     */
    @Autowired
    private SystemConstants constants;

    /**
     * 签到.
     * @param form 参数
     * @param file 图片
     * @param token token
     * @return 签到结果
     */
    @PostMapping("/checkin")
    @ApiOperation("签到")
    public R checkin(@Valid final CheckinForm form, @RequestParam("photo")final MultipartFile file,
                     @RequestHeader("token")final String token) {
        if (file == null) {
            return R.error("没有上传文件");
        }
        final int userId = jwtUtil.getUserId(token);
        final String fileName = file.getOriginalFilename().toLowerCase();
        if (!fileName.endsWith(".jpg")) {
            return R.error("必须提交JPG格式图片");
        } else {
            final String path = imageFolder + "/" + fileName;
            try {
                file.transferTo(Paths.get(path));
                form.setUserId(userId);
                form.setPath(path);
                checkinService.checkin(form);
                return R.ok("签到成功");
            } catch (final Exception e) {
                log.error(e.getMessage(), e);
                throw new ServiceException("图片保存错误");
            } finally {
                FileUtil.del(path);
            }
        }

    }

    /**
     * 是否可以签到.
     * @param token token
     * @return 校验信息
     */
    @GetMapping("/validCanCheckin")
    @ApiOperation("查看用户今天是否可以签到")
    public R validCanCheckin(@RequestHeader final String token) {
        final int userId = jwtUtil.getUserId(token);
        final String result = checkinService.validCanCheckIn(userId, DateUtil.today());
        return R.ok(result);
    }

    /**
     * 创建人脸模型.
     * @param file 文件
     * @param token token
     * @return 操作结果
     */
    @PostMapping("createFaceModel")
    @ApiOperation("创建人脸模型")
    public R createFaceModel(@RequestParam("photo")final MultipartFile file,
                             @RequestHeader("token") final String token) {
        final int userId = jwtUtil.getUserId(token);
        if (file == null) {
            return R.error("没有上传文件");
        }
        final String fileName = file.getOriginalFilename().toLowerCase();
        final String path = imageFolder + "/" + fileName;
        if (!fileName.endsWith(".jpg")) {
            return R.error("必须提交JPG格式图片");
        } else {
            try {
                file.transferTo(Paths.get(path));
                faceModelService.createFaceModel(userId, path);
                return R.ok("人脸建模成功");
            } catch (final Exception e) {
                log.error(e.getMessage(), e);
                throw new ServiceException("保存图片错误", e);
            } finally {
                FileUtil.del(path);
            }
        }

    }

    /**
     * 查询用户当日签到数据.
     * @param token token
     * @return 用户当日签到数据
     */
    @GetMapping("/searchTodayCheckin")
    @ApiOperation("查询用户当日签到数据")
    public R<HashMap> searchTodayCheckin(@RequestHeader("token")final String token) {
        final int userId = jwtUtil.getUserId(token);
        final HashMap<String, Object> map = checkinService.searchTodayCheckin(userId);
        map.put("attendanceTime", constants.getAttendanceTime());
        map.put("closingTime", constants.getClosingTime());
        final long days = checkinService.searchCheckinDays(userId);
        map.put("checkinDays", days);

        //判断日期是否在用户入职之前
        final User user = userService.searchById(userId);
        final LocalDate localDate = user.getHiredate();
        final Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        final DateTime hiredate = new DateTime(DateTime.from(instant));
        DateTime startDate = DateUtil.beginOfWeek(DateUtil.date());
        if (startDate.isBefore(hiredate)) {
            startDate = hiredate;
        }
        final DateTime endDate = DateUtil.endOfWeek(DateUtil.date());
        final HashMap<String, Object> param = new HashMap<>();
        param.put("startDate", startDate.toString());
        param.put("endDate", endDate.toString());
        param.put("userId", userId);
        final List<Map<String, Object>> list = checkinService.searchWeekCheckin(param);
        map.put("weekCheckin", list);
        return R.ok(map);
    }

    /**
     * 查询用户当月签到数据.
     * @param form form
     * @param token token
     * @return 用户当月签到数据
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    @GetMapping("/searchMonthCheckin")
    @ApiOperation("查询用户当月签到数据")
    public R<List<Map<String, Object>>> searchMonthCheckin(@Valid final SearchMonthCheckinForm form,
                                                           @RequestHeader("token")final String token) {
        final int userId = jwtUtil.getUserId(token);
        //判断日期是否在用户入职之前
        final User user = userService.searchById(userId);
        final LocalDate localDate = user.getHiredate();
        final Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        final DateTime hiredate = new DateTime(DateTime.from(instant));
        final String month = form.getMonth() < 10 ? "0" + form.getMonth() : form.getMonth().toString();
        DateTime startDate = DateUtil.parse(form.getYear() + "-" + month + "-01");
        if (startDate.isBefore(DateUtil.beginOfMonth(hiredate))) {
            throw new ServiceException("只能查询入职之后日期的数据");
        } else if (startDate.isBefore(hiredate)) {
            startDate = hiredate;
        }
        final DateTime endDate = DateUtil.endOfMonth(DateUtil.date());
        final HashMap<String, Object> param = new HashMap<>();
        param.put("startDate", startDate.toString());
        param.put("endDate", endDate.toString());
        param.put("userId", userId);
        final List<Map<String, Object>> list = checkinService.searchWeekCheckin(param);
        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        for (final Map<String, Object> one : list) {
            final String type = one.get("type").toString();
            final String status = one.get("status").toString();
            if ("工作日".equals(type)) {
                if ("正常".equals(status)) {
                    sum1++;
                } else if ("迟到".equals(status)) {
                    sum2++;
                } else if ("缺勤".equals(status)) {
                    sum3++;
                }
            }
        }
        final R<List<Map<String, Object>>> ret = R.ok(list);
        ret.setAdditionalProperties("sum1", sum1);
        ret.setAdditionalProperties("sum2", sum2);
        ret.setAdditionalProperties("sum3", sum3);
        return ret;
    }
}
