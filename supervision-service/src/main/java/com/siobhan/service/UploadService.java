package com.siobhan.service;

import com.siobhan.proxy.MyResponse;

import java.io.InputStream;

/**
 * Created by siobhan.zheng on 2019/3/28.
 */
public interface UploadService {
    /**
     *
     * @param fileName
     * @param inputStream
     * @return
     */
    MyResponse uploadExcel(String fileName, InputStream inputStream);
}
