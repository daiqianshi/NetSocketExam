package com.hand;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try {
			ServerSocket server = new ServerSocket(12347);
			Socket socket = server.accept();  
			JOptionPane.showMessageDialog(null, "有客户端连接到了本机");
			FileInputStream fis = new FileInputStream("../Exam1/new.pdf");
			@SuppressWarnings("resource")
			BufferedInputStream bis = new BufferedInputStream(fis);

			byte input[] = new byte[100];
			int length = 0;
			//String string = null;
			while ((length = bis.read(input)) != -1) {
				//string += new String(input, 0, length);
				socket.getOutputStream().write(input, 0, length);
			}
			//System.out.println(string);
			bis.close();
			fis.close();
			//socket.getOutputStream().write(string.getBytes());
			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
    }
}
