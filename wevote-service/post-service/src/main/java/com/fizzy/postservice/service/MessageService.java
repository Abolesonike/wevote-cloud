package com.fizzy.postservice.service;

import com.fizzy.core.entity.Message;
import com.fizzy.postservice.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/6 15:21
 */
@Service
public class MessageService {
    @Autowired
    MessageMapper messageMapper;

    public boolean insertOne(Message message){
        return messageMapper.insertOne(message);
    }

    public boolean deleteOne(Message message){
        return messageMapper.deleteOne(message);
    }

    public List<Message> select(Message message) {
        return messageMapper.select(message);
    }

    public boolean updateOne(Message message) {
        return messageMapper.updateOne(message);
    }
}