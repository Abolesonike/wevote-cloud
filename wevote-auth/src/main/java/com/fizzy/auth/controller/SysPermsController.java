package com.fizzy.auth.controller;

import com.fizzy.auth.service.SysPermsService;
import com.fizzy.core.entity.SysPerms;
import com.fizzy.core.entity.SysRole;
import com.fizzy.core.entity.SysUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public SysPerms find(@RequestBody HashMap<String, Integer> data) {
        return sysPermsService.selectByPermsId(data.get("permsId"));
    }

    @GetMapping("/findAll")
    public List<SysPerms> findAll() {
        return sysPermsService.selectAll();
    }

    /**
     * 分页查询所有
     * @param pageNum 页数
     * @param pageSize 页大小
     * @return 权限列表
     */
    @GetMapping("/permsList")
    public PageInfo<SysPerms> postListStatus(@RequestParam int pageNum,
                                            @RequestParam int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysPerms> permsList = sysPermsService.selectAll();
        return new PageInfo<>(permsList);
    }

    @PutMapping("/update")
    public Boolean update(@RequestBody SysPerms sysPerms){
        return sysPermsService.updateById(sysPerms);
    }

}
