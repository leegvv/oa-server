package net.arver.oa.service;

import net.arver.oa.pojo.User;

import java.util.Set;

/**
 * UserService.
 * @author leegvv
 */
public interface UserService {

    /**
     * 用户注册.
     * @param registerCode 注册码
     * @param code 授权code
     * @param nickname 昵称
     * @param photo 头像
     * @return 用户id
     */
    int registerUser(String registerCode, String code, String nickname, String photo);

    /**
     * 获取用户权限.
     * @param userId 用户id
     * @return 用户权限
     */
    Set<String> searchUserPermissions(int userId);

    /**
     * 登陆.
     * @param code code
     * @return 用户id
     */
    Integer login(String code);

    /**
     * 根据id查询用户.
     * @param userId 用户id
     * @return 用户信息
     */
    User searchById(int userId);
}
