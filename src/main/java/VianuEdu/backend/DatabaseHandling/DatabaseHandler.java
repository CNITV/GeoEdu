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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
	 * @return The Student object associated with the provided cookie.
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
	 * @throws IOException Most likely thrown if the device doesn't have a connection.
	 * @return The Teacher object associated with the provided cookie.
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
	 *
	 * The ArrayList returned can later be used in order to download a specific lesson from the server.
	 * Essentially, it's just a specialized TokenSerializer.
	 *
	 * @param grade The grade for which to get the list of lessons for.
	 * @return An ArrayList<String> which contains the list of lessons called for.
	 * @throws IOException
	 */
	public ArrayList<String> listLessons(Integer grade) throws IOException {
		if (grade < 1 || grade > 12) {
			throw new IllegalArgumentException("Grade must be valid!");
		}
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/listLessons/" + grade)
				.get()
				.build();

		Response response = client.newCall(request).execute();

		String body = response.body().string();

		return new ArrayList<>(Arrays.asList(body.split("\n")));
	}
}
