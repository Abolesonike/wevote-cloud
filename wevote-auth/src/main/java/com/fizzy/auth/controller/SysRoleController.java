package com.fizzy.auth.controller;

import com.fizzy.auth.service.SysRoleService;
import com.fizzy.auth.service.UserRoleService;
import com.fizzy.core.entity.SysPerms;
import com.fizzy.core.entity.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author FizzyElf
 * Date 2021/10/19 11:17
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {
    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    UserRoleService userRoleService;

    /**
     * 添加一条数据
     * @param permsList 权限列表
     * @param roleName 角色名称
     * @return 是否成功
     */
    @PostMapping("/add")
    public Boolean add(@RequestBody List<Integer> permsList,
                       @RequestParam String roleName,
                       @RequestParam String roleDesc){
        return sysRoleService.insertOne(permsList, roleName, roleDesc);
    }

    @DeleteMapping("/delete")
    public Boolean delete(@RequestBody HashMap<String, Integer> data){
        return sysRoleService.deleteByRoleId(data.get("roleId"));
    }

    @GetMapping("/find")
    public SysRole find(@RequestBody HashMap<String, Integer> data) {
        return sysRoleService.selectByRoleId(data.get("roleId"));
    }

    @GetMapping("/findAll")
    public List<SysRole> findAll(@RequestParam int enableStatus) {
        if (enableStatus == -1){
            // 不使用enableStatus条件
            return sysRoleService.selectAll();
        } else {
            return sysRoleService.selectAll(enableStatus);
        }
    }

    /**
     * 分页查询所有
     * @param pageNum 页数
     * @param pageSize 页大小
     * @return 角色列表
     */
    @GetMapping("/roleList")
    public PageInfo<SysRole> permsList(@RequestParam int pageNum,
                                        @RequestParam int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysRole> roleList = sysRoleService.selectAll();
        return new PageInfo<>(roleList);
    }

    @GetMapping("/checkIsAssigned")
    public Boolean checkIsAssigned(@RequestParam int roleId) {
        return userRoleService.checkIsAssigned(roleId);
    }

    /**
     * 全部更新
     * @param sysRole 更新对象
     * @return 是否成功
     */
    @PutMapping("/update")
    public Boolean update(@RequestBody SysRole sysRole){
        Date date = new Date();
        java.sql.Timestamp dateSQL = new java.sql.Timestamp(date.getTime());
        sysRole.setModifyTime(dateSQL);
        return sysRoleService.updateById(sysRole);
    }

}
