package com.mhj.service;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.mhj.utils.JwtUtil;
import com.mhj.entity.dto.UserInfoVO;
import com.mhj.entity.vo.TokenVO;
import com.mhj.entity.dto.TokenDTO;
import com.mhj.web.ResponseObject;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 鉴权服务，此处的校验接口应在网关处拦截过滤校验，并对参数进行验证
 *
 * @author mhj
 * @date 2019/11/15
 */
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Autowired(required = false)
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResponseObject<TokenVO> generatorToken(String userId, String userName) {
        // 将salt token  userInfo 经过jwt加密
        String salt = salt();
        Map<String, Object> userInfoMap = Maps.newHashMap();
        userInfoMap.put("userId", userId);
        userInfoMap.put("userName", userName);
        userInfoMap.put("salt", salt);
        String token = JwtUtil.generateToken(userInfoMap);

        TokenVO tokenVo = new TokenVO();
        tokenVo.setToken(token);
        tokenVo.setSalt(salt);
        tokenVo.setTimestamp(System.currentTimeMillis());
        return ResponseObject.success(tokenVo);
    }

    @Override
    public ResponseObject<UserInfoVO> verifyWriteRole(TokenDTO tokenDTO) {
        String token = tokenDTO.getToken();

        Long timestamp = tokenDTO.getTimestamp();

        String signature = tokenDTO.getSignature();
        String nonce = tokenDTO.getNonce();
        // 1.检验token是否模拟
        final Claims claims = JwtUtil.verifyAndGetClaimsByToken(token);
        if (Objects.isNull(claims)) {
            log.info("恶意请求,token已经过期或者不存在.token:{} ", token);
            return ResponseObject.fail(401, "恶意请求,token已经过期或者不存在!");
        }
        String userId = claims.get("userId").toString();
        // 2.时间与服务器时间相差60秒,就是恶意请求
        Instant instant = Instant.ofEpochMilli(timestamp);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(dateTime.plusSeconds(900000))) {
            log.error("恶意请求,请求时间过期.token:{}, userId:{}, time:{}, 当前时间:{}", token, userId, dateTime, now);
            return ResponseObject.unauthorized("恶意请求");
        }

        // 3.校验nonce是否被重放
        String nonceCache = (String) redisTemplate.opsForValue().get("auth_nonce" + nonce);
        if (!Strings.isNullOrEmpty(nonceCache)) {
            log.error("恶意请求,请求被重放.token:{}, userId:{}, nonce:{}", token, userId, nonce);
            return ResponseObject.unauthorized("恶意请求");
        }
        // 存放20分钟
        redisTemplate.opsForValue().set("auth_nonce" + nonce, "AUTH_NONCE_" + token + "_" + nonce, 20, TimeUnit.MINUTES);

        HashCode code = Hashing.sha256().hashString(timestamp + "#" + nonce + claims.get("salt"), StandardCharsets.UTF_8);
        if (!signature.equals(code.toString())) {
            log.error("恶意请求,签名被伪造.token:{}, userId:{}, nonce:{}, signature:{}", token, userId, nonce, signature);
            return ResponseObject.unauthorized("恶意请求");
        }
        // 4.参数的校验 未做

        return ResponseObject.success(UserInfoVO.builder().userId(userId).userName(claims.get("userName").toString()).build());
    }

    private static String salt() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return Base64.getEncoder().encodeToString(uuid.getBytes(StandardCharsets.UTF_8));
    }
}
