import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;


public class IterServer 
{

	private static final int portNum = 1943;

	public static void main(String[] args) 
	{
		try
		{
			ServerSocket socket = new ServerSocket(1943);
			System.out.println("Sever side started \n Waiting on connection");
			do
			{
				Socket client = socket.accept();
				if(client != null)
				{
					careForClient(client);
				}
			} while(true);
		}
		catch(IOException e)
		{
			System.err.println("Error connecting to port " + portNum);
			System.exit(-1);
		}
	}
	public static void careForClient(Socket socket)
	{
		String options = "Menu\n0 [1] Host current Time and Date\n1 [2] Host uptime\n3 [3] Host Memory use\n4 "
				+ "[4] Host Netstat\n5 [5] Host current users\n6 [6] Host running processes\n7 [7]Quit\n8";
		BufferedReader input;
		PrintWriter output;
		String inputString;

		try
		{
			output = new PrintWriter(socket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Client is connected on port #" + socket.getPort());
			output.println("Connection Established");
			do
			{
				output.println(options);
				output.println("Select an option 1-6 or 7 to Exit");
				inputString = input.readLine();
				output.println(response(inputString));
				if (Integer.parseInt(inputString) == 7)
				{
					break;
				}

			}while (true);
			socket.close();
			System.out.println("Session terminated");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
	}
	public static String result(String instr)
	{
		String info = "";
		//Trying to set up buffers and make sure there are no errors. 
		try
		{
			Process pro = Runtime.getRuntime().exec(instr);
			BufferedReader instrInput = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			String x = null;
			do
			{
				info += "\n" + x;
			} while((x = instrInput.readLine()) != null);
		}
		catch (IOException e)
		{
			info = "There is an error";
		}
		return info;
	}
	public static String response (String input)
	{
		int num = Integer.parseInt(input);
		String serverResponse = null;
		switch(num)
		{
		case 1:
			serverResponse = result ("date");
			break;
		case 2:
			serverResponse = result ("uptime");
			break;
		case 3:
			serverResponse = result ("free -m");
			break;
		case 4:
			serverResponse = result ("netstat");
			break;
		case 5:
			serverResponse = result ("who");
			break;
		case 6:
			serverResponse = result ("ps aux");
			break;
		case 7:
			serverResponse = "Exit";
			break;
		default:
			serverResponse = "Not a valid option. Please try again.";

		}
      return serverResponse;

	}
}


