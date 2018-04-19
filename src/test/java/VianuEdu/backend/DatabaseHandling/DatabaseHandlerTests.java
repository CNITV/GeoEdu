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

package VianuEdu.backend.DatabaseHandling;

import VianuEdu.backend.Identification.Account;
import VianuEdu.backend.Identification.Student;
import VianuEdu.backend.Identification.Teacher;
import VianuEdu.backend.TestLibrary.Question;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

public class DatabaseHandlerTests {

	@Test
	public void studentLoginWorks() {
		DatabaseHandler handler = new DatabaseHandler();

		try {
			String cookie = handler.studentLogin(new Account("IfDex22", "parolasecreta"));
			assertEquals("5a9586212d22263268dcd282", cookie);
		} catch (IOException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void teacherLoginWorks() {
		DatabaseHandler handler = new DatabaseHandler();

		try {
			String cookie = handler.teacherLogin(new Account("ucsene_the_student_slayer", "SpaghettiBrokenCode22"));
			assertEquals("5aa1012a9e2e662978a3bcad", cookie);
		} catch (IOException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getStudentWorks() {
		DatabaseHandler handler = new DatabaseHandler();

		try {
			Student student = handler.getStudent("5a9586212d22263268dcd282");
			assertEquals(new Student().toString(), student.toString());
		} catch (IOException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getTeacherWorks() {
		DatabaseHandler handler = new DatabaseHandler();

		try {
			Teacher teacher = handler.getTeacher("5aa1012a9e2e662978a3bcad");
			assertEquals(new Teacher().toString(), teacher.toString());
		} catch (IOException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createTestWorks() throws IOException, IllegalAccessException, ParseException {
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

		DatabaseHandler handler = new DatabaseHandler();

		String testID = handler.createTest(test, handler.getTeacher("5ad7921e6ea8b916b80d59df"));
	}

}