package com.huang.simple;

public class SimleJettyMain {
	public static void main(String[] args) {
		try {
			SimpleJetty.openServer();
		} catch (Exception e) {
			try {
				SimpleJetty.closeServer();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
