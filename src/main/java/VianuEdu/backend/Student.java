/*
 * This file is part of VianuEdu.
 *
 *     VianuEdu is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     VianuEdu is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with VianuEdu.  If not, see <http://www.gnu.org/licenses/>.
 *
 *     Developed by Matei Gardus <matei@gardus.eu>
 */

package VianuEdu.backend;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.StringWriter;

/**
 * A class that represents a student.
 *
 * Primarily used to identify the student in the database. The amount of parameters required to construct a Student object
 * are enough to differentiate two potentially similar students. It also keeps track of whether a student is in a specific
 * status (active, absent, on vacation, graduated, etc).
 *
 * @author StormFireFox1
 * @since 2017-12-23
 */
public class Student {

	private String firstName;
	private String fathersInitial;
	private String lastName;
	private String gender;
	private Integer grade;
	private String gradeLetter;
	private String status;


	/**
	 * Constructs and initializes a Student object.
	 *
	 * @param firstName		 The student's first name. Must not be empty.
	 * @param fathersInitial The student's father's first name initial. Used to further identify a student. Must not be empty.
	 * @param lastName       The student's last name. Must not be empty.
	 * @param gender         The student's gender. Must be either male (M) or female (F).
	 * @param grade          The student's grade. Must be between 1 and 12.
	 * @param gradeLetter    The student's grade letter. Must be one letter long and belong to the English alphabet.
	 * @param status         The student's status. Must be one of the following: "active", "absent", "on vacation", or "graduated".
	 */
	public Student(String firstName, String fathersInitial, String lastName, String gender, Integer grade, String gradeLetter, String status) {
		if (firstName.equals("")) {
			throw new IllegalArgumentException("Student must have a first name!");
		} else if (fathersInitial.equals("")) {
			throw new IllegalArgumentException("Student must have his/her father's initial given!");
		} else if (lastName.equals("")) {
			throw new IllegalArgumentException("Student must have a last name!");
		} else if (!gender.equals("M") && !gender.equals("F")) {
			throw new IllegalArgumentException("Student must be either male (M) or female (F)!");
		} else if (!(grade > 0 && grade <= 12)) {
			throw new IllegalArgumentException("Student must be between 1st and 12th grade!");
		} else if (!"ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(gradeLetter) || !(gradeLetter.length() == 1)) {
			throw new IllegalArgumentException("The grade letter must be one letter long, between A and Z!");
		} else if (!(status.equals("active") || status.equals("absent") || status.equals("on vacation") || status.equals("graduated"))) {
			throw new IllegalArgumentException("Student must be either active, absent, on vacation, or graduated!");
		}
		this.firstName = firstName;
		this.fathersInitial = fathersInitial;
		this.lastName = lastName;
		this.gender = gender;
		this.grade = grade;
		this.gradeLetter = gradeLetter;
		this.status = status;
	}

	/**
	 * Advances a student's grade by one.
	 *
	 * If the student is already in the 12th grade, it will not advance, but instead throw an exception.
	 */
	public void advanceGrade() {
		if (grade == 12) {
			throw new IndexOutOfBoundsException("Cannot advance a student's grade higher than 12! Graduate him using the setStatus() method.");
		} else {
			grade++;
		}
	}

	/**
	 * Gets the student's first name.
	 *
	 * @return The student's first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the student's father's first name initial.
	 *
	 * @return The student's father's first name initial.
	 */
	public String getFathersInitial() {
		return fathersInitial;
	}

	/**
	 * Gets the student's last name.
	 * @return The student's last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the student's gender.
	 *
	 * @return The student's gender.
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Gets the student's grade.
	 *
	 * @return The student's grade.
	 */
	public Integer getGrade() {
		return grade;
	}

	/**
	 * Gets the student's grade letter.
	 *
	 * @return The student's grade letter.
	 */
	public String getGradeLetter() {
		return gradeLetter;
	}

	/**
	 * Gets the student's status.
	 *
	 * @return The student's status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the student's status. Do note this method should be used sparingly, as changing status often can cause discrepancies in the database.
	 *
	 * @param status The student's new status. Must be one of the following: "active", "absent", "on vacation", or "graduated".
	 */
	public void setStatus(String status) {
		if (!(status.equals("active") || status.equals("absent") || status.equals("on vacation") || status.equals("graduated"))) {
			throw new IllegalArgumentException("Student must be either active, absent, on vacation, or graduated!");
		} else {
			this.status = status;
		}
	}


	/**
	 * Returns a JSON string with indentation that represents a student. Uses Jackson JSON library.
	 *
	 * @return A JSON string that represents a Student object.
	 */
	@Override
	public String toString() {
		ObjectMapper jsonManager = new ObjectMapper(); // creates an ObjectMapper instance from Jackson
		jsonManager.configure(SerializationFeature.INDENT_OUTPUT, true); // enables pretty print
		StringWriter jsonOutput = new StringWriter(); // writeValue() method requires a Writer
		try {
			jsonManager.writeValue(jsonOutput, this); // write in jsonOutput the value of a Student
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonOutput.toString();
	}

}
