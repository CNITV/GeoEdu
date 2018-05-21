/*
 *
 *  This file is part of VianuEdu.
 *
 *       VianuEdu is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       VianuEdu is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 *
 *       You should have received a copy of the GNU General Public License
 *       along with VianuEdu.  If not, see <http://www.gnu.org/licenses/>.
 *
 *       Developed by Matei Gardus <matei@gardus.eu>
 */

package VianuEdu.backend.Identification;

import VianuEdu.backend.DatabaseHandling.JSONManager;

/**
 * A class that represents a teacher.
 * <p>
 * Primarily used to identify the teacher in the database. The amount of parameters required to construct a Teacher object
 * are enough to differentiate two potentially similar teacher. It also keeps track of the permissions that a specific teacher
 * may have (i.e. what students he/she can give grades to).
 *
 * @author StormFireFox1
 * @since 2018-02-10
 */
public class Teacher {

	private String firstName;
	private String lastName;
	private String gender;
	private String course;
	private Integer grade;
	private String gradeLetter;
	private Account account;

	/**
	 * Creates a generic teacher object that will be used to attach to test answer keys.
	 *
	 * I'm sorry for the teachers that might recognize themselves upon finding this little fragment of code, but alas,
	 * I had no ideas for names, quite frankly.
	 */
	public Teacher() {
		this.firstName = "Acob";
		this.lastName = "Ucsene";
		this.gender = "F";
		this.course = "Info";
		this.grade = 11;
		this.gradeLetter = "G";
		this.account = new Account("ucsene_the_student_slayer", "SpaghettiBrokenCode22");
	}

	/**
	 * Constructs and initialises a Teacher object.
	 *
	 * @param firstName The first name of the teacher. Must not be empty.
	 * @param lastName  The last name of the teacher. Must not be empty.
	 * @param gender    The gender of the teacher. Must be either male (M) or female (F).
	 * @param course    The course the teacher offers. Must be supported by VianuEdu. Currently only geography, physics, informatics and mathematics are supported. (Written as, "Geo", "Phi", "Info" and "Math")
	 * @param account   The account of the teacher.
	 */
	public Teacher(String firstName, String lastName, String gender, String course, Integer grade, String gradeLetter, Account account) {
		if (firstName.equals("")) {
			throw new IllegalArgumentException("First name must not be empty!");
		} else if (lastName.equals("")) {
			throw new IllegalArgumentException("Last name must not be empty!");
		} else if (!gender.equals("M") && !gender.equals("F")) {
			throw new IllegalArgumentException("Teacher must be either male (M) or female (F)!");
		} else if (!(course.equals("Geo") || course.equals("Phi") || course.equals("Info") || course.equals("Math"))) {
			throw new IllegalArgumentException("Teacher must teach a VianuEdu-compatible course!");
		} else if (grade < 1 || grade > 12) {
			throw new IllegalArgumentException("Grade must be between 1 and 12!");
		} else if (!("ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(gradeLetter) && gradeLetter.length() <= 1)) {
			throw new IllegalArgumentException("Grade letter must be between A and Z!");
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.course = course;
		this.grade = grade;
		this.gradeLetter = gradeLetter;
		this.account = account;
	}

	/**
	 * Gets the teacher's first name.
	 *
	 * @return The teacher's first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the teacher's last name.
	 *
	 * @return The teacher's last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the teacher's gender.
	 *
	 * @return The teacher's gender.
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Gets the teacher's gender.
	 *
	 * @return The teacher's gender.
	 */
	public String getCourse() {
		return course;
	}

	/**
	 * Gets the teacher's grade.
	 *
	 * @return The teacher's grade.
	 */
	public Integer getGrade() {
		return grade;
	}

	/**
	 * Gets the teacher's grade letter.
	 *
	 * @return The teacher's grade letter.
	 */
	public String getGradeLetter() {
		return gradeLetter;
	}

	/**
	 * Gets the teacher's account.
	 *
	 * @return The teacher's account.
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * Returns a JSON string with indentation that represents a teacher. Uses Gson JSON library.
	 *
	 * @return A JSON string representing a Teacher object.
	 */
	@Override
	public String toString() {
		return JSONManager.toIndentedJSON(this);
	}
}
