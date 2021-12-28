package net.arver.oa.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * RegisterForm.
 * @author leegvv
 */
@Data
@ApiModel
public class RegisterForm {

    /**
     * 注册码.
     */
    @NotBlank(message = "注册码不能为空")
    @Pattern(regexp = "^[0-9]{6}$", message = "注册码必须是6位数字")
    private String registerCode;

    /**
     * 微信临时授权码.
     */
    @NotBlank(message = "微信临时授权码不能为空")
    private String code;

    /**
     * 昵称.
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 头像.
     */
    @NotBlank(message = "头像不能为空")
    private String photo;

}
