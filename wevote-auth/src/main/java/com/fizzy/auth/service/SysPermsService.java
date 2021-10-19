package com.fizzy.auth.service;

import com.fizzy.auth.mapper.SysPermsMapper;
import com.fizzy.core.entity.SysPerms;
import com.fizzy.core.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/10/19 11:47
 */
@Service
public class SysPermsService {
    @Autowired
    SysPermsMapper sysPermsMapper;

    /**
     * 插入一条数据
     */
    public Boolean insertOne(SysPerms sysPerms){
        return sysPermsMapper.insertOne(sysPerms);
    }

    /**
     * 删除一条数据
     */
    public Boolean deleteByPermsId(int permsId){
        return sysPermsMapper.deleteByPermsId(permsId);
    }

    /**
     * 查询一条数据
     */
    public SysRole selectByPermsId(int permsId){
        return sysPermsMapper.selectByPermsId(permsId);
    }

    /**
     * 查询所有数据
     */
    public List<SysRole> selectAll(){
        return sysPermsMapper.selectAll();
    }

    /**
     * 修改一条数据
     */
    public Boolean updateById(SysPerms sysPerms){
        return sysPermsMapper.updateById(sysPerms);
    }
}
