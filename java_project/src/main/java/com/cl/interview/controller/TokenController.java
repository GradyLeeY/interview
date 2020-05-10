package com.cl.interview.controller;

import com.cl.interview.common.HttpResp;
import com.cl.interview.config.annotation.NoneAuth;
import com.cl.interview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private UserService userService;

    @NoneAuth
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public HttpResp login(String username,String password){
        HttpResp httpResp = userService.login(username, password);
        if (httpResp.isSuccess()){
            return httpResp;
        }
        return HttpResp.fail("登录失败,请重新登录");
    }
}
