package com.fizzy.jobservice.job;

import com.fizzy.core.entity.PostHeat;
import com.fizzy.jobservice.mapper.PostHeatMapper;
import com.fizzy.redis.utils.RedisUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.fizzy.jobservice.job.UpdateHeat.updateHeat;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/9 19:24
 */
@Component
public class UpdateLatestPostHeat extends QuartzJobBean {
    @Autowired
    PostHeatMapper postHeatMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取JobDetail中关联的数据
        String msg = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("msg");
        System.out.println("current time :" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "---" + msg);
        Set<Object> postHeatUpdateList = redisUtil.getSet("postHeatUpdateList");
        redisUtil.delete("postHeatUpdateList");
        if (postHeatUpdateList.size() == 0) {
            return;
        }
        List<PostHeat> postHeatList = postHeatMapper.select(postHeatUpdateList);

        updateHeat(postHeatList, redisUtil, postHeatMapper);
        // System.out.println(postHeatList);
    }

}