package com.fizzy.auth.service;

import com.fizzy.auth.entity.PermsTreeNode;
import com.fizzy.auth.mapper.SysPermsMapper;
import com.fizzy.core.entity.RolePerms;
import com.fizzy.core.entity.SysPerms;
import com.fizzy.core.entity.SysRole;
import com.fizzy.core.utils.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author FizzyElf
 * Date 2021/10/19 11:47
 */
@Service
public class SysPermsService {
    @Autowired
    SysPermsMapper sysPermsMapper;

    @Autowired
    RolePermsService rolePermsService;

    /**
     * 插入一条数据
     */
    public Result insertOne(SysPerms sysPerms){
        if (!insertAble(sysPerms.getPId())) {
            return new Result(401,"父级权限已经被分配，不能添加子权限！");
        }
        if (sysPerms.getPId() != 0){
            // 不为第一级，就拼接路径
            String parentPath = sysPermsMapper.selectByPermsId(sysPerms.getPId()).getPath();
            sysPerms.setPath(parentPath + sysPerms.getPath());
        }
        // 检查路径是否重复
        if (sysPermsMapper.selectByPath(sysPerms.getPath()) != null){
            return new Result(401,"路径重复");
        }
        // 创建时间
        Date nowDate = new Date();
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(nowDate.getTime());
        sysPerms.setCreateTime(sqlDate);
        if (sysPermsMapper.insertOne(sysPerms)) {
            return new Result(200,"添加成功！");
        } else {
            return new Result(401, "添加失败");
        }
    }

    private Boolean insertAble(int permsId){
        // 检查是否能插入
        if(permsId == 0){
            // 添加一级权限，能够添加
            return true;
        } else {
            // 添加子权限
            // 检查父级权限是否分配
            return CollectionUtils.isEmpty(rolePermsService.selectByPermsId(permsId));
        }
    }

    /**
     * 删除一条数据
     */
    public Boolean deleteByPermsId(int permsId){
        // 删除角色权限表里面含有该权限的数据
        rolePermsService.deleteByPermsId(permsId);
        return sysPermsMapper.deleteByPermsId(permsId);
    }


    /**
     * 查询一条数据
     */
    public SysPerms selectByPermsId(int permsId){
        return sysPermsMapper.selectByPermsId(permsId);
    }

    /**
     * 查询所有数据
     */
    public List<SysPerms> selectAll(){
        return sysPermsMapper.selectAll();
    }

    /**
     * 查询一级菜单
     */
    public List<SysPerms> selectParent(){
        List<SysPerms> permsList = sysPermsMapper.selectParent();
        for (SysPerms sysPerms : permsList){
            // 检查是否有子路径
            List<SysPerms> childrenPermsList = sysPermsMapper.selectChildren(sysPerms.getPermsId());
            sysPerms.setHasChildren(childrenPermsList.size() > 0);
        }
        return permsList;
    }

    /**
     * 查询子菜单
     * @param parentId 父菜单id
     * @return 子菜单集合
     */
    public List<SysPerms> selectChildren(int parentId){
        List<SysPerms> permsList = sysPermsMapper.selectChildren(parentId);
        for (SysPerms sysPerms : permsList){
            // 检查是否有子路径
            List<SysPerms> childrenPermsList = sysPermsMapper.selectChildren(sysPerms.getPermsId());
            sysPerms.setHasChildren(childrenPermsList.size() > 0);

        }
        return permsList;
    }

    /**
     * 树形节点权限，父权限节点
     * @return 父节点
     */
    public List<PermsTreeNode> selectParentNode(int roleId) {
        List<SysPerms> permsList = selectParent();
        return getPermsTreeNodes(permsList, roleId);
    }

    /**
     * 树形节点权限，子权限节点
     * @return 父节点
     */
    public List<PermsTreeNode> selectChildrenNode(String parentPath, int roleId) {
        int parentId = selectByPath(parentPath).getPermsId();
        List<SysPerms> permsList = selectChildren(parentId);
        return getPermsTreeNodes(permsList, roleId);
    }

    private List<PermsTreeNode> getPermsTreeNodes(List<SysPerms> permsList, int roleId) {
        List<Integer> permsIds = rolePermsService.selectPermsIdByRoleId(roleId);
        List<PermsTreeNode> permsTreeNodes = new ArrayList<>();
        for (SysPerms sysPerms : permsList){
            PermsTreeNode permsTreeNode = new PermsTreeNode();
            permsTreeNode.setPermsId(sysPerms.getPermsId());
            permsTreeNode.setName(sysPerms.getPath());
            // 检查是否有子路径
            List<SysPerms> childrenPermsList = selectChildren(sysPerms.getPermsId());
            permsTreeNode.setLeaf(CollectionUtils.isEmpty(childrenPermsList));
            if (permsIds.contains(sysPerms.getPermsId())){
                permsTreeNode.setHavePerms(true);
            }
            permsTreeNodes.add(permsTreeNode);
        }
        return permsTreeNodes;
    }

    /**
     * 根据path查询
     * @param path path
     * @return 结果
     */
    public SysPerms selectByPath(String path) {
        return sysPermsMapper.selectByPath(path);
    }

    /**
     * 查询所有权限，以树形数据封装
     * @return 查询结果
     */
    public Map<String, Object> selectAllTree(int roleId){
        // 当前角色拥有的权限
        List<Integer> permsIds = rolePermsService.selectPermsIdByRoleId(roleId);
        // 所有权限
        List<SysPerms> permsList = sysPermsMapper.selectAll();
        // 第一层节点
        List<SysPerms> parentList = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>(200);
        if (!CollectionUtils.isEmpty(permsList)) {
            parentList = permsList.stream().filter(item -> item.getPId() == 0)
                    .peek(item -> {
                        item.setChildren(getChildren(permsList, item, permsIds));
                        if (permsIds.contains(item.getPermsId())){
                            item.setHave(true);
                        }
                    })
                    .collect(Collectors.toList());
        }
        resultMap.put("permsIds", permsIds);
        resultMap.put("parentList", parentList);
        return resultMap;
    }

    /**
     * 递归查询子权限
     * @param permsList 所有权限
     * @param parentSysPerms 父权限
     * @param permsIds 当前角色拥有的权限
     * @return 结果
     */
    private List<SysPerms> getChildren(List<SysPerms> permsList,
                                       SysPerms parentSysPerms,
                                       List<Integer> permsIds) {
        return permsList.stream()
                .filter(item -> item.getPId() == parentSysPerms.getPermsId())
                .peek(item -> {
                    item.setChildren(this.getChildren(permsList, item, permsIds));
                    if (permsIds.contains(item.getPermsId())) {
                        item.setHave(true);
                    }
                }).collect(Collectors.toList());
    }


    /**
     * 修改一条数据
     */
    public Boolean updateById(SysPerms sysPerms){
        return sysPermsMapper.updateById(sysPerms);
    }
}
