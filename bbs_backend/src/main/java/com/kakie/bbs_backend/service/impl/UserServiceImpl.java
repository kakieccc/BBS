package com.kakie.bbs_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kakie.bbs_backend.model.User;
import com.kakie.bbs_backend.service.UserService;
import com.kakie.bbs_backend.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author 29967
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-01-21 19:23:08
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
    @Resource
    UserMapper userMapper;

    public static final String SALT = "kakie";

    /**
     * 用户注册
     * @return 新用户 id
     */
    @Override
    public long userRegister(User user, String checkPassword) {
        String userAccount = user.getUserAccount();
        String userPassword = user.getUserPassword();
        String userName = user.getUsername();
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        if (userAccount.length() < 4) {
            return -1;
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 插入数据
        User newUser = new User();
        newUser.setUsername(userName);
        newUser.setUserAccount(userAccount);
        newUser.setUserPassword(encryptPassword);
        boolean saveResult = this.save(newUser);
        if (!saveResult) {
            return -1;
        }
        return newUser.getId();
    }

    @Override
    public User userLogin(User user, HttpServletRequest request) {
        return null;
    }

}




