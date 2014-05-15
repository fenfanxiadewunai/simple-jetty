package com.huang.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
	
	public static void testWithThread(boolean clearThreadLocal,long sleepTime){
		while(true){
			try{
				Thread.sleep(100);
				new Thread(new ThreadLocalTask(clearThreadLocal,sleepTime)).start();
			}catch(Exception e){}
		}
	}
	
	public static void testWithThreadPool(int poolSize,boolean clearThreadLocal,long sleepTime){
		ExecutorService service = Executors.newFixedThreadPool(poolSize);
		while(true){
			try{
				Thread.sleep(100);
				service.execute(new ThreadLocalTask(clearThreadLocal, sleepTime));
			}catch(Exception e){}
		}
	}
	
	public static void main(String[] args) {
	// 测试情况1. 无线程池，线程不休眠，并且清除thread_local 里面的线程变量；测试结果：无内存溢出
    testWithThread(true, 0); 
    // 测试情况2. 无线程池，线程不休眠，没有清除thread_local 里面的线程变量；测试结果：无内存溢出
    testWithThread(false, 0); 
    // 测试情况3. 无线程池，线程休眠1000毫秒,清除thread_local里面的线程的线程变量；测试结果：无内存溢出,但是新生代内存整体使用高
    testWithThread(false, 1000); 
    // 测试情况4. 无线程池，线程永久休眠（设置最大值）,清除thread_local里面的线程的线程变量；测试结果：无内存溢出
    testWithThread(true, Integer.MAX_VALUE); 
    // 测试情况5. 有线程池，线程池大小50，线程不休眠，并且清除thread_local 里面的线程变量；测试结果：无内存溢出
    testWithThreadPool(50,true,0); 
    // 测试情况6. 有线程池，线程池大小50，线程不休眠，没有清除thread_local 里面的线程变量；测试结果：无内存溢出
    testWithThreadPool(50,false,0); 
    // 测试情况7. 有线程池，线程池大小50，线程无限休眠，并且清除thread_local 里面的线程变量；测试结果：无内存溢出
    testWithThreadPool(50,true,Integer.MAX_VALUE); 
    // 测试情况8. 有线程池，线程池大小1000，线程无限休眠，并且清除thread_local 里面的线程变量；测试结果：无内存溢出
    testWithThreadPool(1000,true,Integer.MAX_VALUE); 
	}
}
