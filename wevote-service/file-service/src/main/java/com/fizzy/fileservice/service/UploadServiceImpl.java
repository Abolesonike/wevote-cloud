package com.fizzy.fileservice.service;

import com.fizzy.fileservice.config.Constant;
import com.fizzy.fileservice.util.QiNiuUtil;
import com.fizzy.fileservice.vo.BaseVO;
import com.fizzy.fileservice.vo.FileVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

/**
 * Author FizzyElf
 * Date 2021/11/29 17:01
 */
@Service
public class UploadServiceImpl implements UploadService{
    @Override
    public BaseVO upload(MultipartFile file, String fileType) throws Exception {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            //log.error("传入的文件名不能为空");
            return new BaseVO(false, "400", "传入的文件名不能为空");
        }
        if (!this.validateFileName(fileName)) {
            //log.error("文件名应仅包含汉字、字母、数字、下划线和点号");
            return new BaseVO(false, "400", "文件名应仅包含汉字、字母、数字、下划线和点号");
        }
        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
        String url = "";
        if (fileType.equals(Constant.IMAGE)) {
            url = new QiNiuUtil().upload(fileInputStream, Constant.IMAGE);
        } else if (fileType.equals(Constant.FILE)) {
            url = new QiNiuUtil().upload(fileInputStream, Constant.FILE);
        }
        FileVO fileVO = new FileVO();
        fileVO.setDownloadUrl(url);
        return fileVO;
    }

    /**
     * 验证文件名称：仅包含 汉字、字母、数字、下划线和点号
     *
     * @param fileName 文件名称
     * @return 返回true表示符合要求
     */
    private boolean validateFileName(String fileName) {
        String regex = "^[a-zA-Z0-9_\\u4e00-\\u9fa5_\\.\\s]+$";
        return fileName.matches(regex);
    }
}
