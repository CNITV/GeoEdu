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

import java.util.ArrayList;

/**
 * The Question class represents a test question, with its respective correct answer.
 * <p>
 * Essentially, this is a Pair object specialised to hold questions and answers. However, this object allows for the easy
 * creation of multiple-choice questions and attachment of images while simultaneously reading them in a simpler way.
 *
 * @author StormFireFox1
 * @since 2018-04-14
 */
public class Question {
	private String question;
	private String answer;
	private byte[] image;
	private ArrayList<String> questionChoices;
	private String questionType;

	/**
	 * Constructs and initializes a normal question.
	 *
	 * @param question The question.
	 * @param answer   The answer.
	 */
	public Question(String question, String answer) {
		this.question = question;
		this.answer = answer;
		this.questionType = "normal";
	}

	/**
	 * Constructs and initializes a normal question with an image attached.
	 *
	 * @param question The question.
	 * @param image    The image to attach to the question.
	 * @param answer   The answer.
	 */
	public Question(String question, byte[] image, String answer) {
		if (image.length == 0) {
			throw new IllegalArgumentException("Image can't be nil! Just make a normal question instead!");
		} else if (image.length > 500000) {
			throw new IllegalArgumentException("Image can't be bigger than 500KB!");
		}
		this.question = question;
		this.image = image;
		this.answer = answer;
		this.questionType = "normal + image";
	}

	/**
	 * Constructs and initializes a multiple-choice question.
	 *
	 * @param question        The question.
	 * @param questionChoices The choices for the question.
	 * @param answer          The answer. Must exist within the choices of the question.
	 */
	public Question(String question, ArrayList<String> questionChoices, String answer) {
		if (!questionChoices.contains(answer)) {
			throw new IllegalArgumentException("Cannot submit a question whose answer doesn't actually exist in the choices!");
		}
		this.question = question;
		this.questionChoices = questionChoices;
		this.answer = answer;
		this.questionType = "multiple-choice";
	}

	/**
	 * Constructs and initializes a multiple-choice question with an image attached.
	 *
	 * @param question        The question.
	 * @param image           The image to attach to the question.
	 * @param questionChoices The choices for the question.
	 * @param answer          The answer. Must exist within the choices of the question.
	 */
	public Question(String question, byte[] image, ArrayList<String> questionChoices, String answer) {
		if (!questionChoices.contains(answer)) {
			throw new IllegalArgumentException("Cannot submit a question whose answer doesn't actually exist in the choices!");
		} else if (image.length == 0) {
			throw new IllegalArgumentException("Image can't be nil! Just make a normal question instead!");
		} else if (image.length > 500000) {
			throw new IllegalArgumentException("Image can't be bigger than 500KB!");
		}
		this.question = question;
		this.image = image;
		this.questionChoices = questionChoices;
		this.answer = answer;
		this.questionType = "multiple-choice + image";
	}

	/**
	 * Gets the question.
	 *
	 * @return The question.
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Gets the answer.
	 *
	 * @return The answer.
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * Gets the image attached to the question in byte array form.
	 *
	 * @return The image in byte array form.
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * Gets the question choices.
	 *
	 * @return An ArrayList<String> containing each one of the choices.
	 */
	public ArrayList<String> getQuestionChoices() {
		if (questionType.equals("normal")) {
			throw new IllegalStateException("This is not a multiple-choice question!");
		}
		return questionChoices;
	}

	/**
	 * Gets the question choice.
	 * <p>
	 * Can be either a normal question or a multiple-choice question, with an image attached or not.
	 *
	 * @return "normal" if this is a normal question or "multiple-choice" if this is a multiple-choice question. "+ image" is appended if image exists.
	 */
	public String getQuestionType() {
		return questionType;
	}

	/**
	 * Changes the question.
	 *
	 * @param newQuestion The new question to change to.
	 */
	public void changeQuestion(String newQuestion) {
		this.question = newQuestion;
	}

	/**
	 * Changes the answer.
	 *
	 * @param newAnswer The new answer to change to.
	 */
	public void changeAnswer(String newAnswer) {
		this.answer = newAnswer;
	}

	public void changeQuestionChoices(ArrayList<String> questionChoices, String answer) {
		if (questionType.equals("normal")) {
			throw new IllegalStateException("This is not a multiple-choice question!");
		}
		if (!questionChoices.contains(answer)) {
			throw new IllegalArgumentException("Cannot submit a question whose answer doesn't actually exist in the choices!");
		}
		this.questionChoices = questionChoices;
		this.answer = answer;
	}

	/**
	 * Returns a JSON string with indentation that represents a question. Uses Gson JSON library.
	 *
	 * @return A JSON string representing a Question object.
	 */
	@Override
	public String toString() {
		return JSONManager.toJSON(this);
	}
}
