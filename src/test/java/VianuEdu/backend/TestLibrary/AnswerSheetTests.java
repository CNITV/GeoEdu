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

import VianuEdu.backend.Identification.Student;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnswerSheetTests {

	@Test
	public void toStringShouldShowJSON() {

		Student testStudent = new Student();

		AnswerSheet testAnswerSheet = new AnswerSheet(testStudent, "T-000000", 3);

		testAnswerSheet.addAnswer(1, "When life gives you lemons, don't make lemonade.");
		testAnswerSheet.addMultipleChoiceAnswer(2, "a");
		testAnswerSheet.addAnswer(3, "The supreme art of war is to subdue the enemy without fighting.");

		String JSONString = "{\n" +
				"  \"answers\": {\n" +
				"    \"1\": \"When life gives you lemons, don\\u0027t make lemonade.\",\n" +
				"    \"2\": \"[MULTIPLE_ANSWER] a\",\n" +
				"    \"3\": \"The supreme art of war is to subdue the enemy without fighting.\"\n" +
				"  },\n" +
				"  \"numberOfAnswersFilled\": 3,\n" +
				"  \"numberOfAnswers\": 3,\n" +
				"  \"testID\": \"T-000000\",\n" +
				"  \"student\": {\n" +
				"    \"firstName\": \"Dexter\",\n" +
				"    \"fathersInitial\": \"Z\",\n" +
				"    \"lastName\": \"Iftode\",\n" +
				"    \"gender\": \"M\",\n" +
				"    \"grade\": 12,\n" +
				"    \"gradeLetter\": \"Z\",\n" +
				"    \"status\": \"graduated\",\n" +
				"    \"account\": {\n" +
				"      \"userName\": \"IfDex22\",\n" +
				"      \"password\": \"parolasecreta\"\n" +
				"    }\n" +
				"  }\n" +
				"}";

		assertEquals(JSONString, testAnswerSheet.toString());

	}
}