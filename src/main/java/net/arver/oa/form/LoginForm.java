package net.arver.oa.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录表单.
 * @author leegvv
 *
 */
@ApiModel
@Data
public class LoginForm {

    /**
     * 授权码.
     */
    @NotBlank(message = "临时授权码不能为空")
    private String code;
}
