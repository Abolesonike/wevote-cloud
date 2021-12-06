package com.fizzy.postservice.controller;

import com.fizzy.core.entity.Vote;
import com.fizzy.postservice.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FizzyElf
 * Date 2021/11/27 16:31
 */
@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    VoteService voteService;

    @GetMapping("/findById")
    public Vote findById(@RequestParam long id){
        return voteService.findVoteById(id);
    }

    @GetMapping("/findByIds")
    public List<Vote> findByIds(@RequestParam String ids){
        String[] idList = ids.split(",");
        List<Vote> voteList = new ArrayList<>();
        for(String id : idList){
            voteList.add(voteService.findVoteById(Long.parseLong(id)));
        }
        return voteList;
    }


}
