package com.fizzy.postservice.controller;

import com.fizzy.core.entity.Post;
import com.fizzy.core.entity.Vote;
import com.fizzy.postservice.entity.PostVo;
import com.fizzy.postservice.feign.SysUserServiceFeign;
import com.fizzy.postservice.service.PostService;
import com.fizzy.postservice.service.VoteService;
import com.fizzy.redis.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author FizzyElf
 * Creat time 2021-4-8
 * Last Edit time 2021-9-22
 * RestController注解 返回数据会被解析成json
 */
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    VoteService voteService;

    @Autowired
    SysUserServiceFeign sysUserServiceFeign;

    @Autowired
    RedisUtil redisUtil;


    static class PostVote {
        public Post post;
        public List<Vote> voteList;
        public Long postUserId;
    }

    @PostMapping("/addPost")
    public boolean addPost(@RequestBody PostVote postVote) {
        Post post = postVote.post;
        // 储存投票
        StringBuilder voteId = new StringBuilder();
        for (Vote vote : postVote.voteList) {
            // 初始化投票数量
            String[] chooses = vote.getChooses().split(",");
            StringBuilder voteNumber = new StringBuilder();
            for (String choose : chooses) {
                voteNumber.append("0,");
            }
            vote.setVoteNumber(String.valueOf(voteNumber));
            // 初始化多选数量
            if (vote.getMultiChoose() == 0) {
                vote.setMultiChoose(1);
            }
            // 初始化投票用户
            vote.setVoteUser("");
            if (!voteService.insertOne(vote)) {
                return false;
            }
            voteId.append(vote.getId());
            voteId.append(",");
        }
        //System.out.println(voteId);
        // 储存帖子
        // 创建用户
        post.setPostUserId(postVote.postUserId);
        // 创建时间
        Date nowDate = new Date();
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(nowDate.getTime());
        post.setCreateTime(sqlDate);
        // 初始化点赞
        post.setLikes(0);
        // 初始化状态
        post.setStatus(1);
        // 绑定投票
        post.setVotes(String.valueOf(voteId));
        // 初始化类型
        post.setType(0);
        return postService.insertOne(post);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Post post) {
        return postService.updateAll(post);
    }

    @GetMapping("/findById")
    public Post postDetail(@RequestParam int id) {
        return postService.findPostById(id);
    }

    @GetMapping("/postListStatus")
    public PageInfo<Post> postListStatus(@RequestParam int pageNum,
                                         @RequestParam int pageSize,
                                         @RequestParam int status) {
        PageHelper.startPage(pageNum, pageSize);

        List<Post> contentList = postService.findAllPostByStatus(status);

        return new PageInfo<>(contentList);
    }

    @GetMapping("/postVoListStatus")
    public PageInfo<PostVo> postVoListStatus(@RequestParam int pageNum,
                                             @RequestParam int pageSize,
                                             @RequestParam int status) {
        PageHelper.startPage(pageNum, pageSize);

        List<PostVo> contentList = postService.findAllPostVoByStatus(status);

        return new PageInfo<>(contentList);
    }

    @RequestMapping("/selectPostVo")
    public PageInfo<PostVo> postVoListStatus(@RequestParam int pageNum,
                                             @RequestParam int pageSize,
                                             @RequestParam int order,
                                             @RequestBody Post post) {
        PageHelper.startPage(pageNum, pageSize);
        List<PostVo> contentList = postService.selectPostVo(post);
        PageInfo<PostVo> pageInfo = new PageInfo<>(contentList);
        if (order == 1) {
            // 热度排序
            Set<Object> postHeat = null;
            if (post.getCommunity() == 0) {
                postHeat = redisUtil.getZSet("postHeat", pageNum, pageSize);
            } else {
                postHeat = redisUtil.getZSet("postHeat:" + post.getCommunity(), pageNum, pageSize);
            }
            List<PostVo> contentList1 = new ArrayList<>();
            for (Object id : postHeat) {
//                Post post1 = new Post();
//                post1.setId((Integer) id);
                List<Long> ids = new ArrayList<>();
                ids.add(Long.valueOf(String.valueOf(id)));
                PostVo postVo = postService.selectPostVoByIds(ids).get(0);
                contentList1.add(postVo);
            }
            pageInfo.setList(contentList1);
        }
        return pageInfo;
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
        Post post = postService.findPostById(id);
        // 改变状态
        post.setStatus(status);
        if (!ObjectUtils.isEmpty(reason) && !"".equals(reason)) {
            post.setStatusReason(reason);
        }
        // 更新全部字段
        return postService.changeStatus(post);
    }

    /**
     * 改变帖子的状态
     *
     * @param id     主键
     * @param type 需要改变的状态
     * @return 是否成功
     */
    @GetMapping("/changeType")
    public boolean changeType(@RequestParam int id, @RequestParam int type) {
        // 查询帖子
        Post post = postService.findPostById(id);
        // 改变状态
        post.setType(type);
        // 更新全部字段
        return postService.changeStatus(post);
    }

    /**
     * 物理删除，从数据库删除
     *
     * @param id 主键
     * @return 是否成功
     */
    @DeleteMapping("/delete")
    public boolean delete(@RequestParam int id) {
        Post post = postService.findPostById(id);
        // 删除投票
        String[] voteIds = post.getVotes().split(",");
        if (voteIds.length > 0) {
            for (String voteId : voteIds) {
                if (!"".equals(voteId)) {
                    voteService.deleteById(Integer.parseInt(voteId));
                }
            }
        }
        return postService.deleteById(id);
    }

    @GetMapping("/like")
    public Map<String, Object> like(@RequestParam int postId, @RequestParam long userId, @RequestParam int opType) {
        return postService.postOperate(postId, userId, opType, "postLike:");
    }

    @GetMapping("/collection")
    public Map<String, Object> collection(@RequestParam int postId, @RequestParam long userId, @RequestParam int opType) {
        return postService.postOperate(postId, userId, opType, "postCollection:");
    }

    @GetMapping("/myCollection")
    public PageInfo<PostVo> myCollection(@RequestParam int pageNum,
                                         @RequestParam int pageSize,
                                         @RequestParam long userId) {
        return postService.myPostLC(pageNum, pageSize, userId, "userpostCollection:");
    }

    @GetMapping("/myLike")
    public PageInfo<PostVo> myLike(@RequestParam int pageNum,
                                   @RequestParam int pageSize,
                                   @RequestParam long userId) {
        return postService.myPostLC(pageNum, pageSize, userId, "userpostLike:");
    }

    @GetMapping("/myPost")
    public PageInfo<PostVo> myPost(@RequestParam int pageNum,
                                   @RequestParam int pageSize,
                                   @RequestParam long userId) {
        Post post = new Post();
        post.setPostUserId(userId);
        PageHelper.startPage(pageNum, pageSize);
        List<PostVo> contentList = postService.selectPostVo(post);
        return new PageInfo<>(contentList);
    }
}
