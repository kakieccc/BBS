package com.kakie.bbs_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kakie.bbs_backend.common.BarStatusEnum;
import com.kakie.bbs_backend.common.ErrorCode;
import com.kakie.bbs_backend.dto.BarDTO;
import com.kakie.bbs_backend.exception.BusinessException;
import com.kakie.bbs_backend.model.domain.Bar;
import com.kakie.bbs_backend.model.domain.User;
import com.kakie.bbs_backend.model.domain.UserBar;
import com.kakie.bbs_backend.model.request.BarJoinRequest;
import com.kakie.bbs_backend.model.request.BarQuitRequest;
import com.kakie.bbs_backend.model.request.BarUpdateRequest;
import com.kakie.bbs_backend.service.BarService;
import com.kakie.bbs_backend.mapper.BarMapper;
import com.kakie.bbs_backend.service.UserBarService;
import com.kakie.bbs_backend.service.UserService;
import com.kakie.bbs_backend.vo.UserBarVO;
import com.kakie.bbs_backend.vo.UserVO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
* @author 29967
* @description 针对表【bar(分区)】的数据库操作Service实现
* @createDate 2024-02-18 19:55:10
*/
@Service
public class BarServiceImpl extends ServiceImpl<BarMapper, Bar>
        implements BarService {

    @Resource
    private UserBarService userBarService;

    @Resource
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long addBar(Bar bar, User loginUser) {
        //1.请求参数是否为空
        if (bar == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //2.是否登录，未登录不允许创建
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        final long userId = loginUser.getId();
        //3.检验信息
        //分区标题 <=20
        String name = bar.getName();
        if (StringUtils.isBlank(name) || name.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分区标题不满足要求");
        }
        // 描述<= 512
        String description = bar.getDescription();
        if (StringUtils.isNotBlank(description) && description.length() > 512) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分区描述过长");
        }
        //status 是否公开，不传默认为0
        int status = Optional.ofNullable(bar.getStatus()).orElse(0);
        BarStatusEnum statusEnum = BarStatusEnum.getEnumByValue(status);
        if (statusEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分区状态不满足要求");
        }

        //如果status是加密状态，一定要密码 且密码<=32
        String password = bar.getPassword();
        if (BarStatusEnum.SECRET.equals(statusEnum)) {
            if (StringUtils.isBlank(password) || password.length() > 32) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码设置不正确");
            }
        }
        //校验用户最多创建5个分区
        QueryWrapper<Bar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        long hasBarNum = this.count(queryWrapper);
        if (hasBarNum >= 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户最多创建5个分区");
        }
        //插入分区消息到分区表
        bar.setId(null);
        bar.setUserId(userId);
        boolean result = this.save(bar);
        Long barId = bar.getId();
        if (!result || bar == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "创建分区失败");
        }
        //插入用户 ==> 分区关系 到关系表
        UserBar userBar = new UserBar();
        userBar.setBarId(userId);
        userBar.setBarId(barId);
        userBar.setJoinTime(new Date());
        result = userBarService.save(userBar);
        if (!result) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "创建分区失败");
        }
        return barId;
    }

    /**
     * 查询分区列表
     * @param barDTO
     * @param isAdmin
     * @return
     */
    @Override
    public List<UserBarVO> listBars(BarDTO barDTO, boolean isAdmin) {
        QueryWrapper<Bar> queryWrapper = new QueryWrapper<>();
        //组合查询条件
        if (barDTO != null) {
            Long id = barDTO.getId();
            if (id != null && id > 0) {
                queryWrapper.eq("id", id);
            }
            String searchText = barDTO.getKeyword();
            if (StringUtils.isNotBlank(searchText)) {
                queryWrapper.and(qw -> qw.like("name", searchText).or().like("expireTime", searchText));
            }
            String name = barDTO.getName();
            if (StringUtils.isNotBlank(name)) {
                queryWrapper.like("name", name);
            }
            String description = barDTO.getDescription();
            if (StringUtils.isNotBlank(description)) {
                queryWrapper.like("description", description);
            }
            Long userId = barDTO.getUserId();
            //根据创建人来查询
            if (userId != null && userId > 0) {
                queryWrapper.eq("userId", userId);
            }
            //根据状态来查询
            Integer status = barDTO.getStatus();
            BarStatusEnum statusEnum = BarStatusEnum.getEnumByValue(status);
            if (statusEnum == null) {
                statusEnum = BarStatusEnum.PUBLIC;
            }
            if (!isAdmin && !statusEnum.equals(BarStatusEnum.PUBLIC)) {
                throw new BusinessException(ErrorCode.NO_AUTH);
            }
            queryWrapper.eq("status", statusEnum.getValue());
        }
        List<Bar> barList = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(barList)) {
            return new ArrayList<>();
        }
        List<UserBarVO> userBarVOList = new ArrayList<>();
        //关联查询创建人的用户信息
        for (Bar bar : barList) {
            Long userId = bar.getUserId();
            if (userId == null) {
                continue;
            }
            User user = userService.getById(userId);
            UserBarVO userBarVO = new UserBarVO();
            BeanUtils.copyProperties(bar, userBarVO);
            //脱敏用户信息
            if (user!=null){
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(user, userVO);
                userBarVO.setCreateUser(userVO);
            }
            userBarVOList.add(userBarVO);
        }
        return userBarVOList;
    }

    /**
     * 修改分区
     * @param barUpdateRequest
     * @param loginUser
     * @return
     */
    @Override
    public boolean updateBar(BarUpdateRequest barUpdateRequest, User loginUser) {
        if (barUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = barUpdateRequest.getId();
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Bar oldBar = this.getById(id);
        if (oldBar==null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"分区不存在");
        }
        //只有管理员或者分区的创建者才可以修改
        if (oldBar.getUserId()!=loginUser.getId()&&!userService.isAdmin(loginUser)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        BarStatusEnum statusEnum = BarStatusEnum.getEnumByValue(barUpdateRequest.getStatus());
        if (statusEnum.equals(BarStatusEnum.SECRET)) {
            if (StringUtils.isBlank(barUpdateRequest.getPassword())) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "加密分区必须要设置密码");
            }
        }
        Bar updateBar = new Bar();
        BeanUtils.copyProperties(barUpdateRequest,updateBar);
        return this.updateById(updateBar);
    }

    /**
     * 用户加入分区
     * @param barJoinRequest
     * @param loginUser
     * @return
     */
    @Override
    public boolean joinBar(BarJoinRequest barJoinRequest, User loginUser) {
        if (barJoinRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long barId = barJoinRequest.getBarId();
        if (barId == null || barId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Bar bar = this.getById(barId);
        if (bar == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分区不存在");
        }
        Integer status = bar.getStatus();
        BarStatusEnum barStatusEnum = BarStatusEnum.getEnumByValue(status);
        if (barStatusEnum.PRIVATE.equals(barStatusEnum)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "禁止加入私有分区");
        }
        String password = barJoinRequest.getPassword();
        if (barStatusEnum.SECRET.equals(barStatusEnum)) {
            if (StringUtils.isBlank(password) || !password.equals(bar.getPassword())) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
            }
        }
        //不能重复加入已加入的分区
        QueryWrapper<UserBar> userBarQueryWrapper = new QueryWrapper<>();
        Long userId = loginUser.getId();
        userBarQueryWrapper.eq("userId", userId);
        userBarQueryWrapper.eq("barId", barId);
        long hasUserJoinBar = userBarService.count(userBarQueryWrapper);
        if (hasUserJoinBar > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户已加入该分区");
        }
        //修改分区信息
        UserBar userBar = new UserBar();
        userBar.setUserId(userId);
        userBar.setBarId(barId);
        userBar.setJoinTime(new Date());
        return userBarService.save(userBar);
    }

    /**
     * 退出分区
     * @param barQuitRequest
     * @param loginUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean quitBar(BarQuitRequest barQuitRequest, User loginUser) {
        if (barQuitRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long barId = barQuitRequest.getBarId();
        if (barId == null || barId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Bar bar = this.getById(barId);
        if (bar == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "分区不存在");
        }
        long userId = loginUser.getId();
        UserBar queryUserbar = new UserBar();
        queryUserbar.setBarId(barId);
        queryUserbar.setUserId(userId);
        QueryWrapper<UserBar> queryWrapper = new QueryWrapper<>(queryUserbar);
        long count = userBarService.count(queryWrapper);
        if (count == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "未加入分区");
        }
        long barHasJoinNum = this.countBarUserByBarId(barId);
        //分区只剩下一个人，解散
        if (barHasJoinNum == 1) {
            //删除分区
            this.removeById(barId);
        } else {
            //分区至少还剩下两人
            //是吧主
            if (bar.getUserId() == userId) {
                //把分区转移给最早加入的用户
                //1.查询已加入分区的所有用户和加入时间
                QueryWrapper<UserBar> userBarQueryWrapper = new QueryWrapper<>();
                userBarQueryWrapper.eq("barId", barId);
                userBarQueryWrapper.last("order by id asc limit 2");
                List<UserBar> userBarList = userBarService.list(userBarQueryWrapper);
                if (CollectionUtils.isEmpty(userBarList) || userBarList.size() <= 1) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                UserBar nextUserBar = userBarList.get(1);
                Long nextBarLeaderId = nextUserBar.getUserId();
                //更新当前分区的吧主
                Bar updateBar = new Bar();
                updateBar.setId(barId);
                updateBar.setUserId(nextBarLeaderId);
                boolean result = this.updateById(updateBar);
                if (!result) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "转移吧主失败");
                }
            }
        }
        //移除关系
        return userBarService.remove(queryWrapper);
    }

    /**
     * 删除分区
     * @param id
     * @param loginUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBar(long id, User loginUser) {
        // 校验分区是否存在
        Bar bar = getBarById(id);
        long barId = bar.getId();
        // 校验是不是吧主
        if (!bar.getUserId().equals(loginUser.getId())){
            throw new BusinessException(ErrorCode.NO_AUTH,"无访问权限");
        }
        // 移除所有加入分区的关联信息
        QueryWrapper<UserBar> userBarQueryWrapper = new QueryWrapper<>();
        userBarQueryWrapper.eq("barId", barId);
        boolean result = userBarService.remove(userBarQueryWrapper);
        if (!result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除分区关联信息失败");
        }
        // 删除分区
        return this.removeById(barId);
    }

    /**
     * 获取某分区当前人数
     *
     * @param barId
     * @return
     */
    private long countBarUserByBarId(long barId) {
        QueryWrapper<UserBar> userBarQueryWrapper = new QueryWrapper<>();
        userBarQueryWrapper.eq("barId", barId);
        return userBarService.count(userBarQueryWrapper);
    }

    /**
     * 根据 id 获取分区信息
     *
     * @param barId
     * @return
     */
    private Bar getBarById(Long barId) {
        if (barId == null || barId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Bar bar = this.getById(barId);
        if (bar == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "分区不存在");
        }
        return bar;
    }
}




