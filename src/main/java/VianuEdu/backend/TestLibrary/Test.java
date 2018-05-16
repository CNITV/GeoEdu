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

package VianuEdu.backend.TestLibrary;

import VianuEdu.backend.DatabaseHandling.JSONManager;
import VianuEdu.backend.Identification.Student;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * The Test class represents all the information necessary in order to organise a test in the VianuEdu interface.
 * <p>
 * A test should be defined by the following:
 * -start time and end time of the test;
 * -the subject for which the test is dispensed;
 * -the ID;
 * -the grade that will take the test;
 * -the questions that the test will contain.
 * <p>
 * This Test class is capable of quickly rendering the test in HTML format to be displayed in the application.
 *
 * @author StormFireFox1
 * @since 2018-04-12
 */
public class Test {
	private String testID;
	private String testName;
	private String course;
	private Date startTime;
	private Date endTime;
	private Double normalQuestionPercentage;
	private Integer grade;
	private String gradeLetter;
	private HashMap<Integer, Question> contents;

	/**
	 * Constructs and initializes a test where all question are graded with equal points.
	 *
	 * @param testID      The ID for the test. Must follow the specific VianuEdu format! (i.e. ID for a test is T-000001)
	 * @param testName    The name of the test. Must not be empty.
	 * @param course      The course for which the test is made for. Must be supported by VianuEdu. Currently only geography, physics, informatics and mathematics are supported. (Written as, "Geo", "Phi", "Info" and "Math")
	 * @param startTime   The time at which the test will start.
	 * @param endTime     The time at which the test will end.
	 * @param grade       The grade for which the test will be administered.
	 * @param gradeLetter The letter of the grade for which the test will be administered.
	 * @param contents    The contents of the test. A HashMap which contains the question and the answer key for the test.
	 */
	public Test(String testID, String testName, String course, Date startTime, Date endTime, Integer grade, String gradeLetter, HashMap<Integer, Question> contents) {
		if (!(testID.matches("T-([0123456789])\\w+"))) {
			throw new IllegalArgumentException("Test ID must be of specific VianuEdu format! (i.e. ID for tests are T-00001)");
		} else if (testName.isEmpty()) {
			throw new IllegalArgumentException("Test name must not be empty!");
		} else if (!(course.equals("Geo") || course.equals("Phi") || course.equals("Info") || course.equals("Math"))) {
			throw new IllegalArgumentException("Teacher must teach a VianuEdu-compatible course!");
		} else if (startTime.before(new Date())) {
			throw new IllegalArgumentException("Cannot dispense a test in the past! (Back to the future?)");
		} else if (endTime.before(startTime)) {
			throw new IllegalArgumentException("Cannot have a test finish before it even started! (Stop time travelling, god dammit!)");
		} else if (grade < 9 || grade > 12 || !"ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(gradeLetter) || gradeLetter.length() != 1) {
			throw new IllegalArgumentException("This is not a class! Deal with it!");
		}
		this.testID = testID;
		this.testName = testName;
		this.course = course;
		this.startTime = startTime;
		this.endTime = endTime;
		this.grade = grade;
		this.gradeLetter = gradeLetter;
		this.contents = contents;
	}

	/**
	 * Constructs and initializes a test where the percentage of the grade attributed to normal questions is defined.
	 *
	 * @param testID                   The ID for the test. Must follow the specific VianuEdu format! (i.e. ID for a test is T-000001)
	 * @param testName                 The name of the test. Must not be empty.
	 * @param course                   The course for which the test is made for. Must be supported by VianuEdu. Currently only geography, physics, informatics and mathematics are supported. (Written as, "Geo", "Phi", "Info" and "Math")
	 * @param startTime                The time at which the test will start.
	 * @param endTime                  The time at which the test will end.
	 * @param normalQuestionPercentage The percentage of the final grade attributed to normal questions.
	 * @param grade                    The grade for which the test will be administered.
	 * @param gradeLetter              The letter of the grade for which the test will be administered.
	 * @param contents                 The contents of the test. A HashMap which contains the question and the answer key for the test.
	 */
	public Test(String testID, String testName, String course, Date startTime, Date endTime, Double normalQuestionPercentage, Integer grade, String gradeLetter, HashMap<Integer, Question> contents) {
		if (!(testID.matches("T-([0123456789])\\w+"))) {
			throw new IllegalArgumentException("Test ID must be of specific VianuEdu format! (i.e. ID for tests are T-00001)");
		} else if (testName.isEmpty()) {
			throw new IllegalArgumentException("Test name must not be empty!");
		} else if (!(course.equals("Geo") || course.equals("Phi") || course.equals("Info") || course.equals("Math"))) {
			throw new IllegalArgumentException("Teacher must teach a VianuEdu-compatible course!");
		} else if (startTime.before(new Date())) {
			throw new IllegalArgumentException("Cannot dispense a test in the past! (Back to the future?)");
		} else if (endTime.before(startTime)) {
			throw new IllegalArgumentException("Cannot have a test finish before it even started! (Stop time travelling, god dammit!)");
		} else if (normalQuestionPercentage < 0 || normalQuestionPercentage > 100) {
			throw new IllegalArgumentException("Normal question percentage must be between 0 and 100%!");
		} else if (grade < 9 || grade > 12 || !"ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(gradeLetter) || gradeLetter.length() != 1) {
			throw new IllegalArgumentException("This is not a class! Deal with it!");
		}
		this.testID = testID;
		this.testName = testName;
		this.course = course;
		this.startTime = startTime;
		this.endTime = endTime;
		this.normalQuestionPercentage = normalQuestionPercentage;
		this.grade = grade;
		this.gradeLetter = gradeLetter;
		this.contents = contents;
	}

