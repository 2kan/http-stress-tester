package com.b2kan.stresstest;
import java.util.HashMap;

public class ThreadSendRequest implements Runnable {
	private String url, user, purpose, method;
	private boolean complete;
	private int reqCount;
	
	public ThreadSendRequest(String url, String user, String purpose, String method, int reqCount) {
		this.url	= url;
		this.user	= user;
		this.method	= method;
		this.purpose	= purpose;
		this.complete	= true;
		this.reqCount	= reqCount;
	}
	
	public void run() {
		ThreadCounter.threadStarted();
		URLs load	= new URLs();
		HashMap<String, String> params	= new HashMap<String, String>();
		
		// Add post/get parameters
		params.put("user", this.user);
		params.put("purpose", this.purpose);
		params.put("appname", Main.appName);
		params.put("version", Main.version);
		
		int count	= 0;
		while(count < reqCount && !Main.terminate) {
			String response;
			if(this.method.equals("post")) {
				response	= load.post(url, params);
			} else {
				response	= load.get(url, params);
			}
			
			Main.requests++;
			if(response ==  null) {
				print("(" + Main.requests + ") Error sending data to website.");
			} else {
				print("(" + Main.requests + ") Request sent!");
			}
			count++;
			Main.sleep(Main.delay);
		}
		
		// Clear parameters
		params.remove("user");
		params.remove("purpose");
		params.remove("appname");
		params.remove("version");
		
		// Indicate that thread is finished
		complete	= true;
		ThreadCounter.threadEnded();
	}
	
	/**
	 * Prints <code>text</code> if the program isn't trying to exit
	 * @param text	the text to print
	 */
	private void print(String text) {
		if(!Main.terminate) {
			System.out.println(text);
		}
	}
	
	/**
	 * Checks if the thread is complete
	 * @return true if the thread has finished
	 */
	public boolean isComplete() {
		return complete;
	}
}
