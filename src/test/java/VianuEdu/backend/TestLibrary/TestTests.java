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

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TestTests {

	@Test
	public void toStringWorks() throws ParseException {
		HashMap<Integer, Question> contents = new HashMap<>();
		contents.put(1, new Question("Did you answer this question?", "Yes."));
		contents.put(2, new Question("How are you?", "Fine, thank you very much."));
		ArrayList<String> questionChoices = new ArrayList<>();
		questionChoices.add("a) Pretty.");
		questionChoices.add("b) Very.");
		questionChoices.add("c) Not at all.");
		contents.put(3, new Question("How happy are you right now?", questionChoices, "a) Pretty."));
		ArrayList<String> questionChoices2 = new ArrayList<>();
		questionChoices2.add("a) It's cool.");
		questionChoices2.add("b) It's not cool.");
		questionChoices2.add("c) Please send help.");
		byte[] image = "Hypothetically, this is an image in a byte array, but y'know.".getBytes();
		contents.put(4, new Question("Is everything cool?", image, questionChoices2, "c) Please send help."));
		Date startTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2049-02-21 10:30:00");
		Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2049-02-21 11:20:00");

		VianuEdu.backend.TestLibrary.Test test = new VianuEdu.backend.TestLibrary.Test("T-000012", "The Genesis Test Mk42", "Geo", startTime, endTime, 75.0, 12, "Z", contents);

		String jsonString = "{\n" +
				"  \"testID\": \"T-000012\",\n" +
				"  \"testName\": \"The Genesis Test Mk42\",\n" +
				"  \"course\": \"Geo\",\n" +
				"  \"startTime\": \"Feb 21, 2049 10:30:00 AM\",\n" +
				"  \"endTime\": \"Feb 21, 2049 11:20:00 AM\",\n" +
				"  \"normalQuestionPercentage\": 75.0,\n" +
				"  \"grade\": 12,\n" +
				"  \"gradeLetter\": \"Z\",\n" +
				"  \"contents\": {\n" +
				"    \"1\": {\n" +
				"      \"question\": \"Did you answer this question?\",\n" +
				"      \"answer\": \"Yes.\",\n" +
				"      \"questionType\": \"normal\"\n" +
				"    },\n" +
				"    \"2\": {\n" +
				"      \"question\": \"How are you?\",\n" +
				"      \"answer\": \"Fine, thank you very much.\",\n" +
				"      \"questionType\": \"normal\"\n" +
				"    },\n" +
				"    \"3\": {\n" +
				"      \"question\": \"How happy are you right now?\",\n" +
				"      \"answer\": \"a) Pretty.\",\n" +
				"      \"questionChoices\": [\n" +
				"        \"a) Pretty.\",\n" +
				"        \"b) Very.\",\n" +
				"        \"c) Not at all.\"\n" +
				"      ],\n" +
				"      \"questionType\": \"multiple-choice\"\n" +
				"    },\n" +
				"    \"4\": {\n" +
				"      \"question\": \"Is everything cool?\",\n" +
				"      \"answer\": \"c) Please send help.\",\n" +
				"      \"image\": [\n" +
				"        72,\n" +
				"        121,\n" +
				"        112,\n" +
				"        111,\n" +
				"        116,\n" +
				"        104,\n" +
				"        101,\n" +
				"        116,\n" +
				"        105,\n" +
				"        99,\n" +
				"        97,\n" +
				"        108,\n" +
				"        108,\n" +
				"        121,\n" +
				"        44,\n" +
				"        32,\n" +
				"        116,\n" +
				"        104,\n" +
				"        105,\n" +
				"        115,\n" +
				"        32,\n" +
				"        105,\n" +
				"        115,\n" +
				"        32,\n" +
				"        97,\n" +
				"        110,\n" +
				"        32,\n" +
				"        105,\n" +
				"        109,\n" +
				"        97,\n" +
				"        103,\n" +
				"        101,\n" +
				"        32,\n" +
				"        105,\n" +
				"        110,\n" +
				"        32,\n" +
				"        97,\n" +
				"        32,\n" +
				"        98,\n" +
				"        121,\n" +
				"        116,\n" +
				"        101,\n" +
				"        32,\n" +
				"        97,\n" +
				"        114,\n" +
				"        114,\n" +
				"        97,\n" +
				"        121,\n" +
				"        44,\n" +
				"        32,\n" +
				"        98,\n" +
				"        117,\n" +
				"        116,\n" +
				"        32,\n" +
				"        121,\n" +
				"        39,\n" +
				"        107,\n" +
				"        110,\n" +
				"        111,\n" +
				"        119,\n" +
				"        46\n" +
				"      ],\n" +
				"      \"questionChoices\": [\n" +
				"        \"a) It\\u0027s cool.\",\n" +
				"        \"b) It\\u0027s not cool.\",\n" +
				"        \"c) Please send help.\"\n" +
				"      ],\n" +
				"      \"questionType\": \"multiple-choice + image\"\n" +
				"    }\n" +
				"  }\n" +
				"}";

		assertEquals(jsonString, test.toString());
	}

}