	/**
	 * Gets the test ID of the test.
	 *
	 * @return The test ID.
	 */
	public String getTestID() {
		return testID;
	}

	/**
	 * Gets the name of the test.
	 *
	 * @return The name of the test.
	 */
	public String getTestName() {
		return testName;
	}

	/**
	 * Gets the course for which the test is made for.
	 *
	 * @return The course for which the test is made for.
	 */
	public String getCourse() {
		return course;
	}

	/**
	 * Gets the start time of the test.
	 *
	 * @return The start time of the test.
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * Gets the end time of the test.
	 *
	 * @return The end time of the test.
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * Gets the percentage for the grade attributed to normal questions.
	 *
	 * @return The percentage for the grade attributed to normal questions.
	 */
	public Double getNormalQuestionPercentage() {
		return normalQuestionPercentage;
	}

	/**
	 * Get the class for which the test is administered.
	 *
	 * @return The class for which the test is administered.
	 */
	public String getGrade() {
		return grade + gradeLetter;
	}

	/**
	 * Gets the contents of the test in RAW format.
	 *
	 * @return The contents of the test in RAW format.
	 */
	public HashMap<Integer, Question> getContents() {
		return contents;
	}

	/**
	 * Checks whether the question is a multiple-choice question or not.
	 *
	 * @param questionNumber The question number for which to check the type of question.
	 * @return True if it is a multiple-choice question, false if otherwise.
	 */
	public boolean isMultipleAnswer(Integer questionNumber) {
		return contents.get(questionNumber).getQuestionType().equals("multiple-choice") || contents.get(questionNumber).getQuestionType().equals("multiple-choice + image");
	}

	/**
	 * Gets the multiple choices that a specific question has, if it does have any.
	 *
	 * @param questionNumber The question number for which to get the multiple choices.
	 * @return The multiple choices in an ArrayList of Strings.
	 */
	public ArrayList<String> getMultipleChoices(Integer questionNumber) {
		if (this.isMultipleAnswer(questionNumber)) {
			throw new IllegalArgumentException("Question is not a multiple-choice question!");
		}
		return contents.get(questionNumber).getQuestionChoices();
	}

	/**
	 * Gets the answer key for the test.
	 * <p>
	 * Essentialy, this parses the content and only extracts the answers for each of the questions in the test.
	 *
	 * @return An AnswerSheet object containing all of the answers for the test.
	 */
	public AnswerSheet getAnswerKey() {
		AnswerSheet result = new AnswerSheet(new Student(), this.testID, contents.size());

		Integer questionNumber = 1;

		for (Question question : contents.values()) {
			if (question.getQuestionType().equals("multiple-choice") || question.getQuestionType().equals("multiple-choice + image")) {
				result.addAnswer(questionNumber, "[MULTIPLE_ANSWER] " + question.getAnswer().substring(0, 1));
			} else {
				result.addAnswer(questionNumber, question.getAnswer());
			}
			questionNumber++;
		}
		return result;
	}

	/**
	 * Returns a JSON string with indentation that represents a test. Uses Gson JSON library.
	 *
	 * @return A JSON string representing a Test object.
	 */
	@Override
	public String toString() {
		return JSONManager.toIndentedJSON(this);

	}
}
