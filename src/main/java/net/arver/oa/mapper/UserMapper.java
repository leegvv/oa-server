package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.User;
import net.arver.oa.pojo.gen.UserExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Set;

/**
 * UserMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface UserMapper extends BaseMapper<User, Integer, UserExample> {

    /**
     * 根据openId查询用户id.
     * @param openId openId
     * @return 用户id
     */
    Integer searchIdByOpenId(String openId);

    /**
     * 查询用户权限.
     * @param userId 用户id
     * @return 权限集合
     */
    Set<String> searchUserPermissions(int userId);

    /**
     * 查询员工的姓名和部门名称.
     * @param userId 员工id
     * @return 姓名和部门
     */
    HashMap<String, String> searchNameAndDept(Integer userId);

    /**
     * 查询用户信息.
     * @param userId 用户id
     * @return result
     */
    HashMap<String, Object> searchUserSummary(Integer userId);

}