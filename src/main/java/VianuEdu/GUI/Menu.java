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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Menu extends JPanel implements ActionListener {

    private static final int ScreenHeight = 1080;
    private static final int ScreenWidth = 1920;
    private static final int PanelWidth = ScreenWidth / 5;
    private static final int PanelHeight = ScreenHeight;
    private static int X_leftPanel;
    private static int X_rightPanel;
    private static int X_pressed;
    private static int Y_pressed;
    private static int X_hovered;
    private static int Y_hovered;
    private int currentPressed = 0;
    private boolean Panels_Hidden = false;
    private static boolean MousePressed = false;
    private static int Font_size = 60;
    private int Relativesize = Font_size;
    private int classNumber = 4;
    private int NrButtons = 6;
    private int ButtonWidth = PanelWidth;
    private int ButtonHeight = PanelHeight / NrButtons * 4 / 5;
    private int ClassHeight = ButtonHeight / 2;
    private int ClassWidth = ButtonWidth * 2 / 3;
    private int ArrowWidth = 80;
    private int ArrowHeight = 45;
    private int Framerate = 1;
    private int dir = 1;

    public int Leftpanel = -ScreenWidth / 5;
    public int Rightpanel = ScreenWidth;
    public boolean classPressed[] = new boolean[10];
    public boolean classHovered[] = new boolean[10];
    public boolean pressed[] = new boolean[10];
    public boolean hovered[] = new boolean[10];
    public boolean ArrowPressed = false;
    public String Button_name[] = new String[10];
    public Timer timer;
    public BufferedImage Arrow_unpressed = null;
    public BufferedImage Arrow_Pressed = null;
    public BufferedImage TurnedArrow_Unpressed = null;
    public BufferedImage TurnedArrow_Pressed = null;
    public BufferedImage Background = null;

    ClassLoader loader = Menu.class.getClassLoader();

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
     * This method imports all the images needed in the program
     *
     * @author Sabin Anton
     */

    public void generateImages() {

        try {
            Arrow_unpressed = ImageIO.read(new File(loader.getResource("gui_assets/notPressedDownArrowLeft.png").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Arrow_Pressed = ImageIO.read(new File(loader.getResource("gui_assets/PressedDownArrowLeft.png").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            TurnedArrow_Pressed = ImageIO.read(new File(loader.getResource("gui_assets/PressedDownArrowRight.png").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            TurnedArrow_Unpressed = ImageIO.read(new File(loader.getResource("gui_assets/notPressedDownArrowRight.png").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Background = ImageIO.read(new File(loader.getResource("gui_assets/Background.jpg").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the class object.
     * <p>
     * It calls the StartTimer and generateImages methods only once, and it also calls the Control class
     *
     * @author Sabin Anton
     */

    public Menu() {

        StartTimer();
        generateImages();
        addMouseListener(new Control());
    }

    /**
     * This method creates the animation of the left panel
     *
     * @param direction specifes whether the left panel moves left (-1) or right (1)
     * @return the X coordinate of the panel
     * @author Sabin Anton
     */

    public int AnimateLeftP(int direction) {

        if (Leftpanel <= 0 && Leftpanel >= -PanelWidth)
            Leftpanel += direction;
        if (Leftpanel > 0) Leftpanel = 0;
        if (Leftpanel < -PanelWidth) Leftpanel = -PanelWidth;
        return Leftpanel;
    }

    /**
     * This method creates the animation of the right panel
     *
     * @param direction specifes whether the left panel moves left (-1) or right (1)
     * @return the X coordinate of the panel
     * @author Sabin Anton
     */

    public int AnimateRightP(int direction) {

        if (Rightpanel >= ScreenWidth * 4 / 5 && Rightpanel <= ScreenWidth)
            Rightpanel -= direction;
        if (Rightpanel < ScreenWidth * 4 / 5) Rightpanel = ScreenWidth * 4 / 5;
        if (Rightpanel > ScreenWidth) Rightpanel = ScreenWidth;
        return Rightpanel;
    }

    /**
     * This method changes the size of the font used for the main buttons
     *
     * @author Sabin Anton
     */

    public void AnimateFont() {

        if (Relativesize < Font_size * 5 / 4) Relativesize++;

    }

    /**
     * This method generates the background image of the menu and the 2 panels;
     *
     * @param g is the graphics component used for generating it
     * @author Sabin Anton
     */

    public void generateBackground(Graphics g) {

        X_leftPanel = AnimateLeftP(dir);
        X_rightPanel = AnimateRightP(dir);
        g.drawImage(Background, 0, 0, this);
        g.setColor(new Color(10, 10, 10));
        g.fillRect(X_leftPanel, 0, PanelWidth, PanelHeight);
        g.fillRect(X_rightPanel, 0, PanelWidth, PanelHeight);
    }

    /**
     * This method creates a button for every grade of highschool
     *
     * @param g      is the Graphics component
     * @param x      is the X coordinate of the button
     * @param y      is th Y coordinate of the button
     * @param Width  is the width of the button
     * @param Height is the height of the button
     * @param clasa  is the grade that the button will show
     * @author Sabin Anton
     */

    public void makeClassButton(Graphics g, int x, int y, int Width, int Height, int clasa) {

        if (classPressed[clasa - 9] == true) {
            g.setColor(new Color(250, 250, 250));
            g.fillRect(x, y, Width, Height);
            g.setColor(new Color(50, 50, 50));
            g.fillRect(x, y, Width, Height / 100);
            g.fillRect(x, y, Width, Height * 99 / 100);

            String cls = "Clasa a " + clasa + "-a";
            Font small = new Font("Consolas", Font.PLAIN, Font_size / 2 * 5 / 4);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(250, 250, 250));
            g.setFont(small);
            g.drawString(cls, x + Width / 2 - metricsx.stringWidth(cls) / 2, y + Height / 2 + metricsy.getHeight() / 4);
        } else if (classHovered[clasa - 9] == true) {
            g.setColor(new Color(164, 148, 110));
            g.fillRect(x, y, Width, Height);
            g.setColor(new Color(231, 198, 63));
            g.fillRect(x, y, Width, Height / 100);
            g.fillRect(x, y, Width, Height * 99 / 100);

            String cls = "Clasa a " + clasa + "-a";
            Font small = new Font("Consolas", Font.PLAIN, Font_size / 2 * 5 / 4);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(255, 231, 170));
            g.setFont(small);
            g.drawString(cls, x + Width / 2 - metricsx.stringWidth(cls) / 2, y + Height / 2 + metricsy.getHeight() / 4);
        } else {
            g.setColor(new Color(50, 50, 50));
            g.fillRect(x, y, Width, Height);
            g.setColor(new Color(200, 200, 200));
            g.fillRect(x, y, Width, Height / 100);
            g.fillRect(x, y, Width, Height * 99 / 100);

            String cls = "Clasa a " + clasa + "-a";
            Font small = new Font("Consolas", Font.PLAIN, Font_size / 2);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(164, 148, 110));
            g.setFont(small);
            g.drawString(cls, x + Width / 2 - metricsx.stringWidth(cls) / 2, y + Height / 2 + metricsy.getHeight() / 4);
        }

    }

    /**
     * This method shows all the highschool grades for which the materials are available for lessons, tests and exercises;
     *
     * @param g is the Graphics component
     * @param x is the adress used in the main button array for the button in question
     * @author Sabin Anton
     */

    public void showClasses(Graphics g, int x) {

        if (x <= 4 && x >= 2 && currentPressed == x) {
            for (int i = 0; i < classNumber; i++) {
                makeClassButton(g, Leftpanel + PanelWidth, x * ButtonHeight + i * ClassHeight, ClassWidth, ClassHeight, i + 9);
            }
        }
    }

    /**
     * This method creates a main button for each feature of the program
     *
     * @param g      is the Graphics component
     * @param x      is the X coordinate of the buton
     * @param y      is the Y coordinate of the button
     * @param Width  is the width of the button
     * @param Height is the height of the button
     * @param Name   is the name of the button
     * @param i      is the adress of the button in the main button array
     * @author Sabin Anton
     */

    public void makeButton(Graphics g, int x, int y, int Width, int Height, String Name, int i) {

        if (pressed[i] == true) {

            AnimateFont();
            pressed[i] = false;
            g.setColor(new Color(150, 150, 150));
            g.fillRect(x, y, Width, Height);
            g.fillRect(x, y, Width, Height / 100);
            g.fillRect(x, y + 99 * Height / 100, Width, Height / 100);


            Font small = new Font("Futura", Font.PLAIN, Relativesize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + Width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + Height / 2 + metricsy.getHeight() / 4);
            currentPressed = i;
            showClasses(g, i);
        } else if (hovered[i] == true) {

            AnimateFont();
            g.setColor(new Color(138, 114, 23));
            g.fillRect(x, y, Width, Height);
            g.fillRect(x, y, Width, Height / 100);
            g.fillRect(x, y + 99 * Height / 100, Width, Height / 100);


            Font small = new Font("Futura", Font.PLAIN, Relativesize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(255, 213, 38));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + Width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + Height / 2 + metricsy.getHeight() / 4);
            currentPressed = i;
            showClasses(g, i);

        } else {
            g.setColor(new Color(200, 200, 200));
            g.fillRect(x, y, Width, Height / 100);
            g.fillRect(x, y + 99 * Height / 100, Width, Height / 100);

            Font small = new Font("Futura", Font.PLAIN, Font_size);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(225, 223, 209));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + Width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + Height / 2 + metricsy.getHeight() / 4);

        }
        int show = 0;
        for (int j = 0; j < classNumber; j++) {
            if (classHovered[j] == true) show = 1;
        }
        if (show == 1) showClasses(g, i);
    }

    /**
     * This method creates a special button that can show or hide the panels
     *
     * @param g is the Graphics component
     * @param x is the X coordinate of the button
     * @param y is the Y coordinate of the button
     * @author Sabin Anton
     */

    public void makeArrow(Graphics g, int x, int y) {

        if (Panels_Hidden == false) {

            if (ArrowPressed == true && MousePressed == true) {
                g.drawImage(Arrow_Pressed, x, y, this);
            } else {
                g.drawImage(Arrow_unpressed, x, y, this);
            }
        } else {

            if (ArrowPressed == true && MousePressed == true) {
                g.drawImage(TurnedArrow_Pressed, x, y, this);
            } else {
                g.drawImage(TurnedArrow_Unpressed, x, y, this);
            }

        }

    }

    /**
     * This method interprets  the commands form the mouse into actions done to the buttons
     *
     * @author Sabin Anton
     */

    public void mouseState() {

        X_hovered = MouseInfo.getPointerInfo().getLocation().x;
        Y_hovered = MouseInfo.getPointerInfo().getLocation().y;
        for (int i = 1; i <= NrButtons; i++) {
            hovered[i] = false;
        }
        for (int i = 1; i <= NrButtons; i++) {
            pressed[i] = false;
        }
        boolean ok = false;
        for (int i = 0; i < classNumber; i++) {
            if (classHovered[i] == true) ok = true;
            classHovered[i] = false;
            classPressed[i] = false;
        }
        if (X_hovered >= Leftpanel && X_hovered <= Leftpanel + PanelWidth) {

            hovered[(Y_hovered - ButtonHeight / 5) / ButtonHeight] = true;
            currentPressed = (Y_hovered - ButtonHeight / 5) / ButtonHeight;

        }
        if (X_hovered >= Leftpanel + PanelWidth - PanelWidth / 20 && X_hovered <= Leftpanel + PanelWidth + ClassWidth && currentPressed != 0) {

            if (Y_hovered >= currentPressed * ButtonHeight && Y_hovered <= currentPressed * ButtonHeight + ClassHeight * 4 + ClassHeight / 2 && (hovered[currentPressed] == true || ok == true)) {
                if ((Y_hovered - currentPressed * ButtonHeight - ClassHeight / 2) / ClassHeight < 0)
                    classHovered[0] = true;
                else
                    classHovered[(Y_hovered - currentPressed * ButtonHeight - ClassHeight / 2) / ClassHeight] = true;
            }
        }
        if (X_hovered >= Leftpanel + PanelWidth - PanelWidth / 20 && X_hovered <= Leftpanel + PanelWidth + ClassWidth && currentPressed != 0) {

            if (MousePressed == true && Y_hovered >= currentPressed * ButtonHeight - ClassHeight && Y_hovered <= currentPressed * ButtonHeight + ClassHeight * 4 + ClassHeight / 2 && (hovered[currentPressed] == true || ok == true)) {
                classPressed[(Y_hovered - currentPressed * ButtonHeight - ClassHeight / 2) / ClassHeight] = true;
            }
        }
        if (X_hovered >= Leftpanel && X_hovered <= Leftpanel + PanelWidth) {

            if (hovered[(Y_hovered - ButtonHeight / 5) / ButtonHeight] == true && MousePressed == true) {
                pressed[(Y_hovered - ButtonHeight / 5) / ButtonHeight] = true;
                currentPressed = (Y_hovered - ButtonHeight / 5) / ButtonHeight;
            } else pressed[(Y_hovered - ButtonHeight / 5) / ButtonHeight] = false;
        }

        if (X_hovered >= Leftpanel + PanelWidth + ArrowWidth / 4 && X_hovered <= Leftpanel + PanelWidth + 5 * ArrowWidth / 4) {

            if (Y_pressed >= ScreenHeight - 3 * ArrowHeight && Y_pressed <= ScreenHeight - 2 * ArrowHeight && MousePressed == true) {
                ArrowPressed = true;
            } else {
                ArrowPressed = false;
            }
        } else {
            ArrowPressed = false;
        }
    }

    /**
     * This method gives a specific name to each main button
     *
     * @author Sabin Anton
     */

    public void initializeButtons() {

        Button_name[1] = "Meniu";
        Button_name[2] = "Lectii";
        Button_name[3] = "Exercitii";
        Button_name[4] = "Teste";
        Button_name[5] = "Optiuni";

    }

    /**
     * This method generates the main buttons
     *
     * @param g is the graphics component
     * @author Sabin Anton
     */

    public void GenerateButtons(Graphics g) {

        for (int i = 1; i < NrButtons; i++) {

            makeButton(g, X_leftPanel, i * ButtonHeight, ButtonWidth, ButtonHeight, Button_name[i], i);

        }
        makeArrow(g, X_leftPanel + PanelWidth + ArrowWidth / 4, ScreenHeight - 3 * ArrowHeight);
    }

    /**
     * This method checks if the panels are hidden or visible
     *
     * @author Sabin Anton
     */

    public void Panelstate() {

        if (X_pressed >= Leftpanel + PanelWidth + ArrowWidth / 4 && X_pressed <= Leftpanel + PanelWidth + 5 * ArrowWidth / 4) {

            if (Y_pressed >= ScreenHeight - 3 * ArrowHeight && Y_pressed <= ScreenHeight - 2 * ArrowHeight && MousePressed == false) {
                if (Leftpanel == 0) Panels_Hidden = true;
                else if (Leftpanel == -PanelWidth) Panels_Hidden = false;
            }
        }
        if (Panels_Hidden == true) dir = -1;
        else dir = 1;

    }

    /**
     * This method creates every component form the screen
     *
     * @param g is the Graphics component
     * @author Sabin Anton
     */

    public void paint(Graphics g) {
        super.paint(g);
        generateBackground(g);
        initializeButtons();
        GenerateButtons(g);
    }

    /**
     * This method initialises every change from one frame to the next
     *
     * @param e is the ActionEvent parameter
     * @author Sabin Anton
     */

    public void actionPerformed(ActionEvent e) {
        mouseState();
        Panelstate();
        repaint();
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


}
