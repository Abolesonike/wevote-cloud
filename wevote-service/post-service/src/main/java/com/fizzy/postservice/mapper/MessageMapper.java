package com.fizzy.postservice.mapper;

import com.fizzy.core.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/6 15:16
 */
@Repository
public interface MessageMapper {
    boolean insertOne(Message message);

    boolean deleteOne(Message message);

    List<Message> select(Message message);

    boolean updateOne(Message message);
}
