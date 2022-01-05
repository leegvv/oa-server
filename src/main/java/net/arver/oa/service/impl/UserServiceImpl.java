package net.arver.oa.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import net.arver.oa.exception.ServiceException;
import net.arver.oa.mapper.UserMapper;
import net.arver.oa.pojo.User;
import net.arver.oa.pojo.gen.UserExample;
import net.arver.oa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UserServiceImpl.
 * @author leegvv
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * appId.
     */
    @Value("${wx.app-id}")
    private String appId;

    /**
     * appSecret.
     */
    @Value("${wx.app-secret}")
    private String appSecret;

    /**
     * 用户dao.
     */
    @Autowired
    private UserMapper userMapper;

    @Override
    public int registerUser(final String registerCode, final String code, final String nickname, final String photo) {
        if ("000000".equals(registerCode)) {
            final boolean bool = haveRootUser();
            if (!bool) {
                // 把当前用户绑定到ROOT账户
                final String openId = getOpenId(code);
                final User user = new User();
                user.setOpenId(openId);
                user.setNickname(nickname);
                user.setPhoto(photo);
                user.setRole("[0]");
                user.setStatus(1);
                user.setCreateTime(LocalDateTime.now());
                user.setRoot(Boolean.TRUE);
                userMapper.insert(user);
                final Integer id = searchIdByOpenId(openId);
                return id;
            } else {
                throw new ServiceException("无法绑定超级管理员账号");
            }
        } else {
            return 0;
        }
    }

    @Override
    public Set<String> searchUserPermissions(final int userId) {
        return userMapper.searchUserPermissions(userId);
    }

    @Override
    public Integer login(final String code) {
        final String openId = getOpenId(code);
        final Integer id = userMapper.searchIdByOpenId(openId);
        if (id == null) {
            throw new ServiceException("账户不存在");
        }
        // TODO 从消息队列中接收消息，转移到消息表
        return id;
    }

    @Override
    public User searchById(final int userId) {
        final User user = userMapper.selectByPrimaryKey(userId);
        if (user != null && user.getStatus() == 1) {
            return user;
        }
        return null;
    }

    /**
     * 获取openId.
     * @param code code
     * @return openId
     */
    private String getOpenId(final String code) {
        final String url = "https://api.weixin.qq.com/sns/jscode2session";
        final Map<String, Object> map = new HashMap<>();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        final String response = HttpUtil.post(url, map);
        final JSONObject jsonObject = JSONUtil.parseObj(response);
        final String openId = jsonObject.getStr("openid");
        if (openId == null || openId.length() == 0) {
            throw new RuntimeException("临时登陆凭证错误");
        }
        return openId;
    }

    /**
     * 是否有管理员.
     * @return 是否有管理员
     */
    private boolean haveRootUser() {
        final UserExample example = new UserExample();
        example.createCriteria().andRootEqualTo(true);
        final long count = userMapper.countByExample(example);
        return count > 0;
    }

    /**
     * 根据openId查询用户id.
     * @param openId openId
     * @return 用户id
     */
    private Integer searchIdByOpenId(final String openId) {
        final UserExample example = new UserExample();
        example.createCriteria().andOpenIdEqualTo(openId).andStatusEqualTo(1);
        final List<User> users = userMapper.selectByExample(example);
        if (CollUtil.isEmpty(users)) {
            return null;
        }
        return users.get(0).getId();
    }
}
