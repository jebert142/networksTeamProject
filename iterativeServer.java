import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class iterativeServer{
	//private static LinkedBlockingQueue<Socket> clientsToServe;
	private static final int serverPortNumber = 3515;

	public static void main(String[] args){
/*
listening thread (parent)
				when request came add into handling q
processing thread (child)
				process command from q and respond to approiate client
*/
		try{
			ServerSocket listeningSocket = new ServerSocket(serverPortNumber);
			System.out.println("Server Started Up");
			while(true){
				Socket newClient = listeningSocket.accept();
				if(newClient != null){
					handleClient(newClient);
				}
			}
		}
		catch(IOException e){
			System.err.println("Could not listen on port " + serverPortNumber);
			System.exit(-1);
		}
	}
	public static void handleClient(Socket socket){
		//Variables
		PrintWriter out;
		BufferedReader in;
		String inputLine;
		String menu = "1. Host current Date and Time\n2. Host uptime\n3. Host memory use\n4. Host Netstat\n5. Host current users\n6. Host running processes\n7. Quit";
		//attempt to set up input and output streams
		try {
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(
						new InputStreamReader(
								socket.getInputStream()));
				//return message to client and print
				System.out.println("Client connected on port: " + socket.getPort());
				out.println("Connection Accepted");
				//loop
				while(true) {
						//print out menu
						out.println(CommandCaller.menu);
						//let client know server is done messaging
						out.println("Select Menu Option");
						//wait for response
						inputLine = in.readLine();
						//respond with correct response
						out.println(CommandCaller.respondData(inputLine));
						//checkes if cmd was the exit Option
						try{
							if(Integer.parseInt(inputLine) == 7)
								break;
						}
						catch(Exception e){
							out.println("Invalid Choice");
						}
						//end of response fix
						out.println("---Response-Compelete---");
				}
				socket.close();
				System.out.println("Client on port: " + socket.getPort()+" Exited");
		} catch (IOException e) {
				e.printStackTrace();
				return;
		}
	}
}
