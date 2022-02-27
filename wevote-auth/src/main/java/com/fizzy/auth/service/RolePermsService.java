package com.fizzy.auth.service;

import com.fizzy.auth.entity.PermsTreeNode;
import com.fizzy.auth.mapper.RolePermsMapper;
import com.fizzy.core.entity.RolePerms;
import com.fizzy.core.entity.SysPerms;
import com.fizzy.core.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FizzyElf
 * Date 2021/10/19 17:28
 */
@Service
public class RolePermsService {
    @Autowired
    RolePermsMapper rolePermsMapper;

    /**
     * 插入一条数据
     */
    public Boolean insertOne(RolePerms rolePerms){
        return rolePermsMapper.insertOne(rolePerms);
    }

    /**
     * 批量插入
     * @param permsList 分配的权限
     * @param roleId 角色id
     * @return 成功条数
     */
    public Result batchInsert(List<Integer> permsList, long roleId) {
        List<RolePerms> rolePermsList = new ArrayList<>();
        for (Integer perms : permsList){
            RolePerms rolePerms = new RolePerms();
            rolePerms.setRoleId(roleId);
            rolePerms.setPermsId(perms);
            rolePermsList.add(rolePerms);
        }
        if (deleteByRoleId(roleId) < 0 ) {
            return new Result(500, "权限分配失败！");
        } else {
            try {
                int successNum =  rolePermsMapper.batchInsert(rolePermsList);
                return new Result(200, successNum + "条权限分配成功！");
            } catch (Exception e) {
                return new Result(500, "权限分配失败！原因：" + e.getMessage());
            }
        }
    }

    /**
     * 删除一条数据
     */
    public Boolean deleteById(int id){
        return rolePermsMapper.deleteById(id);
    }

    public Boolean deleteByPermsId(int permsId) {
        return rolePermsMapper.deleteByPermsId(permsId);
    }

    public int deleteByRoleId(long roleId) {
        return rolePermsMapper.deleteByRoleId(roleId);
    }

    /**
     * 指定角色，查询权限
     */
    public List<RolePerms> selectByRoleId(int roleId){
        return rolePermsMapper.selectPermsByRoleId(roleId);
    }

    /**
     * 指定角色，查询权限
     */
    public List<RolePerms> selectByPermsId(int permsId){
        return rolePermsMapper.selectByPermsId(permsId);
    }

    /**
     * 指定角色，查询权限id
     */
    public List<Integer> selectPermsIdByRoleId(int roleId){
        return rolePermsMapper.selectPermsIdByRoleId(roleId);
    }

    /**
     * 查询所有数据
     */
    public List<RolePerms> selectAll(){
        return rolePermsMapper.selectAll();
    }

    /**
     * 修改一条数据
     */
    public Boolean updateById(RolePerms rolePerms){
        return rolePermsMapper.updateById(rolePerms);
    }
}
