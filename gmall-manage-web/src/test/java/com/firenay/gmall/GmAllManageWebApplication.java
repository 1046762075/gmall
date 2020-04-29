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
		String orginalFilename = "C:\\Users\\root\\Desktop\\5a38e39b2975e837.jpg";
		String extName = StringUtils.substringAfterLast(orginalFilename, ".");
		String[] upload_file = storageClient.upload_file(orginalFilename, extName, null);
		for (int i = 0; i < upload_file.length; i++) {
			String s = upload_file[i];
			System.out.println("s = " + s);
//			s = group1
//			s = M00/00/00/CisBNF6oIdqAeWkJAAifB5wykMs818.jpg
		}
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
