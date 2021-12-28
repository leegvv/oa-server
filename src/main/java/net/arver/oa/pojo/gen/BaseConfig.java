package net.arver.oa.pojo.gen;

import java.io.Serializable;

/**
 * sys_config.
 * 由MybatisGenerator自动生成请勿修改
 */
public class BaseConfig implements Serializable {
    /**
     * 主键.
     */
    private Integer id;

    /**
     * 参数名.
     */
    private String paramKey;

    /**
     * 参数值.
     */
    private String paramValue;

    /**
     * 状态.
     */
    private Integer status;

    /**
     * 备注.
     */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}