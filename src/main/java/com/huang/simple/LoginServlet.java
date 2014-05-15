package com.huang.simple;

public class LoginServlet extends HttpServletImpl{
	
	@Override
	public void doGet(Request req, Response res) {
		String username = req.getParameterValue("username");
		String password = req.getParameterValue("password");
		if(username.equals("huang")&&password.equals("123456")){
			res.out.print("Hello, "+username+"<br>");
		}
		res.out.close();
	}
	
	@Override
	public void doPost(Request req, Response res) {
		doGet(req, res);
	}
}
