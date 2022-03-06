package com.fizzy.postservice.mapper;

import com.fizzy.core.entity.Notice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/6 11:38
 */
@Repository
public interface NoticeMapper {
    /**
     * 插入
     * @param notice 数据
     * @return 结果
     */
    boolean insertOne(Notice notice);

    /**
     * 删除
     * @param notice 数据
     * @return 结果
     */
    boolean deleteOne(Notice notice);

    /**
     * 查询
     * @param notice 数据
     * @return 结果
     */
    List<Notice> select(Notice notice);

    /**
     * 更新
     * @param notice 数据
     * @return 结果
     */
    boolean updateOne(Notice notice);
}
