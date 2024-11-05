package edu.server.backend;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.IOException;
//import java.io.EOFException;
//import java.net.ConnectException;
//import java.net.UnknownHostException;

import edu.seg2105.client.common.ChatIF;
import ocsf.server.*;
import seg2105.ServerConsole;

import java.util.ArrayList;
import java.util.List;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the server.
   */
  private ChatIF serverUI; 
  
  private List<ConnectionToClient> clientsList;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port, ChatIF serverUI) 
  {
    super(port);
    this.serverUI = serverUI;
    clientsList = new ArrayList<>();
  }
  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
	//if the message is from the client, echo it to the server's console.
	//otherwise we can ignore it as we typed it.
	if(client != null)
    {
		if(msg.toString().startsWith("#login"))
		{			
			if(client.getInfo("connected") != null) 
			{
				if((boolean) client.getInfo("connected"))
				{
					if(client.getInfo("ID") != null)
					{
						this.serverUI.display("User @" + client.getInfo("ID") + " is already connected.");
						this.serverUI.display("terminating client to prevent misuse.");
						this.sendToAllClients(ServerConsole.OUTPUT_PREFIX + "User already connected, terminating client. " + client.getInfo("ID"));
						try
						{
							client.close();
						}
						catch(IOException ex)
						{
							this.serverUI.displayError("Error: something went wrong when trying to terminate client for multiple logins.");
						}
						this.serverUI.display("Connection to @" + client.getInfo("ID") + " terminated.");
					}
					else
					{
						this.sendToAllClients(ServerConsole.OUTPUT_PREFIX + "User already connected, terminating client.");
						try
						{
							client.close();
						}
						catch(IOException ex)
						{
							this.serverUI.displayError("Error: something went wrong when trying to terminate client for multiple logins.");
						}
						this.serverUI.display("Connection to client terminated.");
					}
				}
			}
			
			String newuserID = msg.toString().split(" ", 2)[1];
			Boolean newClient = true;
			for(ConnectionToClient c : clientsList)
			{
				if(c.getInfo("ID").equals(newuserID))
				{
					newClient = false;
					//The client has previously connected.
					client.setInfo("ID", newuserID);
					c = client;
					this.sendToAllClients(ServerConsole.OUTPUT_PREFIX + "User logged in: " + client.getInfo("ID"));
					this.sendToAllClients(ServerConsole.OUTPUT_PREFIX + "Welcome back, " + client.getInfo("ID"));
				}
			}
			if(newClient)
			{
				this.serverUI.print("Anew client has connected to the server.");
				this.serverUI.print("CLIENT MSG> @" + client.getInfo("ID") + ": " + msg);
				client.setInfo("ID", newuserID);
				this.serverUI.print(client.getInfo("ID") + " has logged on.");
				this.sendToAllClients(ServerConsole.OUTPUT_PREFIX + "User logged in: " + client.getInfo("ID"));
				clientsList.add(client);
			}
				
			if(client.getInfo("connected") == null) 
			{
				client.setInfo("connected", true);
			}
		}
		String clientname = client.getInfo("ID") != null ? (String) client.getInfo("ID") : "[login ID not provided]";
		clientname += " @ " + client.getInetAddress().getHostName();
		
		this.serverUI.print("CLIENT MSG> " + clientname + ": " + msg);
		
		if(client.getInfo("ID") != null && !msg.toString().startsWith("login"))
		{
			this.sendToAllClients("@" + client.getInfo("ID") + ": " + msg);
		}
    }
	else
	{
		this.serverUI.display(msg);
		this.sendToAllClients(ServerConsole.OUTPUT_PREFIX + msg);
	}
  }
  
  /**
   * This method handles all data coming from the UI
   * 
   * @param message - the message from the UI
   */
  public void handleMessageFromServerUI(String message)
  {
	  if(message.contains("#"))
	  {
		  handleCommand(message);
	  }
	  else
	  {
		  if(isListening())
		  {
			  try
			  {
				  handleMessageFromClient(message, null);
			  }
			  catch(Exception e)
			  {
				  serverUI.displayError("Could not send messages to clients, something went wrong.");
				  e.printStackTrace();
			  }
		  }
		  else
		  {
			  serverUI.print("I am not listening!");
		  }
	  }
  }
  
  /**
   * This method handles specific commands that will be processed by the server.
   */
  private void handleCommand(String command)
  {
	  if(command.length() > 1)
	  command = command.split("#", 2)[1];
	  if(command.toLowerCase().startsWith("#") || command.equalsIgnoreCase("#") || command.isEmpty())
	  {
		  this.serverUI.print("the possible commands are: quit, stop, close, setport <port>, start, getport\n");
		  this.serverUI.print("maybe you meant one of these?");
	  }
	  else if(command.equalsIgnoreCase("quit"))
	  {
		  if(getNumberOfClients() > 0)
		  {
			  this.serverUI.print("There are " + getNumberOfClients() + " clients still connected.");
			  this.serverUI.print("Closing connections to all clients...");
		  }
		  this.serverUI.print("Shutting down Server.");
		  try
		  {
			  close();
			  this.serverUI.print("No clients remaining.");
			  this.serverUI.print("Server shut down.");
			  System.exit(0);
		  }
		  catch (IOException e) 
		  {
			this.serverUI.displayError("Error closing connections.");
		  }
	  }
	  else if(command.equalsIgnoreCase("stop"))
	  { 
		  if(isListening())
		  {
			  stopListening();
			  this.serverUI.print("The server has stopped listening for connections.");
		  }
		  else
		  {
			  this.serverUI.print("The server has already stopped listening. Command disregarded.");
		  }
		  this.serverUI.print("Currently connected clients: " + getNumberOfClients());
		  this.serverUI.print("Server status: server stopped.");
	  }
	  else if(command.equalsIgnoreCase("start"))
	  {	
		  if(!isListening())
		  {
			  /*String[] messages = { "Connecting the cables.", "Winding up the little monkey with the cymbals.", "Turning on the kettle.", "Eating an apple a day." };
			  java.util.Random random = new java.util.Random();
		      int rand = random.nextInt(messages.length);
			  this.serverUI.print(messages[rand]);*/
		      
			  this.serverUI.print("Starting up the server.");
			  try
			  {
				  listen();
			  }
			  catch(IOException ex)
			  {
				  this.serverUI.displayError("Error: we had some trouble starting the server.");
			  }
			  this.serverUI.print("Server status: server started: " + isListening());
		  }
		  else
		  {
			  this.serverUI.print("The server is already listening. Command disregarded.");
			  this.serverUI.print("Currently connected clients: " + getNumberOfClients());
		  }
	  }
	  else if(command.equalsIgnoreCase("close"))
	  {	
		  if(isListening())
		  {
			  this.serverUI.print("Server status: server stopped: " + !isListening());
			  if(getNumberOfClients() > 0)
			  {
				 this.serverUI.print("Currently connected clients: " + getNumberOfClients());
				 this.serverUI.print("Closing server and disconnecting clients.");
			  }
				 this.serverUI.print("Closing server, no clients remaining to disconnect.");
				 try
				 {
					close();
				 }
				 catch (IOException e)
				 {	
					this.serverUI.displayError("Error: issue disconnecting clients.");
				 	e.printStackTrace();
				 }
		  
			  this.serverUI.print("Server status: server closed.");
		  }
		  else
		  {
			 if(!isListening() && getNumberOfClients() == 0)
			 {
				 this.serverUI.print("The server has already been closed.");
			 }
			 else
			 {
				 this.serverUI.print("Server status: server stopped: " + !isListening());
				 this.serverUI.print("Clients currently connected: " + getNumberOfClients());
				 this.serverUI.print("Disconnecting all clients.");
				 try
				 {
					close();
				 }
				 catch (IOException e)
				 {	
					this.serverUI.displayError("Error: issue disconnecting clients.");
				 	e.printStackTrace();
				 }
			 }
		  }
	  }
	  else if(command.toLowerCase().startsWith("setport"))
	  {
		  if(!isListening() && getNumberOfClients() == 0)
		  {
			  String newport = command.toLowerCase().split(" ", 2)[1];
			  this.serverUI.print("updating port number...from [" + getPort() + "] to [" + newport + "]");
			  try
			  {
				  int iport = Integer.parseInt(newport);
				  setPort(iport);
				  this.serverUI.print("port number updated successfully.");
			  }
			  catch (NumberFormatException e)
			  {
				  this.serverUI.displayError("Incorrect format for port number...please try again.");
				  this.serverUI.displayError("port number update unsuccessful.");
			  }
		  }
		  else
		  {
			  this.serverUI.print("please close the server first before trying to modify connection details. [#close]");
		  }
	  }
	  else if(command.equalsIgnoreCase("getport"))
	  {	
		  this.serverUI.print("the server is currently listening on port: " + getPort());
	  }
	  else
	  {
		  this.serverUI.displayError("Unknown command.");
	  }
	  //this.serverUI.display("cmd: " + command);
  }

