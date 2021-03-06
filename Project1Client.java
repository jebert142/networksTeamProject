/*William Perley, Jon Ebert, Joey Theobald, Houston Bori
 * Client side of the program used to connect to the server side. When connected
 * to the server side, will be able to put in options 1-7 in order to get different 
 * responses back from the server. When starting, will have option to directly connect 
 * to server, if any other key is pressed, will exit immediately
 */
import java.io.*;
import java.net.*;

public class Project1Client {
	//Global variables so don't have to re-initialized in every method
	protected static int port;
	protected static int multiClients;
	protected static int status;
	public static String host;
	//main to take care of inputs and if there are multiple clients
	public static void main(String[] args) throws IOException {
		inputArguments(args);
	//	System.out.println(status);
    switch(status)
		{
		case 0:
			giveToServer(new Socket(host, 1943));
    //  System.out.println("check check");
			break;
		default:
			manyClients();
			break;
		}
	}
	//Used to treat the arguments
	public static void inputArguments(String[] args)
	{
		if(args.length > 0)
		{
			if(args.length == 3)
			{	//used for date/time
				if(Integer.parseInt(args[1]) == 1)
				{
					status = 1;
				}//used for Netstat
				else if(Integer.parseInt(args[1]) == 2)
				{
					status = 2;
				}
				multiClients = Integer.parseInt(args[2]);
			}
      else
      {
      status = 0;
      }
		}
		host = getHost(args);
	}
	//Used for functions and single client
	public static void giveToServer(Socket cSocket) throws IOException
	{
 //System.out.println("check check");
		if(cSocket != null)
		{
     //System.out.println("check2 check2");
			//Trying to setup input/output stream
			try
			(
					PrintWriter output = new PrintWriter(cSocket.getOutputStream(), true);
					BufferedReader input = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
					)
			{	//Will only reach this point if input/output stream is successful
				BufferedReader userData = new BufferedReader(new InputStreamReader(System.in));
				String userOutput;
				String serverOutput;
				//Loop to keep user in the menu options until Exit is selected which
				//will cause the program to terminate
				do
				{
					if((serverOutput = input.readLine()) != null)
					{
						if(serverOutput.equals("Select an option 1-6 or 7 to Exit"))
						{
							userOutput = userData.readLine();
							if(userOutput != null)
							{
								output.println(userOutput);
							}
						}
						//Will break the loop if the user selects Exit option
						else if(serverOutput.equals("Quit"))
						{
							System.out.println("Program Terminated");
							break;
						}
						else
						{
							if(serverOutput != null){
								System.out.println(serverOutput);
							}
						}
					}
				}while(!cSocket.isClosed()); //closing socket
			}
			catch(IOException e)
			{
				System.err.println("Error " +e.toString());
			}
		}
		//Ending the program
		System.out.println("Session Terminated");
		cSocket.close();
	}
	//A way to get the host
	public static String getHost(String[] arg)
	{
		String host = "192.168.100.115";
		port = 1943;
		if(arg.length > 0)
		{
			return arg[0];
		}
		else 
		{
			System.out.println("Choose 1 for defaut host");
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			try
			{
				int test = Integer.parseInt(input.readLine());
				switch(test)
				{
				case 1:
					System.out.println("Connecting to " + host);
					return host;
				default:
					System.out.println("Error. Terminating Program");
					System.exit(-1);
				}
			}
			catch (Exception e)
			{
				System.out.println("Error");
				System.exit(-1);
			}
		}
		return null;
	}
	//Used if there is going to be more than 1 client
	public static void manyClients()
	{
		switch (status)
		{
		case 1:
			try
			{
				System.out.println("Time \n");
				SmallThread[] thread = new SmallThread[multiClients];
				for (int x = 0; x < multiClients; x++)
				{
					thread[x] = new SmallThread(new Socket(host, port));
				}
				for(int x = 0; x < multiClients; x++)
				{
					thread[x].start();
				}
			}
			catch(Exception e)
			{
			}
			break;
		case 2:
			try
			{
				System.out.println("Time \n");
				LargeThread[] thread = new LargeThread[multiClients];
				for (int x = 0; x < multiClients; x++)
				{
					thread[x] = new LargeThread(new Socket(host, port));
				}
				for(int x = 0; x < multiClients; x++)
				{
					thread[x].start();
				}
			}
			catch(Exception e)
			{
			}
			break;
		}
	}

}
//Used to run date/time for multi clients
class SmallThread extends Thread
{
	private Socket inSocket = null;
	SmallThread(Socket client)
	{
		this.inSocket = client;

	}
	@Override
	public void run()
	{
		try
		{
			serviceThreads(this.inSocket);
		}
		catch(Exception e)
		{
		}

	}
	public static void serviceThreads(Socket cSocket) throws IOException
	{
   long startTime;
   long endTime;
		if(cSocket != null)
		{
			try
			(
					PrintWriter output = new PrintWriter(cSocket.getOutputStream(), true);
					BufferedReader input = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
					)
			{
				String serverOutput;
				startTime = System.currentTimeMillis();
				while(!cSocket.isClosed())
				{
					if((serverOutput = input.readLine()) != null)
					{
						if (serverOutput.equals("Select an option 1-6 or 7 to Exit"))
						{
               
							output.println("1");
							do
							{
								if((serverOutput = input.readLine()) != null)
								{
									if(serverOutput.equals("Select an option 1-6 or 7 to Exit"))
									{
                    
										endTime = System.currentTimeMillis();
										output.println("7");
                    System.out.println("Response time ");
                    System.out.println(endTime - startTime);
                    return;
										//while(true)
										//{
                      
										//	if(serverOutput.equals("Quit"))
										//	{
                     //   System.out.println("Small Quit");
	                    //  System.out.println(endTime - startTime);
											//	return;
										//	}
                    //  System.out.println("While True");
									//	}
									}
								}
							}while(true);
						}
					}
				}
			}
			catch (IOException e)
			{
				System.err.println(e.toString());
			}
		}
		cSocket.close();
	}
}
//used to run Netstat for multiple clients
class LargeThread extends Thread
{
	//creates threads
	private Socket inSocket = null;
	LargeThread(Socket client)
	{
		this.inSocket = client;
	}
	@Override
	public void run()
	{
		try
		{
			serviceThreads(this.inSocket);
		}
		catch(Exception e)
		{
		}

	}
	//To take care of each thread
	public static void serviceThreads(Socket cSocket) throws IOException
	{
   long startTime;
   long endTime;
		if(cSocket != null)
		{
			try
			(
					//attempt to set up input/out stream
					PrintWriter output = new PrintWriter(cSocket.getOutputStream(), true);
					BufferedReader input = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
					)
			{
				String serverOutput;
				startTime = System.currentTimeMillis();
				while(!cSocket.isClosed())
				{
					if((serverOutput = input.readLine()) != null)
					{
						if (serverOutput.equals("Select an option 1-6 or 7 to Exit"))
						{
							output.println("4");
							do
							{
								if((serverOutput = input.readLine()) != null)
								{
									if(serverOutput.equals("Select an option 1-6 or 7 to Exit"))
									{
										endTime = System.currentTimeMillis();
										output.println("7");
                    System.out.println("Response time ");
                    System.out.println(endTime- startTime);
                    return;
									//	while(true)
										//{
                      //System.out.println("While True");
											//if(serverOutput.equals("Quit"))
											//{
                       //  System.out.println("Quit");
												//System.out.println(endTime - startTime);
												//return;
											//}
										//}
									}
								}
							}while(true);
						}
					}
				}
			}
			catch (IOException e)
			{
				System.err.println(e.toString());
			}
		}
		cSocket.close();
	}
}
