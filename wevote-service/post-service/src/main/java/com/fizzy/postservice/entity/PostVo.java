package com.fizzy.postservice.entity;

import com.fizzy.core.entity.Post;
import lombok.Data;

/**
 * @author jinxian.ren@hand-china.com
 * @version 1.0
 * @date 2022/2/7 14:49
 */
@Data
public class PostVo {
    private int id;
    private String title;
    private String postUserName;
    private String content;
    private java.sql.Timestamp createTime;
    private int likes;
    private int status;
    private String votes;
    private String community;
}