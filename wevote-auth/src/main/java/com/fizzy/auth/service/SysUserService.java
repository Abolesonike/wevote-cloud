package com.fizzy.auth.service;

import com.fizzy.auth.mapper.SysUserMapper;
import com.fizzy.core.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/10/19 17:59
 */
@Service
public class SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;
    /**
     * 插入一条数据
     */
    Boolean insertOne(SysUser sysUser){
        return sysUserMapper.insertOne(sysUser);
    }

    /**
     * 删除一条数据
     */
    Boolean deleteById(int id){
        return sysUserMapper.deleteById(id);
    }

    /**
     * 根据id查询用户
     */
    SysUser selectByUserId(int id){
        return sysUserMapper.selectByUserId(id);
    }

    /**
     * 根据用户名查询用户
     */
    SysUser selectRoleByUserId(String username){
        return sysUserMapper.selectByUsername(username);
    }

    /**
     * 查询所有数据
     */
    List<SysUser> selectAll(){
        return sysUserMapper.selectAll();
    }

    /**
     * 修改一条数据
     */
    Boolean updateById(SysUser sysUser){
        return sysUserMapper.updateById(sysUser);
    }
}
