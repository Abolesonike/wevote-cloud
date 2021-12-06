package com.fizzy.fileservice.service;

import com.fizzy.fileservice.vo.BaseVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author FizzyElf
 * Date 2021/11/29 16:54
 */
public interface  UploadService {
    /**
     * 文件上传
     *
     * @param file
     * @param fileType
     * @return
     * @throws Exception
     */
    BaseVO upload(MultipartFile file, String fileType) throws Exception;

}
