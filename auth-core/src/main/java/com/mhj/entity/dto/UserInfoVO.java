package com.mhj.entity.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author mhj
 * @date 2019/11/15
 */
@Data
@Builder
public class UserInfoVO {

    private String userId;

    private String userName;
}
