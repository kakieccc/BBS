package com.kakie.bbs_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kakie.bbs_backend.model.domain.Bar;
import com.kakie.bbs_backend.service.BarService;
import com.kakie.bbs_backend.mapper.BarMapper;
import org.springframework.stereotype.Service;

/**
* @author 29967
* @description 针对表【bar(分区)】的数据库操作Service实现
* @createDate 2024-02-18 19:55:10
*/
@Service
public class BarServiceImpl extends ServiceImpl<BarMapper, Bar>
    implements BarService{

}




