package com.fizzy.core.entity;

import lombok.Data;

import java.util.List;

/**
 * @author FizzyElf
 */
@Data
public class Comment {

  private int id;

  private int fromUserId;

  /**
   * 非数据库字段，发送者名称
   */
  private String fromUserName;

  private int toUserId;

  /**
   * 非数据库字段，回复对象的名称
   */
  private String toUserName;

  private String content;

  private int likeCount;

  private int replayType;

  private int belong;

  private int status;

  private java.sql.Timestamp createTime;

  /**
   * 该条回复的回复
   */
  private List<Comment> replay2replay;

}
