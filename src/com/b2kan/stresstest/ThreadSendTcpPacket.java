package com.b2kan.stresstest;

import java.io.DataOutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class ThreadSendTcpPacket implements Runnable {
	private SocketAddress address;
	private Socket clientSocket;
	private String url, message;
	private int port, reqCount;
		
	public ThreadSendTcpPacket(String url, int port, String message, int reqCount) {
		this.url	= url;
		this.port	= port;
		this.message	= message;
		this.reqCount	= reqCount;
	}
	
	public void run() {
		try {
			for (int i=0; i<reqCount; i++) {
				System.out.println("count: " + i);
				try {
					address = new InetSocketAddress(url, port);
					clientSocket = new Socket();
					clientSocket.connect(address, 20000); 
					DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
					//BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					outToServer.writeBytes(message);
					clientSocket.close();
				} catch(ConnectException e) {
		        	print("Could not connect, is target available?");
		    		Main.endProgram(false);
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void print(String text) {
		if(!Main.terminate) {
			System.out.println(text);
		}
	}
}
