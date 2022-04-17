package net.arver.oa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.arver.oa.common.util.R;
import net.arver.oa.config.shiro.JwtUtil;
import net.arver.oa.form.SearchMyMeetingListByPageForm;
import net.arver.oa.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MeetingController.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@RestController
@RequestMapping("/meeting")
@Api("会议模块网络接口")
public class MeetingController {

    /**
     * jwt工具.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 会议服务.
     */
    @Autowired
    private MeetingService meetingService;

    /**
     * 查询会议列表分页数据.
     * @param params 参数
     * @param token token
     * @return 分页数据
     */
    @ApiOperation("查询会议列表分页数据")
    @GetMapping("/searchMyMeetingListByPage")
    public R searchMyMeetingListByPage(@Valid final SearchMyMeetingListByPageForm params,
                                       @RequestHeader final String token) {
        final int userId = jwtUtil.getUserId(token);
        final Integer page = params.getPage();
        final Integer length = params.getLength();
        final long start = (page - 1) * length;
        final HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("start", start);
        map.put("length", length);
        final List<? extends Map> list = meetingService.searchMyMeetingListByPage(map);
        return R.ok(list);
    }

}
