package multiplayer.game;

import java.net.*;
import java.io.*;

public class Client {
	public boolean stop;
	
	public Client() {
		stop = false;
	}
	
	public void start() throws IOException {
		//Open your connection to a server, at port 15000
		Socket s1 = new Socket("localhost", 15000);
		//Output stream initialization
		OutputStream s1out = s1.getOutputStream();
		DataOutputStream dos = new DataOutputStream(s1out);
		String msg = new String();
		BufferedReader bufReader = new BufferedReader(new InputStreamReader(System.in));
		//Get an input file handle from the socket and read the input
		InputStream s1in = s1.getInputStream();
		DataInputStream dis = new DataInputStream(s1in);
		String receivedSt = new String();
		while(!msg.equals("end")) {
			receivedSt = dis.readUTF();
			System.out.println("Server said: " + receivedSt);
			System.out.println("Type in your message: ");
			msg = bufReader.readLine();
			dos.writeUTF(msg);
		}
		//When done, just close the connection and exit
		dis.close();
		s1in.close();
		s1.close();
		dos.close();
		s1out.close();
		System.out.println("Client stopped");
	}
}