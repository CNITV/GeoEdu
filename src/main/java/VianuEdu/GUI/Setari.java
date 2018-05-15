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

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Setari extends JPanel {

    private static boolean copy_pressed = false;
    public static boolean SettingsOn = false;
    private static int precentage = 50;
    private static boolean copyMousePressed = false;
    private static boolean ExitPressed = false;
    private static boolean ExitHovered = false;
    public static int FrameBarY = 30;
    public static int FrameBarX = 6;

    static ClassLoader loader = Menu.class.getClassLoader();

    public Setari() {


    }

    /**
     * This method adjusts the volume of an audio file
     *
     * @param clip is the audio clip
     * @author Sabin Anton
     */

    public static void ChangeVolume(Clip clip) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        BooleanControl mute = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
        gainControl.getMaximum();
        float max = gainControl.getMaximum();
        gainControl.setValue((float) precentage / 100 * max * 2 - max);
        if ((float) precentage / 100 * max * 2 - max == -max) mute.setValue(true);
        else mute.setValue(false);
    }

    /**
     * This mehod imports and plays an audio file
     *
     * @param name is the name and format of the file
     * @author Sabin Anton
     */

    public static void ButtonSound(String name) {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(String.valueOf(new File(loader.getResource("gui_assets/" + name).getFile()))));
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            clip.open(audioInputStream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChangeVolume(clip);
        clip.start();
    }

    /**
     * This method draws a little box where the volume bar is filled
     *
     * @param g      is the Graphics component
     * @param x      is the X coordinate
     * @param y      is the Y coordinate
     * @param width  is the width of the box
     * @param height is the height of the box
     * @author Sabin Anton
     */

    public static void DrawEnd(Graphics g, int x, int y, int width, int height) {

        g.setColor(new Color(152, 135, 68));
        g.fillRect(x, y - height / 8, width, height);
        g.setColor(new Color(255, 255, 255));
        g.fillRect(x + width / 4, (y - height / 8) + width / 4, width / 2, height - width / 4);

    }

    /**
     * This method draws the exit icon at the right top of the settings menu
     *
     * @param g      is the Graphics component
     * @param x      is the X coordinate
     * @param y      is the Y coordinate
     * @param width  is the width of the icon
     * @param height is the height of the icon
     * @author Sabin Anton
     */

    public static void DrawExit(Graphics g, int x, int y, int width, int height) {

        if (ExitPressed == true) {
            g.setColor(new Color(250, 250, 250));
            g.fillRect(x, y, width, height);
            g.setColor(new Color(50, 50, 50));
            g.drawLine(x + width / 10, y + height / 10, x + width * 9 / 10, y + height * 9 / 10);
            g.drawLine(x + width * 9 / 10, y + height / 10, x + width / 10, y + height * 9 / 10);
        } else if (ExitHovered == true) {
            g.setColor(new Color(255, 231, 170));
            g.fillRect(x, y, width, height);
            g.setColor(new Color(152, 135, 68));
            g.drawLine(x + width / 10, y + height / 10, x + width * 9 / 10, y + height * 9 / 10);
            g.drawLine(x + width * 9 / 10, y + height / 10, x + width / 10, y + height * 9 / 10);
        } else {
            g.setColor(new Color(100, 100, 100));
            g.fillRect(x, y, width, height);
            g.setColor(new Color(250, 250, 250));
            g.drawLine(x + width / 10, y + height / 10, x + width * 9 / 10, y + height * 9 / 10);
            g.drawLine(x + width * 9 / 10, y + height / 10, x + width / 10, y + height * 9 / 10);
        }
        if (copyMousePressed != Menu.MousePressed && copyMousePressed == true && (ExitPressed == true || ExitHovered == true)) {
            ButtonSound("button_click.wav");
            SettingsOn = false;
        }
        copyMousePressed = Menu.MousePressed;
    }

    /**
     * This method draws and updates the volume bar in the settings menu accordingly to the user's comands
     *
     * @param g          is the Graphics component
     * @param percentage is the percentage of the volume
     * @author Sabin Anton
     */

    public static void drawVolumeBar(Graphics g, int percentage) {

        int y = Menu.ScreenHeight / 4 + Menu.ScreenHeight / 5;

        Font small = new Font("Futura", Font.PLAIN, GeoEduMenu.Relativesize / 2);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(250, 250, 250));
        g.setFont(small);
        g.drawString(String.valueOf("Volum:"), Menu.ScreenWidth / 4 + Menu.ScreenWidth / 50, y);

        int BarWidth = Menu.ScreenWidth / 3;
        int BarHeight = Menu.ScreenHeight / 30;
        int x = Menu.ScreenWidth / 2 - BarWidth / 2 + metricsx.stringWidth("Volum:") / 2;
        y -= metricsy.getHeight() * 2 / 3;

        g.setColor(new Color(250, 250, 250));
        g.drawRect(x, y, BarWidth, BarHeight);

        if (FindPercentage(x, y, BarWidth, BarHeight) != -1 && Menu.MousePressed == true)
            precentage = FindPercentage(x, y, BarWidth, BarHeight);

        g.setColor(new Color(255, 247, 213));
        g.fillRect(x, y, BarWidth * percentage / 100, BarHeight);

        small = new Font("Consolas", Font.PLAIN, GeoEduMenu.Relativesize / 2);
        metricsy = g.getFontMetrics(small);
        metricsx = g.getFontMetrics(small);
        g.setColor(new Color(250, 250, 250));
        g.setFont(small);
        g.drawString(String.valueOf(percentage + " %"), x + BarWidth / 2 - metricsx.stringWidth(percentage + " %") / 2, y + BarHeight + metricsy.getHeight());
        DrawEnd(g, x + BarWidth * percentage / 100, y, BarWidth / 50, BarHeight * 5 / 4);

    }

    /**
     * This method translates the mouse position into the percentage of the volume in the volume bar
     *
     * @param x      is the X coordinate of the bar
     * @param y      is the Y coordinate of the bar
     * @param Width  is the width of the bar
     * @param height is the height of the bar
     * @return the percentage of volume
     * @author Sabin Anton
     */

    public static int FindPercentage(int x, int y, int Width, int height) {

        int X = MouseInfo.getPointerInfo().getLocation().x - Menu.XFrame;
        int Y = MouseInfo.getPointerInfo().getLocation().y - Menu.YFrame;

        if (X >= x && X < x + Width) {
            if (Y >= y + height / 2 && Y <= y + height * 2) {

                if (Menu.MousePressed == true && Menu.MousePressed != copyMousePressed) ButtonSound("button_click.wav");
                copyMousePressed = Menu.MousePressed;
                return (X - x) * 100 / Width;

            }
        } else if (X < x) {
            if (Y >= y + height / 2 && Y <= y + 2 * height) {

                if (Menu.MousePressed == true && Menu.MousePressed != copyMousePressed) ButtonSound("button_click.wav");
                copyMousePressed = Menu.MousePressed;
                return 0;

            }
        } else if (X > x) {
            if (Y >= y + height / 2 && Y <= y + 2 * height) {

                if (Menu.MousePressed == true && Menu.MousePressed != copyMousePressed) ButtonSound("button_click.wav");
                copyMousePressed = Menu.MousePressed;
                return 100;

            }
        }
        return -1;
    }

    /**
     * This method verifies if the Exist icon has benn pressed
     *
     * @author Sabin Anton
     */

    public static void Exit_Pressed() {

        int X = MouseInfo.getPointerInfo().getLocation().x - Menu.XFrame - FrameBarX;
        int Y = MouseInfo.getPointerInfo().getLocation().y - Menu.YFrame - FrameBarY;

        if (X >= Menu.ScreenWidth / 4 + Menu.ScreenWidth / 60 * 29 && X <= Menu.ScreenWidth * 3 / 4) {
            if (Y >= Menu.ScreenHeight / 4 && Y <= Menu.ScreenHeight / 4 + Menu.ScreenWidth / 60) {

                if (Menu.MousePressed == false) {
                    ExitHovered = true;
                    ExitPressed = false;
                } else {
                    ExitPressed = true;
                }

            } else {
                ExitPressed = false;
                ExitHovered = false;
            }
        } else {
            ExitPressed = false;
            ExitHovered = false;
        }
    }

    /**
     * This method creates and draws the settings panel
     *
     * @param g is the Graphics component
     * @author Sabin Anton
     */

    public static void DrawSettings(Graphics g) {

        int PanelWidth = Menu.ScreenWidth / 2;
        int PanelHeight = Menu.ScreenHeight / 2;
        g.setColor(new Color(200, 200, 200, 160));
        g.fillRect(0, 0, Menu.ScreenWidth, Menu.ScreenHeight);
        g.setColor(new Color(50, 50, 50));
        g.fillRect(Menu.ScreenWidth / 4, Menu.ScreenHeight / 4, PanelWidth, PanelHeight);

        Font small = new Font("Futura", Font.PLAIN, GeoEduMenu.Relativesize);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(250, 250, 250));
        g.setFont(small);
        g.drawString(String.valueOf("SETARI:"), Menu.ScreenWidth / 2 - metricsx.stringWidth(String.valueOf("SETARI")) / 2, Menu.ScreenHeight / 4 + metricsy.getHeight() * 4 / 5);

        g.setColor(new Color(255, 255, 255));
        g.drawRect(Menu.ScreenWidth / 4 + Menu.ScreenWidth / 100, Menu.ScreenHeight / 4 + metricsy.getHeight() + Menu.ScreenHeight / 100, PanelWidth - Menu.ScreenWidth / 50, PanelHeight - metricsy.getHeight() - Menu.ScreenHeight / 50);

        drawVolumeBar(g, precentage);
        Exit_Pressed();
        DrawExit(g, Menu.ScreenWidth / 4 + PanelWidth * 29 / 30, Menu.ScreenHeight / 4, PanelWidth / 30, PanelWidth / 30);
    }

    /**
     * This method opens the Settings panel and freezes the rest of the program
     *
     * @param g is the Graphics component
     * @author Sabin Anton
     */

    public static void Settings(Graphics g) {

        if (GeoEduMenu.pressed[GeoEduMenu.NrButtons] == false && copy_pressed == true) {
            SettingsOn = true;
            GeoEduMenu.hovered[GeoEduMenu.NrButtons] = false;
        }
        copy_pressed = GeoEduMenu.pressed[GeoEduMenu.NrButtons];
        if (SettingsOn == true) {
            DrawSettings(g);
            Tests.essay.setVisible(false);
        }
    }
}

