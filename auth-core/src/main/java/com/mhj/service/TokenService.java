package com.mhj.service;

import com.mhj.entity.dto.UserInfoVO;
import com.mhj.entity.vo.TokenVO;
import com.mhj.entity.dto.TokenDTO;
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
     * @param tokenDTO token
     * @return ResponseObject
     */
    ResponseObject<UserInfoVO> verifyWriteRole(TokenDTO tokenDTO);
}
