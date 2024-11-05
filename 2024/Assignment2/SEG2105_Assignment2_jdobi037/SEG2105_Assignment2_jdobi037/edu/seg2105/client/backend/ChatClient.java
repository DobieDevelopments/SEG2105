// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package edu.seg2105.client.backend;

import ocsf.client.*;

import java.io.*;
import java.net.ConnectException;
import java.net.UnknownHostException;

import edu.seg2105.client.common.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  
  /**
   * The Login ID variable of this ChatClient instance.
   * Used to differentiate a user from one another.
   */
  String loginID;

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String id, String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    this.loginID = id;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    this.clientUI.display(msg.toString());
    
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
	    if(message.startsWith("#"))
	    {
	    	handleCommand(message);
	    }
	    else
	    {
	    	if(isConnected())
	    	{
	    		sendToServer(message);
	    	}
	    	else
	    	{
	    		this.clientUI.displayError("Error: you are not connected to the server.");
	    	}
	    }
    }
    catch(IOException e)
    {
      clientUI.displayError("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  /**
   * This method handles specific commands that will be sent to the server.
   */
  private void handleCommand(String command)
  {
	  if(command.equalsIgnoreCase("#quit"))
	  {
		  this.clientUI.display("Exiting client...");
		  quit();
	  }
	  else if(command.equalsIgnoreCase("#login"))
	  { 
		  if(isConnected())
		  {
			  this.clientUI.display("client is already connected to the server.");
			  this.clientUI.display("Sending login info...");
			  sendLogon(this.loginID);
		  }
		  else
		  {
			  Boolean issues = false;
			  this.clientUI.display("client logging in...");
			  
			  try
			  {
				  openConnection();
				  sendLogon(this.loginID);
			  } catch (ConnectException e)
			  {
				  issues = true;
				  this.clientUI.displayError("Connection refused.");
				  this.clientUI.displayError("perhaps the server you are trying to connect to is listening on a different port?");
			  }
			  catch(UnknownHostException e)
			  {
				  issues = true;
				  this.clientUI.displayError("Unknown host...");
				  this.clientUI.displayError("Maybe the hostname is incorrect? Was there a typo?");
			  }
			  catch (IOException e) 
			  {
				  issues = true;
				  e.printStackTrace();
			  }
			  finally
			  {
				 if(issues)
				 {
					 this.clientUI.displayError("Error logging on.");
				 }
			  }
		  }
	  }
	  else if(command.equalsIgnoreCase("#logoff"))
	  {	
		  if(!isConnected())
		  {
			  this.clientUI.display("you are not connected to the server.");
		  }
		  else
		  {
			  this.clientUI.display("client logging off...");
			  try
			  {
				  super.closeConnection();
				  this.clientUI.display("client disconnected successfully.");
			  } catch (IOException e) 
			  {
				  this.clientUI.displayError("Error logging off.");
				  e.printStackTrace();
			  }
		  }
	  }
	  else if(command.toLowerCase().startsWith("#sethost"))
	  {
		  if(!isConnected())
		  {
			  String newhost = command.toLowerCase().substring(9);
			  this.clientUI.display("updating host name...from [" + getHost() + "] to [" + newhost + "]");
			  setHost(newhost);
			  this.clientUI.display("host name updated successfully.");
		  }
		  else
		  {
			  this.clientUI.display("please log off before trying to modify connection details. [#logoff]");
		  }
	  }
	  else if(command.toLowerCase().startsWith("#setport"))
	  {
		  if(!isConnected())
		  {
			  String newport = command.toLowerCase().substring(9);
			  this.clientUI.display("updating port number...from [" + getPort() + "] to [" + newport + "]");
			  try
			  {
				  int iport = Integer.parseInt(newport);
				  setPort(iport);
				  this.clientUI.display("port number updated successfully.");
			  }
			  catch (NumberFormatException e)
			  {
				  this.clientUI.displayError("Incorrect format for port number...please try again.");
				  this.clientUI.displayError("port number update unsuccessful.");
			  }
		  }
		  else
		  {
			  this.clientUI.display("please log off before trying to modify connection details. [#logoff]");
		  }
	  }
	  else if(command.equalsIgnoreCase("#gethost"))
	  {	this.clientUI.display("the current host for this client is: " + getHost()); }
	  else if(command.equalsIgnoreCase("#getport"))
	  {	this.clientUI.display("the current port for this client is: " + getPort()); }
	  else if(command.toLowerCase().startsWith("#"))
	  {
		  this.clientUI.display("the possible commands are: quit, logoff, sethost <host>, setport <port>, login, gethost, getport");
		  this.clientUI.display("maybe you meant one of these?");
	  }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  public void sendLogon(String id)
  {
	//Connection to server has succeeded, initially send the user's login ID to the server to complete clientside setup.
	    try
	    {
	    	sendToServer("#login " + id);
	    	this.clientUI.display("Logging in user... [" + id + "]");
	    }
	    catch(NullPointerException ex)
	    {
	    	this.clientUI.displayError("there was a problem with the specified loginID");
	    	this.clientUI.displayError("USER ID: null.");
	    	ex.printStackTrace();
	    }
	    catch(IOException ex)
	    {
	    	this.clientUI.displayError("There was a problem sending this message to the server.");
	    	this.clientUI.displayError("Shutting down.");
	    	ex.printStackTrace();
	    	quit();
	    }
  }

//////////////////////
//Overridden methods
//////////////////////

/**
 * Implements the hook method called after the connection has been closed. The default
 * implementation does nothing. The method may be overriden by subclasses to
 * perform special processing such as cleaning up and terminating, or
 * attempting to reconnect.
 */
@Override
protected void connectionClosed()
{
	this.clientUI.display("Connection closed.");
}

/**
 * Implements the hook method called each time an exception is thrown by the client's
 * thread that is waiting for messages from the server. The method may be
 * overridden by subclasses.
 * 
 * @param exception
 *            the exception raised.
 */
@Override
protected void connectionException(Exception exception)
{
	this.clientUI.display("The server has shut down.");
	this.clientUI.display("Error connecting to the server.");
	
	quit();
}

/**
 * Implementation of hook method called after a connection has been established. The default
 * implementation does nothing. It may be overridden by subclasses to do
 * anything they wish.
 */
@Override
protected void connectionEstablished() 
{
	this.clientUI.display("Connection established successfully.");
}

}
//End of ChatClient class
