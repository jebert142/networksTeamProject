/*Group 8, William Perley, Joey Theobald, Jon Ebert, Houston Bori
  Server Side of Project 1. 
 */
import java.io.*;
import java.net.*;

public class project1server 
{
	//Start the Server side and check to make sure everything is correct
	public class runServer
	{
		//Port Number
		int portNum = 1943
				System.out.println("Server side started");
		try(SeverSocket socket = new ServerSocket(portNum));
				{
					while(true)
					{
						new serverThread (ServerSocket.accept()).start();
					}
				}
				catch (IOException e)
		{
					System.err.println("Server could not listen on port " +portNum);	
					System.exit(-1);
		}
	}
	public static void main(String[] args)
	{
		//Runs the program on the server
		new project1server().runServer();
	}


}

//To create an instance
class serverThread extends Thread
{
	//Global Variables
	private Socket socket = null;
	public serverThread (Socket socket)
	{
		this.socket  = socket;
	}


	public void runServer
	{
		//Input/output variables
		PrintWriter output;
		BufferedReader input;
		String inputString;
		//Dispays menu
		String options = "Menu\n0 [1] Host current Time and Date\n1 [2] Host uptime\n3 [3] Host Memory use\n4 "
				+ "[4] Host Netstat\n5 [5] Host current users\n6 [6] Host running processes\n7 [7]Quit\n8";
		//Tries to set up an input output stream so the server anc client and write to each other
		try
		{
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Connected on port" + socket.getPort());
			while(true)
			{
				//Display menu and what client should do
				out.println(options);
				out.println("Select an option 1-6 or 7 to Exit");
				inputString = in.readLine();
				output.println(response(inputString));
				if (Integer.parseInt(inputString) == 7)
				{
					break;
				}
			}
			socket.close();
			System.out.println("Session terminated");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
	}
	//Method for Case switch to get the different responses
	public static String response (String input)
	{
		int num = Integer.parseInt(input);
		String serverResponse = null;
		switch(num)
		{
		case 1:
			serverRespose = result("date");
			break;
		case 2:
			serverRespose = result ("uptime");
			break;
		case 3:
			serverRespose = result ("free -m");
			break;
		case 4:
			serverRespose = result ("netstat");
			break;
		case 5:
			serverRespose = result("who");
			break;
		case 6:
			serverRespose = result ("ps aux");
			break;
		case 7:
			serverRespose = "Exit";
			break;
		default:
			serverRespose = "Not a valid option. Please try again."
			
		}

	}
}
