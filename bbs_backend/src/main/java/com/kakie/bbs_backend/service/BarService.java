package com.kakie.bbs_backend.service;

import com.kakie.bbs_backend.dto.BarDTO;
import com.kakie.bbs_backend.model.domain.Bar;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kakie.bbs_backend.model.domain.User;
import com.kakie.bbs_backend.vo.UserBarVO;

import java.util.List;

/**
* @author 29967
* @description 针对表【bar(分区)】的数据库操作Service
* @createDate 2024-02-18 19:55:10
*/
public interface BarService extends IService<Bar> {
    /**
     *   添加分区
     * @param bar
     * @param loginUser
     * @return
     */
    long addBar(Bar bar, User loginUser);

    /**
     * 查询队伍列表
     * @param barDTO
     * @param isAdmin
     * @return
     */
    List<UserBarVO> listTeams(BarDTO barDTO, boolean isAdmin);
}
