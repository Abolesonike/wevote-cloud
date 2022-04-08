package com.fizzy.core.entity;

import lombok.Data;

/**
 *
 * @author FizzyElf
 */
@Data
public class Post {
    private int id;

    private String title;

    private long postUserId;

    /**
     * 非数据库字段，用于用户名查询
     */
    private String postUserName;

    private String content;

    private java.sql.Timestamp createTime;

    /**
     * 非数据库字段，用于时间段查询
     */
    private java.sql.Timestamp createTimeStart;

    /**
     * 非数据库字段，用于时间段查询
     */
    private java.sql.Timestamp createTimeEnd;

    private int likes;

    private int status;

    /**
     * 非数据库字段，改变帖子状态的理由
     */
    private String statusReason;

    private String votes;

    private long community;

    private int type;

    /**
     * 非数据库字段，浏览量
     */
    private int viewNumber;
}
