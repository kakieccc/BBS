package com.kakie.bbs_backend;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.kakie.bbs_backend.mapper.UserMapper;
import com.kakie.bbs_backend.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BbsBackendApplicationTests {

    @Resource
    private UserMapper userMapper;

}
