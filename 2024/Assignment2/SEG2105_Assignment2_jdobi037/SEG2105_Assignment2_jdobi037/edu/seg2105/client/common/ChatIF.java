// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package edu.seg2105.client.common;

/**
 * This interface implements the abstract method used to display
 * objects onto the client or server UIs.
 *
 * @author Dr Robert Lagani&egrave;re
 * @author Dr Timothy C. Lethbridge
 */
public interface ChatIF 
{	
	/**
	 * Method that when overridden is used to display objects onto
	 * a UI.
	 */
	public abstract void display(Object msg);
  
	/**
	 * Method that when overridden is used to display error objects onto
	 * a UI.
	 * @param message - the error to print
	 */
	public abstract void displayError(Object message);

	/**
	 * Method that when overidden is used to display unprefixed print statements to the inheritors UI
	 * @param statement - the message to print
	 */
	public abstract void print(Object statement);
}
