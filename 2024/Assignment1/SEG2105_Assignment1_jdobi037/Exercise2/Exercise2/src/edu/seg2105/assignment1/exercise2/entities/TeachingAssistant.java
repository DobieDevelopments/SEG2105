package edu.seg2105.assignment1.exercise2.entities;

/**
 * The TeachingAssistant class represents a teacher's assistant, extending the Instructor class and implementing the Teacher interface.
 * It includes methods specific to the role of a TA.
 * 
 * @author John Dobie - jdobi037@uottawa.ca - 300443432
 */
public class TeachingAssistant extends Instructor
{

	/**
	 * Constructs a new Teaching assistant with the specified details.
	 *
	 * @param firstName the first name of the professor
	 * @param lastName the last name of the professor
	 * @param id the unique identifier of the professor
	 * @param salary the salary of the professor
	 */
	public TeachingAssistant(String firstName, String lastName, String id, double salary) {
		super(firstName, lastName, id, salary);
	}
	
	//A teacher's assistant can only take 3 courses on at a time.
	private final int max_courses = 3;

	/**
	 * Assigns a course to the teacher's assistant if the maximum number of courses is not exceeded.
	 * 
	 * @param course the course to be assigned
	 * @return true if the course was successfully assigned, false otherwise
	 */
	@Override
	public boolean assignCourse(Course course) 
	{
		if (courses.size() < max_courses) 
		{
			courses.add(course);
			return true;
		} 
		else 
		{
			return false;
		}
	}

}
