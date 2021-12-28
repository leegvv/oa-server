package net.arver.oa.pojo.gen;

import java.io.Serializable;

/**
 * tb_permission.
 * 由MybatisGenerator自动生成请勿修改
 */
public class BasePermission implements Serializable {
    /**
     * 主键.
     */
    private Integer id;

    /**
     * 权限.
     */
    private String permissionName;

    /**
     * 模块ID.
     */
    private Integer moduleId;

    /**
     * 行为ID.
     */
    private Integer actionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }
}