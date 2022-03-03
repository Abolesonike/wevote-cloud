package com.fizzy.core.entity;


import lombok.Data;

/**
 * @author FizzyElf
 */
@Data
public class CommunityClassification {

  private long id;
  private String name;
  private java.sql.Timestamp creationDate;
  private long status;

  /**
   * 非数据库字段，社区数量
   */
  private int communityNum;

  private java.sql.Timestamp creationDateStart;
  private java.sql.Timestamp creationDateEnd;
}
