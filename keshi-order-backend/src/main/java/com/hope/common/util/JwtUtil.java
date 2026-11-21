package com.hope.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire-ms}")
    private Long expireMs;

    private Algorithm getAlgorithm(){
        return Algorithm.HMAC256(secret);
    }

    /**
     * 生成token
     * @param userId
     * @param username
     * @param roleCodeList
     * @return
     */
    public String createToken(Long userId, String username, List<String> roleCodeList) {
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + expireMs);
        return JWT.create()
                .withSubject(username)
                .withClaim("userId", userId)
                .withClaim("roleCodeList", roleCodeList)
                .withIssuedAt(now)
                .withExpiresAt(expireTime)
                .sign(getAlgorithm());
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public DecodedJWT parseToken(String token) {
        return JWT.require(getAlgorithm())
                .build()
                .verify(token);
    }

    public String getUsername(String token) {
        return parseToken(token).getSubject();
    }

    public List<String> getRoleCodeList(String token) {
        return parseToken(token).getClaim("roleCodeList").asList(String.class);
    }

    public Long getUserId(String token) {
        Claim claim = parseToken(token).getClaim("userId");
        return claim.isNull() ? null : claim.asLong();
    }

    public boolean isExpired(String token) {
        try {
            Date exp = parseToken(token).getExpiresAt();
            return exp.before(new Date());
        }catch (Exception e){
            return true;
        }
    }

    /**
     *  校验token是否合法、未过期
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JWTVerificationException e) {
            // 签名错误、过期、格式错误都会进这里
            return false;
        }
    }
}
