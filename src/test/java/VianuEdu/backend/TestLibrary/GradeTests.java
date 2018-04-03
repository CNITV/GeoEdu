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
import VianuEdu.backend.Identification.Teacher;
import org.junit.Test;

import static org.junit.Assert.*;

public class GradeTests {

	@Test
	public void calculateGrade() {

		Student testStudent = new Student();

		AnswerSheet testAnswerSheet = new AnswerSheet(testStudent, "T-000000", 3);

		testAnswerSheet.addAnswer(1, "When life gives you lemons, don't make lemonade.");
		testAnswerSheet.addMultipleChoiceAnswer(2, "a");
		testAnswerSheet.addAnswer(3, "The supreme art of war is to subdue the enemy without fighting.");

		AnswerSheet testAnswerKey = new AnswerSheet(testStudent, "T-000000", 3);

		testAnswerKey.addAnswer(1, "When life gives you lemons, don't make lemonade.");
		testAnswerKey.addMultipleChoiceAnswer(2, "a");
		testAnswerKey.addAnswer(3, "The supreme art of war is to subdue the enemy without fighting.");

		Teacher teacher = new Teacher();

		Grade testGrade = new Grade(testAnswerSheet, testAnswerKey, teacher);
		for (Integer answer :
			 testGrade.getAnswersToEvaluate()) {
			testGrade.EvaluateAnswer(answer, 100.0);
		}

		Double testFinalGrade = 100.0;
		assertEquals(testFinalGrade, testGrade.getCurrentGrade());
	}
}