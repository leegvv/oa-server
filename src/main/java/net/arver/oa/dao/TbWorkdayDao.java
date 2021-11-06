package net.arver.oa.dao;

import java.util.List;
import net.arver.oa.pojo.TbWorkday;
import net.arver.oa.pojo.TbWorkdayExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TbWorkdayDao {
    long countByExample(TbWorkdayExample example);

    int deleteByExample(TbWorkdayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbWorkday record);

    int insertSelective(TbWorkday record);

    List<TbWorkday> selectByExample(TbWorkdayExample example);

    TbWorkday selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbWorkday record, @Param("example") TbWorkdayExample example);

    int updateByExample(@Param("record") TbWorkday record, @Param("example") TbWorkdayExample example);

    int updateByPrimaryKeySelective(TbWorkday record);

    int updateByPrimaryKey(TbWorkday record);
}