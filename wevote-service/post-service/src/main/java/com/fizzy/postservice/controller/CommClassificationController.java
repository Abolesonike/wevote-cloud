package com.fizzy.postservice.controller;

import com.fizzy.core.entity.CommunityClassification;
import com.fizzy.postservice.service.CommClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/2 19:34
 */
@RestController
@RequestMapping("/communityClassification")
public class CommClassificationController {
    @Autowired
    CommClassificationService commClassificationService;

    @PostMapping("/select")
    public List<CommunityClassification> select(@RequestBody CommunityClassification classification) {
        return commClassificationService.select(classification);
    }

    @PutMapping("/update")
    public boolean update(@RequestBody CommunityClassification classification) {
        return commClassificationService.updateAllById(classification);
    }

    @DeleteMapping("/deleteOne")
    public boolean deleteOne(@RequestParam int id) {
        return commClassificationService.deleteOne(id);
    }

    @PostMapping("/insertOne")
    public boolean insertOne(@RequestBody CommunityClassification classification) {
        Date date = new Date();
        java.sql.Timestamp creationDate = new java.sql.Timestamp(date.getTime());
        classification.setCreationDate(creationDate);
        return commClassificationService.insertOne(classification);
    }
}