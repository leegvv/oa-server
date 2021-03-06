package net.arver.oa.pojo.gen;

import java.io.Serializable;

/**
 * 模块资源表.
 * 由MybatisGenerator自动生成请勿修改
 */
public class BaseModule implements Serializable {
    /**
     * 主键.
     */
    private Integer id;

    /**
     * 模块编号.
     */
    private String moduleCode;

    /**
     * 模块名称.
     */
    private String moduleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}