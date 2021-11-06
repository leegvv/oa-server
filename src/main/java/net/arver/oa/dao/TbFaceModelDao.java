package net.arver.oa.dao;

import java.util.List;
import net.arver.oa.pojo.TbFaceModel;
import net.arver.oa.pojo.TbFaceModelExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TbFaceModelDao {
    long countByExample(TbFaceModelExample example);

    int deleteByExample(TbFaceModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbFaceModel record);

    int insertSelective(TbFaceModel record);

    List<TbFaceModel> selectByExample(TbFaceModelExample example);

    TbFaceModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbFaceModel record, @Param("example") TbFaceModelExample example);

    int updateByExample(@Param("record") TbFaceModel record, @Param("example") TbFaceModelExample example);

    int updateByPrimaryKeySelective(TbFaceModel record);

    int updateByPrimaryKey(TbFaceModel record);
}