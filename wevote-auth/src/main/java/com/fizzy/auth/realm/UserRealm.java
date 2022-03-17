package com.fizzy.auth.realm;

import com.fizzy.auth.service.RolePermsService;
import com.fizzy.auth.service.SysPermsService;
import com.fizzy.auth.service.SysUserService;
import com.fizzy.auth.service.UserRoleService;
import com.fizzy.core.entity.RolePerms;
import com.fizzy.core.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author FizzyElf
 * Date 2021/10/18 9:31
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    SysPermsService sysPermsService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RolePermsService rolePermsService;

    /**
     * 角色的权限信息，授权时使用
     * @param principalCollection 授权
     * @return 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        SysUser sysUser = (SysUser)subject.getPrincipal();
        SysUser dbUser = sysUserService.selectUserByName(sysUser.getUsername());
        // 获取当前用户的角色
        int sysRole = userRoleService.selectRoleByUserId(dbUser.getUserId().intValue());
        // 获取角色对应的权限
        List<RolePerms> perms = rolePermsService.selectByRoleId(sysRole);
        // 添加授权字符串
        for(RolePerms perm : perms){
            String permName =  sysPermsService.selectByPermsId((int) perm.getPermsId()).getPath();
            info.addStringPermission(permName);
        }
        return info;

    }

    /**
     * 用户的角色信息，认证时使用
     * @param authenticationToken 认证
     * @return 认证结果
     * @throws AuthenticationException 异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        SysUser sysUser = sysUserService.selectUserByName(token.getUsername());
        if(sysUser != null){
            return new SimpleAuthenticationInfo(sysUser,sysUser.getPassword(),getName());
        }
        return null;
    }
}
