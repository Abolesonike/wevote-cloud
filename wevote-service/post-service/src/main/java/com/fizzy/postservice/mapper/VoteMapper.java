package com.fizzy.postservice.mapper;

import com.fizzy.core.entity.Vote;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/11/27 16:17
 */
@Repository
public interface VoteMapper {
    /**
     * 查找所有帖子
     */
    List<Vote> findAllVote();


    /**
     * 通过id查询帖子
     */
    Vote findVoteById(long id);

    /**
     *  插入一条
     */
    boolean insertOne(Vote vote);

    /**
     * 删除一条
     * @param id 删除主键
     * @return 是否成功
     */
    boolean deleteById(int id);
}
