package com.fizzy.auth.controller;

import com.fizzy.auth.service.SysRoleService;
import com.fizzy.core.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/10/19 11:17
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {
    @Autowired
    SysRoleService sysRoleService;

    @PostMapping("/add")
    public Boolean add(@RequestBody SysRole sysRole){
        return sysRoleService.insertOne(sysRole);
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
    public List<SysRole> findAll() {
        return sysRoleService.selectAll();
    }



    @PutMapping("/update")
    public Boolean update(@RequestBody SysRole sysRole){
        return sysRoleService.updateById(sysRole);
    }

}
