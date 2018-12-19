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
import VianuEdu.backend.LessonLibrary.Lesson;
import VianuEdu.backend.TestLibrary.AnswerSheet;
import VianuEdu.backend.TestLibrary.Grade;
import VianuEdu.backend.TestLibrary.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the class that handles all of the connections to the VianuEdu database.
 * <p>
 * This is a class that will not be directly accessed by anything. This will be the foundation for all of the functions
 * that might be related to the database, such as test collection and account management.
 * NEVER USE THIS CLASS TO DIRECTLY ACCESS THE DATABASE, SINCE ANY ACTION COMMITTED HERE IS NOT DIRECTLY
 * CONTROLLED BY ANYONE.
 * <p>
 * This database is accessed via a middle-man HTTP REST API, written in Golang by the author of this handler.
 * To understand the intricacies of said server, view its source code at:
 * <p>
 * http://www.github.com/CNITV/VianuEdu-Server
 *
 * @author StormFireFox1
 * @since 2018-04-03
 */
public class DatabaseHandler {

	private String serverURL;

	/**
	 * Initializes and constructs a DatabaseHandler instance.
	 * <p>
	 * This should only be initialised once in any given moment, as to reduce the number of connections to the server.
	 * This will read the configuration file database.properties, and apply necessary changes to the handler.
	 */
	public DatabaseHandler() {

		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream("database.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.serverURL = properties.getProperty("serverURL");
	}

	private JsonObject sanitizeObject(Response response) throws IOException {
		JsonObject object;
		if (response.body() != null) {
			object = new JsonParser().parse(response.body().string()).getAsJsonObject();
		} else {
			throw new NullPointerException("Response body came out null! Try again!");
		}
		response.close();

		object.remove("_id");
		return object;
	}

	/**
	 * Logs in a Student by reading its Account object.
	 * <p>
	 * This will throw an IllegalAccessError should the username and password combination belong to no student in the
	 * database.
	 *
	 * @param account The account for which to log in.
	 * @return A cookie that represents the ID of the student in the database.
	 * @throws IOException            Most likely thrown if the device doesn't have a connection.
	 * @throws IllegalAccessException Thrown if the Authorization fails.
	 */
	public String studentLogin(Account account) throws IOException, IllegalAccessException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\n" +
				"\t\"userName\": \"" + account.getUserName() + "\",\n" +
				"\t\"password\": \"" + account.getPassword() + "\"\n" +
				"}");
		Request request = new Request.Builder()
				.url(serverURL + "/api/findStudentID")
				.post(body)
				.build();

		Response response = client.newCall(request).execute();

		if (response.code() == 404) {
			throw new IllegalAccessException("Invalid username and password combination!");
		}

		String result;
		if (response.body() != null) {
			result = response.body().string();
		} else {
			throw new NullPointerException("Response body came out null! Try again!");
		}
		response.close();

