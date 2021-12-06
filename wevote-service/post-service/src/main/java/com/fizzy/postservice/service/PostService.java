package com.fizzy.postservice.service;

import com.fizzy.core.entity.Post;
import com.fizzy.postservice.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * 通过id查询帖子
     * @param id 要查询的id
     * @return 帖子对象
     */
    public Post findPostById(int id){
        return postMapper.findPostById(id);
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

    /**
     * 删除一条
     * @param id 删除主键
     * @return 是否成功
     */
    public boolean deleteById(int id){
        return postMapper.deleteById(id);
    }

}
