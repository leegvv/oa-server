package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.User;
import net.arver.oa.pojo.gen.UserExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * UserMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface UserMapper extends BaseMapper<User, Integer, UserExample> {

    /**
     * 查询用户权限.
     * @param userId 用户id
     * @return 权限集合
     */
    Set<String> getUserPermissions(int userId);
}