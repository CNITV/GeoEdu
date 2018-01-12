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

import VianuEdu.backend.TestLibrary.AnswerSheet;
import VianuEdu.backend.Identification.Student;
import org.junit.Assert;
import org.junit.Test;

public class AnswerSheetTests {

	@Test
	public void AnswerSheetToStringShouldReturnJSONFormat() {

		String answerSheetSupposedOutput = "{\n" +
				"  \"answers\" : {\n" +
				"    \"1\" : \"gdisf\",\n" +
				"    \"2\" : \"lol\",\n" +
				"    \"3\" : \"[MULTIPLE_ANSWER] a\"\n" +
				"  },\n" +
				"  \"numberOfAnswersFilled\" : 3,\n" +
				"  \"numberOfAnswers\" : 3,\n" +
				"  \"testID\" : 462,\n" +
				"  \"student\" : {\n" +
				"    \"firstName\" : \"John\",\n" +
				"    \"fathersInitial\" : \"I\",\n" +
				"    \"lastName\" : \"Doe\",\n" +
				"    \"gender\" : \"M\",\n" +
				"    \"grade\" : 10,\n" +
				"    \"gradeLetter\" : \"F\",\n" +
				"    \"status\" : \"active\",\n" +
				"    \"userName\" : \"username\",\n" +
				"    \"password\" : \"password\"\n" +
				"  }\n" +
				"}";

		Student testStudent = new Student("John", "I", "Doe", "M", 10, "F", "active", "username", "password");
		AnswerSheet testAnswerSheet = new AnswerSheet(testStudent, 462, 3);

		testAnswerSheet.addAnswer(1, "gdisf");
		testAnswerSheet.addAnswer(2, "lol");
		testAnswerSheet.addMultipleChoiceAnswer(3, "a");

		Assert.assertEquals(answerSheetSupposedOutput, testAnswerSheet.toString());
	}

}
