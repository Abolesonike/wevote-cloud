package com.fizzy.postservice.controller;

import com.fizzy.core.entity.Community;
import com.fizzy.core.entity.CommunityClassification;
import com.fizzy.core.entity.SysUser;
import com.fizzy.postservice.service.CommClassificationService;
import com.fizzy.postservice.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/2/28 12:38
 */
@RestController
@RequestMapping("/community")
public class CommunityController {
    @Autowired
    CommunityService communityService;

    @Autowired
    CommClassificationService commClassificationService;

    /**
     * 创建社区
     * @param community 社区数据
     * @return 是否成功
     */
    @PostMapping("/createCommunity")
    public boolean createCommunity(@RequestBody Community community){
        return communityService.createCommunity(community);
    }


    /**
     * 条件查询社区
     * @param community 查询条件
     * @return 查询结果
     */
    @PostMapping("/select")
    public List<Community> select(@RequestBody Community community){
        return communityService.select(community);
    }

    /**
     * 查询用户加入的社区
     * @param userId 用户id
     * @return 加入的社区
     */
    @GetMapping("/selectUserJoinedComm")
    public List<Community> selectAdminComm(@RequestParam int userId) {
        return communityService.selectAdminComm(userId);
    }

    /**
     * 查询社区的所有用户
     * @param communityId 社区id
     * @return 社区用户
     */
    @GetMapping("/selectCommAdmin")
    public List<SysUser> selectCommAdmin(@RequestParam Long communityId) {
        return communityService.selectAdmin(communityId);
    }


    /**
     * 查询所有启用的社区分类
     * @param status 状态
     * @return 结果
     */
    @GetMapping("/getAllClassification")
    public List<CommunityClassification> getAllClassification(@RequestParam int status){
        return commClassificationService.findByAllEnable(status);
    }
}