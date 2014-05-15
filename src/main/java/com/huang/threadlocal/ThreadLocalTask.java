package com.huang.threadlocal;

public class ThreadLocalTask implements Runnable{

	private boolean clearThreadLocal;
	
	private long sleepTime;
	
	
	
	public ThreadLocalTask(boolean clearThreadLocal, long sleepTime) {
		this.clearThreadLocal = clearThreadLocal;
		this.sleepTime = sleepTime;
	}



	public void run() {
		try{
			ThreadLocalHolder.set(allocateMem());
			try{
				if(sleepTime>0){
					Thread.sleep(sleepTime);
				}
			}catch(InterruptedException e){}
		}finally{
			if(clearThreadLocal){
				ThreadLocalHolder.clear();
			}
		}
	}
	
	private static final byte[] allocateMem(){
		byte[]b = new byte[1024*1024];
		return b;
	}

}
