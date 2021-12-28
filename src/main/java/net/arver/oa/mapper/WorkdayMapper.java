package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.Workday;
import net.arver.oa.pojo.gen.WorkdayExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * WorkdayMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface WorkdayMapper extends BaseMapper<Workday, Integer, WorkdayExample> {
}