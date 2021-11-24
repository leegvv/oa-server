package net.arver.oa.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * sayHello表单.
 * @author leegvv
 */
@ApiModel("sayHello表单")
@Data
public class TestSayHelloForm {

    /**
     * 姓名.
     */
    //@NotBlank
    //@Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,15}$")
    @ApiModelProperty("姓名")
    private String name;
}
