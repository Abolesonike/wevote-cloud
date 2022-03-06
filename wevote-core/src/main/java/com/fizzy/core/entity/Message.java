package com.fizzy.core.entity;

import lombok.Data;

/**
 * @author FizzyElf
 * @date 2022/03/06
 */
@Data
public class Message {

  private int id;
  private int userId;
  private String content;
  private String title;
  private int isRead;
  private java.sql.Timestamp creationDate;


}
