package com.fizzy.fileservice.util;

import com.fizzy.fileservice.config.Constant;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.FileInputStream;

/**
 * Author FizzyElf
 * Date 2021/11/29 16:52
 */
public class QiNiuUtil {
    /**
     * 将图片上传到七牛云
     */
    public String upload(FileInputStream file, String fileType) throws Exception {
        // zone0华东区域,zone1是华北区域,zone2是华南区域
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传
        Auth auth = Auth.create(Constant.accessKey, Constant.secretKey);
        String upToken = null;
        String path = null;
        if (fileType.equals(Constant.IMAGE)) {
            upToken = auth.uploadToken(Constant.bucketPictureName);
            path = Constant.domainPicture;
        } else if (fileType.equals(Constant.FILE)) {
            upToken = auth.uploadToken(Constant.bucketFileName);
            path = Constant.domainFile;
        }
        Response response = uploadManager.put(file, null, upToken, null, null);
        // 解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return path + putRet.key;
    }
}