//////////////////////
//Overridden methods
//////////////////////
/**
 * Implementation of hook method called each time a new client connection is
 * accepted. The default implementation does nothing.
 * @param client the connection connected to the client.
 */
@Override
protected void clientConnected(ConnectionToClient client) 
{
	this.serverUI.print("Client " + getNumberOfClients() + " connected: " + client.toString() + " on port: " + getPort());
	super.clientConnected(client);
}

/**
 * Implementation of hook method called each time a client disconnects.
 * The default implementation does nothing. The method
 * may be overridden by subclasses but should remains synchronized.
 *
 * @param client the connection with the client.
 */
@Override
synchronized protected void clientDisconnected(ConnectionToClient client) 
{
	this.serverUI.print("Client disconnected");
	super.clientDisconnected(client);
}


/**
 * Implementation of hook method called each time an exception is thrown in a
 * ConnectionToClient thread.
 * The method may be overridden by subclasses but should remains
 * synchronized.
 *
 * @param client the client that raised the exception.
 * @param Throwable the exception thrown.
 */
/*@Override
synchronized protected void clientException(ConnectionToClient client, Throwable exception) 
{
	if(client != null)
	{
		String clientname = client.getInfo("ID") != null ? (String) client.getInfo("ID") : "[login ID not provided]";
		this.serverUI.displayError("Error: something has happened with client: " + clientname);
	}
	else
	{
		this.serverUI.displayError("Error: something has happened with a client.");
	}
	
	if(exception instanceof EOFException)	//if we override this, then we consistently get EOFException hit when we call #quit on the client side...
	{
		this.serverUI.displayError("EOF Exception: The client has disconnected unexpectedly.");
	}
	else if(exception instanceof IOException)
	{
		this.serverUI.displayError("IO Exception: An I/O error has occured: " + exception.getMessage());
	}
	else
	{
		this.serverUI.displayError("An unexpected error has occured: " + exception.getMessage());
		exception.printStackTrace();
	}
	super.clientException(client, exception);
}*/

/**
 * Implementation of hook method called when the server stops accepting
 * connections because an exception has been raised.
 * The default implementation does nothing.
 * This method may be overriden by subclasses.
 *
 * @param exception the exception raised.
 */
@Override
protected void listeningException(Throwable exception)
{
	this.serverUI.displayError("Error: the server has run into a problem.");
	this.serverUI.displayError(exception.getMessage());
	exception.printStackTrace();
}

@Override
protected void serverStarted() 
{
	this.serverUI.print("The server is listening for connections on port " + getPort());
	super.serverStarted();
}

}
//End of EchoServer class