package com.fizzy.auth.mapper;

import com.fizzy.core.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/10/19 17:44
 */
@Mapper
public interface SysUserMapper {
    /**
     * 插入一条数据
     */
    Boolean insertOne(SysUser sysUser);

    /**
     * 删除一条数据
     */
    Boolean deleteById(long id);

    /**
     * 根据id查询用户
     */
    SysUser selectByUserId(long id);

    /**
     * 根据用户名查询用户
     */
    SysUser selectUserByName(String username);

    /**
     * 根据启用状态
     */
    List<SysUser> selectUserByEnable(String enable);

    /**
     * 根据用户名模糊查询
     */
    List<SysUser> selectByUsernameLike(String username);

    /**
     * 查询所有数据
     */
    List<SysUser> selectAll();

    /**
     * 修改一条数据
     */
    Boolean updateById(SysUser sysUser);
}
