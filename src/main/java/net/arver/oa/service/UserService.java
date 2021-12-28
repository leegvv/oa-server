package net.arver.oa.service;

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
    Set<String> getUserPermissions(int userId);
}
