package com.fizzy.jobservice.feign;

import com.fizzy.core.entity.Message;
import com.fizzy.core.entity.Post;
import com.fizzy.core.entity.PostVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/7 16:29
 */
@FeignClient(name = "post-service")
public interface PostServiceFeign {

    /**
     * 查询帖子
     * @param pageNum 页码
     * @param pageSize 页大小
     * @param post 条件
     * @return 结果
     */
    @PostMapping("/post/select")
    PageInfo<PostVo> postVoListStatus(@RequestParam int pageNum,
                                      @RequestParam int pageSize,
                                      @RequestBody Post post);
}