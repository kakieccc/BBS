package com.kakie.bbs_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kakie.bbs_backend.common.BarStatusEnum;
import com.kakie.bbs_backend.common.ErrorCode;
import com.kakie.bbs_backend.exception.BusinessException;
import com.kakie.bbs_backend.model.domain.Bar;
import com.kakie.bbs_backend.model.domain.User;
import com.kakie.bbs_backend.model.domain.UserBar;
import com.kakie.bbs_backend.service.BarService;
import com.kakie.bbs_backend.mapper.BarMapper;
import com.kakie.bbs_backend.service.UserBarService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        //6.超出时间 > 当前时间
        Date expireTime = bar.getExpireTime();
        if (new Date().after(expireTime)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "超出时间 > 当前时间");
        }

        //7.校验用户最多创建5个队伍
        QueryWrapper<Bar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        long hasBarNum = this.count(queryWrapper);
        if (hasBarNum >= 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户最多创建5个分区");
        }
        //8.插入分区消息到分区表
        bar.setId(null);
        bar.setUserId(userId);
        boolean result = this.save(bar);
        Long barId = bar.getId();
        if (!result || bar == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "创建分区失败");
        }
        //9. 插入用户 ==> 队伍关系 到关系表
        UserBar userBar = new UserBar();
        userBar.setBarId(userId);
        userBar.setBarId(barId);
        userBar.setJoinTime(new Date());
        result = userBarService.save(userBar);
        if (!result) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "创建队伍失败");
        }
        return barId;
    }
}




