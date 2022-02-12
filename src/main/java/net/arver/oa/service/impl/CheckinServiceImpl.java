package net.arver.oa.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import net.arver.oa.exception.ServiceException;
import net.arver.oa.form.CheckinForm;
import net.arver.oa.mapper.CheckinMapper;
import net.arver.oa.mapper.HolidaysMapper;
import net.arver.oa.mapper.UserMapper;
import net.arver.oa.mapper.WorkdayMapper;
import net.arver.oa.pojo.Checkin;
import net.arver.oa.pojo.FaceModel;
import net.arver.oa.pojo.gen.CheckinExample;
import net.arver.oa.service.CheckinService;
import net.arver.oa.service.CityService;
import net.arver.oa.service.FaceModelService;
import net.arver.oa.task.EmailTask;
import net.arver.oa.vo.SystemConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签到service实现类.
 * @author leegvv
 */
@Slf4j
@Service
public class CheckinServiceImpl implements CheckinService {

    /**
     * 创建人脸模型url.
     */
    @Value("${oa.face.createFaceModelUrl}")
    private String createFaceModelUrl;

    /**
     * 签到url.
     */
    @Value("${oa.face.checkinUrl}")
    private String checkinUrl;

    /**
     * hr email.
     */
    @Value("${oa.email.hr}")
    private String hrEmail;

    /**
     * 签到mapper.
     */
    @Autowired
    private CheckinMapper checkinMapper;

    /**
     * 节假日mapper.
     */
    @Autowired
    private HolidaysMapper holidaysMapper;

    /**
     * 工作日mapper.
     */
    @Autowired
    private WorkdayMapper workdayMapper;

    /**
     * 系统常量.
     */
    @Autowired
    private SystemConstants constants;

    /**
     * 人脸模型服务.
     */
    @Autowired
    private FaceModelService faceModelService;

    /**
     * 城市服务.
     */
    @Autowired
    private CityService cityService;

    /**
     * 右键服务.
     */
    @Autowired
    private EmailTask emailTask;

    /**
     * 用户mapper.
     */
    @Autowired
    private UserMapper userMapper;

    @Override
    public Checkin haveCheckIn(final Integer userId, final LocalDateTime start, final LocalDateTime end) {
        final CheckinExample example = new CheckinExample();
        example.createCriteria().andUserIdEqualTo(userId).andDateEqualTo(LocalDate.now())
                        .andCreateTimeBetween(start, end);
        example.setLimit(1);
        final List<Checkin> checkins = checkinMapper.selectByExample(example);
        if (CollUtil.isNotEmpty(checkins)) {
            return checkins.get(0);
        }
        return null;
    }

    @Override
    public String validCanCheckIn(final int userId, final String date) {
        final boolean isHoliday = holidaysMapper.searchTodayIsHoliday() != null;
        final boolean isWorkday = workdayMapper.searchTodayIsWorkday() != null;
        String type = "工作日";
        if (DateUtil.date().isWeekend()) {
            type = "节假日";
        }

        if (isHoliday) {
            type = "节假日";
        } else if (isWorkday) {
            type = "工作日";
        }

        if (type.endsWith("节假日")) {
            return "节假期不需要考勤";
        } else {
            final LocalDateTime now = LocalDateTime.now();
            final String start = DateUtil.today() + " " + constants.getAttendanceStartTime();
            final String end = DateUtil.today() + " " + constants.getAttendanceEndTime();

            final LocalDateTime attendanceStart = LocalDateTimeUtil.parse(start);
            final LocalDateTime attendanceEnd = LocalDateTimeUtil.parse(end);
            if (now.isBefore(attendanceStart)) {
                return "没到上班考勤开始时间";
            } else if (now.isAfter(attendanceEnd)) {
                return "超过了上班考勤结束时间";
            } else {
                final boolean hasCheckin = haveCheckIn(userId, attendanceStart, attendanceEnd) != null;
                return hasCheckin ? "今日已经考勤，不用重复考勤" : "可以考勤";
            }
        }
    }

    @Override
    public Checkin save(final Checkin checkin) {
        if (checkin.getId() == null) {
            checkinMapper.insert(checkin);
        } else {
            checkinMapper.updateByPrimaryKey(checkin);
        }
        return checkin;
    }

