package com.fizzy.postservice.mapper;

import com.fizzy.core.entity.Community;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 条件查询
     * @param community 条件
     * @return 结果
     */
    List<Community> select(Community community);

    /**
     * 插入一条
     * @param community 插入数据
     * @return 是否成功
     */
    boolean insertOne(Community community);
}
