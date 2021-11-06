package net.arver.oa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.arver.oa.common.util.R;
import net.arver.oa.form.TestSayHelloForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
     * @param form 表单
     * @return 测试数据
     */
    @PostMapping("/sayHello")
    @ApiOperation("简单的测试方法")
    public R<String> sayHello(@Valid @RequestBody final TestSayHelloForm form) {
        return R.ok("Hello " + form.getName());
    }
}
