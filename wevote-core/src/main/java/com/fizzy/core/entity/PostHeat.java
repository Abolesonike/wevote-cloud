package com.fizzy.core.entity;

import lombok.Data;

import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/9 18:51
 */
@Data
public class PostHeat {
    private int postId;
    private int communityId;
    private int comment;
    private List<Long> likes;
    private int time;
    private List<Long> collection;
    private double heat;
    private PostVo post;
}