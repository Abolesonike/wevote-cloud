package com.fizzy.postservice.service;

import com.fizzy.core.entity.CommunityAdmin;
import com.fizzy.core.entity.CommunityApply;
import com.fizzy.core.utils.Result;
import com.fizzy.postservice.mapper.CommunityApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/5 16:59
 */
@Service
public class CommApplyService {
    @Autowired
    CommunityApplyMapper communityApplyMapper;

    @Autowired
    CommAdminService commAdminService;

    public Result insertOne(CommunityApply communityApply){
        // 检查是否已经申请，避免重复申请
        CommunityApply communityApply1 = new CommunityApply();
        communityApply1.setApplyUserId(communityApply.getApplyUserId());
        communityApply1.setApplyCommunity(communityApply.getApplyCommunity());
        if (!CollectionUtils.isEmpty(select(communityApply1))) {
            return new Result(401,"已经提交申请，请等待管理员审核！");
        }
        Date date = new Date();
        communityApply.setApplyTime(new java.sql.Timestamp(date.getTime()));
        if(communityApplyMapper.insertOne(communityApply)) {
            return new Result(200,"申请成功，请等待管理员审核！");
        } else {
            return new Result(500,"申请失败，请重新提交！");
        }
    }

    public boolean deleteOne(CommunityApply communityApply){
        return communityApplyMapper.deleteOne(communityApply);
    }

    public List<CommunityApply> select(CommunityApply communityApply){
        return communityApplyMapper.select(communityApply);
    }

    public boolean agree(CommunityApply communityApply){
        CommunityAdmin commAdmin = new CommunityAdmin();
        commAdmin.setType(3);
        commAdmin.setUserId(communityApply.getApplyUserId());
        commAdmin.setCommunityId(communityApply.getApplyCommunity());
        Date date = new Date();
        commAdmin.setJoinTime(new java.sql.Timestamp(date.getTime()));
        if(commAdminService.insertOne(commAdmin)) {
            return communityApplyMapper.deleteOne(communityApply);
        } else {
            return false;
        }
    }

    public boolean disagree(CommunityApply communityApply) {
        return communityApplyMapper.deleteOne(communityApply);
    }
}