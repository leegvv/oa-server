package net.arver.oa.dao;

import java.util.List;
import net.arver.oa.pojo.TbAction;
import net.arver.oa.pojo.TbActionExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TbActionDao {
    long countByExample(TbActionExample example);

    int deleteByExample(TbActionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbAction record);

    int insertSelective(TbAction record);

    List<TbAction> selectByExample(TbActionExample example);

    TbAction selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbAction record, @Param("example") TbActionExample example);

    int updateByExample(@Param("record") TbAction record, @Param("example") TbActionExample example);

    int updateByPrimaryKeySelective(TbAction record);

    int updateByPrimaryKey(TbAction record);
}