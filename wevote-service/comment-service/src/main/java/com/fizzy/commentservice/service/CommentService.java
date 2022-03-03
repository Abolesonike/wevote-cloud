package com.fizzy.commentservice.service;

import com.fizzy.commentservice.mapper.CommentMapper;
import com.fizzy.core.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/3 14:27
 */
@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;


    /**
     * 查询所有回复
     * @return 用户对象的集合
     */
    public List<Comment> findAllReplay(){
        return commentMapper.findAllReplay();
    }


    public Comment findById(int id) {
        return commentMapper.findById(id);
    }


    public List<Comment> findByReplayType(int type){
        return commentMapper.findByReplayType(type);
    }


    public List<Comment> findByReplayTypeAndBelong(int replayType, int belong) {
        return commentMapper.findByReplayTypeAndBelong(replayType, belong);
    }

    public List<Comment> findBy2or3AndBelong(int belong) {
        return commentMapper.findBy2or3AndBelong(belong);
    }



    public boolean insertOneReplay(Comment replay){
        return commentMapper.insertOneReplay(replay);
    }

    public boolean replayLikeAdd(int id){
        return commentMapper.replayLikeAdd(id);
    }
}