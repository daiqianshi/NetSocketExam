package com.hand;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.JsonObject;

public class GetMessage extends Thread {

	public JsonObject createObject = new JsonObject();
	DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder;

	@Override
	public void run() {
		try {
			// 获取连接
			URL url = new URL("http://hq.sinajs.cn/list=sz300170");
			// 打开链接
			URLConnection connection = url.openConnection();
			// 获得输出流
			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "GBK");
			BufferedReader bf = new BufferedReader(isr);
			String line;
			StringBuffer sBuffer = new StringBuffer();
			while ((line = bf.readLine()) != null) {
				sBuffer.append(line);
			}

			bf.close();
			isr.close();
			is.close();
			String result = sBuffer.toString();
			result = result.substring(result.indexOf("\"") + 1);
			String[] str = result.split(",");
			create(str);
			//System.out.println(result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void create(String[] str) {
		try {
			dBuilder = dFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document document = dBuilder.newDocument();
		Element root = document.createElement("stock");
		for (int i = 0; i < str.length; i++) {
			if (i > 5) {
				break;
			}
			// System.out.println(str[i]);
			switch (i) {
			case 0:
				createObject.addProperty("name", str[i]);
				Element name = document.createElement("name");
				name.setTextContent(str[i]);
				root.appendChild(name);
				break;
			case 1:
				createObject.addProperty("open", str[i]);
				Element open = document.createElement("open");
				open.setTextContent(str[i]);
				root.appendChild(open);
				break;
			case 2:
				createObject.addProperty("close", str[i]);
				Element close = document.createElement("close");
				close.setTextContent(str[i]);
				root.appendChild(close);
				break;
			case 3:
				createObject.addProperty("current", str[i]);
				Element current = document.createElement("current");
				current.setTextContent(str[i]);
				root.appendChild(current);
				break;
			case 4:
				createObject.addProperty("high", str[i]);
				Element high = document.createElement("high");
				high.setTextContent(str[i]);
				root.appendChild(high);
				break;
			case 5:
				createObject.addProperty("low", str[i]);
				Element low = document.createElement("low");
				low.setTextContent(str[i]);
				root.appendChild(low);
				break;

			default:
				break;
			}
		}
		document.appendChild(root);
		outOfFile(document, createObject);
	}

	public static void outOfFile(Document document, JsonObject createObject) {
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer transformer = factory.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(document), new StreamResult(
					writer));
			System.out.println(writer.toString());
			transformer.transform(new DOMSource(document), new StreamResult(
					new File("new.xml")));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		try {
			FileOutputStream fos = new FileOutputStream(new File("new.txt"));
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(createObject.toString());
			bw.close();
			osw.close();
			fos.close();
			System.out.println(createObject.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
