package com.fizzy.auth.realm;

import com.fizzy.auth.feign.UserServiceFeign;
import com.fizzy.core.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author FizzyElf
 * Date 2021/10/18 9:31
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserServiceFeign userServiceFeign;

    // 角色的权限信息，授权时使用
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        User dbuser = userServiceFeign.getUserByName(user.getUsername());
        // 添加授权字符串
        info.addRole(dbuser.toString());
        return info;

    }

    // 用户的角色信息，认证时使用
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userServiceFeign.getUserByName(token.getUsername());
        if(user != null){
            return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        }
        return null;
    }
}
