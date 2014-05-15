package com.huang.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class JettyUtil {
	private static Properties props = new Properties();
	
	static{
		try {
			props.load(new FileInputStream("src/main/resources/jetty.config"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static String getValue(String key){
		return props.getProperty(key);
	}
}
