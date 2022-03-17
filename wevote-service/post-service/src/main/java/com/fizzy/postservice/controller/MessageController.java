package com.fizzy.postservice.controller;

import com.fizzy.core.entity.Message;
import com.fizzy.core.entity.Notice;
import com.fizzy.postservice.service.MessageService;
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

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/6 15:40
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping("/insert")
    public boolean insertOne(@RequestBody Message message) {
        return messageService.insertOne(message);
    }


    /**
     * 删除
     * @param message 数据
     * @return 结果
     */
    @DeleteMapping("/delete")
    public boolean deleteOne(@RequestBody Message message){
        return messageService.deleteOne(message);
    }

    /**
     * 查询
     * @param pageNum 页大小
     * @param pageSize 页码
     * @param message 条件
     * @return 结果
     */
    @PostMapping("/select")
    public PageInfo<Message> select(@RequestParam int pageNum,
                                   @RequestParam int pageSize,
                                   @RequestBody Message message) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(messageService.select(message));
    }

    /**
     * 更新
     * @param message 数据
     * @return 结果
     */
    @PutMapping("/update")
    public boolean updateOne(@RequestBody Message message){
        return messageService.updateOne(message);
    }
}