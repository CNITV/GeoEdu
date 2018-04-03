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
import VianuEdu.backend.TestLibrary.AnswerSheet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class that handles all of the connections to the VianuEdu database.
 *
 * This is a class that will not be directly accessed by anything. This will be the foundation for all of the functions
 * that might be related to the database, such as test collection and account management.
 * NEVER USE THIS CLASS TO DIRECTLY ACCESS THE DATABASE, SINCE ANY ACTION COMMITTED HERE IS NOT DIRECTLY
 * CONTROLLED BY ANYONE.
 *
 * @author StormFireFox1
 * @since 2018-01-03
 */
public class DatabaseHandler {

}
