package com.huang.threadlocal;

class Content{
	byte[] data = new byte[1024*1024*10];
	protected void finalize(){
		System.out.println("I am released");
	}
}

public class ThreadLocaTest {

	
	ThreadLocal<Content> threadLocal = new ThreadLocal<Content>();
	
	public void start(){
		System.out.println("begin");
		Content content = threadLocal.get();
		if(content == null){
			content = new Content();
			threadLocal.set(content);
		}
		System.out.println("try to release content data");
//		threadLocal.set(null);
		threadLocal.remove();
		threadLocal = null;
		content = null;
		System.out.println("request gc");
		System.gc();
		try{
			Thread.sleep(1000);
		}catch(InterruptedException e){}
		
		System.out.println("end");
	}
	
	public static void main(String[] args) {
		ThreadLocaTest t = new ThreadLocaTest();
		t.start();
	}
}
