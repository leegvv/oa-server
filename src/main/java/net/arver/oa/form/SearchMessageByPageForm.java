package net.arver.oa.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页参数查询条件.
 * @author leegvv
 */
@ApiModel
@Data
public class SearchMessageByPageForm {

    /**
     * 页号.
     */
    @NotNull
    @Min(1)
    private Integer page;

    /**
     * 页大小.
     */
    @NotNull
    @Range(min = 1, max = 40)
    private Integer length;
}
