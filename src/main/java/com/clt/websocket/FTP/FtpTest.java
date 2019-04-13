package com.clt.websocket.FTP;

import com.clt.websocket.HTTP.HttpClient;

import java.io.*;

import static com.clt.websocket.FTP.FtpUtils.downloadFile;
import static com.clt.websocket.FTP.FtpUtils.uploadFile;

public class FtpTest {

	 public static void main(String[] args) throws FileNotFoundException {
		 
	        String ftpHost = "192.144.176.134";
	        String ftpUserName = "system001";
	        String ftpPassword = "fwfs#652dQ31fsdJ*4d";
	        int ftpPort = 21;
	        String ftpPath = "";
	        String localPath = "D:\\";
	        String fileName = "测试文档.pdf";

	        //上传一个文件
	        /*try{
	            FileInputStream in=new FileInputStream(new File(localPath));
	            boolean test = FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName,in);
	            System.out.println(test);
	        } catch (FileNotFoundException e){
	            e.printStackTrace();
	            System.out.println(e);
	        }*/

	        //在FTP服务器上生成一个文件，并将一个字符串写入到该文件中
//	        try {
//	            InputStream input = new ByteArrayInputStream("test ftp jyf".getBytes("GBK"));
//	            input = new ByteArrayInputStream(new File();
//	            boolean flag = FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName,input);;
//	            System.out.println(flag);
//	        } catch (UnsupportedEncodingException e) {
//	            e.printStackTrace();
//	        }


	        //下载一个文件
//	        FtpUtil.downloadFtpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, localPath, fileName);

		 uploadFile("D:\\","《大泼猴》.txt","E:\\《大泼猴》.txt");

//		 downloadFile("D:\\","《大泼猴》.txt","D:\\");



		 //FTP文件传输完成后 向签章服务器发送处理请求
//		 String url = "http://localhost:8081/analyze?fileName="+"测试文档.pdf";
//
//		 String HttpResult = HttpClient.doGet(url);

	 }
	
}
