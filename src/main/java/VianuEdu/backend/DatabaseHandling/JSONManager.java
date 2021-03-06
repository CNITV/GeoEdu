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

import VianuEdu.backend.Identification.Student;
import VianuEdu.backend.Identification.Teacher;
import VianuEdu.backend.LessonLibrary.Lesson;
import VianuEdu.backend.TestLibrary.AnswerSheet;
import VianuEdu.backend.TestLibrary.Grade;
import VianuEdu.backend.TestLibrary.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.ResponseBody;

/**
 * A class to serialize/deserialize JSON quickly.
 *
 * Created to reduce boilerplate code required for JSON manipulation. Uses Gson.
 *
 * @author StormFireFox1
 * @since 2018-2-06
 */
public class JSONManager {

	/**
	 * Serializes an object to JSON format.
	 *
	 * @param object The object to be serialized.
	 * @return JSON string that represents passed object.
	 */
	public static String toJSON(Object object) {
		Gson json = new Gson();
		return json.toJson(object);
	}

	/**
	 * Serializes an object to JSON format with indentation.
	 *
	 * @param object The object to be serialized.
	 * @return JSON string that represents passed object with indentation.
	 */
	public static String toIndentedJSON(Object object) {
		Gson json = new GsonBuilder().setPrettyPrinting().create();
		return json.toJson(object);
	}

	/**
	 * Deserializes a JSON string into a Student object.
	 *
	 * @param jsonString The JSON string to deserialize.
	 * @return The deserialized Student object.
	 */
	public static Student fromJSONToStudent(String jsonString) {
		Gson json = new GsonBuilder().setPrettyPrinting().create();
		return json.fromJson(jsonString, Student.class);
	}

	/**
	 * Deserializes a JSON string into a Teacher object.
	 *
	 * @param jsonString The JSON string to deserialize.
	 * @return The deserialized Teacher object.
	 */
	public static Teacher fromJSONtoTeacher(String jsonString) {
		Gson json = new GsonBuilder().setPrettyPrinting().create();
		return json.fromJson(jsonString, Teacher.class);
	}

	/**
	 * Deserializes a JSON string into a Test object.
	 *
	 * @param jsonString The JSON string to deserialize.
	 * @return The deserialized Test object.
	 */
	public static Test fromJSONToTest(String jsonString) {
		Gson json = new GsonBuilder().setPrettyPrinting().create();
		return json.fromJson(jsonString, Test.class);
	}

	public static Lesson fromJSONToLesson(String jsonString) {
		Gson json = new GsonBuilder().setPrettyPrinting().create();
		return json.fromJson(jsonString, Lesson.class);
	}

	/**
	 * Deserializes a JSON string into a AnswerSheet object.
	 *
	 * @param jsonString The JSON string to deserialize.
	 * @return The deserialized AnswerSheet object.
	 */
	public static AnswerSheet fromJSONToAnswerSheet(String jsonString) {
		Gson json = new GsonBuilder().setPrettyPrinting().create();
		return json.fromJson(jsonString, AnswerSheet.class);
	}

	/**
	 * Deserializes a JSON string into a Grade object.
	 *
	 * @param jsonString The JSON string to deserialize.
	 * @return The deserialized Grade object.
	 */
	public static Grade fromJSONToGrade(String jsonString) {
		Gson json = new GsonBuilder().setPrettyPrinting().create();
		return json.fromJson(jsonString, Grade.class);
	}
}
