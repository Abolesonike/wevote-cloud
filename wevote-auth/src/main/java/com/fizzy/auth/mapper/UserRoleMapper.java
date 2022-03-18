package com.fizzy.auth.mapper;

import com.fizzy.core.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/10/19 17:33
 */
@Mapper
public interface UserRoleMapper {
    /**
     * 插入一条数据
     */
    Boolean insertOne(UserRole userRole);

    /**
     * 删除一条数据
     */
    Boolean deleteById(int id);

    /**
     * 查询指定用户的角色
     */
    Integer selectRoleByUserId(int id);

    Integer selectCommRoleByUserId(int userId,int communityId);

    /**
     * 根据角色查询
     * @param roleId id
     * @return 结果
     */
    List<UserRole> selectByRoleId(int roleId);

    /**
     * 查询所有数据
     */
    List<UserRole> selectAll();

    /**
     * 修改一条数据
     */
    Boolean updateById(UserRole userRole);
}
