package VianuEdu.GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Clock;

import static VianuEdu.GUI.Menu.*;

public class GeoEduMenu {


    public static int Leftpanel = -ScreenWidth / 5;
    public static int Rightpanel = ScreenWidth;
    public static int PanelWidth = ScreenWidth / 5;
    public static int PanelHeight = ScreenHeight;
    private static int Font_size = 60;
    public static int Relativesize = Font_size;
    public static boolean[] classPressed = new boolean[10];
    public static boolean[] classHovered = new boolean[10];
    public static boolean[] pressed = new boolean[10];
    public static boolean[] hovered = new boolean[10];
    public static boolean ArrowPressed = false;
    private static boolean Panels_Hidden = false;
    public static boolean copyMousePressed = false;
    public static String[] Button_name = new String[10];
    private static int X_leftPanel = -PanelWidth;
    private static int X_rightPanel = ScreenWidth;
    private static int classNumber = 4;
    private static int NrButtons = 7;
    private static int ButtonWidth = PanelWidth;
    private static int ButtonHeight = PanelHeight / NrButtons * 4 / 5;
    private static int ClassHeight = ButtonHeight / 2;
    private static int ClassWidth = ButtonWidth * 2 / 3;
    private static int ArrowWidth = 80;
    private static int ArrowHeight = 45;
    public static int currentPressed = 0;
    public static int copyScreenWidth = ScreenWidth;
    private static int dir = 1;
    public static Image Arrow_unpressed = null;
    public static Image Arrow_Pressed = null;
    public static Image TurnedArrow_Unpressed = null;
    public static Image TurnedArrow_Pressed = null;
    public static Image Background = null;
    public static Clock clock = Clock.systemUTC();
    public static long lastStep = clock.millis() - 1;
    public static long firstStep = clock.millis();

    static ClassLoader loader = Menu.class.getClassLoader();

    /**
     * This method imports all the images needed in the program
     *
     * @author Sabin Anton
     */

