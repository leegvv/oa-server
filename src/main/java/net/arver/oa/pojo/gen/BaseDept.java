package net.arver.oa.pojo.gen;

import java.io.Serializable;

/**
 * tb_dept.
 * 由MybatisGenerator自动生成请勿修改
 */
public class BaseDept implements Serializable {
    /**
     * 主键.
     */
    private Integer id;

    /**
     * 部门名称.
     */
    private String deptName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}