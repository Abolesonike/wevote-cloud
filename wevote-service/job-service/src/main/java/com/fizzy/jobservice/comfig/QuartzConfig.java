package com.fizzy.jobservice.comfig;

import com.fizzy.jobservice.job.UpdateLatestPostHeat;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/9 19:28
 */
@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail updateLatestPostHeatDetail(){
        return JobBuilder.newJob(UpdateLatestPostHeat.class)
                //PrintTimeJob我们的业务类
                .withIdentity("UpdateLatestPostHeat")
                //可以给该JobDetail起一个id
                //每个JobDetail内都有一个Map，包含了关联到这个Job的数据，在Job类中可以通过context获取
                .usingJobData("msg", "更新近一个小时的帖子热度")
                //关联键值对
                .storeDurably()
                //即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }
    @Bean
    public Trigger updateLatestPostHeatTrigger() {
        // 每小时
        // CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 */1 * * ?");
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("*/30 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(updateLatestPostHeatDetail())
                //关联上述的JobDetail
                .withIdentity("quartzTaskService")
                //给Trigger起个名字
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail updatePostHeatDetail(){
        return JobBuilder.newJob(UpdateLatestPostHeat.class)
                //PrintTimeJob我们的业务类
                .withIdentity("UpdateLatestPostHeat")
                //可以给该JobDetail起一个id
                //每个JobDetail内都有一个Map，包含了关联到这个Job的数据，在Job类中可以通过context获取
                .usingJobData("msg", "更新全部的帖子热度")
                //关联键值对
                .storeDurably()
                //即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }
    @Bean
    public Trigger updatePostHeatTrigger() {
        // 每天凌晨2点执行一次
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 2 * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(updatePostHeatDetail())
                //关联上述的JobDetail
                .withIdentity("quartzTaskService")
                //给Trigger起个名字
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}