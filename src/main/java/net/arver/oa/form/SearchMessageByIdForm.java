package net.arver.oa.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 根据id查询表单.
 * @author leegvv
 */
@ApiModel
@Data
public class SearchMessageByIdForm {

    /**
     * id.
     */
    @NotBlank
    private String id;
}
