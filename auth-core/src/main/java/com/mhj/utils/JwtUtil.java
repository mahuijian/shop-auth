package com.mhj.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author mahuijian
 * @date 2019-11-15 10:37
 */
@Slf4j
public class JwtUtil {

    /**
     * 存放token的请求头对应的key的名字
     */
    private static String headerKey = "token";
    /**
     * 加密的secret
     */
    private static String secret = "auth_token_secret";
    /**
     * 过期时间，单位为秒
     */
    private static long expire = 1800L;

    static {
        //  上面变量的值应该从配置文件中读取,方便测试这里就不从配置文件中读取
        // 利用配置文件中的值覆盖静态变量初始化的值
    }

    /**
     * 生成jwt token
     */
    public static String generateToken(Map<String, Object> userInfoMap) {
        if (Objects.isNull(userInfoMap)) {
            userInfoMap = new HashMap<>();
        }
        //  过期时间
        Date expireDate = new Date(System.currentTimeMillis() + expire * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")   // 设置头部信息
                .setClaims(userInfoMap)               // 装入自定义的用户信息
                .setExpiration(expireDate)            // token过期时间
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", "12");
        map.put("price", "12");
        map.put("orderId", "123123123");
        System.out.println(generateToken(map));
        System.out.println(verifyAndGetClaimsByToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJvcmRlcklkIjoiMTIzMTIzMTIzIiwicHJpY2UiOiIxMiIsImV4cCI6MTU3Mzc4Njg2NCwidXNlcklkIjoiMTIifQ.ZCFNXaHp9RZILACG4UZC_0KPMPQ_MGHMiusDaJJ-jpWsTGW4rbQoGhUH48PiEPz0jXwpUcMW3aTy8VQJW8FrYA"));
        System.out.println(verifyAndGetClaimsByToken(generateToken(map)));
    }

    /**
     * 校验token并解析token
     *
     * @param token 54
     * @return Claims：它继承了Map,而且里面存放了生成token时放入的用户信息
     */
    public static Claims verifyAndGetClaimsByToken(String token) {
        try {
            /* 如果过期或者是被篡改，则会抛异常.
                注意点：只有在生成token设置了过期时间(setExpiration(expireDate))才会校验是否过期，
                可以参考源码io.jsonwebtoken.impl.DefaultJwtParser的299行。
                拓展：利用不设置过期时间就不校验token是否过期的这一特性，我们不设置Expiration;
                      而采用自定义的字段来存放过期时间放在Claims（可以简单的理解为map）中;
                      通过token获取到Claims后自己写代码校验是否过期。
                      通过这思路，可以去实现对过期token的手动刷新
            */
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.debug("verify token error:[{}] ", e.getMessage());
            return null;
        }
    }

    public static String getHeaderKey() {
        return headerKey;
    }

}
