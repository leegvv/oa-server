package net.arver.oa.pojo.gen;

import java.io.Serializable;

/**
 * 疫情城市列表.
 * 由MybatisGenerator自动生成请勿修改
 */
public class BaseCity implements Serializable {
    /**
     * 主键.
     */
    private Integer id;

    /**
     * 城市名称.
     */
    private String city;

    /**
     * 拼音简称.
     */
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}