import java.io.BufferedReader;
import java.io.*;
import java.net.*;

public class project1 {

	public static void main(String[] args) {
		//Default host Name
		String host = "192.168.100.115";
		if(args.length!= 0 && args[0] != null){
			host = args[0];

		}else {
			System.out.println("No port was inserted into the command line");
			return;
		}
		int portNum 1943;

		Socket inputSocket = new Socket(host, portNum);

		if(inputSocket != null){
			try (PrintWriter output = new PrintWriter(inputSocket.getOutputStream());
					BufferedReader input = new BufferedReader (inputSocket.getInputStream());
					){
				BufferedReader userFeed = new BufferedReader( new StreamReader(System.in));
				String serverOutput;
				String userOutput;
				
			}
		}
	}

}