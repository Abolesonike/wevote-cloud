package com.fizzy.postservice.service;

import com.fizzy.core.entity.Notice;
import com.fizzy.postservice.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/6 11:47
 */
@Service
public class NoticeService {
    @Autowired
    NoticeMapper noticeMapper;

    /**
     * 插入
     * @param notice 数据
     * @return 结果
     */
    public boolean insertOne(Notice notice){
        return noticeMapper.insertOne(notice);
    }

    /**
     * 删除
     * @param notice 数据
     * @return 结果
     */
    public boolean deleteOne(Notice notice){
        return noticeMapper.deleteOne(notice);
    }

    /**
     * 查询
     * @param notice 数据
     * @return 结果
     */
    public List<Notice> select(Notice notice){
        return noticeMapper.select(notice);
    }

    /**
     * 更新
     * @param notice 数据
     * @return 结果
     */
    public boolean updateOne(Notice notice){
        return noticeMapper.updateOne(notice);
    }
}