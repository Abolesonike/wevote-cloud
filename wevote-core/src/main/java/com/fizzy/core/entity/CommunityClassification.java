package com.fizzy.core.entity;


import lombok.Data;

/**
 *
 */
@Data
public class CommunityClassification {

  private long id;
  private String name;
  private java.sql.Timestamp creationDate;
  private long status;


}
