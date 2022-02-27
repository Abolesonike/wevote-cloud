package com.fizzy.postservice.mapper;

import com.fizzy.core.entity.Vote;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FizzyElf
 * Date 2021/11/27 16:17
 */
@Repository
public interface VoteMapper {
    /**
     * 查询所有帖子
     * @return 帖子列表
     */
    List<Vote> findAllVote();

    /**
     * 根据id查询选项
     * @param id id
     * @return 结果
     */
    String findChoosesById(long id);


    /**
     * 通过id查询帖子
     * @param id id
     * @return 结果
     */
    Vote findVoteById(long id);

    /**
     *  插入一条
     *  @param vote 擦入数据
     *  @return 是否成功
     */
    boolean insertOne(Vote vote);

    /**
     * 更新全部字段
     * @param vote 更新对象
     * @return 是否成功
     */
    boolean updateAll(Vote vote);

    /**
     * 删除一条
     * @param id 删除主键
     * @return 是否成功
     */
    boolean deleteById(int id);
}
