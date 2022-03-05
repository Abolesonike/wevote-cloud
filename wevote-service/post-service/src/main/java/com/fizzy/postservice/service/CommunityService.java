package com.fizzy.postservice.service;

import com.fizzy.core.entity.Community;
import com.fizzy.core.entity.CommunityAdmin;
import com.fizzy.core.entity.SysUser;
import com.fizzy.postservice.mapper.CommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author jinxian.ren@hand-china.com
 * @version 1.0
 * @date 2022/2/7 15:17
 */
@Service
public class CommunityService {
    @Autowired
    CommunityMapper communityMapper;

    @Autowired
    CommAdminService commAdminService;

    public boolean insertOne(Community community) {
        return communityMapper.insertOne(community);
    }

    public List<Community> select(Community community) {
        return communityMapper.select(community);
    }

    public boolean updateAllById(Community community) {
        return communityMapper.updateAllById(community);
    }

    /**
     * 通过id删除一条
     * @param community 删除的数据
     * @return 是否成功
     */
    public boolean deleteById(Community community) {
        return communityMapper.deleteById(community);
    }

    public Community findById(long id) {
        return communityMapper.findById(id);
    }

    public boolean createCommunity(Community community) {
        // 待审核
        community.setStatus(1);
        Date date = new Date();
        java.sql.Timestamp creationDate = new java.sql.Timestamp(date.getTime());
        community.setCreationDate(creationDate);
        // 将社区插入数据库
        if (insertOne(community)) {
            // 设置社区成员关联表
            CommunityAdmin communityAdmin = new CommunityAdmin();
            communityAdmin.setCommunityId(community.getId());
            communityAdmin.setUserId(community.getOwner());
            // 设置为管理员
            communityAdmin.setType(0);
            // 将管理员插入成员表
            return  commAdminService.insertOne(communityAdmin);
        } else {
            return false;
        }
    }

    /**
     * 查询社区所有的成员
     * @param communityId 社区id
     * @return 社区成员
     */
    public List<SysUser> selectAdmin(Long communityId, SysUser user){
        return communityMapper.selectAdmin(communityId, user);
    }

    /**
     * 查询用户加入的社区
     * @param userId 用户id
     * @return 加入的社区
     */
    public List<Community> selectAdminComm(int userId){
        return communityMapper.selectAdminComm(userId);
    }

    /**
     * 查询用户是否加入色社区
     * @param communityId 社区id
     * @param userId 用户id
     * @return true or false
     */
    public boolean checkUserIsJoined(Long communityId, int userId) {
        return !CollectionUtils.isEmpty(communityMapper.checkUserIsJoined(communityId, userId));
    }
}