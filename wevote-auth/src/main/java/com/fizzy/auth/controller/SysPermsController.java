package com.fizzy.auth.controller;

import com.fizzy.auth.entity.PermsTreeNode;
import com.fizzy.auth.service.SysPermsService;
import com.fizzy.core.entity.SysPerms;
import com.fizzy.core.entity.SysRole;
import com.fizzy.core.entity.SysUser;
import com.fizzy.core.utils.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FizzyElf
 * Date 2021/10/19 11:49
 */
@RestController
@RequestMapping("/sysPerms")
public class SysPermsController {
    @Autowired
    SysPermsService sysPermsService;

    /**
     * 插入一条
     * @param sysPerms 插入数据
     * @return 结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody SysPerms sysPerms){
        return sysPermsService.insertOne(sysPerms);
    }

    /**
     * 删除一条
     * @param permsId 删除主键
     * @return 结果
     */
    @DeleteMapping("/delete")
    public Boolean delete(@RequestParam int permsId){
        return sysPermsService.deleteByPermsId(permsId);
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
    public PageInfo<SysPerms> permsList(@RequestParam int pageNum,
                                            @RequestParam int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysPerms> permsList = sysPermsService.selectAll();
        return new PageInfo<>(permsList);
    }

    /**
     * 分页查询所有一级菜单
     * @param pageNum 页数
     * @param pageSize 页大小
     * @return 权限列表
     */
    @PostMapping("/parentPermsList")
    public PageInfo<SysPerms> parentPermsList(@RequestParam int pageNum,
                                              @RequestParam int pageSize,
                                              @RequestBody SysPerms sysPerms) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysPerms> permsList = sysPermsService.selectParent(sysPerms);
        return new PageInfo<>(permsList);
    }

    /**
     * 查询所有子菜单
     * @param parentId 父菜单id
     * @return 权限列表
     */
    @GetMapping("/childrenPermsList")
    public List<SysPerms> childrenPermsList(@RequestParam int parentId) {
        return sysPermsService.selectChildren(parentId);
    }

    /**
     * 树形节点权限，父权限节点
     * @return 父节点
     */
    @PostMapping("/selectParentNode")
    public List<PermsTreeNode> selectParentNode(@RequestParam int roleId, @RequestBody SysPerms sysPerms) {
        return sysPermsService.selectParentNode(roleId, sysPerms);
    }

    /**
     * 树形节点权限，子权限节点
     * @return 父节点
     */
    @GetMapping("/selectChildrenNode")
    public List<PermsTreeNode> selectChildrenNode(@RequestParam String parentPath,
                                                  @RequestParam int roleId) {
        return sysPermsService.selectChildrenNode(parentPath, roleId);
    }

    /**
     * 查询所有树形菜单
     * @return 权限列表
     */
    @GetMapping("/permsTreeList")
    public Map<String, Object> permsTreeList(@RequestParam int roleId) {
        return sysPermsService.selectAllTree(roleId);
    }

    @PutMapping("/update")
    public Boolean update(@RequestBody SysPerms sysPerms){
        return sysPermsService.updateById(sysPerms);
    }

}
