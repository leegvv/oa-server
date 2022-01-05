package net.arver.oa.controller;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.arver.oa.common.util.R;
import net.arver.oa.config.shiro.JwtUtil;
import net.arver.oa.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
