package com.mhj.controller;

import com.mhj.entity.dto.UserInfoVO;
import com.mhj.entity.vo.TokenVO;
import com.mhj.entity.dto.TokenDTO;
import com.mhj.service.TokenService;
import com.mhj.web.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("/verify")
    public ResponseObject<UserInfoVO> verifyWriteRole(@RequestBody @Valid TokenDTO tokenDTO) {
        return tokenService.verifyWriteRole(tokenDTO);
    }

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }
}
