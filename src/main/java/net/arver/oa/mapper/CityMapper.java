package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.City;
import net.arver.oa.pojo.gen.CityExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * CityMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface CityMapper extends BaseMapper<City, Integer, CityExample> {
}