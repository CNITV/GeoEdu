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

import java.util.ArrayList;

/**
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

	/**
	 * Constructs and initializes a Grade object. This takes the answer sheet to correct, and gets the answer key required.
	 *
	 * @param studentAnswerSheet     The answer sheet that will be given a grade.
	 * @param answerKey				 The answer key to evaluate the given answer sheet on.
	 */
	public Grade(AnswerSheet studentAnswerSheet, AnswerSheet answerKey) {
		this.studentAnswerSheet = studentAnswerSheet;
		this.answerKey = answerKey;
		this.gradeScoreDistribution = MAXIMUM_GRADE / answerKey.getNumberOfAnswers();
	}

	public void calculateGrade() {
		for (int i = 1; i <= studentAnswerSheet.getNumberOfAnswers(); i++) { // iterate through each answer
			String answer = studentAnswerSheet.getAnswer(i); // get the answer
			String answerType = answer.split(" ", 1)[0]; // see what kind of answer it is, if it is something
			if (answerType.equals("[MULTIPLE_ANSWER]")) { // if it is multiple choice type, we can evaluate, so ...
				if (studentAnswerSheet.getAnswer(i).equals(answerKey.getAnswer(i))) { // see if it is the same, and if so...
					currentGrade += gradeScoreDistribution; // ...add that score up.
				}
			} else {
				studentAnswerSheet.changeAnswer(i, "[EVALUATE] " + studentAnswerSheet.getAnswer(i)); // mark for evaluation by teacher
			}
		}
	}

	public ArrayList<Integer> getAnswersToEvaluate() {
		ArrayList<Integer> returnValue = new ArrayList<>();

		for (int i = 1; i <= studentAnswerSheet.getNumberOfAnswers(); i++) {
			//we are going to take every answer, split it, take the tag at the beginning if any, and see if it is marked for evaluation.
			if (studentAnswerSheet.getAnswer(i).split(" ", 1)[0].equals("[EVALUATE]")) { // if so...
				returnValue.add(i); // add it to the return.
			}
		}
		return returnValue;
	}

	public void EvaluateAnswer(Integer questionNumber, Double percentageGiven) {
		currentGrade += (percentageGiven / 100) * gradeScoreDistribution; // add that score to the grade
		studentAnswerSheet.changeAnswer(questionNumber, studentAnswerSheet.getAnswer(questionNumber).split(" ", 1)[1]); // Remove the "[EVALUATE]" part of the answer in the student sheet
	}

	public Double getCurrentGrade() {
		return currentGrade;
	}

	public AnswerSheet getStudentAnswerSheet() {
		return studentAnswerSheet;
	}

	public AnswerSheet getAnswerKey() {
		return answerKey;
	}
}
