package com.kakie.bbs_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kakie.bbs_backend.model.domain.UserBar;
import com.kakie.bbs_backend.service.UserBarService;
import com.kakie.bbs_backend.mapper.UserBarMapper;
import org.springframework.stereotype.Service;

/**
* @author 29967
* @description 针对表【user_bar(用户_分区关系)】的数据库操作Service实现
* @createDate 2024-02-18 19:59:36
*/
@Service
public class UserBarServiceImpl extends ServiceImpl<UserBarMapper, UserBar>
    implements UserBarService{

}