    @Override
    public void checkin(final CheckinForm checkinParams) {
        final DateTime now = DateUtil.date();
        final DateTime startTime = DateUtil.parse(DateUtil.today() + " " + constants.getAttendanceStartTime());
        final DateTime endTime = DateUtil.parse(DateUtil.today() + " " + constants.getAttendanceEndTime());
        int status = 1;
        if (now.compareTo(startTime) <= 0) {
            status = 1;
        } else if (now.compareTo(startTime) > 0 && now.compareTo(endTime) < 0) {
            status = 2;
        }
        final Integer userId = checkinParams.getUserId();
        final FaceModel faceModel = faceModelService.searchFaceModel(userId);
        if (faceModel == null) {
            throw new ServiceException("不存在人脸模型");
        } else {
            final String path = checkinParams.getPath();
            final HttpRequest request = HttpUtil.createPost(checkinUrl);
            request.form("photo", FileUtil.file(path), "targetModel", faceModel.getFaceModel());
            final HttpResponse response = request.execute();
            if (response.getStatus() != HttpStatus.HTTP_OK) {
                log.error("人脸识别服务异常");
                throw new ServiceException("人脸识别服务异常");
            }
            final String body = response.body();
            if ("无法识别除人脸".equals(body) || "照片中存在多张人脸".equals(body)) {
                throw new ServiceException(body);
            }
            if ("False".equals(body)) {
                throw new ServiceException("签到无效，非本人签到");
            }
            if ("True".equals(body)) {
                int risk = 1;
                final String city = checkinParams.getCity();
                final String district = checkinParams.getDistrict();
                if (StrUtil.isNotBlank(city) && StrUtil.isNotBlank(district)) {
                    final String code = cityService.searchCodeByName(city);
                    try {
                        final String url = "http://m." + code + ".bendibao.com/news/yqdengji/?qu=" + district;
                        final Document document = Jsoup.connect(url).get();
                        final Elements elements = document.getElementsByClass("list-content");
                        if (CollUtil.isNotEmpty(elements)) {
                            final Element element = elements.get(0);
                            final String result = element.select("p:last-child").text();
                            if ("高风险".equals(result)) {
                                risk = 3;
                                //todo 发送告警邮件
                                final HashMap<String, String> map = userMapper.searchNameAndDept(userId);
                                final String name = map.get("name");
                                String deptName = map.get("dept_name");
                                deptName = deptName != null ? deptName : "";
                                final String address = checkinParams.getAddress();
                                final SimpleMailMessage message = new SimpleMailMessage();
                                message.setTo(hrEmail);
                                message.setSubject("员工" + name + "身处高风险疫情地区警告");
                                message.setText(deptName + "员工" + name + ","
                                        + DateUtil.format(LocalDateTime.now(), "yyyy年MM月dd日"
                                        + "处于" + address + ",属于新冠疫情高风险地区，请及时与员工联系，核实情况!"));
                                emailTask.sendAsync(message);
                            } else if ("中风险".equals(result)) {
                                risk = 2;
                            }
                        }
                    } catch (final Exception e) {
                        throw new ServiceException("获取风险等级异常", e);
                    }
                }
                final Checkin checkin = new Checkin();
                checkin.setUserId(userId);
                checkin.setAddress(checkinParams.getAddress());
                checkin.setCountry(checkinParams.getCountry());
                checkin.setProvince(checkinParams.getProvince());
                checkin.setCity(checkinParams.getCity());
                checkin.setDistrict(checkinParams.getDistrict());
                checkin.setRisk(risk);
                checkin.setStatus(status);
                checkin.setDate(LocalDate.now());
                checkinMapper.insert(checkin);
            }
        }
    }

    @Override
    public HashMap<String, Object> searchTodayCheckin(final int userId) {
        return checkinMapper.searchTodayCheckin(userId);
    }

    @Override
    public long searchCheckinDays(final int userId) {
        return checkinMapper.searchCheckinDays(userId);
    }

    @Override
    public List<Map<String, Object>> searchWeekCheckin(final Map<String, Object> params) {
        final ArrayList<HashMap<String, String>> checkins = checkinMapper.searchWeekCheckin(params);
        final ArrayList<String> holidays = holidaysMapper.searchHolidaysInRange(params);
        final ArrayList<String> workdays = workdayMapper.searchWorkdayInRange(params);
        final DateTime startDate = DateUtil.parseDate(params.get("startDate").toString());
        final DateTime endDate = DateUtil.parseDate(params.get("endDate").toString());
        final DateRange range = DateUtil.range(startDate, endDate, DateField.DAY_OF_MONTH);
        final List<Map<String, Object>> result = new ArrayList<>();
        range.forEach(one -> {
            final String date = one.toString("yyyy-MM-dd");
            String type = "工作日";
            if (one.isWeekend()) {
                type = "节假日";
            }
            if (holidays != null && holidays.contains(date)) {
                type = "节假日";
            } else if (workdays != null && workdays.contains(date)) {
                type = "工作日";
            }
            String status = "";
            if (type.equals("工作日") && DateUtil.compare(one, DateUtil.date()) <= 0) {
                status = "缺勤";
                boolean flag = false;
                for (final HashMap<String, String> checkin : checkins) {
                    if (checkin.containsValue(date)) {
                        status = checkin.get("status");
                        flag = true;
                        break;
                    }
                }

                final DateTime endTime = DateUtil.parse(DateUtil.today() + " " + constants.getAttendanceEndTime());
                final String today = DateUtil.today();
                if (date.equals(today) && DateUtil.date().isBefore(endTime) && !flag) {
                    status = "";
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("date", date);
            map.put("status", status);
            map.put("type", type);
            map.put("day", one.dayOfWeekEnum().toChinese("周"));
            result.add(map);
        });

        return result;
    }

    @Override
    public List<Map<String, Object>> searchMonthCheckin(final Map<String, Object> params) {
        return this.searchWeekCheckin(params);
    }
}
