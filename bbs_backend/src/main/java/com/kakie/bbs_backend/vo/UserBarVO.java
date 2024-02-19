package com.kakie.bbs_backend.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分区和用户信息封装类（脱敏）
 */
@Data
public class UserBarVO implements Serializable {

    private static final long serialVersionUID = 163478861968488713L;
    /**
     * id
     */
    private Long id;

    /**
     * 分区名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人用户信息
     */
    private UserVO createUser;

    /**
     * 是否已加入分区
     */
    private boolean hasJoin = false;
}