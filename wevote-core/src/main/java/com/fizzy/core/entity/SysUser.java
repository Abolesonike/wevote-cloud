package com.fizzy.core.entity;


import lombok.Data;

/**
 * @author FizzyElf
 * Date 2021/10/19 10:07
 * last edit 2021/11/08 12:14 添加headUrl
 *
 */
@Data
public class SysUser {
  private Long userId;

  private String username;

  private String password;

  private String headUrl;

  private java.sql.Timestamp lastLoginDate;

  private String email;

  private String tel;

  private String enableStatus;

  private java.sql.Timestamp createTime;

  private java.sql.Timestamp createTimeStart;

  private java.sql.Timestamp createTimeEnd;

  private String idCard;

  private java.sql.Timestamp modifyTime;


  /**
   * 用户角色，非数据库字段
   */
  private String roleName;

  /**
   * 加入社区时间，非数据库字段，关联社区用户表
   */
  private java.sql.Timestamp commJoinedTime;

  private java.sql.Timestamp commJoinedTimeStart;

  private java.sql.Timestamp commJoinedTimeEnd;

  /**
   * 用户社区角色，非数据库字段，关联社区用户表
   */
  private int commRole;

  /**
   * 用户在社区的发帖数，非数据库字段，关联帖子表
   */
  private int postNum;


}
