package com.mhj.entity.vo;

import lombok.Data;

/**
 * @author mhj
 * @date 2019/11/15
 */
@Data
public class TokenVO {

    private String token;
    private String salt;
    private long timestamp;
}
