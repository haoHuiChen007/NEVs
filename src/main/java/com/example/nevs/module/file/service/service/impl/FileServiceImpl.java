package com.example.nevs.module.file.service.service.impl;

import com.example.nevs.common.Fileinfo;
import com.example.nevs.module.file.service.service.IFileService;
import com.example.nevs.util.MinionUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    MinionUtil minioUtil;

    @SneakyThrows
    @Override
    public String uploadFile(MultipartFile uploadFile, String fileName, String bucket) {
        minioUtil.createBucket(bucket);
        if (fileName != null) {
            fileName = fileName + "/" + uploadFile.getOriginalFilename();
            minioUtil.uploadFile(uploadFile.getInputStream(), bucket, fileName + "/" + uploadFile.getOriginalFilename());
        } else {
            minioUtil.uploadFile(uploadFile.getInputStream(), bucket, uploadFile.getOriginalFilename());
        }
        Fileinfo fileinfo = new Fileinfo();
        fileinfo.setFilename(fileName).setBucket(bucket);
        return fileName;
    }

    @Override
    public void downloadFile(String fileName) {

    }
}
