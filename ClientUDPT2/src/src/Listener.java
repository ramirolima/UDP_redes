package src;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Listener extends Thread {

	private DatagramSocket clientSocket;

	public Listener(DatagramSocket socket) {
		clientSocket = socket;
	}

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				byte[] buffer = new byte[1024];
				DatagramPacket response = new DatagramPacket(buffer, buffer.length);
				clientSocket.receive(response);
				String dataResponse = new String(buffer, 0, response.getLength());
				System.out.println(dataResponse);
			} catch (IOException e) {
			}
		}
	}
}
