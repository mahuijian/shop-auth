package com.mhj.entity.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author mhj
 * @date 2019/11/15
 */
@Data
public class TokenDTO {

    /**
     * token
     */
    @NotBlank(message = "token不能为空")
    private String token;

    /**
     * 随机数
     */
    @NotBlank(message = "随机数不能位空")
    private String nonce;

    /**
     * 签名
     */
    @NotBlank(message = "签名不能位空")
    private String signature;

    /**
     * 时间戳
     */
    @Min(value = 1, message = "时间戳必须大于1")
    @NotNull(message = "时间戳不能为空")
    private Long timestamp;
}
