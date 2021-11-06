package net.arver.oa.dao;

import java.util.List;
import net.arver.oa.pojo.TbCheckin;
import net.arver.oa.pojo.TbCheckinExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TbCheckinDao {
    long countByExample(TbCheckinExample example);

    int deleteByExample(TbCheckinExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbCheckin record);

    int insertSelective(TbCheckin record);

    List<TbCheckin> selectByExample(TbCheckinExample example);

    TbCheckin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbCheckin record, @Param("example") TbCheckinExample example);

    int updateByExample(@Param("record") TbCheckin record, @Param("example") TbCheckinExample example);

    int updateByPrimaryKeySelective(TbCheckin record);

    int updateByPrimaryKey(TbCheckin record);
}