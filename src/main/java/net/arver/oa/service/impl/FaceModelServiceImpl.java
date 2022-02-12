package net.arver.oa.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import net.arver.oa.exception.ServiceException;
import net.arver.oa.mapper.FaceModelMapper;
import net.arver.oa.pojo.FaceModel;
import net.arver.oa.pojo.gen.FaceModelExample;
import net.arver.oa.service.FaceModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 人脸模型服务实现.
 * @author leegvv
 */
@Service
public class FaceModelServiceImpl implements FaceModelService {

    /**
     * 创建人脸模型url.
     */
    @Value("${oa.face.createFaceModelUrl}")
    private String createFaceModelUrl;

    /**
     * 人脸模型mapper.
     */
    @Autowired
    private FaceModelMapper faceModelMapper;

    @Override
    public FaceModel searchFaceModel(final int userId) {
        final FaceModelExample example = new FaceModelExample();
        example.createCriteria().andUserIdEqualTo(userId);
        final List<FaceModel> faceModels = faceModelMapper.selectByExample(example);
        if (CollUtil.isNotEmpty(faceModels)) {
            return faceModels.get(0);
        }
        return null;
    }

    @Override
    public FaceModel save(final FaceModel faceModel) {
        if (faceModel.getId() == null) {
            faceModelMapper.insert(faceModel);
        } else {
            faceModelMapper.updateByPrimaryKey(faceModel);
        }
        return faceModel;
    }

    @Override
    public int deleteByUserId(final int userId) {
        final FaceModelExample example = new FaceModelExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return faceModelMapper.deleteByExample(example);
    }

    @Override
    public void createFaceModel(final int userId, final String path) {
        final HttpRequest request = HttpUtil.createPost(createFaceModelUrl);
        request.form("photo", FileUtil.file(path));
        final HttpResponse response = request.execute();
        final String body = response.body();
        if ("无法识别出人脸".equals(body) || "照片中存在多张人脸".equals(body)) {
            throw new ServiceException(body);
        } else {
            final FaceModel faceModel = new FaceModel();
            faceModel.setUserId(userId);
            faceModel.setFaceModel(body);
            faceModelMapper.insert(faceModel);
        }
    }
}
