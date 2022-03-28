package com.fizzy.postservice.service;

import com.fizzy.core.entity.CommunityAdmin;
import com.fizzy.postservice.mapper.CommunityAdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/1 10:37
 */
@Service
public class CommAdminService {
    @Autowired
    CommunityAdminMapper communityAdminMapper;

    public boolean insertOne(CommunityAdmin communityAdmin) {
        return communityAdminMapper.insertOne(communityAdmin);
    }

    public boolean update(CommunityAdmin communityAdmin) {
        return communityAdminMapper.update(communityAdmin);
    }
    public boolean delete(CommunityAdmin communityAdmin) {
        return communityAdminMapper.delete(communityAdmin);
    }
}