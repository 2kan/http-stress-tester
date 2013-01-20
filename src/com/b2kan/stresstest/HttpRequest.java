package com.b2kan.stresstest;

public class HttpRequest {
	/*private int threadCount;
	private String url, user, purpose, method;
	private int reqCount, delay;
	
	public HttpRequest(String url, String user, String purpose, String method, int reqCount) {
		this.url	= url;
		this.user	= user;
		this.method	= method;
		this.reqCount	= reqCount;
		this.purpose	= purpose;
	}
	
	public void start() {
		initThreads();
		createThreads(true);
	}*/
	
	/**
	 * Fill array <code>objs</code> with new ThreadSendRequest objects
	 * @return	array of 'empty' ThreadSendRequest objects
	 */
	public static void initThreads() {
		Main.objs	= new ThreadSendHttpRequest[Main.threadCount];
		Main.threads	= new Thread[Main.threadCount];
		for(int i=0; i<Main.threadCount; i++) {
			Main.objs[i]	= new ThreadSendHttpRequest(null, null, null, null, 0);
		}
	}
	
	/**
	 * Create and start <code>threads</code> threads
	 * @param threads	array of threads to start
	 * @param objs		array of ThreadSendRequest objects
	 */
	public static void createThreads(boolean waitForThreads) {
		for(int i=0; i<Main.threads.length; i++) {
			if(Main.terminate) {
				System.out.println("Caught error, waiting for threads to finish...");
				break;
			}
			
			Main.objs[i]		= new ThreadSendHttpRequest(Main.url, Main.user, Main.purpose, Main.method, Main.reqCount);
			Main.threads[i]	= new Thread(Main.objs[i]);
			Main.threads[i].start();
			
			Main.sleep(Main.delay);
		}
		
		Main.threadsStarted	= true;
	}
}
