package com.fizzy.postservice.mapper;

import com.fizzy.core.entity.CommunityCovers;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/21 20:28
 */
@Mapper
public interface CommunityCoversMapper {
    /**
     * 插入一条
     * @param communityCovers 插入数据
     * @return 是否成功
     */
    boolean insertOne(CommunityCovers communityCovers);
    /**
     * 查询
     * @param communityId id
     * @return 是否成功
     */
    List<CommunityCovers> selectByCommId(int communityId);
    /**
     * 查询
     * @param communityId id
     * @param coverUrl 封面
     * @return 是否成功
     */
    List<CommunityCovers> selectByCommIdAndUrl(long communityId, String coverUrl);
    /**
     * 删除一条
     * @param id id
     * @return 是否成功
     */
    boolean deleteById(int id);
}
