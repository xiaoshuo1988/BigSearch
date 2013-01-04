package com.baidu.solr.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;

import org.junit.Test;

public class ReadFiles {
	FileReader in = null;
	StringBuffer sb=new StringBuffer();
	String readContent;
	/**
	 * 给指定路径filePath，返回该路径下所有以.xml文件结尾的文件内容。
	 */
	public String readFile(String filePath) {
		File file = new File(filePath);
		String fileName;
		if (!file.isDirectory()) {
			System.out.println("读取的文件或路径不存在！");
			System.exit(-1);
		}
		
		for (File f : file.listFiles()) {
			fileName = f.getName();
			if (fileName.endsWith(".xml")) {
				
				readContent=getInputStream(filePath,fileName);
			}
			
		}
		return readContent;
	}

	public String getInputStream(String filePath, String fileName) {
		int index = 0;
		try {
			
			in = new FileReader(filePath+"\\"+fileName);
			
			BufferedReader reader = new BufferedReader(in);

			while (reader.ready()) {
				sb.append(reader.readLine()).append("\n");

			}
			reader.close();
			in.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		return sb.toString();
	}
	
	@Test
	public void test(){
		
		String a=readFile("C:\\Users\\lenovo\\Desktop\\搜索引擎\\新建文件夹");
		System.out.println(a);
	}
	

}
