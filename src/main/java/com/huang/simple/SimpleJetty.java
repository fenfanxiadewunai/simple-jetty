package com.huang.simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.huang.util.JettyUtil;
import com.huang.util.WebUtil;

public class SimpleJetty extends Thread{
	private static ServerSocket server;
	private Socket socket;
	
	public SimpleJetty(Socket socket){
		this.setSocket(socket);
	}
	
	@Override
	public void run() {
		InputStream in = null;  
	    OutputStream out = null;  
	    FileInputStream fin = null;
		try{
			in = socket.getInputStream();
			out = socket.getOutputStream();
			byte[] b = new byte[1024*1024];
			in.read(b);
			String txt = new String(b).trim();
			IRequestHeaderParser parser = (IRequestHeaderParser)Class.forName(JettyUtil.getValue("jetty.requestheader.class")).newInstance();
			RequestHeader header = parser.parse(txt);
			System.out.println(header);
			Request request = new Request();
			request.setParameter(header.getParameter());
			request.setHeader(header);
			// 封装响应
			Response res = new Response();
			            
			// 有请求处理类就加载，没有就检索静态网页文件
			if(WebUtil.getValue(header.getUrl()) != null) {
			    try {
			        IServlet servlet = (IServlet) Class.forName(WebUtil.getValue(header.getUrl())).newInstance();
			        res.setOut(new PrintWriter(out));
			        servlet.service(request, res);
			    } catch(Exception e) {// 编译Servlet发生异常，加载500
			        File file = new File(JettyUtil.getValue("jetty.webapps"), "500.htm");
			        fin = new FileInputStream(file);
			        byte[] buf = new byte[(int) file.length()];
			        fin.read(buf);
			        out.write(buf);
			    }
			} else {
			    File file = new File(JettyUtil.getValue("jetty.webapps"),header.getUrl()); // 从配置文件检索服务端静态页面存放目录，定位到服务器端的静态页面
			    if(!file.exists()) {// 请求静态页面不存在，加载404
			        file = new File(JettyUtil.getValue("jetty.webapps"), "404.htm");
			    } 
			    fin = new FileInputStream(file);
			    byte[] buf = new byte[(int) file.length()];
			    fin.read(buf); // 读取静态页面内容
			    out.write(buf); // 将静态页面内容写回给客户端浏览器显示
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(fin!=null){
					fin.close();
				}
				if (out != null) {  
	                out.close();  
	            }  
	            if (in != null) {  
	                in.close();  
	            }  
	            if (socket != null) {  
	                socket.close();  
	            }  
			}catch(IOException e){}
		}
	}
	
	public static void openServer() throws Exception{
		server = new ServerSocket(Integer.parseInt(JettyUtil.getValue("jetty.port")));
		while(true){
			new SimpleJetty(server.accept()).start();
		}
	}
	
	public static void closeServer()throws Exception{
		if(server!=null){
			if(!server.isClosed()){
				server.close();
			}
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
