package com.cl.interview.service.impl;

import com.cl.interview.common.HttpResp;
import com.cl.interview.common.IoTErrorCode;
import com.cl.interview.config.token.RedisTokenImpl;
import com.cl.interview.config.token.TokenModel;
import com.cl.interview.dao.UserDao;
import com.cl.interview.entity.UserEntity;
import com.cl.interview.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTokenImpl redisToken;

    public HttpResp login(String username,String password){
        Map<String,Object> objectMap = new HashMap<>();
        int byUsername = userDao.findByUsername(username);
        if (byUsername == 0){
            return HttpResp.fail("用户名不存在");
        }
        UserEntity userEntity = userDao.findByUsernameAndPassword(username,password);
        if (userEntity == null){
            return HttpResp.fail("密码错误，请重新输入密码");
        }
        TokenModel tokenModel = redisToken.create(userEntity.getId());
        objectMap.put("access_token",tokenModel.getToken());
        userEntity.setPassword(StringUtils.EMPTY);
        objectMap.put("userInfo",userEntity);
        return HttpResp.createBySuccess("登录成功",objectMap);
    }

}
