package net.arver.oa.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 更新纬度信息表单参数.
 * @author leegvv
 */
@ApiModel
@Data
public class UpdateUnreadMessageForm {

    /**
     * 主键.
     */
    @NotBlank
    private String id;
}
