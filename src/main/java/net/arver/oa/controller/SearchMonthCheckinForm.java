package net.arver.oa.controller;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Range;

/**
 * 查询月考勤参数.
 * @author leegvv
 */
@Data
@ApiModel
public class SearchMonthCheckinForm {

    /**
     * 年.
     */
    @NonNull
    @Range(min=2000, max = 3000)
    private Integer year;

    /**
     * 月.
     */
    @NonNull
    @Range(min = 1, max = 12)
    private Integer month;
}
