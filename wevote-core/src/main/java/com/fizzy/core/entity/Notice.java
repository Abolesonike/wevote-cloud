package com.fizzy.core.entity;

import lombok.Data;

/**
 * @author FizzyElf
 * @date 2022/03/06
 */
@Data
public class Notice {

  private int id;
  private int postUserId;
  private String postUserName;
  private String title;
  private String content;
  private int community;
  private int status;
  private java.sql.Timestamp creationDate;
  private java.sql.Timestamp creationDateStart;
  private java.sql.Timestamp creationDateEnd;



}
