package com.b2kan.stresstest;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	public final static String version	= "1.2.14";
	public final static String appName	= "Kitteh Website Blaster";
	public static int delay;
	
	protected static int requests	= 0;
	protected static boolean terminate	= false;
	protected static boolean threadsStarted	= false;
	
	private static int reqCount, threadCount;
	private static String user, url, purpose, method;
	private static Scanner inScan	= new Scanner(System.in);
	
	private static Thread[] threads;
	private static ThreadSendRequest[] objs;
	
	public static void main(String[] args) {
		System.out.println("~~ " + appName + " v" + version + " ~~");
		String fastSetup	= prompt("Fast setup? (Y/n)");
		
		if(fastSetup.equalsIgnoreCase("n") || fastSetup.equalsIgnoreCase("no")) {
			manualSetup(); // Prompt user for each setting value (manual setup)
		} else {
			initDefaults(); // Load default settings (fast setup)
		}
		
		user	= "lacertus";
		url		= prompt("Url:");
		method	= prompt("Method: (post/GET)");
		
		// User wants an "infinite" number of requests per thread, set number of requests per thread to one-hundred-million
		if(reqCount == 0) {
			reqCount	= 100000000;
		}
		
		// Create and start threads
		objs	= initThreads();
		createThreads(true);
	}
	
	public static void manualSetup() {
		try {
			threadCount	= promptInt("How many threads to use?");
			reqCount	= promptInt("How many requests to send per thread? (0 for infinite)");
			delay		= promptInt("How long of a delay bewteen requests in milliseconds?");
			purpose		= prompt("Purpose of attack? (i.e. stress-test, ddos, lolz) ");
		} catch (InputMismatchException e) {
			// User entered an invalid response, load default values
			System.out.println("Please enter valid integers. Using default values");
			initDefaults();
		}
	}
	
	/**
	 * Display <code>prompt</code> and return the value entered by the user
	 * @param prompt	text to be printed to prompt user
	 * @return	value entered by user
	 * @throws InputMismatchException
	 */
	public static int promptInt(String prompt) throws InputMismatchException {
		System.out.print(prompt + " ");
		int in	= inScan.nextInt();
		inScan.nextLine();
		return in;
	}
	
	/**
	 * Display <code>prompt</code> and return the value entered by the user
	 * @param prompt	text to be printed to prompt user
	 * @return	value entered by user
	 */
	public static String prompt(String prompt) {
		System.out.print(prompt + " ");
		return inScan.nextLine();
	}
	
	/**
	 * Load the default (fast-setup) variables
	 */
	public static void initDefaults() {
		reqCount	= 1000;
		threadCount	= 10;
		delay		= 20;
		
		purpose	= "meow";
	}
	
	/**
	 * Fill array <code>objs</code> with new ThreadSendRequest objects
	 * @return	array of 'empty' ThreadSendRequest objects
	 */
	public static ThreadSendRequest[] initThreads() {
		ThreadSendRequest[] objs	= new ThreadSendRequest[threadCount];
		threads	= new Thread[threadCount];
		for(int i=0; i<threadCount; i++) {
			objs[i]	= new ThreadSendRequest(null, null, null, null, 0);
		}
		return objs;
	}
	
	/**
	 * Create and start <code>threads</code> threads
	 * @param threads	array of threads to start
	 * @param objs		array of ThreadSendRequest objects
	 */
	public static void createThreads(boolean waitForThreads) {
		for(int i=0; i<threads.length; i++) {
			if(terminate) {
				System.out.println("Caught error, waiting for threads to finish...");
				break;
			}
			
			objs[i]		= new ThreadSendRequest(url, user, purpose, method, reqCount);
			threads[i]	= new Thread(objs[i]);
			threads[i].start();
			
			sleep(delay);
		}
		
		threadsStarted	= true;
	}
	
	protected static void endProgram(boolean threadsFinished) {
		if(!terminate) {
			terminate	= true;
			if(!threadsFinished) {
				for(int i=0; i<threadCount; i++) {
					while(!objs[i].isComplete()) {
						sleep(100);
					}
				}
			}
			
			System.out.println("Execution complete.");
		}
	}
	
	/**
	 * Sleep thread for specified number of milliseconds
	 * @param millis	milliseconds to sleep for
	 */
	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
