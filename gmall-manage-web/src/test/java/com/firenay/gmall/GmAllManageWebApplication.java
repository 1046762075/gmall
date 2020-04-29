package com.firenay.gmall;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmAllManageWebApplication {

	@Test
	public void textFileUpload() throws IOException, MyException {
		String file = this.getClass().getResource("/tracker.conf").getFile();
		ClientGlobal.init(file);
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getTrackerServer();
		StorageClient storageClient = new StorageClient(trackerServer, null);
		String orginalFilename = "F:/Dev/桌面/StartupWp.jpg";
		String extName = StringUtils.substringAfterLast(orginalFilename, ".");
		String[] upload_file = storageClient.upload_file(orginalFilename, extName, null);
		for (int i = 0; i < upload_file.length; i++) {
			String s = upload_file[i];
			System.out.println("s = " + s);
//			s = group1
//			s = M00/00/00/CisBNF6oIdqAeWkJAAifB5wykMs818.jpg
		}
	}
}