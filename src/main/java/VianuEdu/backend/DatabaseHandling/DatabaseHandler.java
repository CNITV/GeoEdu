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
import okhttp3.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * This is the class that handles all of the connections to the VianuEdu database.
 *
 * This is a class that will not be directly accessed by anything. This will be the foundation for all of the functions
 * that might be related to the database, such as test collection and account management.
 * NEVER USE THIS CLASS TO DIRECTLY ACCESS THE DATABASE, SINCE ANY ACTION COMMITTED HERE IS NOT DIRECTLY
 * CONTROLLED BY ANYONE.
 *
 * This database is accessed via a middle-man HTTP REST API, written in Golang by the author of this handler.
 * To understand the intricacies of said server, view its source code at:
 *
 * http://www.github.com/CNITV/VianuEdu-Server
 *
 * @author StormFireFox1
 * @since 2018-04-03
 */
public class DatabaseHandler {

	private String serverURL;

	public DatabaseHandler() {

		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream("database.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.serverURL = properties.getProperty("serverURL");
	}

	public String studentLogin(Account account) throws IOException {
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
			throw new IllegalAccessError("Invalid username and password combination!");
		}
		return response.body().string();
	}

	public String teacherLogin(Account account) throws IOException {
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
			throw new IllegalAccessError("Invalid username and password combination!");
		}
		return response.body().string();
	}

	public String getStudent(String cookie) throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/getStudent/" + cookie)
				.get()
				.build();

		Response response = client.newCall(request).execute();

		if (response.code() == 404) {
			throw new IllegalAccessError("Cookie invalid! (Possibly wrong ID)");
		}
		return response.body().string();
	}

	public String getTeacher(String cookie) throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(serverURL + "/api/getTeacher/" + cookie)
				.get()
				.build();

		Response response = client.newCall(request).execute();

		if (response.code() == 404) {
			throw new IllegalAccessError("Cookie invalid! (Possibly wrong ID)");
		}
		return response.body().string();
	}

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
