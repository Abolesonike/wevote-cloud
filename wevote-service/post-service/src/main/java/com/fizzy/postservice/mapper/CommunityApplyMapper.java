package com.fizzy.postservice.mapper;

import com.fizzy.core.entity.CommunityApply;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/5 16:47
 */
@Repository
public interface CommunityApplyMapper {
    boolean insertOne(CommunityApply communityApply);

    boolean deleteOne(CommunityApply communityApply);

    List<CommunityApply> select(CommunityApply communityApply);
}
