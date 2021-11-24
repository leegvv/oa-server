package net.arver.oa.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * OAuth2Token.
 * @author leegvv
 */
public class OAuth2Token implements AuthenticationToken {

    /**
     * 构造函数.
     * @param token token
     */
    public OAuth2Token(final String token) {
        this.token = token;
    }

    /**
     * token.
     */
    private String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
