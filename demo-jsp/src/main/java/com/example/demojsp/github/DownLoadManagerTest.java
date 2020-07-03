package com.example.demojsp.github;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Year;
import java.util.concurrent.CountDownLatch;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.DefaultManagedTaskExecutor;

/**
 * 
 * 文件下载管理类
 */
public class DownLoadManagerTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DownLoadManagerTest.class);

	/**
	 * 
	 * 每个线程下载的字节数
	 */
	private long unitSize = 1000 * 1024;
	

	private TaskExecutor taskExecutor;

	public static void main(String[] args) throws IOException {
		DownLoadManagerTest downLoadManagerTest = new DownLoadManagerTest();
		downLoadManagerTest.doDownload();
	}



	/**
	 * 
	 * 启动多个线程下载文件
	 */
	public void  doDownload() throws IOException {
		TaskExecutor taskExecutor = new DefaultManagedTaskExecutor();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(100);
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		String remoteFileUrl="https://api.github.com/repos/Snailclimb/JavaGuide/zipball/master";
		String localPath="E://test//";
		
		String fileName = "abc.zip";

		System.out.println("远程文件名称："+"abc.zip");
		fileName = fileName.substring(fileName.lastIndexOf("/") + 1,
				fileName.length()).replace("%20", " ");
		System.out.println("本地文件名称："+fileName);
		long fileSize = this.getRemoteFileSize(remoteFileUrl);

		this.createFile(localPath+System.currentTimeMillis()+fileName, fileSize);

		Long threadCount = (fileSize/unitSize)+(fileSize % unitSize!=0?1:0);
		long offset = 0;
		
		CountDownLatch end = new CountDownLatch(threadCount.intValue());
		// 如果远程文件尺寸小于等于unitSize
		if (fileSize <= unitSize) {

			DownloadThreadTest downloadThread = new DownloadThreadTest(remoteFileUrl,
					localPath+fileName, offset, fileSize,end,httpClient);

			taskExecutor.execute(downloadThread);

		} else {
			// 如果远程文件尺寸大于unitSize
			for (int i = 1; i < threadCount; i++) {
				DownloadThreadTest downloadThread = new DownloadThreadTest(
				remoteFileUrl, localPath+fileName, offset, unitSize,end,httpClient);
				taskExecutor.execute(downloadThread);
				offset = offset + unitSize;

			}
			// 如果不能整除，则需要再创建一个线程下载剩余字节
			if (fileSize % unitSize != 0) {
				DownloadThreadTest downloadThread = new DownloadThreadTest(remoteFileUrl, localPath+fileName, offset, fileSize - unitSize * (threadCount-1),end,httpClient);
				taskExecutor.execute(downloadThread);
			}
		}
		try {
			end.await();
		} catch (InterruptedException e) {
			LOGGER.error("DownLoadManager exception msg:{}",ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
//		System.out.println("111111");
		LOGGER.debug("下载完成！{} ",localPath+fileName);
		//return localPath+fileName;
	}

	/**
	 * 
	 * 获取远程文件尺寸
	 */
	private long getRemoteFileSize(String remoteFileUrl) throws IOException {
		long fileSize = 0;
		HttpURLConnection httpConnection = (HttpURLConnection) new URL(
		remoteFileUrl).openConnection();
		httpConnection.setRequestMethod("HEAD");
		int responseCode = httpConnection.getResponseCode();
		if (responseCode >= 400) {
			LOGGER.debug("Web服务器响应错误!");
			return 0;
		}

		String sHeader;
		for (int i = 1;; i++) {
			sHeader = httpConnection.getHeaderFieldKey(i);
			if (sHeader != null && sHeader.equals("Content-Length")) {
				System.out.println("文件大小ContentLength:"
						+ httpConnection.getContentLength());
				fileSize = Long.parseLong(httpConnection
						.getHeaderField(sHeader));
				break;
			}
		}
		return fileSize;
	}

	/**
	 * 
	 * 创建指定大小的文件
	 */
	private void createFile(String fileName, long fileSize) throws IOException {
		File newFile = new File(fileName);
		RandomAccessFile raf = new RandomAccessFile(newFile, "rw");
		raf.setLength(fileSize);
		raf.close();
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
}
