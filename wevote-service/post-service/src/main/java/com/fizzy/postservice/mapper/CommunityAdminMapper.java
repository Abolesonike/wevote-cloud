package com.fizzy.postservice.mapper;

import com.fizzy.core.entity.CommunityAdmin;
import com.fizzy.core.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/2/28 12:32
 */
@Repository
public interface CommunityAdminMapper {
    /**
     * 插入一条
     * @param communityAdmin 插入数据
     * @return 是否成功
     */
    boolean insertOne(CommunityAdmin communityAdmin);

}
