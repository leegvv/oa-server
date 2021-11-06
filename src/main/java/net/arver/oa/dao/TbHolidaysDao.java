package net.arver.oa.dao;

import java.util.List;
import net.arver.oa.pojo.TbHolidays;
import net.arver.oa.pojo.TbHolidaysExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TbHolidaysDao {
    long countByExample(TbHolidaysExample example);

    int deleteByExample(TbHolidaysExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbHolidays record);

    int insertSelective(TbHolidays record);

    List<TbHolidays> selectByExample(TbHolidaysExample example);

    TbHolidays selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbHolidays record, @Param("example") TbHolidaysExample example);

    int updateByExample(@Param("record") TbHolidays record, @Param("example") TbHolidaysExample example);

    int updateByPrimaryKeySelective(TbHolidays record);

    int updateByPrimaryKey(TbHolidays record);
}