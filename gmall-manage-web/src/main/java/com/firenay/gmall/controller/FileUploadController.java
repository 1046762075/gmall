package com.firenay.gmall.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>Title: FileUploadController</p>
 * Description：文件上传控制器
 * date：2020/4/28 20:50
 */
@RestController
@CrossOrigin
@Slf4j
public class FileUploadController {

	@Value("${fileServer.url}")
	private String fileUrl;

	@RequestMapping("fileUpload")
	public String fileUpload(MultipartFile file) throws IOException, MyException {
		String imgUrl = fileUrl;
		if(file != null){
			String configFile = this.getClass().getResource("/tracker.conf").getFile();
			ClientGlobal.init(configFile);
			TrackerClient trackerClient = new TrackerClient();
			TrackerServer trackerServer = trackerClient.getTrackerServer();
			StorageClient storageClient = new StorageClient(trackerServer, null);
			// 获取需要上传的文件名
			String originalfilename = file.getOriginalFilename();
			// 取这个字符串中最后一个.的字符
			String extName = StringUtils.substringAfterLast(originalfilename, ".");
			// 本地文件上传
			//			String[] upload_file = storageClient.upload_file(originalfilename, extName, null);
			// 字节文件
			String[] upload_file = storageClient.upload_file(file.getBytes(), extName, null);
			for (int i = 0; i < upload_file.length; i++) {
				String path = upload_file[i];
				imgUrl += "/" + path;
			}
		}
		log.info("上传图片：" + imgUrl);
		return imgUrl;
	}
}
