package com.fizzy.auth.controller;

import com.fizzy.auth.service.SysPermsService;
import com.fizzy.core.entity.SysPerms;
import com.fizzy.core.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/10/19 11:49
 */
@RestController
@RequestMapping("/sysPerms")
public class SysPermsController {
    @Autowired
    SysPermsService sysPermsService;

    @PostMapping("/add")
    public Boolean add(@RequestBody SysPerms sysPerms){
        return sysPermsService.insertOne(sysPerms);
    }

    @DeleteMapping("/delete")
    public Boolean delete(@RequestBody HashMap<String, Integer> data){
        return sysPermsService.deleteByPermsId(data.get("permsId"));
    }

    @GetMapping("/find")
    public SysRole find(@RequestBody HashMap<String, Integer> data) {
        return sysPermsService.selectByPermsId(data.get("permsId"));
    }

    @GetMapping("/findAll")
    public List<SysRole> findAll() {
        return sysPermsService.selectAll();
    }

    @PutMapping("/update")
    public Boolean update(@RequestBody SysPerms sysPerms){
        return sysPermsService.updateById(sysPerms);
    }

}
