package com.fizzy.postservice.service;

import com.fizzy.core.entity.Community;
import com.fizzy.postservice.mapper.CommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean insertOne(Community community) {
        return communityMapper.insertOne(community);
    }

    public List<Community> select(Community community) {
        return communityMapper.select(community);
    }

    public Community findById(long id) {
        return communityMapper.findById(id);
    }
}