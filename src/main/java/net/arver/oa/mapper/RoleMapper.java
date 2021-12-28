package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.Role;
import net.arver.oa.pojo.gen.RoleExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * RoleMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role, Integer, RoleExample> {
}