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

import VianuEdu.backend.DatabaseHandling.DatabaseHandler;
import VianuEdu.backend.TestLibrary.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;




public class Menu extends JPanel implements ActionListener {

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int ScreenHeight = (int) screenSize.getHeight();
    public static int ScreenWidth = (int) screenSize.getWidth();
    public static int X_pressed;
    public static int Y_pressed;
    public static int X_hovered;
    public static int Y_hovered;
    public static int XFrame;
    public static int YFrame;
    public static int Framerate = 1;
    public static boolean MousePressed = false;
    public static boolean isTeacher;
    public static boolean ENTER = false;
    public static JTextField username = new JTextField(20);
    public static JPasswordField password = new JPasswordField(30);
    public Timer timer;
    public static DatabaseHandler Maner = new DatabaseHandler();

    /**
     * This method sets the framerate of the menu.
     *
     * @author Sabin Anton
     */

    public void StartTimer() {

        timer = new Timer(Framerate, this);
        timer.start();

    }

    /**
     * This is the class object.
     * <p>
     * It calls the StartTimer and generateImages methods only once, and it also calls the Control class
     *
     * @author Sabin Anton
     */

    public Menu() {

        addKeyListener(new KeyControl());
        StartTimer();
        GeoEduMenu.generateImages();
        StartMenu.initializeButtons();
        StartMenu.importImages();
        UserImput.ImportImages();
        Window.frame.add(Tests.scroll);
        TestEditor.initialiseTextboxes();
        addMouseListener(new Control());
        setFocusable(true);
    }

    /**
     * This method reads the window size and coordinates;
     *
     * @author Sabin Anton
     */

    public void getWindowSize() {
        int width = Window.frame.getSize().width;
        int height = Window.frame.getSize().height;
        XFrame = Window.frame.getX();
        YFrame = Window.frame.getY();
        if (height != 0 && width != 0) {
            ScreenWidth = width;
            ScreenHeight = height;
        }
    }

    /**
     * This method creates every component form the screen
     *
     * @param g is the Graphics component
     * @author Sabin Anton
     */

    @Override

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (StartMenu.Start_GeoEdu == true) StartMenu.Paint(g);
        else {
            GeoEduMenu.Paint(g);
        }
    }

    /**
     * This method initialises every change from one frame to the next
     *
     * @param e is the ActionEvent parameter
     * @author Sabin Anton
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        getWindowSize();
        if (StartMenu.Start_GeoEdu == true) {
            StartMenu.Run();
        } else {
            GeoEduMenu.Run();
        }
        Window.frame.repaint();

    }

    /**
     * This class reads the mouse actions
     *
     * @author Sabin Anton
     */

    private class Control implements MouseListener {


        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

            X_pressed = e.getX();
            System.out.println("DA");
            Y_pressed = e.getY();
            MousePressed = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {

            MousePressed = false;

        }

        @Override
        public void mouseEntered(MouseEvent e) {


        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }

    private class KeyControl implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
                ENTER = true;
                System.out.println("DA");
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                ENTER = false;
            }

        }
    }

}
