package com.mhj.controller;

import com.google.common.base.Strings;
import com.mhj.entity.vo.TokenVO;
import com.mhj.service.TokenService;
import com.mhj.web.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author mhj
 * @date 2019/11/15
 */
@RestController
@RequestMapping("/token/v1")
public class TokenController {

    private TokenService tokenService;

    @GetMapping("/generator/{userId}")
    public ResponseObject<TokenVO> generator(@PathVariable("userId") String userId, @RequestParam(value = "userName") String userName) {
        return tokenService.generatorToken(userId, userName);
    }

    @GetMapping("/verify/{token}/{nonce}/{timestamp}/{signature}")
    public ResponseObject verifyWriteRole(@PathVariable("token") String token,
                                                    @PathVariable("nonce") String nonce, @PathVariable long timestamp,
                                                    @PathVariable("signature") String signature) {
        checkArgument(!Strings.isNullOrEmpty(token), "token不能为空");
        checkArgument(!Strings.isNullOrEmpty(nonce), "nonce不能为空");
        checkArgument(!Strings.isNullOrEmpty(signature), "signature不能为空");
        checkArgument(timestamp > 0, "timestamp必须大于0");
        return tokenService.verifyWriteRole(token, timestamp, nonce, signature);
    }

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }
}
