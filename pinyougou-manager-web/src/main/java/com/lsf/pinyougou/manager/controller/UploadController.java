package com.lsf.pinyougou.manager.controller;

import com.lsf.pinyougou.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vo.Result;


/**
 * 图片上传的控制器
 */
@RestController
public class UploadController {

    /**
     * 图片服务器的 ip 地址
     */
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;


    /**
     *
     * 图片上传的对象
     */
    private FastDFSClient fastDFSClient;


    {
        try {
            fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/upload.do")
    public Result upload(MultipartFile file) {
        try {
            // 获取文件的扩展名
            String originalFilename = file.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            // 执行上传请求
            // 此处获得的 path 是图片的 组名+虚拟文件路径
            String path = fastDFSClient.uploadFile(file.getBytes(), extName);

            // 构造浏览器请求图片的 url
            String url = FILE_SERVER_URL + path;

            // 构造返回体
            return new Result(true, url);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "上传失败");
        }
    }
}