package com.fizzy.auth.mapper;

import com.fizzy.core.entity.RolePerms;
import com.fizzy.core.entity.SysPerms;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author FizzyElf
 * Date 2021/10/19 17:19
 */
@Mapper
public interface RolePermsMapper {
    /**
     * 插入一条
     * @param rolePerms 插入数据
     * @return 是否成功
     */
    Boolean insertOne(RolePerms rolePerms);

    /**
     * 批量插入
     * @param list 插入的list
     * @return 成功条数
     */
    int batchInsert(List<RolePerms> list);

    /**
     * 根据id删除
     * @param id id
     * @return 是否成功
     */
    Boolean deleteById(int id);

    /**
     * 根据权限删除，用于权限删除时联动删除，减少没用的数据
     * @param permsId 权限id
     * @return 是否成功
     */
    Boolean deleteByPermsId(int permsId);

    /**
     * 根据角色删除，用于批量插入时，删除之前角色分配的权限
     * @param roleId 角色id
     * @return 删除条数
     */
    int deleteByRoleId(long roleId);

    /**
     * 根据roleId查询
     * @param roleId id
     * @return 查询结果
     */
    List<RolePerms> selectPermsByRoleId(int roleId);

    /**
     * 指定角色，查询权限id
     * @param roleId 角色id
     * @return 查询结果
     */
    List<Integer> selectPermsIdByRoleId(int roleId);

    /**
     * 根据权限id查询
     * @param permsId 权限Id
     * @return 查询结果
     */
    List<RolePerms> selectByPermsId(int permsId);

    /**
     * 查询所有数据
     * @return 查询结果
     */
    List<RolePerms> selectAll();

    /**
     * 修改一条数据
     * @param rolePerms 修改的数据
     * @return 是否成功
     */
    Boolean updateById(RolePerms rolePerms);
}