		return result;
	}

	/**
	 * Logs in a Teacher by reading its Account object.
	 * <p>
	 * This will throw an IllegalAccessError should the username and password combination belong to no teacher in the
	 * database.
	 *
	 * @param account The account for which to log in.
	 * @return A cookie that represents the ID of the teacher in the database.
	 * @throws IOException            Most likely thrown if the device doesn't have a connection.
	 * @throws IllegalAccessException Thrown if the Authorization fails.
	 */
	public String teacherLogin(Account account) throws IOException, IllegalAccessException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\n" +
				"\t\"userName\": \"" + account.getUserName() + "\",\n" +
				"\t\"password\": \"" + account.getPassword() + "\"\n" +
				"}");
		Request request = new Request.Builder()
				.url(serverURL + "/api/findTeacherID")
				.post(body)
				.build();

		Response response = client.newCall(request).execute();

		if (response.code() == 404) {
			throw new IllegalAccessException("Invalid username and password combination!");
		}

		String result;
		if (response.body() != null) {
			result = response.body().string();
		} else {
			throw new NullPointerException("Response body came out null! Try again!");
		}
		response.close();

		return result;
	}

	/**
	 * Gets the student from the database, using the student's cookie.
	 *
	 * @param cookie The cookie representing the ID of the student.
	 * @return The Student object associated with the provided cookie.
	 * @throws IOException            Most likely thrown if the device doesn't have a connection.
	 * @throws IllegalAccessException Thrown if the ID is invalid and unattached to a Student.
	 */
	public Student getStudent(String cookie) throws IOException, IllegalAccessException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/getStudent/" + cookie)
				.get()
				.build();

		Response response = client.newCall(request).execute();

		if (response.code() == 404) {
			throw new IllegalAccessException("Cookie invalid! (Possibly wrong ID)");
		}

		JsonObject object = sanitizeObject(response);

		return JSONManager.fromJSONToStudent(object.toString());
	}

	/**
	 * Gets the teacher from the database, using the teacher's cookie.
	 *
	 * @param cookie The cookie representing the ID of the teacher.
	 * @return The Teacher object associated with the provided cookie.
	 * @throws IOException            Most likely thrown if the device doesn't have a connection.
	 * @throws IllegalAccessException Thrown if the ID is invalid and unattached to a Teacher.
	 */
	public Teacher getTeacher(String cookie) throws IOException, IllegalAccessException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/getTeacher/" + cookie)
				.get()
				.build();

		Response response = client.newCall(request).execute();

		if (response.code() == 404) {
			throw new IllegalAccessException("Cookie invalid! (Possibly wrong ID)");
		}

		JsonObject object = sanitizeObject(response);

		return JSONManager.fromJSONtoTeacher(object.toString());
	}

	/**
	 * Changes the password of the provided Teacher object to the new one provided.
	 *
	 * @param student     The Student for which to change the password.
	 * @param newPassword The new password to change to.
	 * @return The new Student object in the database.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
	 */
	public Student changeStudentPassword(Student student, String newPassword) throws IOException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, newPassword);
		Request request = new Request.Builder()
				.url(serverURL + "/api/changeStudentPassword")
				.post(body)
				.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((student.getAccount().getUserName() + ":" + student.getAccount().getPassword()).getBytes()))
				.build();

		Response response = client.newCall(request).execute();
		if (response.code() == 401) {
			throw new IllegalArgumentException("Invalid username and password combination! (Student might not exist?)");
		}
		response.close();

		student.getAccount().changePassword(newPassword);
		return student;
	}

	/**
	 * Changes the password of the provided Teacher object to the new one provided.
	 *
	 * @param teacher     The Teacher for which to change the password.
	 * @param newPassword The new password to change to.
	 * @return The new Teacher object in the database.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
	 */
	public Teacher changeTeacherPassword(Teacher teacher, String newPassword) throws IOException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, newPassword);
		Request request = new Request.Builder()
				.url(serverURL + "/api/changeTeacherPassword")
				.post(body)
				.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((teacher.getAccount().getUserName() + ":" + teacher.getAccount().getPassword()).getBytes()))
				.build();

		Response response = client.newCall(request).execute();
		if (response.code() == 401) {
			throw new IllegalArgumentException("Invalid username and password combination! (Teacher might not exist?)");
		}
		response.close();

		teacher.getAccount().changePassword(newPassword);
		return teacher;
	}

	/**
	 * Gets the list of lessons for the current grade in which this method is called.
	 * <p>
	 * The ArrayList returned can later be used in order to download a specific lesson from the server.
	 * Essentially, it's just a specialized TokenSerializer.
	 *
	 * @param grade   The grade for which to get the list of lessons for.
	 * @param subject The subject for which to get the list of lessons for.
	 * @return An ArrayList of Strings which contains the list of lessons called for.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
	 */
	public ArrayList<String> listLessons(String subject, Integer grade) throws IOException {
		if (grade < 1 || grade > 12) {
			throw new IllegalArgumentException("Grade must be valid!");
		}
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/listLessons/" + subject + "/" + grade)
				.get()
				.build();

		Response response = client.newCall(request).execute();

		String body;
		if (response.body() != null) {
			body = response.body().string();
		} else {
			throw new NullPointerException("Response body came out null! Try again!");
		}

		response.close();

		return new ArrayList<>(Arrays.asList(body.split("\n")));
	}

	/**
	 * Uploads a lesson provided by a teacher.
	 *
	 * @param subject The subject for which this lesson should be uploaded.
	 * @param lesson  The lesson to be uploaded.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
	 */
	public void uploadLesson(String subject, Lesson lesson, Teacher teacher) throws IOException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, lesson.toString());

		Request request = new Request.Builder()
				.url(serverURL + "/api/uploadLesson/" + subject + "/" + lesson.getGrade())
				.post(body)
				.addHeader("authorization", "Basic " + Base64.getEncoder().encodeToString((teacher.getAccount().getUserName() + ":" + teacher.getAccount().getPassword()).getBytes()))
				.build();

		Response response = client.newCall(request).execute();

		response.close();
	}

	/**
	 * Downloads a lesson from the server.
	 *
	 * @param subject The subject for which the lesson exists.
	 * @param ID The ID of the lesson to be downloaded.
	 * @return A Lesson object.
	 * @throws IOException Most likely thrown if the device doesn't have a connection, or if the lesson doesn't exist.
	 */
	public Lesson getLesson(String subject, String ID) throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(serverURL + "/lessons/" + subject + "/" + ID)
				.get()
				.build();
		Response response = client.newCall(request).execute();
		if (response.code() == 404) {
			throw new IOException("404 file not found");
		}

		JsonObject object = sanitizeObject(response);

		return JSONManager.fromJSONToLesson(object.toString());
	}

	/**
	 * Registers a student into the database.
	 *
	 * @param student The student that will be registered into the database.
	 * @return An ID which represents the student in the database.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
	 */
	public String registerStudent(Student student) throws IOException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, student.toString());

		Request request = new Request.Builder()
				.url(serverURL + "/api/registerStudent")
				.post(body)
				.build();

		Response response = client.newCall(request).execute();

		String result;
		if (response.body() != null) {
			result = response.body().string();
		} else {
			throw new NullPointerException("Response body came out null! Try again!");
		}
		response.close();

		return result;
	}

	/**
	 * Registers a teacher into the database.
	 *
	 * @param teacher The teacher that will be registered into the database.
	 * @return An ID which represents the teacher in the database.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
	 */
	public String registerTeacher(Teacher teacher) throws IOException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, teacher.toString());

		Request request = new Request.Builder()
				.url(serverURL + "/api/registerTeacher")
				.post(body)
				.build();

		Response response = client.newCall(request).execute();

		String result;
		if (response.body() != null) {
			result = response.body().string();
		} else {
			throw new NullPointerException("Response body came out null! Try again!");
		}
		response.close();

		return result;
	}

	/**
	 * Gets a test from the server by querying for a test ID.
	 *
	 * @param testID The test ID of the test you wish to get.
	 * @return A Test object attached to that specific test ID.
	 * @throws IOException            Most likely thrown if the device doesn't have a connection.
	 * @throws IllegalAccessException Thrown if there isn't a test attached to that test ID or if the test is not yet avaiable for administration.
	 */
	public Test getTest(String testID) throws IOException, IllegalAccessException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/getTest/" + testID)
				.get()
				.build();

		Response response = client.newCall(request).execute();
		if (response.code() == 404) {
			throw new IllegalAccessException("404 test not found!");
		} else if (response.code() == 401) {
			throw new IllegalAccessException("Test not yet available!");
		}

		JsonObject object = sanitizeObject(response);

		return JSONManager.fromJSONToTest(object.toString());
	}

	/**
	 * A specialized version of getTest that bypasses the time limitation surrounding the getTest method.
	 *
	 * @param testID  The test ID of the test you wish to get.
	 * @param teacher The teacher who wants to view the test.
	 * @return A Test object attached to that specific test ID.
	 * @throws IllegalAccessException Thrown if there isn't a test attached to that test ID.
	 * @throws IOException            Most likely thrown if the device doesn't have a connection.
	 */
	public Test viewTest(String testID, Teacher teacher) throws IllegalAccessException, IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/viewTest/" + testID)
				.get()
				.addHeader("authorization", "Basic " + Base64.getEncoder().encodeToString((teacher.getAccount().getUserName() + ":" + teacher.getAccount().getPassword()).getBytes()))
				.build();

		Response response = client.newCall(request).execute();
		if (response.code() == 404) {
			throw new IllegalAccessException("404 test not found!");
		}

		JsonObject object;
		if (response.body() != null) {
			object = new JsonParser().parse(response.body().string()).getAsJsonObject();
		} else {
			throw new NullPointerException("Response body came out null! Try again");
		}
		response.close();

		object.remove("_id");

		return JSONManager.fromJSONToTest(object.toString());
	}

	/**
	 * Gets the tests that a student might have to take by using his ID.
	 *
	 * @param subject   The subject for which the student has tests.
	 * @param studentID The student ID for which this method will search tests for.
	 * @return An ArrayList of Strings containing the test ID's of each test that the student might have to take.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
	 */
	public ArrayList<String> getTestQueue(String subject, String studentID) throws IOException {
		if (!(subject.equals("Geo") || subject.equals("Phi") || subject.equals("Info") || subject.equals("Math"))) {
			throw new IllegalArgumentException("Subject must be a VianuEdu-compatible course!");
		}

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/getTestQueue/" + subject + "/" + studentID)
				.get()
				.build();

		Response response = client.newCall(request).execute();

		String body;
		if (response.body() != null) {
			body = response.body().string();
		} else {
			throw new NullPointerException("Response body came out null! Try again!");
		}
		response.close();

		return new ArrayList<>(Arrays.asList(body.split("\n")));

	}

	/**
	 * Adds a new test to the database.
	 *
	 * @param test    The Test object to add to the database.
	 * @param teacher The teacher who will upload the test.
	 * @return The test ID of the newly-added test.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
	 */
	public String createTest(Test test, Teacher teacher) throws IOException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, test.toString());
		Request request = new Request.Builder()
				.url(serverURL + "/api/createTest/" + teacher.getCourse())
				.post(body)
				.addHeader("content-type", "application/json")
				.addHeader("authorization", "Basic " + Base64.getEncoder().encodeToString((teacher.getAccount().getUserName() + ":" + teacher.getAccount().getPassword()).getBytes()))
				.build();

		Response response = client.newCall(request).execute();

		if (response.code() == 400) {
			throw new IllegalArgumentException("Test provided has an invalid test ID! Extract valid test ID from getNextTestID server endpoint!");
		}

		Pattern pattern = Pattern.compile("T-([0-9])\\w+");
		Matcher matcher;
		if (response.body() != null) {
			matcher = pattern.matcher(response.body().string());
		} else {
			throw new NullPointerException("Response body came out null! Try again");
		}
		response.close();
		if (!matcher.find()) {
			throw new RemoteException("Something didn't work at the server! Sorry!");
		}
		return matcher.group(1);
	}

	/**
	 * Updates a test in the database.
	 *
	 * @param test    The new Test object to be updated into the database. It will find the reference of the test in the database by test ID.
	 * @param teacher The teacher who will upload the test.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
	 */
	public void updateTest(Test test, Teacher teacher) throws IOException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, test.toString());
		Request request = new Request.Builder()
				.url(serverURL + "/api/updateTest/" + test.getTestID())
				.post(body)
				.addHeader("content-type", "application/json")
				.addHeader("authorization", "Basic " + Base64.getEncoder().encodeToString((teacher.getAccount().getUserName() + ":" + teacher.getAccount().getPassword()).getBytes()))
				.build();

		Response response = client.newCall(request).execute();

		if (response.code() == 404) {
			throw new IllegalArgumentException("Test provided does not currently exist in the database! Create a new one!");
		} else if (response.code() == 401) {
			throw new IllegalArgumentException("Invalid Test object to update!");
		}
		response.close();
	}

	/**
	 * Queries the database for a list of tests that are to be administered in the future.
	 *
	 * @param subject The subject for which the list of tests will be made.
	 * @param teacher The teacher who wants to see the planned tests. Required for Authorization.
	 * @return An ArrayList of Strings which contains the test ID's of all future tests to be administered.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
	 */
	public ArrayList<String> getPlannedTests(String subject, Teacher teacher) throws IOException {
		if (!(subject.equals("Geo") || subject.equals("Phi") || subject.equals("Info") || subject.equals("Math"))) {
			throw new IllegalArgumentException("Subject must be a VianuEdu-compatible course!");
		}

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/getPlannedTests/" + subject)
				.get()
				.addHeader("authorization", "Basic " + Base64.getEncoder().encodeToString((teacher.getAccount().getUserName() + ":" + teacher.getAccount().getPassword()).getBytes()))
				.build();

		Response response = client.newCall(request).execute();

		String body;
		if (response.body() != null) {
			body = response.body().string();
		} else {
			throw new NullPointerException("Response body came out null! Try again!");
		}
		response.close();

		return new ArrayList<>(Arrays.asList(body.split("\n")));
	}

	/**
	 * Gets the next test ID for submission.
	 *
	 * @return The next test ID for submission.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
	 */
	public String getNextTestID() throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/getNextTestID")
				.get()
				.build();

		Response response = client.newCall(request).execute();

		String result;
		if (response.body() != null) {
			result = response.body().string();
		} else {
			throw new NullPointerException("Response body came out null! Try again!");
		}
		response.close();

		return result;
	}

	/**
	 * Gets an answer sheet to evaluate from the database.
	 *
	 * @param testID    The test ID for which to find an answer sheet for.
	 * @param studentID The student ID for which to find an answer sheet for.
	 * @return An AnswerSheet object associated to the provided test ID and student ID.
	 * @throws IOException            Most likely thrown if the device doesn't have a connection.
	 * @throws IllegalAccessException Thrown if no answer sheet is found.
	 */
	public AnswerSheet getAnswerSheet(String testID, String studentID) throws IOException, IllegalAccessException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/getAnswerSheet/" + studentID + "/" + testID)
				.get()
				.build();

		Response response = client.newCall(request).execute();
		if (response.code() == 404) {
			throw new IllegalAccessException("404 answer sheet not found!");
		}


		JsonObject object = sanitizeObject(response);

		return JSONManager.fromJSONToAnswerSheet(object.toString());
	}

	/**
	 * Submits an answer sheet to the database.
	 *
	 * @param student     The student who is uploading the database.
	 * @param answerSheet The answer sheet to upload.
	 * @throws IOException            Most likely thrown if the device doesn't have a connection.
	 * @throws IllegalAccessException Thrown if answer sheet has already been uploaded for test.
	 */
	public void submitAnswerSheet(Student student, AnswerSheet answerSheet) throws IOException, IllegalAccessException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, answerSheet.toString());
		Request request = new Request.Builder()
				.url(serverURL + "/api/submitAnswerSheet/" + answerSheet.getTestID())
				.post(body)
				.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((student.getAccount().getUserName() + ":" + student.getAccount().getPassword()).getBytes()))
				.build();

		Response response = client.newCall(request).execute();
		if (response.code() == 400) {
			throw new IllegalArgumentException("Malformed answer sheet! (different test ID)");
		} else if (response.code() == 401) {
			throw new IllegalArgumentException("Malformed answer sheet! (credentials do not match)");
		} else if (response.code() == 208) {
			throw new IllegalAccessException("Already submitted answer sheet!");
		}
		response.close();
	}

	/**
	 * Gets the student ID's for the answer sheets that must be evaluated for the provided test ID.
	 *
	 * @param testID The test ID for which to find answer sheets for.
	 * @return An ArrayList of Strings which contains the student ID of each answer sheet to be evaluated.
	 * @throws IOException            Most likely thrown if the device doesn't have a connection.
	 * @throws IllegalAccessException Thrown if no answer sheets are found.
	 */
	public ArrayList<String> getAnswerSheetsForTest(String testID) throws IOException, IllegalAccessException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/getAnswerSheetsForTest/" + testID)
				.get()
				.build();

		Response response = client.newCall(request).execute();
		if (response.code() == 404) {
			throw new IllegalAccessException("404 answer sheets for test not found!");
		} else if (response.code() == 500) {
			throw new RemoteException("Something didn't work at the server! Sorry!");
		}

		String body;
		if (response.body() != null) {
			body = response.body().string();
		} else {
			throw new NullPointerException("Response body came out null! Try again");
		}
		response.close();

		return new ArrayList<>(Arrays.asList(body.split("\n")));

	}

	/**
	 * Submits a new grade to the database.
	 *
	 * @param grade The grade to be submitted.
	 * @throws IOException            Most likely thrown if the device doesn't have a connection.
	 * @throws IllegalAccessException Thrown if submission is unsuccessful, reason is included in the exception message.
	 */
	public void submitGrade(Grade grade) throws IOException, IllegalAccessException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, grade.toString());
		Request request = new Request.Builder()
				.url(serverURL + "/api/submitGrade/" + grade.getAnswerKey().getTestID())
				.post(body)
				.addHeader("content-type", "application/json")
				.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(((grade.getTeacher().getAccount().getUserName() + ":" + grade.getTeacher().getAccount().getPassword()).getBytes())))
				.build();

		Response response = client.newCall(request).execute();

		if (!(response.isSuccessful())) {
			if (response.body() != null) {
				throw new IllegalAccessException(response.code() + " submission not successful! Reason: " + response.body().string());
			} else {
				throw new NullPointerException("Response body came out null! Try again!");
			}
		}

		response.close();
	}

	/**
	 * Gets a grade from the database.
	 *
	 * @param studentID The student ID for which to get the grade for.
	 * @param testID    The test ID for which to get the grade for.
	 * @return The Grade object associated with the provided student ID and test ID.
	 * @throws IOException            Most likely thrown if the device doesn't have a connection.
	 * @throws IllegalAccessException Thrown if no grade is found in the database.
	 */
	public Grade getGrade(String studentID, String testID) throws IOException, IllegalAccessException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/getGrade/" + studentID + "/" + testID)
				.get()
				.build();

		Response response = client.newCall(request).execute();
		if (response.code() == 404) {
			throw new IllegalAccessException("404 grade not found!");
		}

		JsonObject object = sanitizeObject(response);

		return JSONManager.fromJSONToGrade(object.toString());
	}

	/**
	 * Gets the current grades that a specific student has available for review.
	 *
	 * @param student The student for which to get the grades for.
	 * @param subject The subject for which the grades are administered.
	 * @return An ArrayList of Grade objects which contain all of the grades the provided Student has received in the past 150 days.
	 * @throws IllegalAccessException Thrown if no grade is found in the database.
	 * @throws IOException            Most likely thrown if the device doesn't have a connection.
	 */
	public ArrayList<Grade> getCurrentGrades(Student student, String subject) throws IllegalAccessException, IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/getCurrentGrades/" + subject)
				.get()
				.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(((student.getAccount().getUserName() + ":" + student.getAccount().getPassword()).getBytes())))
				.build();

		Response response = client.newCall(request).execute();
		if (response.code() == 404) {
			throw new IllegalAccessException("404 grade not found!");
		}

		String body;
		if (response.body() != null) {
			body = response.body().string();
		} else {
			throw new NullPointerException("Response body came out null! Try again");
		}
		response.close();

		ArrayList<String> testIDList = new ArrayList<>(Arrays.asList(body.split("\n")));

		String studentID = this.studentLogin(student.getAccount());

		ArrayList<Grade> result = new ArrayList<>();

		for (String testID : testIDList) {
			Grade grade = this.getGrade(studentID, testID);
			result.add(grade);
		}
		return result;
	}

	/**
	 * Gets the test ID's of all current tests that must be corrected for a specific subject.
	 *
	 * @param teacher The teacher who will correct the subjects.
	 * @param subject The subjecct that the tests were administered on.
	 * @return An ArrayList of Strings containing the test ID's of each uncorrected test.
	 * @throws IllegalAccessException Thrown if no tests are found.
	 * @throws IOException            Most likely thrown if the device doesn't have a connection.
	 */
	public ArrayList<String> getUncorrectedTests(Teacher teacher, String subject) throws IllegalAccessException, IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/getUncorrectedTests/" + subject)
				.get()
				.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((teacher.getAccount().getUserName() + ":" + teacher.getAccount().getPassword()).getBytes()))
				.build();

		Response response = client.newCall(request).execute();
		if (response.code() == 404) {
			throw new IllegalAccessException("404 no uncorrected tests found!");
		}

		String body;
		if (response.body() != null) {
			body = response.body().string();
		} else {
			throw new NullPointerException("Response body came out null! Try again");
		}
		response.close();

		return new ArrayList<>(Arrays.asList(body.split("\n")));
	}

	/**
	 * Gets all the students in a specified classroom.
	 *
	 * @param grade       The year of the classroom.
	 * @param gradeLetter The letter of the classroom.
	 * @return An ArrayList of Student objects containing all students from specified classroom.
	 * @throws IllegalAccessException Thrown if no students are found in specified classroom.
	 * @throws IOException            Most likely thrown if the device doesn't have a connection.
	 */
	public ArrayList<Student> listClassbook(Integer grade, String gradeLetter) throws IllegalAccessException, IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/listClassbook/" + grade + "/" + gradeLetter)
				.get()
				.build();

		Response response = client.newCall(request).execute();
		if (response.code() == 404) {
			throw new IllegalAccessException("404 no uncorrected tests found!");
		}

		String body;
		if (response.body() != null) {
			body = response.body().string();
		} else {
			throw new NullPointerException("Response body came out null! Try again");
		}
		response.close();

		ArrayList<String> studentIDList = new ArrayList<>(Arrays.asList(body.split("\n")));

		ArrayList<Student> classbook = new ArrayList<>();
		for (String studentID : studentIDList) {
			classbook.add(this.getStudent(studentID));
		}

		return classbook;
	}
}
