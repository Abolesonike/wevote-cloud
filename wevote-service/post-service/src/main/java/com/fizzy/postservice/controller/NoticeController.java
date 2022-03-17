package com.fizzy.postservice.controller;

import com.fizzy.core.entity.CommunityApply;
import com.fizzy.core.entity.Notice;
import com.fizzy.postservice.service.NoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/6 11:49
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    /**
     * 插入
     * @param notice 数据
     * @return 结果
     */
    @PostMapping("/insert")
    public boolean insertOne(@RequestBody Notice notice){
        Date date = new Date();
        notice.setCreationDate(new java.sql.Timestamp(date.getTime()));
        notice.setStatus(1);
        return noticeService.insertOne(notice);
    }

    /**
     * 删除
     * @param notice 数据
     * @return 结果
     */
    @PostMapping("/delete")
    public boolean deleteOne(@RequestBody Notice notice){
        return noticeService.deleteOne(notice);
    }

    /**
     * 查询
     * @param pageNum 页大小
     * @param pageSize 页码
     * @param notice 条件
     * @return 结果
     */
    @PostMapping("/select")
    public PageInfo<Notice> select(@RequestParam int pageNum,
                                   @RequestParam int pageSize,
                                   @RequestBody Notice notice) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(noticeService.select(notice));
    }

    /**
     * 更新
     * @param notice 数据
     * @return 结果
     */
    @PutMapping("/update")
    public boolean updateOne(@RequestBody Notice notice){
        return noticeService.updateOne(notice);
    }
}