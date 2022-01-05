package net.arver.oa.config.shiro;

import net.arver.oa.pojo.User;
import net.arver.oa.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * OAuth2Realm.
 * @author leegvv
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    /**
     * jwt工具类.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户服务.
     */
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(final AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principalCollection) {
        final User user = (User) principalCollection.getPrimaryPrincipal();
        final Integer userId = user.getId();
        final Set<String> permissions = userService.searchUserPermissions(userId);
        final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token)
            throws AuthenticationException {
        final String accessToken = (String) token.getPrincipal();
        final int userId = jwtUtil.getUserId(accessToken);
        //查询用户信息
        final User user = userService.searchById(userId);
        if (user == null) {
            throw new LockedAccountException("账号已被锁定，请联系管理员");
        }
        final SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}
