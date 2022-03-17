package com.fizzy.jobservice.job;

import com.fizzy.core.entity.PostHeat;
import com.fizzy.jobservice.mapper.PostHeatMapper;
import com.fizzy.redis.utils.RedisUtil;

import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/12 11:09
 */
public class UpdateHeat {
    public static void updateHeat(List<PostHeat> postHeatList, RedisUtil redisUtil, PostHeatMapper postHeatMapper) {
        for (PostHeat postHeat : postHeatList) {
            redisUtil.removeZSet("postHeat:" + postHeat.getCommunityId(), postHeat.getPostId());
            redisUtil.removeZSet("postHeat", postHeat.getPostId());
            postHeat.setPost(postHeatMapper.selectById(postHeat.getPostId()));
            long postLike = redisUtil.setSize("postLike:" + postHeat.getPostId());
            long postCollection = redisUtil.setSize("postCollection:" + postHeat.getPostId());
            String s = redisUtil.get("postViewNumber:" + postHeat.getPostId());
            int view = 0;
            try {
                view = Integer.parseInt(s);
            } catch (NumberFormatException ignored){
            }
            double score = (view +10*postHeat.getComment() + 20*postLike+ 40*postCollection + 1000) / Math.pow((postHeat.getTime() + 2), 1.8);
            postHeat.setHeat(score);
            redisUtil.setZSet("postHeat:" + postHeat.getCommunityId(), postHeat.getPostId(), score);
            redisUtil.setZSet("postHeat", postHeat.getPostId(), score);
            // System.out.println(postHeat.getPostId()+":"+score);
        }
    }
}