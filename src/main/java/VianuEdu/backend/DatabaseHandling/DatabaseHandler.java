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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

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

	/**
	 * Logs in a Student by reading its Account object.
	 * <p>
	 * This will throw an IllegalAccessError should the username and password combination belong to no student in the
	 * database.
	 *
	 * @param account The account for which to log in.
	 * @return A cookie that represents the ID of the student in the database.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
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
		return response.body().string();
	}

	/**
	 * Logs in a Teacher by reading its Account object.
	 * <p>
	 * This will throw an IllegalAccessError should the username and password combination belong to no teacher in the
	 * database.
	 *
	 * @param account The account for which to log in.
	 * @return A cookie that represents the ID of the teacher in the database.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
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
		return response.body().string();
	}

	/**
	 * Gets the student from the database, using the student's cookie.
	 *
	 * @param cookie The cookie representing the ID of the student.
	 * @return The Student object associated with the provided cookie.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
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

		JsonObject object = new JsonParser().parse(response.body().string()).getAsJsonObject();

		object.remove("_id");

		return JSONManager.fromJSONToStudent(object.toString());
	}

	/**
	 * Gets the teacher from the database, using the teacher's cookie.
	 *
	 * @param cookie The cookie representing the ID of the teacher.
	 * @return The Teacher object associated with the provided cookie.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
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

		JsonObject object = new JsonParser().parse(response.body().string()).getAsJsonObject();

		object.remove("_id");

		return JSONManager.fromJSONtoTeacher(object.toString());
	}

	/**
	 * Gets the list of lessons for the current grade in which this method is called.
	 * <p>
	 * The ArrayList returned can later be used in order to download a specific lesson from the server.
	 * Essentially, it's just a specialized TokenSerializer.
	 *
	 * @param grade   The grade for which to get the list of lessons for.
	 * @param subject The subject for which to get the list of lessons for.
	 * @return An ArrayList<String> which contains the list of lessons called for.
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

		String body = response.body().string();

		return new ArrayList<>(Arrays.asList(body.split("\n")));
	}

	/**
	 * Uploads a lesson provided by a teacher.
	 *
	 * @param grade   The grade for which this lesson should be uploaded.
	 * @param subject The subject for which this lesson should be uploaded.
	 * @param file    The lesson to upload.
	 * @param teacher The teacher who is uploading this file.
	 * @return True if lesson has been uploaded successfully, false if otherwise.
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
	 */
	public boolean uploadLesson(String subject, Integer grade, File file, Teacher teacher) throws IOException {
		String fileType;
		if (file.getName().length() < 4) {
			fileType = file.getName();
		} else {
			fileType = file.getName().substring(file.getName().length() - 4);
		}

		if (!fileType.equals(".png")) {
			throw new IllegalArgumentException("File is not a PNG!");
		}

		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("image/png");
		RequestBody body = RequestBody.create(mediaType, file);
		Request request = new Request.Builder()
				.url(serverURL + "/api/uploadLesson/" + subject + "/" + grade)
				.post(body)
				.addHeader("content-type", "image/png")
				.addHeader("filename", file.getName())
				.addHeader("authorization", "Basic " + Arrays.toString(Base64.getEncoder().encode((teacher.getAccount().getUserName() + ":" + teacher.getAccount().getPassword()).getBytes())))
				.build();

		Response response = client.newCall(request).execute();
		return response.code() == 200;
	}

	/**
	 * Downloads a lesson from the server.
	 * <p>
	 * This currently returns a byte-slice until we can figure out a way to save the lessons in a non-invasive manner.
	 *
	 * @param grade    The grade for which the lesson exists.
	 * @param filename The name of the lesson.
	 * @param subject  The subject for which the lesson exists.
	 * @return A byte-slice containing the lesson that has been downloaded.
	 * @throws IOException Most likely thrown if the device doesn't have a connection, or if the lesson doesn't exist.
	 */
	public byte[] downloadLesson(String subject, Integer grade, String filename) throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(serverURL + "/lessons/" + subject + "/" + grade + "/" + filename + ".png")
				.get()
				.build();
		Response response = client.newCall(request).execute();
		if (response.code() == 404) {
			throw new IOException("404 file not found");
		}
		return response.body().bytes();
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

		return response.body().string();
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

		return response.body().string();
	}
}
