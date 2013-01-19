package com.b2kan.stresstest;

public class ThreadCounter {
	private static int threads	= 0;
	
	/**
	 * Increments the number of threads running by one
	 */
	protected static void threadStarted() {
		threads++;
	}
	
	/**
	 * Decrements the number of threads running by one and, if all threads have been started and the number of threads is zero,
	 * call the method to terminate the program
	 */
	protected static void threadEnded() {
		threads--;
		if(threads == 0 && Main.threadsStarted) {
			Main.endProgram(true);
		}
	}
}
