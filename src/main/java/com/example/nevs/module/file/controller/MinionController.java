package com.example.nevs.module.file.controller;

import com.example.nevs.common.Fileinfo;
import com.example.nevs.util.MinionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@Api(tags = "文件操作接口")
@RestController
@RequestMapping(value = "/file")
public class MinionController {
    @Autowired
    MinionUtil minioUtil;

    @ApiOperation("上传一个文件")
    @PostMapping(value = "/uploadFile")
    public boolean fileUpload(@RequestParam MultipartFile uploadFile, @RequestParam String bucket,
                              @RequestParam(required = false) String objectName) throws Exception {
        minioUtil.createBucket(bucket);
        if (objectName != null) {
            minioUtil.uploadFile(uploadFile.getInputStream(), bucket, objectName + "/" + uploadFile.getOriginalFilename());
        } else {
            minioUtil.uploadFile(uploadFile.getInputStream(), bucket, uploadFile.getOriginalFilename());
        }
        return true;
    }

    @ApiOperation("列出所有的桶")
    @GetMapping(value = "/listBuckets")
    public List<String> listBuckets() throws Exception {
        return minioUtil.listBuckets();
    }

    @ApiOperation("递归列出一个桶中的所有文件和目录")
    @GetMapping(value = "/listFiles")
    public List<Fileinfo> listFiles(@RequestParam String bucket) throws Exception {
        return minioUtil.listFiles(bucket);
    }

    @ApiOperation("下载一个文件")
    @GetMapping(value = "/downloadFile")
    public void downloadFile(@RequestParam String bucket, @RequestParam String objectName,
                             HttpServletResponse response) throws Exception {
        InputStream stream = minioUtil.download(bucket, objectName);
        ServletOutputStream output = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(objectName.substring(objectName.lastIndexOf("/") + 1), "UTF-8"));
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        IOUtils.copy(stream, output);
    }


    @ApiOperation("删除一个文件")
    @GetMapping(value = "/deleteFile")
    public boolean deleteFile(@RequestParam String bucket, @RequestParam String objectName) throws Exception {
        minioUtil.deleteObject(bucket, objectName);
        return true;
    }

    @ApiOperation("删除一个桶")
    @GetMapping(value = "/deleteBucket")
    public boolean deleteBucket(@RequestParam String bucket) throws Exception {
        minioUtil.deleteBucket(bucket);
        return true;
    }


    @ApiOperation("复制一个文件")
    @GetMapping("/copyObject")
    public boolean copyObject(@RequestParam String sourceBucket, @RequestParam String sourceObject, @RequestParam String targetBucket, @RequestParam String targetObject) throws Exception {
        minioUtil.copyObject(sourceBucket, sourceObject, targetBucket, targetObject);
        return true;
    }

    @GetMapping("/getObjectInfo")
    @ApiOperation("获取文件信息")
    public String getObjectInfo(@RequestParam String bucket, @RequestParam String objectName) throws Exception {

        return minioUtil.getObjectInfo(bucket, objectName);
    }

    @GetMapping("/getPresignedObjectUrl")
    @ApiOperation("获取一个连接以供下载")
    public String getPresignedObjectUrl(@RequestParam String bucket, @RequestParam String objectName, @RequestParam Integer expires) throws Exception {

        return minioUtil.getPresignedObjectUrl(bucket, objectName, expires);
    }

    @GetMapping("/listAllFile")
    @ApiOperation("获取minio中所有的文件")
    public List<Fileinfo> listAllFile() throws Exception {

        return minioUtil.listAllFile();
    }
}

