package com.kakie.bbs_backend.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class BarJoinRequest implements Serializable {

    private static final long serialVersionUID = -24663018187059425L;

    /**
     * id
     */
    private Long barId;

    /**
     * 密码
     */
    private String password;
}