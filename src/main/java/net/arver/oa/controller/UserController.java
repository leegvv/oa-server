package net.arver.oa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.arver.oa.common.util.R;
import net.arver.oa.config.shiro.JwtUtil;
import net.arver.oa.form.LoginForm;
import net.arver.oa.form.RegisterForm;
import net.arver.oa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * UserController.
 * @author leegvv
 */
@RestController
@RequestMapping("/user")
@Api("用户模块Web接口")
public class UserController {

    /**
     * 用户服务.
     */
    @Autowired
    private UserService userService;

    /**
     * jwt工具类.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * redis.
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 缓存过期时间.
     */
    @Value("${oa.jwt.cache-expire}")
    private int cacheExpire;

    /**
     * 注册用户.
     * @param form 注册表单
     * @return 注册信息
     */
    @PostMapping("/register")
    @ApiOperation("注册用户")
    public R register(@Valid @RequestBody final RegisterForm form) {
        final int userId = userService.registerUser(form.getRegisterCode(), form.getCode(),
                form.getNickname(), form.getPhoto());
        final String token = jwtUtil.createToken(userId);
        final Set<String> permissions = userService.searchUserPermissions(userId);
        saveCacheToken(token, userId);
        return R.ok(userId).setAdditionalProperties("token", token).setAdditionalProperties("permission", permissions);
    }

    /**
     * 登陆.
     * @param form 授权码
     * @return 登陆状态
     */
    @PostMapping("/login")
    @ApiOperation("登陆系统")
    public R login(@Valid @RequestBody final LoginForm form) {
        final Integer id = userService.login(form.getCode());
        final String token = jwtUtil.createToken(id);
        final Set<String> userPermissions = userService.searchUserPermissions(id);
        saveCacheToken(token, id);
        return R.ok("登陆成功")
                .setAdditionalProperties("token", token)
                .setAdditionalProperties("permission", userPermissions);
    }

    /**
     * 查询用户摘要信息.
     * @param token token
     * @return 用户摘要信息
     */
    @GetMapping("/searchUserSummary")
    @ApiOperation("查询用户摘要信息")
    public R searchUserSummary(@RequestHeader("token")final String token) {
        final int userId = jwtUtil.getUserId(token);
        final HashMap<String, Object> result = userService.searchUserSummary(userId);
        return R.ok(result);
    }

    /**
     * 将token保存到redis.
     * @param token token
     * @param userId 用户id
     */
    private void saveCacheToken(final String token, final int userId) {
        redisTemplate.opsForValue().set(token, userId + "", cacheExpire, TimeUnit.DAYS);
    }

}
