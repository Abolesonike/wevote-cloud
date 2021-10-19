package com.fizzy.core.entity;

import lombok.Data;

/**
 * Author FizzyElf
 * Date 2021/10/19 10:07
 */
@Data
public class SysPerms {

  private long permsId;
  private String pId;
  private String menuName;
  private String path;
  private String permsCode;
  private String menuType;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp modifyTime;
  private String modifyUsername;
  private String userId;
}
