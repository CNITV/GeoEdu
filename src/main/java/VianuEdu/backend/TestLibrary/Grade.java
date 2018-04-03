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
import VianuEdu.backend.Identification.Teacher;

import java.util.ArrayList;

/**
 * This class represents a grade.
 *
 * This is an object used to easily calculate and submit grades once completing a test in order to save them for later
 * use and analysis. The Grade object was primarily created in order to give teachers the ability to correct some parts
 * of tests themselves, considering a machine is incapable of clearly evaluating a written answer, compared to what a
 * human being is capable of.
 *
 * @author StormFireFox1
 * @since 2018-01-05
 */
public class Grade {

	private final Double MAXIMUM_GRADE = 100.0;
	private Double currentGrade = 0.0;
	private Double gradeScoreDistribution;
	private AnswerSheet studentAnswerSheet;
	private AnswerSheet answerKey;
	private Teacher teacher;

	/**
	 * Constructs and initializes a Grade object. This takes the answer sheet to correct, and gets the answer key required.
	 *
	 * @param studentAnswerSheet The answer sheet that will be given a grade.
	 * @param answerKey          The answer key to evaluate the given answer sheet on.
	 * @param teacher			 The teacher that will evaluate the grade.
	 */
	public Grade(AnswerSheet studentAnswerSheet, AnswerSheet answerKey, Teacher teacher) {
		this.studentAnswerSheet = studentAnswerSheet;
		this.answerKey = answerKey;
		this.teacher = teacher;
		this.gradeScoreDistribution = MAXIMUM_GRADE / answerKey.getNumberOfAnswers();
		this.calculateGrade();
	}

	/**
	 * Calculates the grade that the paper has. Due to physical limitations, the grade will not be fully calculated if the answer sheet
	 * contains any answer that is not multiple choice, as those must be evaluated manually by the teacher in question.
	 */
	private void calculateGrade() {
		for (int i = 1; i <= studentAnswerSheet.getNumberOfAnswers(); i++) { // iterate through each answer
			String answer = studentAnswerSheet.getAnswer(i); // get the answer
			String answerType = answer.substring(0, 17);
			if (answerType.equals("[MULTIPLE_ANSWER]")) { // if it is multiple choice type, we can evaluate, so ...
				if (studentAnswerSheet.getAnswer(i).equals(answerKey.getAnswer(i))) { // see if it is the same, and if so...
					currentGrade += gradeScoreDistribution; // ...add that score up.
				}
			} else {
				studentAnswerSheet.changeAnswer(i, "[EVALUATE] " + studentAnswerSheet.getAnswer(i)); // mark for evaluation by teacher
			}
		}
	}

	/**
	 * Returns the indices for the answers that must be evaluated by the teacher.
	 *
	 * @return An ArrayList with in indices.
	 */
	public ArrayList<Integer> getAnswersToEvaluate() {
		ArrayList<Integer> returnValue = new ArrayList<>();

		for (int i = 1; i <= studentAnswerSheet.getNumberOfAnswers(); i++) {
			//we are going to take every answer, split it, take the tag at the beginning if any, and see if it is marked for evaluation.
			if (studentAnswerSheet.getAnswer(i).substring(0, 10).equals("[EVALUATE]")) { // if so...
				returnValue.add(i); // add it to the return.
			}
		}
		return returnValue;
	}

	/**
	 * Manually evaluate an answer.
	 *
	 * @param questionNumber  The indice of the question number.
	 * @param percentageGiven The percentage given. The number must be between 0 and 100.
	 */
	public void EvaluateAnswer(Integer questionNumber, Double percentageGiven) {
		if (percentageGiven < 0 || percentageGiven > 100) {
			throw new IllegalArgumentException("Percentage must be between 0 and 100!");
		}
		if (!studentAnswerSheet.getAnswer(questionNumber).substring(0, 10).equals("[EVALUATE]")) {
			throw new IllegalArgumentException("Question must be up for evaluation, you cannot evaluate a multiple choice answer!");
		}
		currentGrade += (percentageGiven / 100.0) * gradeScoreDistribution; // add that score to the grade
		studentAnswerSheet.changeAnswer(questionNumber, studentAnswerSheet.getAnswer(questionNumber).substring(10)); // Remove the "[EVALUATE]" part of the answer in the student sheet
	}

	/**
	 * Gets the current grade.
	 *
	 * @return The current grade.
	 */
	public Double getCurrentGrade() {
		return currentGrade;
	}

	/**
	 * Gets the student's answer sheet from the grade.
	 *
	 * @return The student's answer sheet.
	 */
	public AnswerSheet getStudentAnswerSheet() {
		return studentAnswerSheet;
	}

	/**
	 * Gets the answer key from the grade.
	 *
	 * @return The answer key.
	 */
	public AnswerSheet getAnswerKey() {
		return answerKey;
	}

	/**
	 * Gets the teacher that will evaluate the grade.
	 *
	 * @return The teacher object.
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * Returns a JSON string with indentation that represents a grade. Uses Gson JSON library.
	 *
	 * @return A JSON string representing a Grade object.
	 */
	@Override
	public String toString() {
		return JSONManager.toIndentedJSON(this);
	}
}
