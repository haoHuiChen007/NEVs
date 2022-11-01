package com.example.nevs.serve.file.service;

import com.example.nevs.common.Fileinfo;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService  {

    String uploadFile(MultipartFile uploadFile);
}
