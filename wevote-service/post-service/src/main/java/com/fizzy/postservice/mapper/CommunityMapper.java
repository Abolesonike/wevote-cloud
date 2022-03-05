package com.fizzy.postservice.mapper;

import com.fizzy.core.entity.Community;
import com.fizzy.core.entity.SysUser;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author jinxian.ren@hand-china.com
 * @version 1.0
 * @date 2022/2/7 15:08
 */
@Repository
public interface CommunityMapper {
    /**
     * 根据id查询
     * @param id id
     * @return 结果
     */
    Community findById(long id);

    /**
     * 条件查询
     * @param community 条件
     * @return 结果
     */
    List<Community> select(Community community);

    /**
     * 通过id更新全部内容
     * @param community 更新内容
     * @return 是否成功
     */
    boolean updateAllById(Community community);

    /**
     * 通过id删除一条
     * @param community 删除的数据
     * @return 是否成功
     */
    boolean deleteById(Community community);

    /**
     * 插入一条
     * @param community 插入数据
     * @return 是否成功
     */
    boolean insertOne(Community community);

    /**
     * 查询社区所有的成员
     * @param communityId 社区id
     * @return 社区成员
     */
    List<SysUser> selectAdmin(@RequestParam("communityId") Long communityId,
                              @RequestParam("user") SysUser user);

    /**
     * 查询用户加入的社区
     * @param userId 用户id
     * @return 加入的社区
     */
    List<Community> selectAdminComm(int userId);

    /**
     * 检查用户是否加入指定社区
     * @param communityId 社区id
     * @param userId 用户id
     * @return 社区id
     */
    List<Long> checkUserIsJoined(long communityId, int userId);
}
