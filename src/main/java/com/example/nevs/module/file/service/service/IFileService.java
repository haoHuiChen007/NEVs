package com.example.nevs.module.file.service.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    /*文件上传*/
    String uploadFile(MultipartFile uploadFile, String fileName, String bucket);
    /*文件下载*/
    void downloadFile(String fileName);

}
