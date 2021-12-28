package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.Config;
import net.arver.oa.pojo.gen.ConfigExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * ConfigMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface ConfigMapper extends BaseMapper<Config, Integer, ConfigExample> {
}