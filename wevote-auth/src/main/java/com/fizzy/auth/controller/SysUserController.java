package com.fizzy.auth.controller;

import com.fizzy.auth.mapper.UserRoleMapper;
import com.fizzy.auth.service.SysUserService;
import com.fizzy.auth.service.UserRoleService;
import com.fizzy.core.entity.SysUser;
import com.fizzy.core.entity.UserRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author FizzyElf
 * Date 2021/11/3 16:04
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    SysUserService sysUserService;

    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/findById")
    public SysUser findById(@RequestParam long id){
        return sysUserService.selectByUserId(id);
    }

    /**
     * 分页查询所有
     * @param pageNum 页数
     * @param pageSize 页大小
     * @return 用户列表
     */
    @GetMapping("/userList")
    public PageInfo<SysUser> postListStatus(@RequestParam int pageNum,
                                            @RequestParam int pageSize,
                                            @RequestParam String enable) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> userList = sysUserService.selectUserByEnable(enable);
        return new PageInfo<>(userList);
    }

    /**
     * 更新一条数据
     * @param sysUser 更新对象
     * @return 是否成功
     */
    @PutMapping("/updateById")
    public boolean updateById(@RequestBody SysUser sysUser){
        Date date = new Date();
        java.sql.Timestamp dateSQL = new java.sql.Timestamp(date.getTime());
        sysUser.setModifyTime(dateSQL);
        return sysUserService.updateById(sysUser);
    }

    /**
     * 删除一条，物理删除
     * @param id 删除主键
     * @return 是否成功
     */
    @DeleteMapping("/deleteById")
    public boolean deleteById(@RequestParam long id){
        return sysUserService.deleteById(id);
    }

    /**
     * 禁用,启用一个用户
     * @param data 包含id和enable
     * @return 是否成功
     */
    @PutMapping("/changeEnable")
    public boolean disable(@RequestBody Map<String, Object> data){
        SysUser user = sysUserService.selectByUserId(Long.parseLong(data.get("id").toString()));
        user.setEnableStatus((String) data.get("enable"));
        return sysUserService.updateById(user);
    }

    @PutMapping("/changeRole")
    public boolean changeRole(@RequestBody Map<String, Object> data) {
        UserRole userRole = new UserRole();
        userRole.setUserId(Long.parseLong(data.get("userId").toString()));
        userRole.setRoleId(Long.parseLong(data.get("roleId").toString()));
        return userRoleService.updateById(userRole);
    }


    /**
     * 根据用户名模糊查询
     * @param pageNum 页数
     * @param pageSize 页大小
     * @param keyword 关键字
     * @return 搜索结果
     */
    @GetMapping("/searchByName")
    public PageInfo<SysUser> searchByName(@RequestParam int pageNum,
                                          @RequestParam int pageSize,
                                          @RequestParam String keyword){
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> userList = sysUserService.selectByUsernameLike(keyword);
        return new PageInfo<>(userList);
    }

    @GetMapping("/loginUserId")
    public String getLoginUserId(){
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return String.valueOf(sysUser.getUserId());
    }

    @GetMapping("/loginUserName")
    public String getLoginUserName(){
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return String.valueOf(sysUser.getUsername());
    }
}
