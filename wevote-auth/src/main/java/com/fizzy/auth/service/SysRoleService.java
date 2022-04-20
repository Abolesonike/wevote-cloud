package com.fizzy.auth.service;

import com.fizzy.auth.mapper.SysRoleMapper;
import com.fizzy.core.entity.SysRole;
import com.fizzy.core.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/10/19 10:45
 */
@Service
public class SysRoleService {
    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    RolePermsService rolePermsService;

    /**
     * 插入一条数据
     */
    public Boolean insertOne(List<Integer> permsList, String roleName, String roleDesc){
        SysRole sysRole = new SysRole();
        Date date = new Date();
        java.sql.Timestamp dateSQL = new java.sql.Timestamp(date.getTime());
        sysRole.setCreateDate(dateSQL);
        sysRole.setModifyTime(dateSQL);
        sysRole.setRoleName(roleName);
        sysRole.setRoleDesc(roleDesc);
        sysRole.setEnableStatus("1");
        sysRole.setType(1);
        if(sysRoleMapper.insertOne(sysRole)){
            Result result = rolePermsService.batchInsert(permsList, sysRole.getRoleId());
            return result.getCode() == 200;
        } else {
            return false;
        }
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
     * 查询所有数据
     */
    public List<SysRole> selectAll(int enableStatus){
        return sysRoleMapper.selectAll(enableStatus);
    }

    /**
     * 查询所有数据
     */
    public List<SysRole> selectAll(SysRole sysRole){
        return sysRoleMapper.selectAll(sysRole);
    }

    /**
     * 修改一条数据
     */
    public Boolean updateById(SysRole sysRole){
        return sysRoleMapper.updateById(sysRole);
    }
}
