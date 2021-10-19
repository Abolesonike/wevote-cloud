package com.fizzy.postservice.mapper;

import com.fizzy.core.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FizzyElf
 * Creat time 2021-4-8
 * Last Edit time 2021-9-22
 * Repository
 */
@Repository
public interface PostMapper {
    /**
     * 查找所有帖子
     */
    List<Post> findAllPost();

    /**
     * 通过id查询帖子
     */
    Post findPostById(int id);

    /**
     *  插入一条
     */
    boolean insertOne(Post content);

    /**
     * 添加投票用户
     */
    boolean addVoteUser(int userId, int id);

    /**
     * 更新投票用户
     */
    boolean updateVoteNumber(String vote_number, int id);

    /**
     * 通过id查找帖子投票数
     */
    String findVoteNumberById(int id);
}
