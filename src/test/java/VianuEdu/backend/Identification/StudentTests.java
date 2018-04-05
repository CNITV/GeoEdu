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

public class StudentTests {

	@Test
	public void toStringShowsJSON() {
		Student testStudent = new Student();

		String JSONString = "{\n" +
				"  \"firstName\": \"Dexter\",\n" +
				"  \"fathersInitial\": \"Z\",\n" +
				"  \"lastName\": \"Iftode\",\n" +
				"  \"gender\": \"M\",\n" +
				"  \"grade\": 12,\n" +
				"  \"gradeLetter\": \"Z\",\n" +
				"  \"status\": \"graduated\",\n" +
				"  \"account\": {\n" +
				"    \"userName\": \"IfDex22\",\n" +
				"    \"password\": \"parolasecreta\"\n" +
				"  }\n" +
				"}";

		assertEquals(JSONString, testStudent.toString());
	}
}