    public static void generateImages() {

        try {
            Arrow_unpressed = ImageIO.read(new File(loader.getResource("gui_assets/notPressedDownArrowLeft.png").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int newWidth = Arrow_unpressed.getWidth(null) * ScreenWidth / 1920;
        int newHeight = Arrow_unpressed.getHeight(null) * ScreenHeight / 1080;
        Arrow_unpressed = Arrow_unpressed.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        ;
        try {
            Arrow_Pressed = ImageIO.read(new File(loader.getResource("gui_assets/PressedDownArrowLeft.png").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        newWidth = Arrow_Pressed.getWidth(null) * ScreenWidth / 1920;
        newHeight = Arrow_Pressed.getHeight(null) * ScreenHeight / 1080;
        Arrow_Pressed = Arrow_Pressed.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        ;
        try {
            TurnedArrow_Pressed = ImageIO.read(new File(loader.getResource("gui_assets/PressedDownArrowRight.png").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        newWidth = TurnedArrow_Pressed.getWidth(null) * ScreenWidth / 1920;
        newHeight = TurnedArrow_Pressed.getHeight(null) * ScreenHeight / 1080;
        TurnedArrow_Pressed = TurnedArrow_Pressed.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        try {
            TurnedArrow_Unpressed = ImageIO.read(new File(loader.getResource("gui_assets/notPressedDownArrowRight.png").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        newWidth = TurnedArrow_Unpressed.getWidth(null) * ScreenWidth / 1920;
        newHeight = TurnedArrow_Unpressed.getHeight(null) * ScreenHeight / 1080;
        TurnedArrow_Unpressed = TurnedArrow_Unpressed.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        try {
            Background = ImageIO.read(new File(loader.getResource("gui_assets/Background.jpg").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        newWidth = Background.getWidth(null) * ScreenWidth / 1920;
        newHeight = Background.getHeight(null) * ScreenHeight / 1080;
        Background = Background.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
    }

    /**
     * This method creates the animation of the left panel
     *
     * @param direction specifes whether the left panel moves left (-1) or right (1)
     * @return the X coordinate of the panel
     * @author Sabin Anton
     */

    public static int AnimateLeftP(int direction) {

        int value = direction * (int) (clock.millis() - lastStep) * 5 / 6;
        if (copyScreenWidth - ScreenWidth > 0) value += copyScreenWidth - ScreenWidth;
        if (Leftpanel <= 0 && Leftpanel >= -PanelWidth)
            Leftpanel += value;
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

    public static int AnimateRightP(int direction) {

        int value = direction * (int) (clock.millis() - lastStep) * 5 / 6;
        if (copyScreenWidth - ScreenWidth > 0) value += copyScreenWidth - ScreenWidth;
        if (Rightpanel >= ScreenWidth * 4 / 5 && Rightpanel <= ScreenWidth)
            Rightpanel -= value;
        if (Rightpanel < ScreenWidth * 4 / 5) Rightpanel = ScreenWidth * 4 / 5;
        if (Rightpanel > ScreenWidth) Rightpanel = ScreenWidth;
        return Rightpanel;
    }

    /**
     * This method changes the size of the font used for the main buttons
     *
     * @author Sabin Anton
     */

    public static void AnimateFont() {

        if (Relativesize < Font_size * 5 / 4) Relativesize++;

    }

    /**
     * This method generates the background image of the menu and the 2 panels;
     *
     * @param g is the graphics component used for generating it
     * @author Sabin Anton
     */

    public static void generateBackground(Graphics g) {

        if (clock.millis() - lastStep < 100) {
            X_leftPanel = AnimateLeftP(dir);
            X_rightPanel = AnimateRightP(dir);
        }
        copyScreenWidth = ScreenWidth;
        lastStep = clock.millis();
        g.drawImage(Background, (ScreenWidth - Background.getWidth(null)) / 2 - 8, 0, null);
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

    public static void makeClassButton(Graphics g, int x, int y, int Width, int Height, int clasa) {

        if (Panels_Hidden == false && classPressed[clasa - 9] == true) {
            if (MousePressed != copyMousePressed) Setari.ButtonSound("button_click.wav");
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
        } else if (Panels_Hidden == false && classHovered[clasa - 9] == true) {
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
        } else if (Panels_Hidden == false) {
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

    public static void showClasses(Graphics g, int x) {

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

    public static void makeButton(Graphics g, int x, int y, int Width, int Height, String Name, int i) {

        if (pressed[i] == true) {

            AnimateFont();
            if (MousePressed != copyMousePressed) Setari.ButtonSound("button_click.wav");
            g.setColor(new Color(150, 150, 150));
            g.fillRect(x, y, Width, Height);
            g.fillRect(x, y, Width, 1);
            g.fillRect(x, y + 99 * Height / 100, Width, 1);


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
            g.fillRect(x, y, Width, 1);
            g.fillRect(x, y + 99 * Height / 100, Width, 1);


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
            g.fillRect(x, y, Width, 1);
            g.fillRect(x, y + 99 * Height / 100, Width, 1);

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

    public static void makeArrow(Graphics g, int x, int y) {

        if (Panels_Hidden == false) {

            if (ArrowPressed == true && MousePressed == true) {
                if (MousePressed != copyMousePressed) Setari.ButtonSound("button_click.wav");
                g.drawImage(Arrow_Pressed, x, y, null);
            } else {
                g.drawImage(Arrow_unpressed, x, y, null);
            }
        } else {

            if (ArrowPressed == true && MousePressed == true) {
                if (MousePressed != copyMousePressed) Setari.ButtonSound("button_click.wav");
                g.drawImage(TurnedArrow_Pressed, x, y, null);
            } else {
                g.drawImage(TurnedArrow_Unpressed, x, y, null);
            }

        }

    }

    /**
     * This method gives a specific name to each main button
     *
     * @author Sabin Anton
     */

    public static void initializeButtons() {

        Button_name[1] = "Meniu";
        Button_name[2] = "Lectii";
        Button_name[3] = "Exercitii";
        Button_name[4] = "Teste";
        Button_name[5] = "Status";
        Button_name[6] = "Setari";

    }

    /**
     * This method generates the main buttons
     *
     * @param g is the graphics component
     * @author Sabin Anton
     */

    public static void GenerateButtons(Graphics g) {

        for (int i = 1; i < NrButtons; i++) {

            makeButton(g, X_leftPanel, i * ButtonHeight, ButtonWidth, ButtonHeight, Button_name[i], i);

        }
        makeArrow(g, X_leftPanel + PanelWidth + ArrowWidth / 4, ScreenHeight - 3 * ArrowHeight);
        copyMousePressed = MousePressed;
    }

    /**
     * This method checks if the panels are hidden or visible
     *
     * @author Sabin Anton
     */

    public static void Panelstate() {

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
     * This method updates all the dimensions needed in the program accordingly to the screen size and coordinates;
     *
     * @author Sabin Anton
     */

    public static void initializeDimensions() {

        PanelWidth = ScreenWidth / 5;
        PanelHeight = ScreenHeight;
        ButtonWidth = PanelWidth;
        ButtonHeight = PanelHeight / NrButtons * 4 / 5;
        ClassHeight = ButtonHeight / 2;
        ClassWidth = ButtonWidth * 2 / 3;
        Font_size = ScreenWidth * 60 / 1920;
        if (ScreenHeight * 60 / 1080 < Font_size) Font_size = ScreenHeight * 60 / 1080;
        Relativesize = Font_size * 5 / 4;
    }

    /**
     * This method interprets  the commands form the mouse into actions done to the buttons
     *
     * @author Sabin Anton
     */

    public static void mouseState() {

        X_hovered = MouseInfo.getPointerInfo().getLocation().x - XFrame - Setari.FrameBarX;
        Y_hovered = MouseInfo.getPointerInfo().getLocation().y - YFrame - Setari.FrameBarY;
        if (X_hovered > ScreenWidth) X_hovered = ScreenWidth;
        if (Y_hovered > ScreenHeight) Y_hovered = ScreenHeight;
        if (X_hovered < 0) X_hovered = 0;
        if (Y_hovered < 0) Y_hovered = 0;
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

            hovered[(Y_hovered) / ButtonHeight] = true;
            currentPressed = (Y_hovered) / ButtonHeight;

        }
        if (Panels_Hidden == false && X_hovered >= Leftpanel + PanelWidth - PanelWidth / 20 && X_hovered <= Leftpanel + PanelWidth + ClassWidth && currentPressed != 0) {

            if (Y_hovered >= currentPressed * ButtonHeight && Y_hovered <= currentPressed * ButtonHeight + ClassHeight * 4 + ClassHeight / 2 && (hovered[currentPressed] == true || ok == true)) {
                if ((Y_hovered - currentPressed * ButtonHeight) / ClassHeight < 0)
                    classHovered[0] = true;
                else
                    classHovered[(Y_hovered - currentPressed * ButtonHeight) / ClassHeight] = true;
            }
        }
        if (Panels_Hidden == false && X_hovered >= Leftpanel + PanelWidth - PanelWidth / 20 && X_hovered <= Leftpanel + PanelWidth + ClassWidth && currentPressed != 0) {

            if (MousePressed == true && Y_hovered >= currentPressed * ButtonHeight && Y_hovered <= currentPressed * ButtonHeight + ClassHeight * 4 && (hovered[currentPressed] == true || ok == true)) {
                classPressed[(Y_hovered - currentPressed * ButtonHeight) / ClassHeight] = true;
            }
        }
        if (X_hovered >= Leftpanel && X_hovered <= Leftpanel + PanelWidth) {

            if (hovered[(Y_hovered) / ButtonHeight] == true && MousePressed == true) {
                pressed[(Y_hovered) / ButtonHeight] = true;
                currentPressed = (Y_hovered) / ButtonHeight;
            } else pressed[(Y_hovered) / ButtonHeight] = false;
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
     * This method calls all other Graphics methods
     *
     * @param g is the Graphics component
     * @author Sabin Anton
     */

    public static void Paint(Graphics g) {

        GeoEduMenu.generateBackground(g);
        GeoEduMenu.initializeButtons();
        GeoEduMenu.GenerateButtons(g);
        ContentBrowser.Paint(g);
        Setari.Settings(g);

    }

    public static void returnMenu() {

        if (hovered[1] == true && Menu.MousePressed != copyMousePressed && copyMousePressed == true) {
            StartMenu.Start_GeoEdu = true;
            Leftpanel = -PanelWidth;
            Rightpanel = ScreenWidth + PanelWidth;
        }

    }

    /**
     * This method calls all methods that need to be calld at each frame
     *
     * @author Sabin Anton
     */

    public static void Run() {

        initializeDimensions();
        ContentBrowser.initialiseDimensions();
        if (Setari.SettingsOn == false) {
            mouseState();
            Panelstate();
        }
        returnMenu();
    }

}
