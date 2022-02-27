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
}
