package net.arver.oa.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 删除消息ref参数.
 * @author leegvv
 */
@ApiModel
@Data
public class DeleteMessageRefByIdForm {

    /**
     * id.
     */
    @NotBlank
    private String id;
}
