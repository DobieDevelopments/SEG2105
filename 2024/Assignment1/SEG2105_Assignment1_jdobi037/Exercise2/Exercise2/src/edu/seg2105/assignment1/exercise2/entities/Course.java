package edu.seg2105.assignment1.exercise2.entities;

import java.util.ArrayList;
import java.util.List;

import edu.seg2105.assignment1.exercise2.util.TableGenerator;

/**
 * The Course class represents a university course.
 * It includes attributes and methods related to the course details, professor, and students.
 * 
 * @author Hussein Al Osman
 * edited by John Dobie - jdobi037@uottawa.ca - 300443432
 * edits: Added details to allow a teacher's assistant to be assigned to a course.
 */
public class Course {
    // The code identifying the course
    private String courseCode;
    // The title of the course
    private String courseTitle;
    // The description of the course
    private String courseDescription;
    // The professor teaching the course
    private Professor professor;
    // The list of students enrolled in the course
    private List<Student> students;
    // The Teacher's Assistant that is assisting with the course
    private TeachingAssistant ta;

    /**
     * Constructs a new Course with the specified details.
     *
     * @param courseCode the code identifying the course
     * @param courseTitle the title of the course
     * @param courseDescription the description of the course
     */
    public Course(String courseCode, String courseTitle, String courseDescription) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.students = new ArrayList<Student>();
    }

    /**
     * Gets the course code.
     *
     * @return the course code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Sets the course code.
     *
     * @param courseCode the course code to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Gets the course description.
     *
     * @return the course description
     */
    public String getCourseDescription() {
        return courseDescription;
    }

    /**
     * Sets the course description.
     *
     * @param courseDescription the course description to set
     */
    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    /**
     * Gets the course title.
     *
     * @return the course title
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * Sets the course title.
     *
     * @param courseTitle the course title to set
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * Gets the professor teaching the course.
     *
     * @return the professor
     */
    public Professor getProfessor() {
        return professor;
    }

    /**
     * Sets the professor teaching the course.
     *
     * @param professor the professor to set
     */
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    /**
     * Gets the list of students enrolled in the course.
     *
     * @return the list of students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Adds a student to the course.
     *
     * @param student the student to add
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Gets the name of the professor teaching the course.
     *
     * @return the professor's full name or "Not yet assigned" if no professor is set
     */
    private String getProfessorName() {
        return professor != null ? professor.getFirstName() + " " + professor.getLastName() : "Not yet assigned";
    }

    /**
     * Gets a formatted list of the students enrolled in the course.
     *
     * @return a formatted string of the student list
     */
    private String getStudentTable() {
    	
    	
    	// We will generate a table that corresponds to the list of students registered to this course.
    	// The table's columns are Student ID, First name, and Last name. 
    	// To do so, we will transfer the data in the students list into a 2D array
    	// The length of the array corresponds to the number of elements in the students list,
    	// while the width is 3 (since we have three columns).
    	int tableRows = students.size();
    	int tableColumns = 3;
    	
    	String[][] data = new String[tableRows][tableColumns]; // This 2D array will contain the data
    	
    	String[] headers = new String [tableColumns]; // This array will contain the column headers
    	
    	
    	// Fill the first row with column headers
    	headers[0] = "Student ID";
    	headers[1] = "First name";
    	headers[2] = "Last name";
    	
    	// Fill the rest of the 2D array with the information from the students list
    	for (int i = 0; i<data.length; i++) {   		
    		data[i][0] = students.get(i).getId();
    		data[i][1] = students.get(i).getFirstName();
    		data[i][2] = students.get(i).getLastName();

    	}
    	
    	// Use our utility class to generate a string that is formatted like a table
    	String tableStr = TableGenerator.generateTableString(data, headers, 20);
    	
    	return tableStr;
        
    }
    
    /**
     * Gets the teacher's assistant helping out with the course.
     *
     * @return the teacher's assistant
     */
    public TeachingAssistant getTeachersAssistant() {
        return ta;
    }

    /**
     * Sets the teacher's assistant helping out with the course.
     *
     * @param ta - the teacher's assistant to set
     */
    public void setTeachersAssistant(TeachingAssistant teachersAssistant) {
        this.ta = teachersAssistant;
    }
    
    /**
     * Gets the name of the teacher's assistant that is helping out with the course.
     *
     * @return the teacher's assistant's full name or "Not yet assigned" if no teacher's assistant is set yet
     */
    private String getTeachersAssistantsName() {
        return ta != null ? ta.getFirstName() + " " + ta.getLastName() : "Not yet assigned";
    }

    /**
     * Returns a string representation of the course.
     * 
     * @return a string representation of the course
     */
    @Override
    public String toString() {
        return "Course information:\n"
                + "\tCourse code: " + courseCode + "\n"
                + "\tCourse title: " + courseTitle + "\n"
                + "\tCourse description: " + courseDescription + "\n"
                + "\tCourse instructor: " + getProfessorName() + "\n"
                + "\tCourse teaching assistant: " + getTeachersAssistantsName() + "\n"
                + (students.size() > 0 ? "\tList of registered students:" + getStudentTable() : "");
    }
}
