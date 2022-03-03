package com.fizzy.commentservice.mapper;

import com.fizzy.core.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/3 14:18
 */
@Repository
public interface CommentMapper {
    /**
     * 查找所有回复
     * @return 结果
     */
    List<Comment> findAllReplay();

    /**
     * 通过id查找
     * @param id id
     * @return 结果
     */
    Comment findById(int id);


    /**
     * 通过类型查找
     * @param type 类型
     * @return
     */
    List<Comment> findByReplayType(int type);

    /**
     * 通过belong和类型查找帖子找
     * int  id：用户id
     */
    List<Comment> findByReplayTypeAndBelong(int replayType, int belong);

    /**
     * 通过belong和类型查找帖子找
     * int  id：用户id
     */
    List<Comment> findBy2or3AndBelong(int belong);


    /**
     * 插入一个回复
     * @param replay 传入一个用户对象
     * @return 是否成功
     */
    Boolean insertOneReplay(Comment replay);

    Boolean replayLikeAdd(int id);
}
