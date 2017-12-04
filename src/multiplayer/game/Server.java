package multiplayer.game;

import java.net.*;
import java.io.*;

public class Server {
	public boolean stopServer;
	private static ServerSocket serverSocket;
	private static Socket socket;
	private static DataOutputStream dos;
	private static DataInputStream dis;
	private String msg;
	private String receivedMsg;
	
	public Server() {
		stopServer = false;
	}
	
	public void start() throws IOException {
		serverSocket = new ServerSocket(15000);
		socket=serverSocket.accept();
		dos = new DataOutputStream(socket.getOutputStream());
		dis = new DataInputStream(socket.getInputStream());
		msg = "Message from server";
		receivedMsg = dis.readUTF();
		while(!stopServer) {
			System.out.println(receivedMsg);
			dos.writeUTF(msg);
		}
		dos.close();
		dis.close();
		socket.close();
		serverSocket.close();
		System.out.println("Server stopped");
	}
	
	public void sendInitInfo(int gridWidth, int gridHeight, Player player1, Player player2) {
		
	}
}