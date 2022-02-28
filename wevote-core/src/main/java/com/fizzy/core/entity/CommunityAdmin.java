package com.fizzy.core.entity;


import lombok.Data;

@Data
public class CommunityAdmin {

  private long id;
  private long communityId;
  private long userId;
  private long type;

}
