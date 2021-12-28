package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.Holidays;
import net.arver.oa.pojo.gen.HolidaysExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * HolidaysMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface HolidaysMapper extends BaseMapper<Holidays, Integer, HolidaysExample> {
}