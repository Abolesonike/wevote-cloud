package com.fizzy.core.entity;

import lombok.Data;


@Data
public class Post {
    private int id;
    private String title;
    private long postUserId;
    private String content;
    private java.sql.Timestamp createTime;
    private int likes;
    private int status;
    private String votes;
    private long community;
}
