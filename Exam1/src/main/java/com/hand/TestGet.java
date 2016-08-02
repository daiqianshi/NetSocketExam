package com.hand;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TestGet extends Thread {
	@Override
	public void run() {
		try {
			// 获取连接
			URL url = new URL(
					"http://files.saas.hand-china.com/java/target.pdf");
			// 打开链接
			URLConnection connection = url.openConnection();
			// 获得输出流
			InputStream is = connection.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			
			FileOutputStream fos = new FileOutputStream("new.pdf");
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			int length = 0;
			byte input[] = new byte[100];
			while ((length = bis.read(input)) != -1) {
				bos.write(input,0,length);
			}
			bos.flush();
			bos.close();
			fos.close();
			bis.close();
			is.close();
			System.out.println("文件下载完成");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
