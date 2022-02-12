package net.arver.oa.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 签到方法参数.
 * @author leegvv
 */
@Data
@ApiModel
public class CheckinForm {

    /**
     * 用户id.
     */
    private Integer userId;

    /**
     * 图片路径.
     */
    private String path;

    /**
     * 地址.
     */
    private String address;

    /**
     * 国家.
     */
    private String country;

    /**
     * 省份.
     */
    private String province;

    /**
     * 城市.
     */
    private String city;

    /**
     * 街道.
     */
    private String district;
}
