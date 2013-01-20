package com.b2kan.stresstest;

public class TcpPacket {
	/*private Thread[] threads;
	private ThreadSendTcpPacket[] objs;
	private int threadCount;
	private String url, message;
	private int port, reqCount, delay;
	
	public TcpPacket(String url, int port, String message, int reqCount) {
		this.url	= url;
		this.port	= port;
		this.message	= message;
		this.reqCount	= reqCount;
		
		initThreads();
		createThreads(true);
		System.out.println(url+port+message+reqCount);
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
		Main.objsTcp	= new ThreadSendTcpPacket[Main.threadCount];
		Main.threads	= new Thread[Main.threadCount];
		for(int i=0; i<Main.threadCount; i++) {
			Main.objsTcp[i]	= new ThreadSendTcpPacket(null, 0, null, 0);
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
			
			Main.objsTcp[i]		= new ThreadSendTcpPacket(Main.url, Main.port, Main.purpose, Main.reqCount);
			Main.threads[i]		= new Thread(Main.objsTcp[i]);
			Main.threads[i].start();
			
			Main.sleep(Main.delay);
		}
		
		Main.threadsStarted	= true;
	}
}
