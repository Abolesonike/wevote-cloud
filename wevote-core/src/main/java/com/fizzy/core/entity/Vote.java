package com.fizzy.core.entity;

import lombok.Data;

/**
 * Author FizzyElf
 * Date 2021/11/27 16:10
 */
@Data
public class Vote {
    private long id;
    private String title;
    private String chooses;
    private String voteNumber;
    private String voteUser;
    private int multiChoose;
    private java.sql.Timestamp endDate;
}
