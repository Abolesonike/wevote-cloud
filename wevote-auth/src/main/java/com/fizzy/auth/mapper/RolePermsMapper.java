package com.fizzy.auth.mapper;

import com.fizzy.core.entity.RolePerms;
import com.fizzy.core.entity.SysPerms;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/10/19 17:19
 */
@Mapper
public interface RolePermsMapper {
    /**
     * 插入一条数据
     */
    Boolean insertOne(RolePerms RolePerms);

    /**
     * 删除一条数据
     */
    Boolean deleteById(int id);

    /**
     * 查询一条数据
     */
    List<Integer> selectPermsByRoleId(int id);

    /**
     * 查询所有数据
     */
    List<RolePerms> selectAll();

    /**
     * 修改一条数据
     */
    Boolean updateById(RolePerms rolePerms);
}
