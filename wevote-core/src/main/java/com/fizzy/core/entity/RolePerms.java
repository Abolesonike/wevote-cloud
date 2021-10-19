package com.fizzy.core.entity;


import lombok.Data;

@Data
public class RolePerms {

  private long rolePermsId;
  private long roleId;
  private long permsId;

}
