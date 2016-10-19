import java.io.*;
import java.util.*;
public class CommandCaller{
	public static final String menu = "1. Host current Date and Time\n2. Host uptime\n3. Host memory use\n4. Host Netstat\n5. Host current users\n6. Host running processes\n7. Quit";
	/*
			Method Name: respondData
			Purpose: This will take input and returns to the data from the
				user option.
			Parameters: takes an int in form of a string. This int will be the option
				from the user.
			Return: string with the data from the user option.
		*/
		public static String respondData(String input){
				int value = Integer.parseInt(input);
				String response = null;
				switch(value){
						case 1:
								response = getCommandResults("date");
								System.out.println("Client requested date - 1");
								break;
						case 2:
								response = getCommandResults("uptime");
								System.out.println("Client requested uptime - 2");
								break;
						case 3:
								response = getCommandResults("free -m");
								System.out.println("Client requested free - 3");
								break;
						case 4:
								response = getCommandResults("netstat");
								System.out.println("Client requested netstat - 4");
								break;
						case 5:
								response = getCommandResults("who");
								System.out.println("Client requested who - 5");
								break;
						case 6:
								response = getCommandResults("ps aux");
								System.out.println("Client requested ps - 6");
								break;
						case 7:
								response = "Exit";
								break;
						default:
								response = "Not a vaild selection, please choose";
								break;
				}
				return response;
		}
		/*
			Method Name: getCommandResults
			Purpose: This will handle take a command and returns to the data from the
					command ran on commandline.
			Parameters: takes an int in form of a string. This int will be the option
				from the user.
			Return: string with the data from command result.
		*/
		public static String getCommandResults(String cmd){
				String data = "";
				try {
						Process process = Runtime.getRuntime().exec(cmd);
						//attempt to initializing buffers
						BufferedReader stdInput = new BufferedReader(
								new InputStreamReader(process.getInputStream()));
						//read the output from the command
						String s = null;
						while ((s = stdInput.readLine()) != null) {
								data += "\n" + s;
						}
				}
				catch(Exception e){
						data = "error";
				}
				return data;
		}
}
