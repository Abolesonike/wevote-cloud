package com.fizzy.commentservice.service;

import com.fizzy.commentservice.feign.PostServiceFeign;
import com.fizzy.commentservice.feign.SysUserServiceFeign;
import com.fizzy.commentservice.mapper.CommentMapper;
import com.fizzy.core.entity.Comment;
import com.fizzy.core.entity.Message;
import com.fizzy.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    PostServiceFeign postFeign;

    @Autowired
    SysUserServiceFeign userFeign;

    @Autowired
    RedisUtil redisUtil;


    /**
     * 查询所有回复
     * @return 用户对象的集合
     */
    public List<Comment> select(Comment comment){
        return commentMapper.select(comment);
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
        if(replay.getReplayType() != 1) {
            // 获取回复的评论内容
            Comment comment = null;
            int postId = 0;
            if (replay.getReplayType() == 2) {
                comment = findById(replay.getBelong());
                postId = comment.getBelong();
            } else {
                comment = findById(replay.getToComment());
                postId = findById(comment.getBelong()).getBelong();
            }
            // 发送消息
            Message message = new Message();
            Date date = new Date();
            message.setCreationDate(new java.sql.Timestamp(date.getTime()));
            message.setTitle("评论回复通知");
            message.setUserId(replay.getToUserId());
            message.setIsRead(2);
            String content = "<p><span style=\"font-weight: bold\">" + userFeign.findById(replay.getFromUserId()).getUsername() +
                    "</span>回复了你的评论</p><p>" + comment.getContent() + "</p><p>回复：</p>" + replay.getContent()
                    + "<br /><br /><a style=\"color:#252525; text-decoration:none;\"  href=\"/postDetail/" + postId + "\">" + "去往投票>></a>";

            message.setContent(content);
            postFeign.insertMessage(message);
        }
        // redisUtil.setSet("postHeatUpdateList", replay.getBelong());
        return commentMapper.insertOneReplay(replay);
    }

    public boolean replayLikeAdd(int id){
        return commentMapper.replayLikeAdd(id);
    }
}