package net.arver.oa.config.shiro;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JwtUtil.
 * @author leegvv
 */
@Component
@Slf4j
public class JwtUtil {

    /**
     * 秘钥.
     */
    @Value("${oa.jwt.secret}")
    private String secret;

    /**
     * 过期时间.
     */
    @Value("${oa.jwt.expire}")
    private int expire;

    /**
     * 创建token.
     * @param userId 用户id
     * @return token
     */
    public String createToken(final int userId) {
        final DateTime now = DateUtil.date();
        final DateTime expireAt = now.offset(DateField.MINUTE, expire);
        final Algorithm algorithm = Algorithm.HMAC256(secret);
        final JWTCreator.Builder builder = JWT.create();
        final String token = builder.withClaim("userId", userId).withExpiresAt(expireAt).sign(algorithm);
        return token;
    }

    /**
     * 获取用户id.
     * @param token token
     * @return 用户id
     */
    public int getUserId(final String token) {
        final DecodedJWT jwt = JWT.decode(token);
        final Integer userId = jwt.getClaim("userId").asInt();
        return userId;
    }

    /**
     * 校验token.
     * @param token token
     */
    public void verifierToken(final String token) {
        final Algorithm algorithm = Algorithm.HMAC256(secret);
        final JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(token);
    }
}
