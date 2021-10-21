package com.fizzy.postservice.controller;

import com.fizzy.core.entity.Post;
import com.fizzy.core.entity.User;
import com.fizzy.core.utils.Result;
import com.fizzy.postservice.feign.UserServiceFeign;
import com.fizzy.postservice.service.PostService;
import com.fizzy.userservice.service.UserService;
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
    UserService userService;

    @Autowired
    UserServiceFeign userServiceFeign;

    @CrossOrigin
    @GetMapping("/postList")
    public List<Map<String,Object>> postList() {
        List<Map<String,Object>> resultList = new ArrayList<>();
        List<Post> contentList = postService.findAllContent();
        //根据帖子找用户，一个map对应一个帖子和用户，最后返回一个map集合
        for(Post content : contentList){
            Map<String, Object> map = new HashMap<>();
            //User user = userService.findUserById(content.getPostUserId());
            User user = userServiceFeign.getUserById(content.getPostUserId());
            map.put("user",user);
            map.put("content",content);
            resultList.add(map);
        }
        return resultList;
    }

    @CrossOrigin
    @GetMapping("/detail/{id}")
    public HashMap<String,Object> detail(@PathVariable("id") int id) {
        // 返回给前端的数据
        HashMap<String, Object> resultMap = new HashMap<>();
        // 根据前端的帖子id，查询帖子
        Post post = postService.findContentById(id);
        resultMap.put("post", post);

        // 分割选项
        String[] choose = post.getChoose().split(",");
        resultMap.put("choose", choose);


        // 分割投票数
        String[] voteNumber = post.getVoteNumber().split(",");
        ArrayList<Integer> arrayList = new ArrayList<>();
        for(String s : voteNumber){
            // 转化为int
            arrayList.add(Integer.parseInt(s));
        }
        resultMap.put("voteNumber", arrayList);

        return resultMap;
    }

    @CrossOrigin
    @PostMapping("/postVote")
    public Result postVote(@RequestBody HashMap<String, Object> vote){
        System.out.println(vote);
        Post content = new Post();
        // 标题
        content.setTitle(vote.get("title").toString());
        // 发布者id
        content.setPostUserId((Integer) vote.get("userId"));
        // 发布时间
        content.setContent(vote.get("content").toString());
        Date dNow = new Date( );
        content.setPostTime(dNow);
        // 选项，投票数
        StringBuilder choose = new StringBuilder();
        StringBuilder vote_number = new StringBuilder();
        String tags = vote.get("tags").toString();
        String[] tag = tags.substring(1, tags.length() -1).replace(" ","").split(",");
        for(String s : tag){
            // 选项
            choose.append(s).append(",");
            // 投票数
            vote_number.append("0,");
        }
        content.setChoose(String.valueOf(choose));
        content.setVoteNumber(String.valueOf(vote_number));
        // 默认投票用户0
        content.setVotedUser("0,");
        // 状态
        content.setStatus(0);



        if(postService.insertOne(content)){
            // 数据库添加成功后，ES也加一条
            //JSONObject jsonObject = (JSONObject) JSONObject.toJSON(content);
            //String result = ElasticsearchUtil.addData(jsonObject,"content","content_doc",String.valueOf(content.getId()));
            //if(result.equals("")){
               // return new Result(400);
            //}
            return new Result(200);

        }else{
            return new Result(400);
        }
    }

    @CrossOrigin
    @PostMapping("/vote")
    public boolean vote(@RequestBody HashMap<String, Object> getId){
        int id = (int) getId.get("id");
        int userId = (int) getId.get("userId");
        String vote_number = (String) getId.get("voteNumber");
        return (postService.addVoteUser(userId, id) & postService.updateVoteNumber(vote_number, id));
    }

    @CrossOrigin
    @GetMapping("/test")
    public List<Integer> test() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        return list;
    }
}
