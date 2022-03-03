package com.fizzy.postservice.service;

import com.fizzy.core.entity.CommunityClassification;
import com.fizzy.postservice.mapper.CommunityClassificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/2/28 12:36
 */
@Service
public class CommClassificationService {
    @Autowired
    CommunityClassificationMapper commClassificationMapper;

    /**
     * 根据id查找
     *
     * @param id id
     * @return 结果
     */
    public CommunityClassification findById(int id) {
        return commClassificationMapper.findById(id);
    }
    /**
     * 查询所有启用的分类
     *
     * @param status 启用状态
     * @return 结果
     */
    public List<CommunityClassification> findByAllEnable(int status){
        return commClassificationMapper.findByAllEnable(status);
    }

    /**
     * 条件查询
     * @param communityClassification 查询条件
     * @return 结果
     */
    public List<CommunityClassification> select(CommunityClassification communityClassification) {
        return commClassificationMapper.select(communityClassification);
    }

    /**
     * 更新全部
     * @param classification 更新数据
     * @return 是否成功
     */
    public boolean updateAllById(CommunityClassification classification){
        return commClassificationMapper.updateAllById(classification);
    }


    /**
     * 插入一条数据
     *
     * @param communityClassification 插入数据
     * @return 是否成功
     */
    public boolean insertOne(CommunityClassification communityClassification){
        if(commClassificationMapper.findByName(communityClassification.getName()) != null) {
            return false;
        }
        return commClassificationMapper.insertOne(communityClassification);
    }

    /**
     * 删除一条
     * @param id 删除id
     * @return 是否成功
     */
    public boolean deleteOne(int id) {
        return commClassificationMapper.deleteOne(id);
    }
}
