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
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DatabaseHandlerTests {

	@Test
	public void studentLoginWorks() {
		DatabaseHandler handler = new DatabaseHandler();

		try {
			String cookie = handler.studentLogin(new Account("IfDex22", "parolasecreta"));
			assertEquals("5a9586212d22263268dcd282", cookie);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void teacherLoginWorks() {
		DatabaseHandler handler = new DatabaseHandler();

		try {
			String cookie = handler.teacherLogin(new Account("uscene_the_student_slayer", "SpaghettiBrokenCode22"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}