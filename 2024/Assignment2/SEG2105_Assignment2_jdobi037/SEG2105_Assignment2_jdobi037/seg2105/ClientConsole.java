package seg2105;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.Scanner;

import edu.seg2105.client.backend.ChatClient;
import edu.seg2105.client.common.ChatIF;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 */
public class ClientConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  /**
	 * The prefix for which the inheritor can display when outputting to a UI
	 */
	public static String OUTPUT_PREFIX = "PREFIX> ";

	/**
	 * The prefix for which the inheritor can display an error when outputting to a UI
	 */
	public static String ERROR_PREFIX = "ERROR_PREFIX> ";
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;
  
  /**
   * Scanner to read from the console
   */
  Scanner fromConsole;
	
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String id, String host, int port) 
  {
	  this.OUTPUT_PREFIX = "> ";
	  this.ERROR_PREFIX = "ERROR> ";
	  
    try 
    {
      client= new ChatClient(id, host, port, this);
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
    
    client.sendLogon(id);
    
    // Create scanner object to read from console
    fromConsole = new Scanner(System.in); 
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
    try
    {

      String message;

      while (true) 
      {
        message = fromConsole.nextLine();
        client.handleMessageFromClientUI(message);
      }
    } 
    catch (Exception ex) 
    {
      System.err.println("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  @Override
  public void display(Object message) 
  {
    System.out.println(OUTPUT_PREFIX + message);
  }
  
  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  @Override
  public void displayError(Object message) 
  {
    System.err.println(ERROR_PREFIX + message);
  }
  
  /**
   * This method overrides the method in the ChatIF interface.
   * it is used to print messages internally to the inheritors console without any special prefixes.
   */
  @Override
	public void print(Object statement) 
  {
	  System.out.println(statement);
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {
	if(args.length == 0)
	{
		System.err.println("Mandatory Command-line arguement not provided... [LOGIN ID], exiting.");
		System.exit(1);
		//throw new IllegalArgumentException("Arguement [0] is required.");
	}
	  
	String loginID = "";
    String host = "";
    int port = -1;

    try
    {
      loginID = args[0];
      host = args[1];
      port = Integer.parseInt(args[2]);
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
      port = DEFAULT_PORT;
    }
    catch (NumberFormatException e) 
    {
    	port = DEFAULT_PORT;
	}
    
    ClientConsole chat = new ClientConsole(loginID, host, port);    
    chat.accept();  //Wait for console data
  }
}
//End of ConsoleChat class
