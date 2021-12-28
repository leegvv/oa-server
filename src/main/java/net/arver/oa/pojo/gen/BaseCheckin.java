package net.arver.oa.pojo.gen;

import java.io.Serializable;

/**
 * 签到表.
 * 由MybatisGenerator自动生成请勿修改
 */
public class BaseCheckin implements Serializable {
    /**
     * 主键.
     */
    private Integer id;

    /**
     * 用户ID.
     */
    private Integer userId;

    /**
     * 签到地址.
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
     * 区划.
     */
    private String district;

    /**
     * 考勤结果.
     */
    private Integer status;

    /**
     * 风险等级.
     */
    private Integer risk;

    /**
     * 签到日期.
     */
    private java.time.LocalDate date;

    /**
     * 签到时间.
     */
    private java.time.LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRisk() {
        return risk;
    }

    public void setRisk(Integer risk) {
        this.risk = risk;
    }

    public java.time.LocalDate getDate() {
        return date;
    }

    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }

    public java.time.LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.time.LocalDateTime createTime) {
        this.createTime = createTime;
    }
}