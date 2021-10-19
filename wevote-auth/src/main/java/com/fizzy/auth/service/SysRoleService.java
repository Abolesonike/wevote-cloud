package com.fizzy.auth.service;

import com.fizzy.auth.mapper.SysRoleMapper;
import com.fizzy.core.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/10/19 10:45
 */
@Service
public class SysRoleService {
    @Autowired
    SysRoleMapper sysRoleMapper;

    /**
     * 插入一条数据
     */
    public Boolean insertOne(SysRole sysRole){
        return sysRoleMapper.insertOne(sysRole);
    }

    /**
     * 删除一条数据
     */
    public Boolean deleteByRoleId(int roleId){
        return sysRoleMapper.deleteByRoleId(roleId);
    }

    /**
     * 查询一条数据
     */
    public SysRole selectByRoleId(int roleId){
        return sysRoleMapper.selectByRoleId(roleId);
    }

    /**
     * 查询所有数据
     */
    public List<SysRole> selectAll(){
        return sysRoleMapper.selectAll();
    }

    /**
     * 修改一条数据
     */
    public Boolean updateById(SysRole sysRole){
        return sysRoleMapper.updateById(sysRole);
    }
}
