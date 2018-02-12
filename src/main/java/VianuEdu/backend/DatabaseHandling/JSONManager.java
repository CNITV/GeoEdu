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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

}
