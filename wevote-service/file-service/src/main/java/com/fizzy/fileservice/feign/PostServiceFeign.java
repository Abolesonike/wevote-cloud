package com.fizzy.fileservice.feign;

import com.fizzy.core.entity.CommunityCovers;
import com.fizzy.core.entity.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/21 21:03
 */
@FeignClient(name = "post-service")
public interface PostServiceFeign {

    /**
     * 插入消息
     * @param communityCovers 封面
     * @return 是否成功
     */
    @PostMapping("/community/uploadCover")
    boolean uploadCover(@RequestBody CommunityCovers communityCovers);
}
