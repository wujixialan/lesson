package com.biz.lesson.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

/**
 * @author kevin zhao
 * @date 2018/7/27
 */
public class CosUtil {
    private static String appId = "**";
    private static String secretId = "**";
    private static String secretKey = "**";
    private static String bucketName = "**";

    public static URL getFileInfo(MultipartFile file) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 设置输入流长度为 500
        objectMetadata.setContentLength(file.getSize());
        // 设置 Content type, 默认是 application/octet-stream
        objectMetadata.setContentType(file.getContentType());
        PutObjectResult putObjectResult = getCosClient().putObject(bucketName,file.getOriginalFilename(), file.getInputStream(), objectMetadata);
        Date expiration = new Date(new Date().getTime() + 5 * 60 * 10000);
        URL url = getCosClient().generatePresignedUrl(bucketName, file.getContentType(), expiration);
        return url;
    }

    //    创建一个COSClient。
    public static COSClient getCosClient() {
        Region region = new Region("ap-shanghai");
        COSCredentials cred = new BasicCOSCredentials(appId, secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }
}
