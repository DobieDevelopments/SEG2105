package seg2105;

import java.util.Scanner;

import edu.server.backend.EchoServer;
import edu.seg2105.client.common.ChatIF;

/**
* This class constructs the UI for a server client.  It implements the
* chat interface in order to activate the display() method.
* Warning: Some of the code here is cloned in ClientConsole 
*
* @author John Dobie jdobi037@uottawa.ca
*/
public class ServerConsole implements ChatIF {

	//instance variables ***************************************************
	  
	/**
	 * The instance of the server that created this console
	 */
	EchoServer server;
	
	/**
	 * Scanner to read from the console
	 */
	Scanner fromConsole;
	
	 //Class variables *************************************************
	  
	/**
	 * The prefix for which the inheritor can display when outputting to a UI
	 */
	public static String OUTPUT_PREFIX = "SERVER MESSAGE PREFIX> ";
	
	/**
	 * The prefix for which the inheritor can display an error when outputting to a UI
	 */
	public static String ERROR_PREFIX = "SERVER ERROR PREFIX> ";
	  
	  
	//Interface methods ***************************************************
	  
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
		 * This method is similar to using the ChatIF interface, but is only used for printing internally to the server console.
		 * @param message
		 */
		@Override
		public void print(Object statement)
		{
			System.out.println(statement);
		}
	
		  /**
		   * Method that when overridden is used to display error objects onto
		   * a UI.
		   */
		  @Override
		  public void displayError(Object message)
		  {
			  System.err.println(ERROR_PREFIX + message);
		  }
		  
		  //Constructors ***************************************************
		  
		  /**
		   * Constructs an instance of the ServerConsole UI.
		   * 
		   * @param port - the port to listen on.
		   */
		  public ServerConsole(int port)
		  {
			//Set up the desired prefixes for output logging
			  this.OUTPUT_PREFIX = "SERVER MESSAGE> ";
			  this.ERROR_PREFIX = "SERVER ERROR> ";
			  server = new EchoServer(port, this);
			  
			  //initialize the scanner object
			  fromConsole = new Scanner(System.in);
		  }
		
		  //Instance methods ***************************************************
		  
		  /**
		   * This method waits for input from the console. Once it is received
		   * it sends to the echoserver's message handler.
		   */
		  public void handleInput()
		  {
			  try
			  {
				  String message;
				  
				  while(true)
				  {
					  message = fromConsole.nextLine();
					  server.handleMessageFromServerUI(message);
				  }
			  }
			  catch (Exception e) {
				displayError("Unexpected error while reading from console!");
				e.printStackTrace();
			}
		  }
		  
		  //Class methods ***************************************************
		  
		  /**
		   * This method is responsible for the creation of 
		   * the server instance (there is no UI in this phase).
		   *
		   * @param args[0] The port number to listen on.  Defaults to 5555 
		   *          if no argument is entered.
		   */
		  public static void main(String[] args) 
		  {
		    int port = 0; //Port to listen on
	
		    try
		    {
		      port = Integer.parseInt(args[0]); //Get port from command line
		    }
		    catch(Throwable t)
		    {
		      port = EchoServer.DEFAULT_PORT; //Set port to 5555
		    }
			
		    ServerConsole server = new ServerConsole(port);
		    //EchoServer server = new EchoServer(port);
		    
		    try 
		    {
		    	server.server.listen(); //Start listening for connections
		    	server.handleInput();
		    } 
		    catch (Exception ex) 
		    {
		    	server.displayError("Error: Could not listen for clients!");
		    }
		  }

}
