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
		Date startTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2049-02-21 10:30:00");
		Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2049-02-21 11:20:00");

		VianuEdu.backend.TestLibrary.Test test = new VianuEdu.backend.TestLibrary.Test("T-000000", "The Genesis Test", "Geo", startTime, endTime, 12, "Z", contents);

		String jsonString = "{\n" +
				"  \"testID\": \"T-000000\",\n" +
				"  \"testName\": \"The Genesis Test\",\n" +
				"  \"course\": \"Geo\",\n" +
				"  \"startTime\": \"Feb 21, 2049 10:30:00 AM\",\n" +
				"  \"endTime\": \"Feb 21, 2049 11:20:00 AM\",\n" +
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
				"    }\n" +
				"  }\n" +
				"}";

		assertEquals(jsonString, test.toString());
	}

}