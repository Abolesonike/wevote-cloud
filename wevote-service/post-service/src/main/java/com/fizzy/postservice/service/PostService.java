package com.fizzy.postservice.service;

import com.fizzy.core.entity.Message;
import com.fizzy.core.entity.Post;
import com.fizzy.postservice.entity.PostVo;
import com.fizzy.postservice.feign.SysUserServiceFeign;
import com.fizzy.postservice.mapper.PostMapper;
import com.fizzy.redis.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author FizzyElf
 * Creat time 2021-4-8
 * Last Edit time 2021-9-22
 * RestController注解 返回数据会被解析成json
 */
@Service
public class PostService {
    @Autowired
    PostMapper postMapper;

    @Autowired
    CommunityService communityService;

    @Autowired
    SysUserServiceFeign sysUserServiceFeign;

    @Autowired
    MessageService messageService;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 查询所有帖子
     * @return 帖子对象的集合
     */
    public List<Post> findAllPost(){
        return postMapper.findAllPost();
    }

    /**
     * 查询指定状态的所以帖子
     * @param status 状态
     * @return 帖子列表
     */
    public List<Post> findAllPostByStatus(int status){
        return postMapper.findAllPostByStatus(status);
    }

    /**
     * 查询指定状态的所以帖子
     * @param status 状态
     * @return 帖子列表
     */
    public List<PostVo> findAllPostVoByStatus(int status){
        return postMapper.findAllPostVoByStatus(status);
    }

    /**
     * 条件查询帖子
     * @param post 查询条件
     * @return 查询结果
     */
    public List<PostVo> selectPostVo(Post post) {
        if (post.getId() != 0) {
            String s = redisUtil.get("postViewNumber:" + post.getId());
            try {
                int view = Integer.parseInt(s) + 1;
                redisUtil.set("postViewNumber:" + post.getId(),String.valueOf(view));
                redisUtil.setSet("postHeatUpdateList", post.getId());
            } catch (NumberFormatException e){
                redisUtil.set("postViewNumber:" + post.getId(),"1");
                redisUtil.setSet("postHeatUpdateList", post.getId());
            }

        }
        return postMapper.selectPostVo(post);
    }

    /**
     * 条件查询帖子
     * @param ids 要查哪些
     * @return 查询结果
     */
    public List<PostVo> selectPostVoByIds(List<Long> ids) {
        return postMapper.selectPostVoByIds(ids);
    }

    /**
     * 通过id查询帖子
     * @param id 要查询的id
     * @return 帖子对象
     */
    public Post findPostById(int id){
        Post post = postMapper.findPostById(id);
        String s = redisUtil.get("postViewNumber:" + post.getId());
        try {
            int view = Integer.parseInt(s) + 1;
            post.setViewNumber(view);
        } catch (NumberFormatException e){
            post.setViewNumber(1);
        }
        return post;
    }

    /**
     * 插入一条数据
     * @param post 插入数据
     * @return 是否成功
     */
    public Boolean insertOne(Post post){
        return postMapper.insertOne(post);
    }

    /**
     * 更新全部字段
     * @param post 跟新的对象
     * @return 是否成功
     */
    public boolean updateAll(Post post){
        return postMapper.updateAll(post);
    }

    public boolean changeStatus(Post post) {
        Message message = new Message();
        Date date = new Date();
        message.setCreationDate(new java.sql.Timestamp(date.getTime()));
        message.setTitle("投票状态改变通知");
        message.setUserId((int) post.getPostUserId());
        message.setIsRead(2);
        StringBuilder content = new StringBuilder();
        content.append("《");
        content.append(post.getTitle());
        content.append("》");
        switch (post.getStatus()) {
            case 1:
                content.append("投票已被管理员重新提交审核。");
                message.setContent(String.valueOf(content));
                messageService.insertOne(message);
                break;
            case 2:
                content.append("投票已经审核通过。");
                message.setContent(String.valueOf(content));
                messageService.insertOne(message);
                break;
            case 3:
                content.append("投票审核未通过,");
                content.append("理由为：");
                content.append(post.getStatusReason());
                content.append("。请编辑后重新提交。");
                message.setContent(String.valueOf(content));
                messageService.insertOne(message);
                break;
            case 4:
                content.append("投票已被管理员删除。");
                content.append("理由为：");
                content.append(post.getStatusReason());
                message.setContent(String.valueOf(content));
                messageService.insertOne(message);
                break;
            case 5:
                content.append("投票被举报");
                content.append("理由为：");
                content.append(post.getStatusReason());
                message.setContent(String.valueOf(content));
                messageService.insertOne(message);
                break;
            default:
                break;
        }
        return updateAll(post);
    }

    public Map<String, Object> postOperate(int postId, long userId, int opType,String type) {
        Map<String, Object> result = new HashMap<>(2);
        boolean isOperated = redisUtil.isSetMember(type + postId, userId);
        if (opType == 0) {
            // 当userId等于0时，查询帖子的点赞数
            result.put("isOperated", isOperated);
            result.put("number",redisUtil.setSize(type+postId));
            return result;
        }
        // 判断是否点过
        if (isOperated) {
            redisUtil.removeSet(type+postId, userId);
            redisUtil.removeSet("user"+type+userId, postId);
            isOperated = redisUtil.isSetMember(type + postId, userId);
            redisUtil.setSet("postHeatUpdateList", postId);
            result.put("isOperated", isOperated);
            result.put("number",redisUtil.setSize(type+postId));
            return result;
        }
        redisUtil.setSet(type+postId, userId);
        redisUtil.setSet("user"+type+userId, postId);
        isOperated = redisUtil.isSetMember(type + postId, userId);
        redisUtil.setSet("postHeatUpdateList", postId);
        result.put("isOperated", isOperated);
        result.put("number",redisUtil.setSize(type+postId));
        return result;
    }

    public PageInfo<PostVo> myPostLC(int pageNum,
                                     int pageSize, long userId, String type) {
        Set<Object> set = redisUtil.getSet(type + userId);
        if (set.size() == 0) {
            List<Long> ids = new ArrayList<>();
            ids.add(0L);
            PageHelper.startPage(pageNum, pageSize);
            List<PostVo> postVoList = selectPostVoByIds(ids);
            return new PageInfo<>(postVoList);
        }
        List<Long> ids = new ArrayList<>();
        set.forEach((s) -> ids.add(Long.valueOf(String.valueOf(s))));
        PageHelper.startPage(pageNum, pageSize);
        List<PostVo> postVoList = selectPostVoByIds(ids);
        return new PageInfo<>(postVoList);
    }


    /**
     * 删除一条
     * @param id 删除主键
     * @return 是否成功
     */
    public boolean deleteById(int id){
        return postMapper.deleteById(id);
    }

    public PostVo getPostVo(Post post){
        PostVo postVo = new PostVo();
        postVo.setId(postVo.getId());
        postVo.setTitle(post.getTitle());
        postVo.setPostUserName(sysUserServiceFeign.findById(post.getPostUserId()).getUsername());
        postVo.setContent(post.getContent());
        postVo.setCreateTime(post.getCreateTime());
        postVo.setLikes(post.getLikes());
        postVo.setVotes(post.getVotes());
        postVo.setCommunity(communityService.findById(post.getCommunity()).getName());
        return postVo;
    }

}
