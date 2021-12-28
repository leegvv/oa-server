package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.Action;
import net.arver.oa.pojo.gen.ActionExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * ActionMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface ActionMapper extends BaseMapper<Action, Integer, ActionExample> {
}