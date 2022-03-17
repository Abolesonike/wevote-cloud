package com.fizzy.auth.controller;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.fizzy.aliyun.util.AliyunMessageClient;
import com.fizzy.auth.service.SysUserService;
import com.fizzy.auth.service.UserRoleService;
import com.fizzy.core.entity.QueryResult;
import com.fizzy.core.entity.SysUser;
import com.fizzy.core.entity.UserRole;
import com.fizzy.core.utils.Result;
import com.fizzy.core.utils.VerifyCode;
import com.fizzy.redis.utils.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Author FizzyElf
 * Date 2021/10/18 17:00
 */
@RestController
public class LoginController {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    UserRoleService userRoleService;

    @PostMapping("/login")
    public QueryResult loginByPwd(@RequestParam String tel,
                                  @RequestParam String password,
                                  @RequestParam String verifyCode,
                                  HttpServletRequest request) {
        Object verifyCode1 = redisUtil.get("verifyCode");
        if(!verifyCode.equalsIgnoreCase(String.valueOf(verifyCode1))) {
            QueryResult queryResult = new QueryResult();
            queryResult.setData("验证码错误！");
            return queryResult;
        }
        try {
            // 认证 Subject：主体
            Subject subject = SecurityUtils.getSubject();
            // 根据用户信息，组成用户令牌token
            Md5Hash md5 = new Md5Hash(password, tel,5);
            password = md5.toString();
            UsernamePasswordToken Token = new UsernamePasswordToken(tel, password, false);
            subject.login(Token);
            SysUser sysUser = (SysUser) subject.getPrincipal();
            System.out.println(sysUser);
            QueryResult queryResult = new QueryResult();
            String token = subject.getSession().getId().toString();
            SecurityUtils.getSubject().getSession().setTimeout(86400000);
            queryResult.setData(sysUser);
            queryResult.setToken(token);
            return queryResult;
        } catch (UnknownAccountException e) {
            QueryResult queryResult = new QueryResult();
            queryResult.setData("电话号码不存在！");
            return queryResult;
        } catch (IncorrectCredentialsException e) {
            QueryResult queryResult = new QueryResult();
            queryResult.setData("密码错误！");
            return queryResult;
        }
    }

    @PostMapping("/signIn")
    public Result signIn(@RequestBody SysUser sysUser,@RequestParam String verifyCode, HttpServletRequest request) {
        String verifyCode1 = redisUtil.get("phoneVerifyCode:" + sysUser.getTel());
        if(!verifyCode.equalsIgnoreCase(String.valueOf(verifyCode1))) {
            return new Result(202,"验证码错误！");
        }
        if(sysUserService.selectUserByName(sysUser.getUsername()) != null) {
            return new Result(201,"用户名重复！");
        }
        Md5Hash md5 = new Md5Hash(sysUser.getPassword(), sysUser.getTel(),5);
        sysUser.setPassword(md5.toString());
        // 创建时间
        Date nowDate = new Date();
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(nowDate.getTime());
        sysUser.setCreateTime(sqlDate);
        sysUserService.insertOne(sysUser);
        UserRole userRole = new UserRole();
        userRole.setUserId(sysUser.getUserId());
        userRole.setRoleId(4);
        userRoleService.insertOne(userRole);
        return new Result(200,"注册成功！");
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
    public boolean isPermitted(@RequestParam String requestUrl,@RequestParam String token, HttpServletRequest request) {
        System.out.println("isPermitted");
        //方案一，不灵活（对于get请求，不允许在url通过/拼接参数，可以通过?拼接）、不易排查问题
        boolean permitted = SecurityUtils.getSubject().isPermitted(requestUrl);
        System.out.println("是否授权：" + permitted);
        return permitted;
    }

    @GetMapping("/verifyCode")
    public void code(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        VerifyCode vc = new VerifyCode();
        BufferedImage image = vc.getImage();
        String text = vc.getText();
        redisUtil.setExpire("verifyCode",text,1,TimeUnit.MINUTES);
        VerifyCode.output(image, resp.getOutputStream());
    }

    @GetMapping("/messageCode")
    public Result messageCode(@RequestParam String phoneNumber) throws Exception {
        // 检查号码是否注册
        SysUser  sysUse = new SysUser();
        sysUse.setTel(phoneNumber);
//        if (!CollectionUtils.isEmpty(sysUserService.selectAll(sysUse))) {
//            return new Result(201,"该号码已经注册！");
//        }
        String coed =  String.valueOf((int)((Math.random()*9+1)*100000));
//        com.aliyun.dysmsapi20170525.Client client = AliyunMessageClient.createClient(
//                "LTAI5t6Z8uvgTr8KPNyg5gK5", "UwkVykTht8JN2YdOE9P3QF4DDAmrAh");
//        SendSmsRequest sendSmsRequest = new SendSmsRequest()
//                .setSignName("阿里云短信测试")
//                .setTemplateCode("SMS_154950909")
//                .setPhoneNumbers(phoneNumber)
//                .setTemplateParam("{\"code\":\""+ coed +"\"}");
//        // 复制代码运行请自行打印 API 的返回值
//        SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
//        if ("OK".equals(sendSmsResponse.getBody().getCode())) {
//            redisUtil.setExpire("phoneVerifyCode:" + phoneNumber,coed,30,TimeUnit.MINUTES);
//            return new Result(200,"验证码发送成功！");
//        } else {
//            return new Result(203,sendSmsResponse.getBody().getMessage());
//        }
        redisUtil.setExpire("phoneVerifyCode:" + phoneNumber,coed,30,TimeUnit.MINUTES);
        return new Result(200,"验证码发送成功！");

    }

    @GetMapping("/resetPassword")
    public Result resetPassword(@RequestParam String tel,
                                  @RequestParam String password,
                                  @RequestParam String verifyCode,
                                  HttpServletRequest request) {
        String verifyCode1 = redisUtil.get("phoneVerifyCode:" + tel);
        if(!verifyCode.equalsIgnoreCase(String.valueOf(verifyCode1))) {
            return new Result(202,"验证码错误！");
        }
        SysUser user = new SysUser();
        user.setTel(tel);
        try {
            SysUser sysUser = sysUserService.selectAll(user).get(0);
            Md5Hash md5 = new Md5Hash(password, tel,5);
            sysUser.setPassword(md5.toString());
            sysUserService.updateById(sysUser);
            return new Result(200,"成功");
        } catch (IndexOutOfBoundsException e){
            return new Result(203,"手机号不存在！");
        }
    }

    @GetMapping("/auth/test")
    public void authTest(){
        System.out.println("进入auth服务");
    }
}
