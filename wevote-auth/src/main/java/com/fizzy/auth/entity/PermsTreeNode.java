package com.fizzy.auth.entity;

import lombok.Data;

import java.util.List;

/**
 * @author jinxian.ren@hand-china.com
 * @version 1.0
 * @date 2022/2/15 14:53
 */
@Data
public class PermsTreeNode {
    private int permsId;
    private String name;
    private boolean leaf = false;
    private boolean havePerms = false;
    private int havePermsNum;
    private List<PermsTreeNode> children;
}