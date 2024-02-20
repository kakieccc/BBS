package com.kakie.bbs_backend.model.dto;

import com.kakie.bbs_backend.common.PageRequest;
import lombok.Data;

import java.util.List;

@Data
public class BarDTO extends PageRequest {
    /**
     * id
     */
    private Long id;

    /**
     * id 列表
     */
    private List<Long> idList;


    /**
     * 分区名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 搜索关键字
     */
    private String keyword;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;
}
