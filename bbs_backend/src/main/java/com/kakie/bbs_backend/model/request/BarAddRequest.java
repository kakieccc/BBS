package com.kakie.bbs_backend.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class BarAddRequest {

    private static final long serialVersionUID = -4162304142710323660L;

    /**
     * 分区名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;

    /**
     * 密码
     */
    private String password;
}