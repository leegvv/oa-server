package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.Permission;
import net.arver.oa.pojo.gen.PermissionExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * PermissionMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission, Integer, PermissionExample> {
}