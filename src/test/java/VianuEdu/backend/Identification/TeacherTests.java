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

package VianuEdu.backend.Identification;

import org.junit.Test;

import static org.junit.Assert.*;

public class TeacherTests {

	@Test
	public void toStringShouldReturnJSON() {

		Account testAccount = new Account("ucsene_the_student_slayer", "SpaghettiBrokenCode22");

		Teacher testTeacher = new Teacher("Acob", "Ucsene", "F", "Info", testAccount);

		String JSONString = "{\n" +
				"  \"firstName\": \"Acob\",\n" +
				"  \"lastName\": \"Ucsene\",\n" +
				"  \"gender\": \"F\",\n" +
				"  \"course\": \"Info\",\n" +
				"  \"account\": {\n" +
				"    \"userName\": \"ucsene_the_student_slayer\",\n" +
				"    \"password\": \"SpaghettiBrokenCode22\"\n" +
				"  }\n" +
				"}";

		assertEquals(JSONString, testTeacher.toString());

	}
}