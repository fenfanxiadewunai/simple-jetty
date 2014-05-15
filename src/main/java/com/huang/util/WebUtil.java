package com.huang.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class WebUtil {

	private static Properties props = new Properties();
	
	static {
		try{
			props.load(new FileInputStream(""));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key){
		return props.getProperty(key);
	}
}
