package com.fizzy.postservice.mapper;

import com.fizzy.core.entity.Community;
import org.springframework.stereotype.Repository;

/**
 * @author jinxian.ren@hand-china.com
 * @version 1.0
 * @date 2022/2/7 15:08
 */
@Repository
public interface CommunityMapper {
    /**
     * 根据id查询
     * @param id id
     * @return 结果
     */
    Community findById(long id);
}
