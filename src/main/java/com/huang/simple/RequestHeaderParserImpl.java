package com.huang.simple;

import java.util.HashMap;

public class RequestHeaderParserImpl implements IRequestHeaderParser{

	public RequestHeader parse(String txt) throws Exception {
		RequestHeader header = new RequestHeader();
		header.setText(txt);
		String firstLine = txt.substring(0,txt.indexOf("\n"));
		String method = firstLine.substring(0,firstLine.indexOf(" "));
		String url = firstLine.substring(firstLine.indexOf(" ") + 1, firstLine.lastIndexOf(" "));  
		String protocal = firstLine.substring(firstLine.lastIndexOf(" ") + 1, firstLine.length());
		
		String parameter = null;  
		try {  
		    if(method.equalsIgnoreCase("post")) {  
		        parameter = txt.substring(txt.lastIndexOf("\n") + 1, txt.length());  
		    } else if(method.equalsIgnoreCase("get")) {  
		        parameter = url.substring(url.indexOf("?") + 1, url.length());  
		        url = url.substring(0, url.indexOf("?"));  
		    }  
		} catch (Exception e) {  
		}  
		  
		header.setParameter(parameter); 
		
		header.setMethod(method);
		header.setUrl(url);
		header.setProtocal(protocal);
		
		String[] lines = txt.split("\n");
		HashMap<String, String> map = new HashMap<String, String>(); 
		for (int i = 1; i < lines.length; i++) {  
            String[] result = lines[i].split(": ");  
            map.put(result[0], (result.length <= 1) ? "" : result[1].replace('\n', ' ').trim());  
        }  
		header.setMap(map); 
		header.setAccept(map.get("Accept")); // 获取Accept参数值，存放到RequestHeader对象当中  
        header.setAccept_language(map.get("Accept-Language")); // 获取Accept-Language参数值，存放到RequestHeader对象当中  
        header.setUser_agent(map.get("User-Agent")); // 获取User-Agent参数值，存放到RequestHeader对象当中  
        header.setAccept_encoding(map.get("Accept-Encoding")); // 获取Accept-Encoding参数值，存放到RequestHeader对象当中  
        header.setIp(map.get("Host").split(":")[0]); // 获取Host参数的IP参数值，存放到RequestHeader对象当中  
        header.setPort(map.get("Host").split(":")[1]); // 获取Host参数的Port参数值，存放到RequestHeader对象当中  
        header.setConnection(map.get("Connection")); // 获取Connection参数值，存放到RequestHeader对象当中  
        header.setCookie(map.get("Cookie")); // 获取Cookie参数值，存放到RequestHeader对象当中 
		
		return header;
	}

}
