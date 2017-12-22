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

import java.util.HashMap;

/**
 * This class provides a method of storing the answer sheet for any test or exercise.
 * <p>
 * In general, it stores all the answers in the test or exercise in a HashMap and also allows for methods
 * to upload to the database. This class could've been avoided by directly uploading to the online database,
 * however it is much safer to also have a local backup of the answers should the database malfunction.
 * In addition, exercises have their scores calculated locally.
 *
 * @author StormFireFox1
 * @since 2017-12-22
 */
public class AnswerSheet {

	private HashMap<Integer, String> answers = new HashMap<>();
	private int numberOfAnswersFilled;
	private int numberOfAnswers;
	private String studentName;

	/**
	 * Constructs and initializes an empty answer sheet.
	 *
	 * @param studentName     The name of the student currently using the program.
	 * @param numberOfAnswers The amount of answers that the current test or exercise can have. The number must be higher than 0.
	 */
	public AnswerSheet(String studentName, int numberOfAnswers) {
		if (!(numberOfAnswers > 0)) {
			throw new IllegalArgumentException("The number of answers has to be higher than 0!");
		}
		this.studentName = studentName;
		this.numberOfAnswers = numberOfAnswers;
	}

	/**
	 * Adds an answer to the answer sheet. This method will not add answers if the answer sheet is already full.
	 *
	 * @param questionNumber The question number. It must be between 1 and the last answer's number.
	 * @param answer         The answer itself. This value must be passed on either plaintext, or formatted accordingly.
	 * @return true if the answer is saved successfully and false if it wasn't added due to the answer exceeding the amount of answers available.
	 */
	public boolean addAnswer(Integer questionNumber, String answer) {
		if (answers.keySet().contains(questionNumber)) {
			throw new IllegalArgumentException("Cannot add an already added answer! You can modify it using the changeAnswer() method.");
		} else if (!(questionNumber > 0 && questionNumber <= numberOfAnswers)) {
			throw new IllegalArgumentException("Cannot add an answer that is not between 1 and the last answer!");
		} else {
			if (numberOfAnswersFilled < numberOfAnswers) {
				answers.put(questionNumber, answer);
				numberOfAnswersFilled++;
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Changes an answer in the answer sheet. This method will fail if the answer does not exist.
	 *
	 * @param questionNumber The question number. It must be between 1 and the last answer's number.
	 * @param answer         The answer itself. This value must be passed on either plaintext, or formatted accordingly.
	 * @return true if the answer is changed successfully and never returns false.
	 */
	public boolean changeAnswer(Integer questionNumber, String answer) {
		if (!answers.keySet().contains(questionNumber)) {
			throw new IllegalArgumentException("Cannot change a non-existent answer! You can add one using the addAnswer() method.");
		} else if (!(questionNumber > 0 && questionNumber <= numberOfAnswers)) {
			throw new IllegalArgumentException("Cannot change an answer that is not between 1 and the last answer!");
		} else {
			answers.put(questionNumber, answer);
			return true;
		}
	}

	/**
	 * Removes an answer from the answer sheet. This method will fail if the answer does not exist.
	 *
	 * @param questionNumber The question number. It must be between 1 and the last answer's number.
	 * @return true if the answer is changed successfully and never returns false.
	 */
	public boolean removeAnswer(Integer questionNumber) {
		if (!answers.keySet().contains(questionNumber)) {
			throw new IllegalArgumentException("Cannot remove a non-existent answer!");
		} else if (!(questionNumber > 0 && questionNumber <= numberOfAnswers)) {
			throw new IllegalArgumentException("Cannot change an answer that is not between 1 and the last answer!");
		} else {
			answers.remove(questionNumber);
			return true;
		}
	}

	/**
	 * Adds an answer to the answer sheet from a multiple-choice question.
	 * <p>
	 * This method is primarily used in order to allow for easy differentiation between a plain textbox answer and
	 * a multiple-choice question answer, since both are stored as Strings.
	 *
	 * @param questionNumber The question number. It must be between 1 and the last answer's number.
	 * @param answer         The answer itself. The string must be one letter long.
	 * @return true if the answer is saved successfully and false if it wasn't added due to the answer exceeding the amount of answers available.
	 */
	public boolean addMultipleChoiceAnswer(Integer questionNumber, String answer) {
		if (answer.length() != 1) {
			throw new IllegalArgumentException("Cannot add an answer different from a, b, c, ... to a multiple-choice question! Make sure to use the addAnswer() method if the answer comes from a textbox.");
		} else {
			return this.addAnswer(questionNumber, "[MULTIPLE_ANSWER] " + answer);
		}
	}

	/**
	 * Gets the student name attached to this answer sheet.
	 *
	 * @return the student name attached to this answer sheet.
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * Gets an answer from the answer sheet.
	 *
	 * @param questionNumber The question number. It must be between 1 and the last answer's number.
	 * @return the answer associated with the question number.
	 */
	public String getAnswer(Integer questionNumber) {
		if (!answers.keySet().contains(questionNumber)) {
			throw new IllegalArgumentException("Cannot get a non-existent answer!");
		} else if (!(questionNumber > 0 && questionNumber <= numberOfAnswers)) {
			throw new IllegalArgumentException("Cannot get an answer that is not between 1 and the last answer!");
		} else {
			return answers.get(questionNumber);
		}
	}

//	TODO create toString() override in JSON format. Most likely done with GSON (Google JSON).

}
