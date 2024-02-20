package com.kakie.bbs_backend.service;

import com.kakie.bbs_backend.model.dto.BarDTO;
import com.kakie.bbs_backend.model.domain.Bar;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kakie.bbs_backend.model.domain.User;
import com.kakie.bbs_backend.model.request.BarJoinRequest;
import com.kakie.bbs_backend.model.request.BarQuitRequest;
import com.kakie.bbs_backend.model.request.BarUpdateRequest;
import com.kakie.bbs_backend.model.vo.UserBarVO;

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
     * 查询分区列表
     * @param barDTO
     * @param isAdmin
     * @return
     */
    List<UserBarVO> listBars(BarDTO barDTO, boolean isAdmin);

    /**
     * 修改分区
     * @param barUpdateRequest
     * @param loginUser
     * @return
     */
    boolean updateBar(BarUpdateRequest barUpdateRequest, User loginUser);

    /**
     * 用户加入分区
     * @param barJoinRequest
     * @param loginUser
     * @return
     */
    boolean joinBar(BarJoinRequest barJoinRequest, User loginUser);

    /**
     * 退出分区
     * @param barQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitBar(BarQuitRequest barQuitRequest, User loginUser);

    /**
     * 删除分区
     * @param id
     * @param loginUser
     * @return
     */
    boolean deleteBar(long id, User loginUser);
}
