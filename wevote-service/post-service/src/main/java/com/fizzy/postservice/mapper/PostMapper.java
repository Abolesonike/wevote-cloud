package com.fizzy.postservice.mapper;

import com.fizzy.core.entity.Post;
import com.fizzy.postservice.entity.PostVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FizzyElf
 * Creat time 2021-4-8
 * Last Edit time 2021-9-22
 * Repository
 */
@Repository
public interface PostMapper {
    /**
     * 查找所有帖子
     * @return 结果
     */
    List<Post> findAllPost();

    /**
     * 查询指定状态的所以帖子
     * @param status 状态
     * @return 帖子列表
     */
    List<Post> findAllPostByStatus(int status);

    /**
     * 查询指定状态的所以帖子
     * @param status 状态
     * @return 帖子列表
     */
    List<PostVo> findAllPostVoByStatus(int status);

    /**
     * 通过id查询帖子
     * @param id id
     * @return 结果
     */
    Post findPostById(int id);

    /**
     * 插入一条
     * @param post 插入数据
     * @return 是否成功
     */
    boolean insertOne(Post post);


    /**
     * 更新全部字段
     * @param post 跟新的对象
     * @return 是否成功
     */
    boolean updateAll(Post post);

    /**
     * 删除一条
     * @param id 删除主键
     * @return 是否成功
     */
    boolean deleteById(int id);
}
