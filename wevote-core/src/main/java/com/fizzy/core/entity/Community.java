package com.fizzy.core.entity;

import lombok.Data;


/**
 * @author FizzyElf
 */
@Data
public class Community {
  private long id;

  private String name;

  private String introduction;

  private String coverUrl;

  private long status;

  private long owner;

  private long classification;

  /**
   * 非数据库字段，分类名称
   */
  private String classificationName;

  private java.sql.Timestamp creationDate;

  /**
   * 非数据库字段，创建时间开始
   */
  private java.sql.Timestamp creationDateStart;

  /**
   * 非数据库字段，创建时间结束
   */
  private java.sql.Timestamp creationDateEnd;

  /**
   * 非数据库字段，帖子数量
   */
  private int postNum;

  /**
   * 非数据库字段，用户数量
   */
  private int userNum;

}
