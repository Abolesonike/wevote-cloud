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
    public Boolean insertOne(SysUser sysUser){
        return sysUserMapper.insertOne(sysUser);
    }

    /**
     * 删除一条数据
     */
    public Boolean deleteById(long id){
        return sysUserMapper.deleteById(id);
    }

    /**
     * 根据id查询用户
     */
    public SysUser selectByUserId(long id){
        return sysUserMapper.selectByUserId(id);
    }

    /**
     * 根据用户名查询用户
     */
    public SysUser selectRoleByUserName(String username){
        return sysUserMapper.selectUserByName(username);
    }

    /**
     * 根据启用状态
     */
    public List<SysUser> selectUserByEnable(String enable){
        return sysUserMapper.selectUserByEnable(enable);
    }

    /**
     * 根据用户名模糊查询
     */
    public List<SysUser> selectByUsernameLike(String username){
        return sysUserMapper.selectByUsernameLike(username);
    }

    /**
     * 查询所有数据
     */
    public List<SysUser> selectAll(){
        return sysUserMapper.selectAll();
    }

    /**
     * 修改一条数据
     */
    public Boolean updateById(SysUser sysUser){
        return sysUserMapper.updateById(sysUser);
    }
}
