package net.arver.oa.pojo.gen;

import java.io.Serializable;

/**
 * tb_face_model.
 * 由MybatisGenerator自动生成请勿修改
 */
public class BaseFaceModel implements Serializable {
    /**
     * 主键值.
     */
    private Integer id;

    /**
     * 用户ID.
     */
    private Integer userId;

    /**
     * 用户人脸模型.
     */
    private String faceModel;

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

    public String getFaceModel() {
        return faceModel;
    }

    public void setFaceModel(String faceModel) {
        this.faceModel = faceModel;
    }
}