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

import VianuEdu.backend.TestLibrary.AnswerSheet;
import VianuEdu.backend.Identification.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class that handles all of the connections to the VianuEdu database. This is a class that will not be
 * directly accessed by anything. This will be the foundation for all of the functions that might be related to the
 * database, such as test collection and account management. NEVER USE THIS CLASS TO DIRECTLY ACCESS THE DATABASE, SINCE
 * ANY ACTION COMMITTED HERE IS NOT DIRECTLY CONTROLLED BY ANYONE.
 *
 * @author StormFireFox1
 * @since 2018-01-03
 */
public class DatabaseHandler {

	private final String databaseDriver = "org.postgresql.Driver";
	private String userName;
	private String password;
	private String databaseURL;
	private String courseName;
	private Connection currentConnection;
	private ResultSet currentResultSet;

	/**
	 * This constructs and initializes a database handler.
	 *
	 * @param userName				   The user name for accessing the database.
	 * @param password				   The password for accessing the database.
	 * @param databaseURL              The database URL.
	 * @param courseName               The course name. Primarily used to differentiate from which schema to access. i.e. "GeoEdu"
	 * @throws ClassNotFoundException  It's highly unlikely this method will ever throw this exception due to the fact that there is only one driver going to be used, hardcoded in the object.
	 * @throws SQLException			   Exception most likely thrown when method is called on faulty database connections.
	 */
	public DatabaseHandler(String userName, String password, String databaseURL, String courseName) throws ClassNotFoundException, SQLException {
		this.userName = userName;
		this.password = password;
		this.databaseURL = databaseURL;
		this.courseName = courseName;
		Class.forName(databaseDriver);
		currentConnection = DriverManager.getConnection(databaseURL, "Connector", "Connector"); // user and pass might change
	}

	/**
	 * Queries the database using a SQL statement given and returns the results in the result set of the database handler. The previous result set cannot be recovered.
	 *
	 * @param query           The SQL statement used to query the database.
	 * @throws SQLException   Exception most likely thrown when statement is invalid or there is nothing to query.
	 */
	private void queryDatabase(String query) throws SQLException {
			Statement currentStatement = currentConnection.createStatement();
			currentResultSet = currentStatement.executeQuery(query);
	}

	/**
	 * Gets the current result set saved.
	 * @return The current result set saved.
	 */
	public ResultSet getCurrentResultSet() {
		return currentResultSet;
	}

	/**
	 * Inserts a student account in the database.
	 *
	 * @param value           The student that will be inserted in the database.
	 * @throws SQLException   Exception most likely thrown when object cannot be inserted in database.
	 */
	public void InsertValue(Student value) throws SQLException {
		String sqlStatement = "INSERT INTO \"Students\".\"Accounts\"(\n" +
				"\t\"ID\", \"firstName\", \"fathersInitial\", \"lastName\", gender, grade, \"gradeLetter\", status, \"userName\", password)\n" +
				"\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"; // Specific statement, might change over time.
		PreparedStatement newInsertion = currentConnection.prepareStatement(sqlStatement);
		newInsertion.setString(2, value.getFirstName());
		newInsertion.setString(3, value.getFathersInitial());
		newInsertion.setString(4, value.getLastName());
		newInsertion.setString(5, value.getGender());                      // TONS OF COLUMNS, just check the statement
		newInsertion.setInt(6, value.getGrade());
		newInsertion.setString(7, value.getGradeLetter());
		newInsertion.setString(8, value.getStatus());
		newInsertion.setString(9, value.getAccount().getUserName());
		newInsertion.setString(10, value.getAccount().getPassword());
		newInsertion.executeUpdate();
	}

	/**
	 * Inserts a answer sheet in the database.
	 *
	 * @param value           The answer sheet that will be inserted in the database.
	 * @throws SQLException   Exception most likely thrown when object cannot be inserted in database.
	 */
	public void InsertValue(AnswerSheet value) throws SQLException {
		// This is going to be tough. Woohoo! Here we go. Welcome to the depths of hell in SQL. Hope you enjoy.
		String sqlStatement = "INSERT INTO \"" + courseName + "\".\"AnswerSheets\"(\n" +
				"\t\"ID\", \"testID\", \"studentID\", answers)\n" +
				"\tVALUES (?, ?, ?, ?);"; // First, we make a specific statement, might change over time.
		PreparedStatement newInsertion = currentConnection.prepareStatement(sqlStatement);
		this.queryDatabase("SELECT * FROM \"Students\".\"Accounts\"\n" +
				"WHERE \"userName\" = \"" + value.getStudent().getAccount().getUserName() + "\""); // get student ID
		ResultSet tempResultSet = this.getCurrentResultSet(); // most likely going to be only one, since we'll check if usernames already exist
		newInsertion.setInt(3, tempResultSet.getInt("ID")); // add it to statement
		List<String> answerList = new ArrayList<>(); // create list first in order to convert to SQL array later
		for (int i = 1; i <= value.getNumberOfAnswers(); i++) {
			answerList.add(value.getAnswer(i)); // get all the answers into list
		}
		String[] answerArray = new String[answerList.size()]; // construct array
		answerArray = answerList.toArray(answerArray); // use toArray to turn List to Array
		newInsertion.setArray(4, currentConnection.createArrayOf("text", answerArray)); // put it in statement
		newInsertion.executeUpdate(); // insert value
		// Oh my god, that was annoying. I mostly made this for whoever else reads this. Hope you can understand.
	}

	/**
	 * Gets the user name currently associated with the database handler.
	 *
	 * @return The user name.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Gets the password currently associated with the database handler.
	 *
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}
}
