package com.fizzy.core.entity;


import lombok.Data;

/**
 * Author FizzyElf
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
  private String idcard;
  private java.sql.Timestamp modifyTime;

}
