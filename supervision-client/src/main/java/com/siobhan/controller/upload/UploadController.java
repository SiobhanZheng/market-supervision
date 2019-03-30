package com.siobhan.controller.upload;

import com.siobhan.proxy.MyResponse;
import com.siobhan.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by siobhan.zheng on 2019/3/27
 * 上传处理类
 */
@RestController
@RequestMapping("/supervision")
public class UploadController {
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private UploadService uploadService;

    @RequestMapping(value = "/uploadCompanyExcel")
    @PostMapping
    public MyResponse uploadCompanyExcel(@RequestBody MultipartFile file) {
        MyResponse response= null;
        try {
            response = uploadService.uploadExcel(file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            logger.error("uploadCompanyExcel occur", e);
        }
        return response;
    }
}
