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

import VianuEdu.backend.DatabaseHandling.JSONManager;

/**
 * A class that represents a VianuEdu account.
 *
 * A mere container for a username and password and nothing more. Constructed to avoid creating too much boilerplate code
 * and for the ability to potentially commit changes to username and password.
 *
 * @author StromFireFox1
 * @since 2018-2-10
 */
public class Account {

	private String userName;
	private String password;

	/**
	 * Constructs and initializes an Account object.
	 *
	 * @param userName The username for the account. Must not be empty.
	 * @param password The password for the account. Must not be empty.
	 */
	public Account(String userName, String password) {
		if (userName.equals("")) {
			throw new IllegalArgumentException("Username must not be empty!");
		} else if (password.equals("")) {
			throw new IllegalArgumentException("Password must not be empty!");
		}
		this.userName = userName;
		this.password = password;
	}

	/**
	 * Gets the account's username.
	 *
	 * @return The account's username.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Changes the account's username to specific value.
	 *
	 * @param userName The new username. Must not be empty.
	 */
	public void changeUserName(String userName) {
		if (userName.equals("")) {
			throw new IllegalArgumentException("Username must not be empty!");
		}
		this.userName = userName;
	}

	/**
	 * Gets the account's password.
	 *
	 * @return The account's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Changes the account's password to specific value.
	 *
	 * @param password The new username. Must not be empty.
	 */
	public void changePassword(String password) {
		if (password.equals("")) {
			throw new IllegalArgumentException("Password must not be empty!");
		}
		this.password = password;
	}

	/**
	 * Returns a JSON string with indentation that represents an account. Uses Gson JSON library.
	 *
	 * @return A JSON string representing an Account object.
	 */
	@Override
	public String toString() {
		return JSONManager.toIndentedJSON(this);
	}
}
