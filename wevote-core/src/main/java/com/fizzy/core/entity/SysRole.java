package com.fizzy.core.entity;

import lombok.Data;

/**
 * Author FizzyElf
 * Date 2021/10/19 10:07
 */
@Data
public class SysRole {
  private long roleId;
  private String roleName;
  private String roleDesc;
  private java.sql.Timestamp createDate;
  private String enableStatus;
  private java.sql.Timestamp modifyTime;
}
