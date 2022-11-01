package com.example.nevs.serve.file.service.impl;

import com.example.nevs.serve.file.service.IFileService;
import com.example.nevs.util.MinionUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService implements IFileService  {
    @Autowired
    MinionUtil minioUtil;
    @SneakyThrows
    @Override
    public String uploadFile(MultipartFile uploadFile) {
        minioUtil.createBucket("car");
        minioUtil.uploadFile(uploadFile.getInputStream(), "car", uploadFile.getOriginalFilename());
        return "http:192.168.135.198/nevs/car"+uploadFile.getOriginalFilename();
    }
}
