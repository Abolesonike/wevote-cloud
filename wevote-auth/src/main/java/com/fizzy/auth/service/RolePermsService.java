package com.fizzy.auth.service;

import com.fizzy.auth.mapper.RolePermsMapper;
import com.fizzy.core.entity.RolePerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author FizzyElf
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
     * 删除一条数据
     */
    public Boolean deleteById(int id){
        return rolePermsMapper.deleteById(id);
    }

    /**
     * 指定角色，查询权限
     */
    public List<Integer> selectByPermsId(int id){
        return rolePermsMapper.selectPermsByRoleId(id);
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
