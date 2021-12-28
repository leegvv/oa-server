package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.Module;
import net.arver.oa.pojo.gen.ModuleExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * ModuleMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface ModuleMapper extends BaseMapper<Module, Integer, ModuleExample> {
}