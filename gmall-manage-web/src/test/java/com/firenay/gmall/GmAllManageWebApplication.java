package com.firenay.gmall;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmAllManageWebApplication {

	@Value("${fileServer.url}")
	private String ipAddr;

	@Test
	public void textFileUpload() throws IOException, MyException {
		String file = this.getClass().getResource("/tracker.conf").getFile();
		ClientGlobal.init(file);
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getTrackerServer();
		StorageClient storageClient = new StorageClient(trackerServer, null);
		String orginalFilename = "D:\\下载\\谷歌下载\\abe1eea3ca79fc28-c577ebdcb0f3dbcc-b4976fac9ddc9806d92f22e03f1e9b43.jpg";
		String extName = StringUtils.substringAfterLast(orginalFilename, ".");
		String[] upload_file = storageClient.upload_file(orginalFilename, extName, null);
		for (int i = 0; i < upload_file.length; i++) {
			String s = upload_file[i];
			ipAddr += "/" + s;
//			s = group1
//			s = M00/00/00/CisBNF6oIdqAeWkJAAifB5wykMs818.jpg
		}
		System.out.println("访问：" + ipAddr);
	}

	@Test
	public void textFileDel() throws IOException, MyException {
		String file = this.getClass().getResource("/tracker.conf").getFile();
		ClientGlobal.init(file);
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getTrackerServer();
		StorageClient storageClient = new StorageClient(trackerServer, null);
		String groupName = "group1";
		String path = "M00/00/00/";

		if(storageClient.delete_file(groupName,path + "CisBNF6pMjWAOu67AAApB7V0h9U383.png") == 0){
			System.out.println("删除成功");
		}

		// update order_detail set img_url = null ;
		// select img_url from order_detail where !(img_url is null);
		// DELETE FROM test
	}
}
