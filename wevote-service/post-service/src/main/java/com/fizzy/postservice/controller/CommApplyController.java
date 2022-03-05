package com.fizzy.postservice.controller;

import com.fizzy.core.entity.CommunityApply;
import com.fizzy.core.utils.Result;
import com.fizzy.postservice.service.CommApplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/5 17:02
 */
@RestController
@RequestMapping("/communityApply")
public class CommApplyController {
    @Autowired
    CommApplyService commApplyService;

    @PostMapping("/insert")
    public Result insert(@RequestBody CommunityApply communityApply) {
        return commApplyService.insertOne(communityApply);
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestBody CommunityApply communityApply) {
        return commApplyService.deleteOne(communityApply);
    }

    @PostMapping("/select")
    public PageInfo<CommunityApply> select(@RequestParam int pageNum,
                                           @RequestParam int pageSize,
                                           @RequestBody CommunityApply communityApply) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(commApplyService.select(communityApply));
    }

    @PostMapping("/agree")
    public boolean agree(@RequestBody CommunityApply communityApply) {
        return commApplyService.agree(communityApply);
    }

    @PostMapping("/disagree")
    public boolean disagree(@RequestBody CommunityApply communityApply) {
        return commApplyService.disagree(communityApply);
    }

}