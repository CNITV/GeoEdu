/*
 * This file is part of VianuEdu.
 *
 *     VianuEdu is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     VianuEdu is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with VianuEdu.  If not, see <http://www.gnu.org/licenses/>.
 *
 * 	Developed by Anton Sabin <sabinanton@gmail.com>
 */

package VianuEdu.GUI;

import javax.swing.*;

/**
 * This class creates a window on which the program is displayed
 *
 * @author Sabin Anton
 */
public class Window extends JFrame {

    public static JFrame frame = new JFrame();

    public Window() {

        frame.add(new Menu());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);
        frame.setTitle("VianuEdu");
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        new Window();

    }
}
