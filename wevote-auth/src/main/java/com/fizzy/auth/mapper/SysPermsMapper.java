package com.fizzy.auth.mapper;

import com.fizzy.core.entity.SysPerms;
import com.fizzy.core.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author FizzyElf
 * Date 2021/10/19 11:35
 */
@Mapper
public interface SysPermsMapper {
    /**
     * 插入一条数据
     * @param sysPerms 插入数据
     * @return 是否成功
     */
    Boolean insertOne(SysPerms sysPerms);

    /**
     * 删除一条数据
     * @param permsId id
     * @return 是否成功
     */
    Boolean deleteByPermsId(int permsId);

    /**
     * 根据id查询一条数据
     * @param permsId id
     * @return 结果
     */
    SysPerms selectByPermsId(int permsId);


    /**
     * 查询所有数据
     * @return 结果
     */
    List<SysPerms> selectAll();

    /**
     * 查询一级菜单
     * @return 一级菜单
     */
    List<SysPerms> selectParent();

    /**
     * 查询子菜单
     * @param parentId 父菜单id
     * @return 结果
     */
    List<SysPerms> selectChildren(int parentId);

    /**
     * 根据path查询
     * @param path path
     * @return 结果
     */
    SysPerms selectByPath(String path);

    /**
     * 修改一条数据
     * @param sysPerms 修改的数据
     * @return 结果
     */
    Boolean updateById(SysPerms sysPerms);
}
