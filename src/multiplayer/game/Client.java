package multiplayer.game;

import java.net.*;
import java.io.*;

public class Client {
	public boolean stopClient;
	private static Socket socket;
	private static DataOutputStream dos;
	private static DataInputStream dis;
	private String msg;
	private String receivedMsg;
	
	public Client() {
		stopClient = false;
	}
	
	public void start() throws IOException {
		socket = new Socket("localhost", 15000);
		dos = new DataOutputStream(socket.getOutputStream());
		dis = new DataInputStream(socket.getInputStream());
		msg = "Message from client";
		receivedMsg = dis.readUTF();
		while(!stopClient) {
			System.out.println(receivedMsg);
			dos.writeUTF(msg);
		}
		dos.close();
		dis.close();
		socket.close();
		System.out.println("Client stopped");
	}
}