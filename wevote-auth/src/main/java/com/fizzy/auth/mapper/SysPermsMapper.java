package com.fizzy.auth.mapper;

import com.fizzy.core.entity.SysPerms;
import com.fizzy.core.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/10/19 11:35
 */
@Mapper
public interface SysPermsMapper {
    /**
     * 插入一条数据
     */
    Boolean insertOne(SysPerms sysPerms);

    /**
     * 删除一条数据
     */
    Boolean deleteByPermsId(int permsId);

    /**
     * 查询一条数据
     */
    SysRole selectByPermsId(int roleId);

    /**
     * 查询所有数据
     */
    List<SysRole> selectAll();

    /**
     * 修改一条数据
     */
    Boolean updateById(SysPerms sysPerms);
}
