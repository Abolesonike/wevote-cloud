package com.fizzy.core.entity;

import lombok.Builder;
import lombok.Data;

import java.beans.Transient;
import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/10/19 10:07
 */
@Data
public class SysPerms {

  private int permsId;
  private int pId;
  private String menuName;
  private String path;
  private String permsCode;
  private String menuType;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp modifyTime;
  private String modifyUsername;
  private String userId;
  /**
   * 非数据库字段，是否有子权限
   */
  private boolean hasChildren;
  /**
   * 非数据库字段，子权限
   */
  private List<SysPerms> children;
  /**
   * 非数据库字段，当前角色是否有该权限
   */
  private boolean isHave;

  private java.sql.Timestamp createTimeStart;

  private java.sql.Timestamp createTimeEnd;

}
