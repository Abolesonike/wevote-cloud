package com.fizzy.fileservice.controller;


import com.fizzy.fileservice.config.Constant;
import com.fizzy.fileservice.service.UploadService;
import com.fizzy.fileservice.vo.BaseVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Author FizzyElf
 * Date 2021/11/29 17:08
 */
@RestController
public class UploadController {
    @Resource
    private UploadService uploadService;

    // @ApiOperation(value = "上传文件接口")
    @PostMapping(value = "/file/upload")
    public BaseVO fileUpload(MultipartFile file) throws Exception {
        return uploadService.upload(file, Constant.FILE);
    }

    // @ApiOperation(value = "上传图片接口")
    @PostMapping(value = "/image/upload")
    public BaseVO ImageUpload(MultipartFile file) throws Exception {
        return uploadService.upload(file, Constant.IMAGE);
    }
}
