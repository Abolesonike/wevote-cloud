package com.fizzy.commentservice.service;

import com.fizzy.commentservice.feign.PostServiceFeign;
import com.fizzy.commentservice.feign.SysUserServiceFeign;
import com.fizzy.commentservice.mapper.CommentMapper;
import com.fizzy.core.entity.Comment;
import com.fizzy.core.entity.Message;
import com.fizzy.core.entity.Post;
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
        redisUtil.setSet("postHeatUpdateList", replay.getBelong());
        return commentMapper.insertOneReplay(replay);
    }

    public boolean changeStatus(Comment comment) {
        Message message = new Message();
        Date date = new Date();
        message.setCreationDate(new java.sql.Timestamp(date.getTime()));
        message.setTitle("评论状态改变通知");
        message.setUserId(comment.getFromUserId());
        message.setIsRead(2);
        StringBuilder content = new StringBuilder();
        content.append("帖子《");
        if (comment.getReplayType() == 1) {
            content.append(postFeign.postDetail(comment.getBelong()).getTitle());
        } else {
            content.append(postFeign.postDetail(findById(comment.getBelong()).getBelong()).getTitle());
        }
        content.append("》，内容：“").append(comment.getContent()).append("”。");
        switch (comment.getStatus()) {
            case 3:
                content.append("评论审核未通过，");
                content.append("理由为：”");
                content.append(comment.getStatusReason());
                content.append("“。");
                message.setContent(String.valueOf(content));
                postFeign.insertMessage(message);
                break;
            case 4:
                content.append("评论已被管理员删除，");
                content.append("理由为：”");
                content.append(comment.getStatusReason()).append("”。");
                message.setContent(String.valueOf(content));
                postFeign.insertMessage(message);
                break;
            case 5:
                content.append("评论被举报");
                content.append("理由为：");
                content.append(comment.getStatusReason());
                message.setContent(String.valueOf(content));
                postFeign.insertMessage(message);
                break;
            default:
                break;
        }
        return updateAll(comment);
    }

    public boolean updateAll(Comment comment){
        return commentMapper.updateAll(comment);
    };
    public boolean replayLikeAdd(int id){
        return commentMapper.replayLikeAdd(id);
    }
}