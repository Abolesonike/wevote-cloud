package com.fizzy.auth.service;

import com.fizzy.auth.mapper.UserRoleMapper;
import com.fizzy.core.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/10/19 17:39
 */
@Service
public class UserRoleService {
    @Autowired
    UserRoleMapper userRoleMapper;

    /**
     * 插入一条数据
     */
    public Boolean insertOne(UserRole userRole){
        return userRoleMapper.insertOne(userRole);
    }

    /**
     * 删除一条数据
     */
    public Boolean deleteById(int id){
        return userRoleMapper.deleteById(id);
    }

    /**
     * 查询指定用户的角色
     */
    public Integer selectRoleByUserId(int id){
        return userRoleMapper.selectRoleByUserId(id);
    }
    /**
     * 查询指定用户的角色
     */
    public Integer selectCommRoleByUserId(int userId, int communityId){
        return userRoleMapper.selectCommRoleByUserId(userId, communityId);
    }

    /**
     * 检查角色是否被分配
     * @param roleId 角色id
     * @return 是否被分配
     */
    public Boolean checkIsAssigned(int roleId) {
        List<UserRole> userRoles = userRoleMapper.selectByRoleId(roleId);
        return CollectionUtils.isEmpty(userRoles) || userRoles.size() == 0;
    }

    /**
     * 查询所有数据
     */
    public List<UserRole> selectAll(){
        return userRoleMapper.selectAll();
    }

    /**
     * 修改一条数据
     */
    public Boolean updateById(UserRole userRole){
        return userRoleMapper.updateById(userRole);
    }
}
