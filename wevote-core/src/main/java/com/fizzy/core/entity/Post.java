package com.fizzy.core.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Post {
    int id;
    String title;
    int postUserId;
    String content;
    Date postTime;
    int status;
    String choose;
    String voteNumber;
    String votedUser;
}
