package com.fizzy.auth.mapper;

import com.fizzy.core.entity.SysRole;
import com.fizzy.core.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/10/19 10:21
 */
@Mapper
public interface SysRoleMapper {
    /**
     * 插入一条数据
     */
    Boolean insertOne(SysRole sysRole);

    /**
     * 删除一条数据
     */
    Boolean deleteByRoleId(int roleId);

    /**
     * 查询一条数据
     */
    SysRole selectByRoleId(int roleId);

    /**
     * 查询所有数据
     */
    List<SysRole> selectAll();

    /**
     * 查询所有数据
     */
    List<SysRole> selectAll(int enableStatus);

    /**
     * 修改一条数据
     */
    Boolean updateById(SysRole sysRole);
}
