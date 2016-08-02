package com.hand;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 12347;
		try {
			@SuppressWarnings("resource")
			Socket socket = new Socket(host, port);

			InputStream is = socket.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			FileOutputStream fos = new FileOutputStream("new.pdf");
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			byte input[] = new byte[100];
			int length = 0;
			while ((length = bis.read(input)) != -1) {
				bos.write(input, 0, length);
			}
			bos.flush();
			bos.close();
			fos.close();
			bis.close();
			is.close();
			socket.close();
			System.out.println("文件传输成功");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
