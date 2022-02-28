package com.fizzy.postservice.mapper;

import com.fizzy.core.entity.CommunityClassification;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/2/28 12:31
 */
@Repository
public interface CommunityClassificationMapper {
    /**
     * 根据id查找
     * @param id id
     * @return 结果
     */
    CommunityClassification findById(int id);

    /**
     * 查询所有启用的分类
     * @param status 启用状态
     * @return 结果
     */
    List<CommunityClassification> findByAllEnable(int status);

    /**
     * 插入一条数据
     * @param communityClassification 插入数据
     * @return 是否成功
     */
    boolean insertOne(CommunityClassification communityClassification);
}
