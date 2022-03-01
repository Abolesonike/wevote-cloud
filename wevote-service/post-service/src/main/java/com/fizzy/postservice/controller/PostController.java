package com.fizzy.postservice.controller;

import com.fizzy.core.entity.Post;
import com.fizzy.core.entity.Vote;
import com.fizzy.postservice.entity.PostVo;
import com.fizzy.postservice.feign.SysUserServiceFeign;
import com.fizzy.postservice.feign.UserServiceFeign;
import com.fizzy.postservice.service.PostService;
import com.fizzy.postservice.service.VoteService;
import com.fizzy.userservice.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
    UserService userService;

    @Autowired
    SysUserServiceFeign sysUserServiceFeign;

    @Autowired
    UserServiceFeign userServiceFeign;

    static class PostVote {
        public Post post;
        public List<Vote> voteList;
        public Long postUserId;
    }

    @PostMapping("/addPost")
    public boolean addPost(@RequestBody PostVote postVote){
        Post post = postVote.post;
        // 储存投票
        StringBuilder voteId = new StringBuilder();
        for(Vote vote : postVote.voteList){
            // 初始化投票数量
            String[] chooses = vote.getChooses().split(",");
            StringBuilder voteNumber = new StringBuilder();
            for(String choose : chooses){
                voteNumber.append("0,");
            }
            vote.setVoteNumber(String.valueOf(voteNumber));
            // 初始化多选数量
            if(vote.getMultiChoose() == 0){
                vote.setMultiChoose(1);
            }
            // 初始化投票用户
            vote.setVoteUser("");
            if(!voteService.insertOne(vote)){
                return false;
            }
            voteId.append(vote.getId());
            voteId.append(",");
        }
        System.out.println(voteId);
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
        post.setStatus(0);
        // 绑定投票
        post.setVotes(String.valueOf(voteId));
        return postService.insertOne(post);
    }

    @GetMapping("/postList")
    public PageInfo<Post> postList(@RequestParam int pageNum, @RequestParam int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<Map<String,Object>> resultList = new ArrayList<>();
        List<Post> contentList = postService.findAllPost();
        //根据帖子找用户，一个map对应一个帖子和用户，最后返回一个map集合
        // 先不查用户，否则分页插件不能正常使用 2021/11/03
//        for(Post content : contentList){
//            Map<String, Object> map = new HashMap<>();
//            //User user = userService.findUserById(content.getPostUserId());
//            // User user = userServiceFeign.getUserById(content.getPostUserId());
//            SysUser user = sysUserServiceFeign.findById(content.getPostUserId());
//            map.put("user",user);
//            map.put("content",content);
//            resultList.add(map);
//        }

        return new PageInfo<>(contentList);
    }

    @GetMapping("/findById")
    public Post postDetail(@RequestParam int id){
        return postService.findPostById(id);
    }

    @GetMapping("/postListStatus")
    public PageInfo<Post> postListStatus(@RequestParam int pageNum,
                                         @RequestParam int pageSize,
                                         @RequestParam int status) {
        PageHelper.startPage(pageNum,pageSize);

        List<Post> contentList = postService.findAllPostByStatus(status);

        return new PageInfo<>(contentList);
    }

    @GetMapping("/postVoListStatus")
    public PageInfo<PostVo> postVoListStatus(@RequestParam int pageNum,
                                         @RequestParam int pageSize,
                                         @RequestParam int status) {
        PageHelper.startPage(pageNum,pageSize);

        List<PostVo> contentList = postService.findAllPostVoByStatus(status);

        return new PageInfo<>(contentList);
    }

    @RequestMapping("/selectPostVo")
    public PageInfo<PostVo> postVoListStatus(@RequestParam int pageNum,
                                             @RequestParam int pageSize,
                                             @RequestBody Post post) {
        PageHelper.startPage(pageNum,pageSize);

        List<PostVo> contentList = postService.selectPostVo(post);

        return new PageInfo<>(contentList);
    }

    /**
     * 改变帖子的状态
     * @param id 主键
     * @param status 需要改变的状态
     * @return 是否成功
     */
    @GetMapping("/changeStatus")
    public boolean changeStatus(@RequestParam int id, @RequestParam int status){
        // 查询帖子
        Post post = postService.findPostById(id);
        // 改变状态
        post.setStatus(status);
        // 更新全部字段
        return postService.updateAll(post);
    }

    /**
     * 物理删除，从数据库删除
     * @param id 主键
     * @return 是否成功
     */
    @DeleteMapping("/delete")
    public boolean delete(@RequestParam int id){
        Post post = postService.findPostById(id);
        // 删除投票
        String []voteIds = post.getVotes().split(",");
        if (voteIds.length > 0){
            for (String voteId : voteIds) {
                if (!"".equals(voteId)) {
                    voteService.deleteById(Integer.parseInt(voteId));
                }
            }
        }
        return postService.deleteById(id);
    }

    @GetMapping("/test")
    public List<Integer> test() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        return list;
    }
}
