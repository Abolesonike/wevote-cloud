package com.fizzy.core.entity;

import lombok.Data;

/**
 * @author FizzyElf
 * @date 2022/03/05
 */
@Data
public class CommunityApply {

  private int id;
  private int applyUserId;
  private long applyCommunity;
  private java.sql.Timestamp applyTime;

  private java.sql.Timestamp applyTimeStart;

  private java.sql.Timestamp applyTimeEnd;

  private String applyReason;

  private String applyUserName;

}
