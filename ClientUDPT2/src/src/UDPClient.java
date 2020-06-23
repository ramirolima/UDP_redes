package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.Arrays;

public class UDPClient {
	
	
	public static void main(String args[]) throws Exception {
	
		DatagramSocket clientSocket = new DatagramSocket();		
		InetAddress IPAddress = InetAddress.getByName("localhost");
		Thread listener = new Listener(clientSocket);
		listener.start();
		sendMessage("connect",clientSocket,IPAddress);	
		
		File file = new File("/home/r1dev/dev/JavaWorkspace/ClientUDPT2/inputFile.txt");		
        byte[] bytes = Files.readAllBytes(file.toPath());
        
        int lengthPack = 1;        
        if(bytes.length >= 1000) {
        	lengthPack = (int) Math.ceil(bytes.length/1000.0);

        	int cont = 0;
        	for(int i =0; i<lengthPack*1000; i+=1000) {
        		byte[] partialPack = Arrays.copyOfRange(bytes, i, i+999);
        		sendMessage("ehusguri;"+cont+";"+new String(partialPack),clientSocket,IPAddress);
        		cont +=2;
        		
        		if(cont == 4)
        			sendMessage("ehusguri;"+1+";"+new String(partialPack),clientSocket,IPAddress);
        	}
        }
		
		sendMessage("disconnect",clientSocket,IPAddress);			
	}
	
	public static void sendMessage(String message, DatagramSocket clientSocket, InetAddress IPAddress) throws IOException {
		byte[] sendData = new byte[1024];		
		String data = message;					   
		sendData = data.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 3000);
		clientSocket.send(sendPacket);		
	}
	
	public static void listeningMessage(DatagramSocket clientSocket) throws IOException {		
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		String dataResponse = new String(receiveData, 0, receivePacket.getLength());		
		System.out.println(dataResponse);		
	}
}
