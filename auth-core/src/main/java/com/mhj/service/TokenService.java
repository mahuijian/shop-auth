package com.mhj.service;

import com.mhj.entity.vo.TokenVO;
import com.mhj.web.ResponseObject;

/**
 * @author mhj
 * @date 2019/11/15
 */
public interface TokenService {


    /**
     * 生成token等信息
     *
     * @param userId userId
     * @return Token
     */
    ResponseObject<TokenVO> generatorToken(String userId, String userName);

    /**
     * 校验请求是否合理
     *
     * @param token     token
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param signature 签名
     * @return ResponseObject
     */
    ResponseObject verifyWriteRole(String token, long timestamp, String nonce, String signature);
}
