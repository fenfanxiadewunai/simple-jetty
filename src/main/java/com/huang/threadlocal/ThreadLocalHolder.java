package com.huang.threadlocal;

public class ThreadLocalHolder {

	private static final ThreadLocal<Object> threadlocal = new ThreadLocal<Object>();
	
	public static final void set(byte[] b){
		threadlocal.set(b);
	}
	
	public static final void clear(){
		threadlocal.set(null);
	}
	
	public static final void remove(){
		threadlocal.remove();
	}
}
