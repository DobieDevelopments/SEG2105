package edu.seg2105.assignment1.exercise2.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * The Administrator class represents an administrative employee with specific tasks.
 * Extends the Employee class.
 * 
 * @autor Hussein Al Osman
 * edited by John Dobie - jdobi037@uottawa.ca - 300443432
 * edits: Added functionality so that the Administrator can be assigned tasks.
 */
public class Administrator extends Employee{

	// List of tasks assigned to the administrator
	List<String> tasks;

	/**
	 * Constructs a new Administrator with the given details.
	 * 
	 * @param firstName the first name of the administrator
	 * @param lastName the last name of the administrator
	 * @param id the ID of the administrator
	 * @param salary the salary of the administrator
	 */
	public Administrator(String firstName, String lastName, String id, double salary) {
		super(firstName, lastName, id, salary);
		tasks = new ArrayList<String>();
	}
	
	/**
	 * Adds a task to the administrator
	 * @param task - the task to be added.
	 */
	public void addTask(String task)
	{
		tasks.add(task);
	}
	
	/**
	 * Returns a list of the administrator's tasks in the form of a string with each task presented on it's own line.
	 * While the administrator has no tasks, they are free to make money and be happy. :)
	 * @return the concatenated string consisting of all existing tasks for the administrator.
	 */
	private String getTasksList()
	{
		String responsibilities = "";
		if(tasks.isEmpty()) { responsibilities += "\n\t  Get money, be happy."; }
		else
		{
			responsibilities += "\n";
			for(String s : tasks)
			{
				responsibilities += "\t  " + s + "\n";
			}
		}
		return responsibilities;
	}
	
	/**
	 * Generates and returns a string representation of the administrator.
	 * 
	 * @return a string representation of the administrator.
	 */
	@Override
	public String toString() {
		return "Administrator information:\n" +
				"\tFirst name: " + getFirstName() + "\n" + 
				"\tLast name: " + getLastName() + "\n" +
				"\tEmployee ID: " + getId() + "\n" + 
				"\tSalary: " + getSalary() + "\n" +
				"\tResponsibilities: " + getTasksList() + "\n";
				
	}
}
