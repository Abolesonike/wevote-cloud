package com.fizzy.auth.controller;

import com.fizzy.auth.service.SysRoleService;
import com.fizzy.core.entity.QueryResult;
import com.fizzy.core.entity.SysRole;
import com.fizzy.core.entity.SysUser;
import com.fizzy.core.entity.User;
import com.fizzy.core.utils.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Author FizzyElf
 * Date 2021/10/18 17:00
 */
@RestController
public class LoginController {

    @PostMapping("/login")
    public QueryResult loginByPwd(@RequestParam String user_name, @RequestParam String Pwd, HttpServletRequest request) {

        try {
            // 认证 Subject：主体
            Subject subject = SecurityUtils.getSubject();
            // 根据用户信息，组成用户令牌token
            UsernamePasswordToken Token = new UsernamePasswordToken(user_name, Pwd, false);
            subject.login(Token);
            SysUser sysUser = (SysUser) subject.getPrincipal();
            System.out.println(sysUser);
            QueryResult queryResult = new QueryResult();
            String token = subject.getSession().getId().toString();
            queryResult.setData(sysUser);
            queryResult.setToken(token);
            return queryResult;
        } catch (UnknownAccountException e) {
            QueryResult queryResult = new QueryResult();
            queryResult.setData("用户不存在！");
            return queryResult;
        } catch (IncorrectCredentialsException e) {
            QueryResult queryResult = new QueryResult();
            queryResult.setData("密码错误！");
            return queryResult;
        }
    }

    @GetMapping("/goLogin")
    public Result goLogin(){
        return new Result(405,"未登录");
    }

    @RequestMapping("/logout")
    public Result logout(@RequestParam String user_name){
        SecurityUtils.getSubject().logout();

        QueryResult queryResult = new QueryResult();
        return new Result(200);
    }

    @GetMapping("/isPermitted")
    public boolean isPermitted(@RequestParam String requestURI,@RequestParam String token) {
        System.out.println("isPermitted");
        //方案一，不灵活（对于get请求，不允许在url通过/拼接参数，可以通过?拼接）、不易排查问题
        boolean permitted = SecurityUtils.getSubject().isPermitted(requestURI);
        SysUser sysUser = (SysUser)SecurityUtils.getSubject().getPrincipal();
        System.out.println(sysUser);
        System.out.println("是否授权：" + permitted);
        return permitted;
    }

    @GetMapping("/auth/test")
    public void authTest(){
        System.out.println("进入auth服务");
    }
}
