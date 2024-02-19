package com.kakie.bbs_backend.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class BarQuitRequest implements Serializable {
    private static final long serialVersionUID = -2038884913144640407L;
    /**
     *  id
     */
    private Long barId;
}
