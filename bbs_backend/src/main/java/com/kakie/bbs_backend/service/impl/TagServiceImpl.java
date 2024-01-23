package com.kakie.bbs_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kakie.bbs_backend.model.domain.Tag;
import com.kakie.bbs_backend.service.TagService;
import com.kakie.bbs_backend.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author 29967
* @description 针对表【tag(标签)】的数据库操作Service实现
* @createDate 2024-01-23 15:59:50
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




