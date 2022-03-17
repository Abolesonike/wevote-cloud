package com.fizzy.jobservice.mapper;

import com.fizzy.core.entity.PostHeat;
import com.fizzy.core.entity.PostVo;;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/9 19:47
 */
@Component
public interface PostHeatMapper {
    /**
     * 查询半年内的帖子信息，最多10万条，防止太多
     * @return 数据
     */
    List<PostHeat> select(Set<Object> ids);

    /**
     * 根据id查询
     * @param id id
     * @return 结果
     */
    PostVo selectById(int id);
}
