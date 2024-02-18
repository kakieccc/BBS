package com.kakie.bbs_backend.dto;

import com.kakie.bbs_backend.common.PageRequest;
import lombok.Data;

@Data
public class BarDTO extends PageRequest {
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
}
