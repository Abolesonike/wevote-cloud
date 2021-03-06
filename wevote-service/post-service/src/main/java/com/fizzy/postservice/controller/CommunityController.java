package com.fizzy.postservice.controller;

import com.fizzy.core.entity.Community;
import com.fizzy.core.entity.CommunityAdmin;
import com.fizzy.core.entity.CommunityClassification;
import com.fizzy.core.entity.CommunityCovers;
import com.fizzy.core.entity.SysUser;
import com.fizzy.core.utils.Result;
import com.fizzy.postservice.mapper.CommunityCoversMapper;
import com.fizzy.postservice.service.CommAdminService;
import com.fizzy.postservice.service.CommClassificationService;
import com.fizzy.postservice.service.CommunityService;
import com.fizzy.redis.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    CommAdminService commAdminService;

    @Autowired
    CommunityCoversMapper communityCoversMapper;


    /**
     * 创建社区
     * @param community 社区数据
     * @return 是否成功
     */
    @PostMapping("/createCommunity")
    public boolean createCommunity(@RequestBody Community community){
        return communityService.createCommunity(community);
    }


//    /**
//     * 条件查询社区
//     * @param community 查询条件
//     * @return 查询结果
//     */
//    @PostMapping("/select")
//    public List<Community> select(@RequestBody Community community){
//        return communityService.select(community);
//    }

    /**
     * 条件查询社区
     *
     * @param community 查询条件
     * @return 查询结果
     */
    @PostMapping("/select")
    public PageInfo<Community> select(@RequestParam int pageNum,
                                      @RequestParam int pageSize,
                                      @RequestBody Community community) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(communityService.select(community));
    }

    /**
     * 更新一条
     * @param community 更新数据
     * @return 是否成功
     */
    @PutMapping("/update")
    public boolean update(@RequestBody Community community) {
        return communityService.updateAllById(community);
    }

    /**
     * 删除一条
     * @param id id
     * @return 是否成功
     */
    @DeleteMapping("/delete")
    public boolean delete(@RequestParam long id) {
        Community community = new Community();
        community.setId(id);
        return communityService.deleteById(community);
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
     *
     * @param communityId 社区id
     * @return 社区用户
     */
    @PostMapping("/selectCommAdmin")
    public PageInfo<SysUser> selectCommAdmin(@RequestParam int pageNum,
                                             @RequestParam int pageSize,
                                             @RequestParam Long communityId,
                                             @RequestBody SysUser user) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(communityService.selectAdmin(communityId, user));
    }

    @GetMapping("/checkIsJoined")
    public boolean checkUserIsJoined(@RequestParam long communityId, @RequestParam int userId) {
        return communityService.checkUserIsJoined(communityId, userId);
    }

    /**
     * 查询用户管理的社区
     *
     * @param userId 用户id
     * @return 社区
     */
    @GetMapping("/managerCommunity")
    public PageInfo<Community> managerCommunity(@RequestParam int pageNum,
                                               @RequestParam int pageSize,
                                               @RequestParam int userId) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(communityService.managerCommunity(userId));
    }

    @PostMapping("/changeCommunityRole")
    public boolean changeCommunityRole(@RequestBody CommunityAdmin communityAdmin) {
        return commAdminService.update(communityAdmin);
    }

    @PostMapping("/removeMember")
    public boolean removeMember(@RequestBody CommunityAdmin communityAdmin) {
        return communityService.removeMember(communityAdmin);
    }

    @PostMapping("/uploadCover")
    public boolean uploadCover(@RequestBody CommunityCovers communityCovers) {
        return communityCoversMapper.insertOne(communityCovers);
    }

    @PutMapping("/changeCover")
    public Result changeCover(@RequestBody CommunityCovers communityCovers) {
        return communityService.changeCover(communityCovers);
    }

    @PutMapping("/changeIntroduction")
    public boolean changeIntroduction(@RequestBody Community community) {
        return communityService.updateAllById(community);
    }



}