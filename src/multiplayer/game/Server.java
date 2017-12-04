package multiplayer.game;

import java.net.*;
import java.io.*;

public class Server {
	public boolean stop;
	
	public Server() {
		stop = false;
	}
	
	public void start() throws IOException {
		//Register service on port 15000
		ServerSocket s = new ServerSocket(15000);
		Socket s1=s.accept();
		//Get a communication stream associated with the socket
		OutputStream s1out = s1.getOutputStream();
		DataOutputStream dos = new DataOutputStream(s1out);
		String msg = new String();
		BufferedReader bufReader = new BufferedReader(new InputStreamReader(System.in));
		//Input stream initialization
		InputStream s1in = s1.getInputStream();
		DataInputStream dis = new DataInputStream(s1in);
		String receivedSt = new String();
		//Send a string
		dos.writeUTF("test");
		receivedSt = dis.readUTF();
		while(!stop) {
			System.out.println("Client said: " + receivedSt);
			System.out.println("Type in your message: ");
			msg = bufReader.readLine();
			dos.writeUTF(msg);
			receivedSt = dis.readUTF();
		}
		//Close the connection, but not the server socket
		dos.close();
		s1out.close();
		s1.close();
		dis.close();
		s1in.close();
		s.close();
		System.out.println("Server stopped");
	}
	
	public void sendInitInfo(int gridWidth, int gridHeight, Player player1, Player player2) {
		
	}
}