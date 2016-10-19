import java.io.*;
import java.net.*;

public class Project1Client {
	//Global variables so don't have to re-initialized in every method
	protected static int port;
	protected static int multiClients;
	protected static int status;
	protected static String host;

	public static void main(String[] args) throws IOException {
		inputArguments(args);
		switch(status)
		{
		case 1:
			giveToServer(new Socket(host, 1943));
			break;
		default:
			manyClients();
			break;
		}
	}
	public static void inputArguments(String[] input)
	{
		if(input.length > 0)
		{
			if(input.length == 3)
			{
				if(Integer.parseInt(input[1]) == 2)
				{
					status = 2;
				}
				else if(Integer.parseInt(input[1]) == 3)
				{
					status = 2;
				}
				multiClients = Integer.parseInt(input[2]);
			}
		}
		host = getHost(input);
	}
	public static void giveToServer(Socket cSocket) throws IOException
	{
		if(cSocket != null)
		{
			try
			(
					PrintWriter output = new PrintWriter(cSocket.getOutputStream(), true);
					BufferedReader input = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
					)
			{
				BufferedReader userData = new BufferedReader(new InputStreamReader(System.in));
				String userOutput;
				String serverOutput;
				do
				{
					if((serverOutput = input.readLine()) != null)
					{
						if(serverOutput.equals("Please select an option"))
						{
							userOutput = userData.readLine();
							if(userOutput != null)
							{
								output.println(userOutput);
							}
						}
						else if(serverOutput.equals("Exit"))
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
				}while(!cSocket.isClosed());
			}
			catch(IOException e)
			{
				System.err.println("Error " +e.toString());
			}
		}
		System.out.println("Session Terminated");
		cSocket.close();
	}
	public static String getHost(String[] arg)
	{
		//String host = "192.168.100.115";
		port = 1943;
		if(arg.length > 0)
		{
			return arg[0];
		}
		else
		{
			System.out.println("No arguments entered \n Good-bye");
			return null;
		}
	}
	public static void manyClients()
	{
		switch (status)
		{
		case 2:
			try
			{
				System.out.println("ID, Time \n");
				multiThread[] thread = new multiThread[multiClients];
				for (int x = 0; x < multiClients; x++)
				{
					thread[x] = new multiThread(new Socket(host,port), x);
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
class multiThread extends Thread
{
	private Socket inSocket = null;
	private static int id;
	multiThread(Socket client, int id)
	{
		this.inSocket = client;
		this.id = id;
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
		if(cSocket != null)
		{
			try
			(
					PrintWriter output = new PrintWriter(cSocket.getOutputStream(), true);
					BufferedReader input = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
					)
			{
				String serverOutput;
				Sample samp;
				samp = new Sample(System.currentTimeMillis());
				while(!cSocket.isClosed())
				{
					if((serverOutput = input.readLine()) != null)
					{
						if (serverOutput.equals("Please select an option"))
						{
							samp.endingTime = System.currentTimeMillis();
							output.println("7");
							do
							{
								if(serverOutput.equals("Exit"))
								{
									Sample.timeResult(id, samp);
									return;
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
class Sample
{
	public long startingTime;
	public long endingTime;
	Sample(long startTimer)
	{
		this.startingTime = startTimer;
	}
	public void endTime(long endTimer)
	{
		this.endingTime = endTimer;
	}
	public String toString()
	{
		return Long.toString(endingTime - startingTime);
	}

	public static void timeResult(int id, Sample time) throws IOException
	{
		System.out.println(id+ "," + time.toString()+ "\n");
	}
}
