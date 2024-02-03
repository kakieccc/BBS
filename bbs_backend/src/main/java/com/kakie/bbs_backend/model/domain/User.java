package com.kakie.bbs_backend.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
@Schema(name = "User", description = "用户")
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @Schema(name = "id", description = "id")
    private long id;

    /**
     * 用户昵称
     */
    @Schema(name = "userName", description = "用户昵称")
    private String userName;

    /**
     * 账号
     */
    @Schema(name = "userAccount", description = "账号")
    private String userAccount;

    /**
     * 用户头像
     */
    @Schema(name = "avatarUrl", description = "用户头像")
    private String avatarUrl;

    /**
     * 性别
     */
    @Schema(name = "gender", description = "性别")
    private Integer gender;

    /**
     * 密码
     */
    @Schema(name = "userPassword", description = "密码")
    private String userPassword;

    /**
     * 电话
     */
    @Schema(name = "phone", description = "电话")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(name = "email", description = "邮箱")
    private String email;

    /**
     * 状态 0 - 正常
     */
    @Schema(name = "userStatus", description = "状态 0 - 正常")
    private Integer userStatus;

    /**
     * 创建时间
     */
    @Schema(name = "createTime", description = "创建时间")
    private Date createTime;

    /**
     *  更新时间
     */
    @Schema(name = "updateTime", description = "更新时间")
    private Date updateTime;

    /**
     * 是否删除
     */
    @Schema(name = "isDelete", description = "是否删除")
    @TableLogic
    private Integer isDelete;

    /**
     * 用户权限 0 - 普通用户 1 - 管理员
     */
    @Schema(name = "userRole", description = "用户权限 0 - 普通用户 1 - 管理员")
    private Integer userRole;

    /**
     * 标签列表_用json存储
     */
    @Schema(name = "tags", description = "标签列表_用json存储")
    private String tags;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}