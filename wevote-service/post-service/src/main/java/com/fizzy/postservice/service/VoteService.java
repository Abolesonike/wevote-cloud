package com.fizzy.postservice.service;

import com.fizzy.core.entity.Vote;
import com.fizzy.postservice.mapper.VoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/11/27 16:18
 */
@Service
public class VoteService {
    @Autowired
    VoteMapper voteMapper;

    /**
     * 查询所有帖子
     * @return 帖子对象的集合
     */
    public List<Vote> findAllVote(){
        return voteMapper.findAllVote();
    }


    /**
     * 通过id查询帖子
     * @param id 要查询的id
     * @return 帖子对象
     */
    public Vote findVoteById(long id){
        return voteMapper.findVoteById(id);
    }

    /**
     * 插入一条数据
     * @param vote 插入数据
     * @return 是否成功
     */
    public boolean insertOne(Vote vote){
        return voteMapper.insertOne(vote);
    }


    /**
     * 删除一条
     * @param id 删除主键
     * @return 是否成功
     */
    public boolean deleteById(int id){
        return voteMapper.deleteById(id);
    }
}
