/*William Perley, Jon Ebert, Joey Theobald, Houston Bori
 * Client side of the program used to connect to the server side. When connected
 * to the server side, will be able to put in options 1-7 in order to get different 
 * responses back from the server.
 */
import java.io.*;
import java.net.*;

public class project1 
{

	public class run 
	{
		//Default host Name
		String host = "192.168.100.115";
		//Makes sure there was an input in the command line
		if(args.length!= 0 && args[0] != null)
		{
			host = args[0];

		}else 
		{
			//To tell that no port number was put into the command line
			System.out.println("No port was inserted into the command line");
			return;
		}
		//Port number
		int portNum 1943;
		//Used to connect to the server
		Socket inputSocket = new Socket(host, portNum);
		//Attempting to connect to the server
		if(inputSocket != null)
		{		
			//Checking the inputs and reading the socket to try and connect
			try (PrintWriter output = new PrintWriter(inputSocket.getOutputStream());
					BufferedReader input = new BufferedReader (inputSocket.getInputStream());)
			{
				//If connection is successful, will set up a way to communicate
				BufferedReader userFeed = new BufferedReader( new StreamReader(System.in));
				//Server side response variables
				String serverOutput;
				String userOutput;
				//Will keep reading inputs as long as the user puts in an input
				while ((serverOutput = in.readLine()) != null)
				{
					System.out.println(serverOutput);
					//Lets user know server is done and can select an option to manipulate the program
					if(severOutput.equals("Please select an option"))
					{
						userOutput = userFeed.readLine();
						if (userOutput != null)
						{
							output.println(userOutput);
						}
					}
					//If the response from the server is "Exit" program will terminate
					if(serverOutput.equals("Exit"))
					{
						System.out.println("Program terminated");
						break;
					}	
					
				}
			}
			//Incase the user does not put in the correct input
			catch (IOException e)
			{
				System.err.println(e.toString()));
			}
		}
		
	}
		//Starts the program
		public static void main(String[] args throws IOException) 
		{ 
			new project1().run();
		}
	

}