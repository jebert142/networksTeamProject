/*Group 8, William Perley, Joey Theobald, Jon Ebert, Houston Bori
  Server Side of Project 1. 
 */
import java.io.*;
import java.net.*;

public class project1server 
{
	//Start the Server side and check to make sure everything is correct
	public static void main(String[] args)
	{
		//Port Number
		int portNum = 1943;
				System.out.println("Server side started");
		try(ServerSocket socket = new ServerSocket(portNum))
				{
					while(true)
					{
						new serverThread (socket.accept()).start();
					}
				}
				catch (IOException e)
		{
					System.err.println("Server could not listen on port " +portNum);	
					System.exit(-1);
		}
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


	public void runServer()
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
			output = new PrintWriter(socket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Connected on port" + socket.getPort());
			while(true)
			{
				//Display menu and what client should do
				output.println(options);
				output.println("Select an option 1-6 or 7 to Exit");
				inputString = input.readLine();
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
