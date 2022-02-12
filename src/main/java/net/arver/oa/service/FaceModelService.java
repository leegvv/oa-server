package net.arver.oa.service;

import net.arver.oa.pojo.FaceModel;

/**
 * 人脸模型service.
 * @author leegvv
 */
public interface FaceModelService {

    /**
     * 根据用户id查询人脸模型.
     * @param userId 用户id
     * @return 人脸模型
     */
    FaceModel searchFaceModel(int userId);

    /**
     * 保存.
     * @param faceModel 人脸模型
     * @return 人脸模型
     */
    FaceModel save(FaceModel faceModel);

    /**
     * 根据用户id删除.
     * @param userId 用户id
     * @return 删除的记录数
     */
    int deleteByUserId(int userId);

    /**
     * 创建人脸模型.
     * @param userId 用户id
     * @param path 路径
     */
    void createFaceModel(int userId, String path);

}
