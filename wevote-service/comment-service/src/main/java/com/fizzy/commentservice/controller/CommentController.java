package com.fizzy.commentservice.controller;

import com.fizzy.commentservice.service.CommentService;
import com.fizzy.core.entity.Comment;
import com.fizzy.core.entity.Post;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/3 14:29
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/select")
    public PageInfo<Comment> select(@RequestParam int pageNum,
                                    @RequestParam int pageSize,
                                    @RequestBody Comment comment) {
        PageHelper.startPage(pageNum, pageSize);

        List<Comment> contentList = commentService.select(comment);

        return new PageInfo<>(contentList);
    }

    @GetMapping("/commentList")
    public List<Comment> list(@RequestParam("id") int id) {

        // 查帖子的回复
        List<Comment> commentList = commentService.findByReplayTypeAndBelong(1,id);
        for(Comment comment : commentList){
            // 回复的回复
            List<Comment> replay2replayList = commentService.findBy2or3AndBelong(comment.getId());
            comment.setReplay2replay(replay2replayList);
        }
        return commentList;
    }


    @PostMapping("/sendComment")
    public boolean sendReplay(@RequestBody Comment comment){
        comment.setLikeCount(0);
        comment.setStatus(1);
        Date date = new Date();
        java.sql.Timestamp creationDate = new java.sql.Timestamp(date.getTime());
        comment.setCreateTime(creationDate);
        return commentService.insertOneReplay(comment);
    }

    /**
     * 改变帖子的状态
     *
     * @param id     主键
     * @param status 需要改变的状态
     * @return 是否成功
     */
    @GetMapping("/changeStatus")
    public boolean changeStatus(@RequestParam int id, @RequestParam int status, @RequestParam String reason) {
        // 查询帖子
        Comment comment = commentService.findById(id);
        // 改变状态
        comment.setStatus(status);
        if (!ObjectUtils.isEmpty(reason) && !"".equals(reason)) {
            comment.setStatusReason(reason);
        }
        // 更新全部字段
        return commentService.changeStatus(comment);
    }

    @PostMapping("/api/replayLike")
    @ResponseBody
    public boolean replayLikeAdd(@RequestBody HashMap<String,Integer> id){
        System.out.println(id);
        return commentService.replayLikeAdd(id.get("id"));
    }
}