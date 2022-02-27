package com.fizzy.auth.controller;

import com.fizzy.auth.service.RolePermsService;
import com.fizzy.core.entity.RolePerms;
import com.fizzy.core.entity.SysPerms;
import com.fizzy.core.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/2/22 15:01
 */
@RestController
@RequestMapping("/rolePerms")
public class RolePermsController {
    @Autowired
    RolePermsService rolePermsService;

    /**
     * 分配角色权限
     * @param permsList 权限列表
     * @param roleId 角色id
     * @return 结果
     */
    @PostMapping("/assignPermissions")
    public Result assignPermissions(@RequestBody List<Integer> permsList, @RequestParam int roleId){
        return rolePermsService.batchInsert(permsList, roleId);
    }
}