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
    public List<Post> findAllContent(){
        return postMapper.findAllPost();
    }

    /**
     * 通过id查询帖子
     * @param id 要查询的id
     * @return 帖子对象
     */
    public Post findContentById(int id){
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
     * 更新投票用户
     * @param userId 用户id
     * @param id 帖子id
     * @return 是否成功
     */
    public boolean addVoteUser(int userId, int id){
        return postMapper.addVoteUser(userId, id);
    }

    private String findVoteNumberById(int id){
        return postMapper.findVoteNumberById(id);
    }

    /**
     * 更新投票数量
     * @param voteNumber 新投票数
     * @param id 帖子id
     * @return 是否成功
     */
    public boolean updateVoteNumber(String voteNumber, int id){
        String[] voteList = voteNumber.split(",");
        String[] voteNumberOld = postMapper.findVoteNumberById(id).split(",");
        StringBuilder voteNumberNew = new StringBuilder();
        int index = 0;
        for( String s : voteList){
            if(s.equals("true")){
                voteNumberNew.append(Integer.parseInt(voteNumberOld[index]) + 1).append(",");
            }else{
                voteNumberNew.append(voteNumberOld[index]).append(",");
            }
            index += 1;
        }

        return postMapper.updateVoteNumber(voteNumberNew.toString(), id);
    }

}
