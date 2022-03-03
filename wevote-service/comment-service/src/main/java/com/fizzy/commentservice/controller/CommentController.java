package com.fizzy.commentservice.controller;

import com.fizzy.commentservice.service.CommentService;
import com.fizzy.core.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @CrossOrigin
    @PostMapping("/api/replayLike")
    @ResponseBody
    public boolean replayLikeAdd(@RequestBody HashMap<String,Integer> id){
        System.out.println(id);
        return commentService.replayLikeAdd(id.get("id"));
    }
}