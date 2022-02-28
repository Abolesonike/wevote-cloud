package com.fizzy.postservice.controller;

import com.fizzy.core.entity.Community;
import com.fizzy.core.entity.CommunityClassification;
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
        // 待审核
        community.setStatus(-1);
        Date date = new Date();
        java.sql.Timestamp dateSQL = new java.sql.Timestamp(date.getTime());
        community.setCreationDate(dateSQL);
        return communityService.insertOne(community);
    }

    @PostMapping("/select")
    public List<Community> select(@RequestBody Community community){
        return communityService.select(community);
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