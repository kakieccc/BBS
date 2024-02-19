package com.kakie.bbs_backend.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 分区修改请求体
 */
@Data
public class BarUpdateRequest implements Serializable {

    private static final long serialVersionUID = -6043915331807008592L;

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
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;

    /**
     * 密码
     */
    private String password;
}