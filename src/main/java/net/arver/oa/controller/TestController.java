package net.arver.oa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.arver.oa.common.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试类.
 * @author leegvv
 */
@RestController
@RequestMapping("/test")
@Api("测试web接口")
public class TestController {

    /**
     * 测试方法.
     * @return 测试数据
     */
    @GetMapping("/sayHello")
    @ApiOperation("简单的测试方法")
    public R sayHello() {
        return R.ok("Hello World!");
    }
}
