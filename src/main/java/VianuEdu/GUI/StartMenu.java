package VianuEdu.GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Clock;

public class StartMenu {

    public static String Menubutton_name[] = new String[30];
    public static String Username = UserImput.USERNAME;
    public static boolean Bhovered[] = new boolean[30];
    public static boolean Start_GeoEdu = true;
    public static boolean copyMousePressed = false;
    public static boolean copyMousePressed2 = false;
    public static boolean copyMousePressed3 = false;
    public static boolean Exithovered = false;
    public static int ScreenWidth = Menu.ScreenWidth;
    public static int ScreenHeight = Menu.ScreenHeight;
    public static int FontSize = 60;
    public static int NrButtons = 3;
    public static int PanelWidth = ScreenWidth / 4;
    public static int PanelHeight = ScreenHeight;
    public static int ButtonHeight = ScreenHeight / 10;
    public static int ButtonWidth = PanelWidth;
    public static int button_start = -NrButtons * ButtonHeight;
    public static Image background;
    public static Clock clock = Clock.systemUTC();
    public static long lastStep = clock.millis();
    public static long firstStep = lastStep;

    static ClassLoader loader = Menu.class.getClassLoader();

    /**
     * This method import the background image and other
     *
     * @author Sabin Anton
     */

    public static void importImages() {

        try {
            background = ImageIO.read(new File(loader.getResource("gui_assets/StartBackground.jpg").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int newWidth = background.getWidth(null) * ScreenWidth / 1920;
        int newHeight = background.getHeight(null) * ScreenHeight / 1080;
        background = background.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);

        ButtonHeight = ScreenHeight * 8 / 10 / NrButtons;
        if (ButtonHeight > ScreenHeight / 10) ButtonHeight = ScreenHeight / 10;
        button_start = -NrButtons * ButtonHeight;

    }

    /**
     * This method gives a name to every button
     *
     * @author Sabin Anton
     */

    public static void initializeButtons() {

        Menubutton_name[1] = "GeoEdu";
        Menubutton_name[2] = "PhiEdu";
        Menubutton_name[3] = "MathEdu";
    }

    /**
     * This method generates the background of the start menu, the panels and calss the method that will generate the writing.
     *
     * @param g is the Graphics component
     * @author Sabin Anton
     */

    public static void generateBackground(Graphics g) {

        g.drawImage(background, (ScreenWidth - background.getWidth(null)) / 2, (ScreenHeight - background.getHeight(null)) / 2, null);
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, PanelWidth, PanelHeight);
        g.setColor(new Color(0, 0, 0));
        g.drawRect(PanelHeight / 200, PanelHeight / 200, PanelWidth - PanelHeight / 100, PanelHeight * 99 / 100);
        g.setColor(new Color(255, 253, 253));
        g.fillRect(PanelHeight / 200, PanelHeight / 200, PanelWidth - PanelHeight / 100, PanelHeight * 99 / 100);
    }

    /**
     * This method animates the sliding of the buttons when the start menu is entered
     *
     * @author Sabin Anton
     */

    public static void AnimateButtons() {

        if (clock.millis() - firstStep > 600) {
            int value = (int) (clock.millis() - lastStep) * 5 / 6;
            if (button_start < 0 && value < 100) {
                button_start += value;
            }
            lastStep = clock.millis();
        }
        if (button_start > 0) button_start = 0;
    }

    /**
     * This method writes the top-left word on the program window
     *
     * @param g is the Graphics component
     * @author Sabin Anton
     */

    public static void Draw_Choose(Graphics g) {

        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, PanelWidth, PanelHeight / 10);

        Font small = new Font("Futura", Font.BOLD, FontSize);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(255, 250, 250));
        g.setFont(small);
        g.drawString(String.valueOf("Alege:"), PanelWidth / 2 - metricsx.stringWidth(String.valueOf("Alege")) / 2, ScreenHeight / 20 + metricsy.getHeight() / 4);

    }

    /**
     * This method creates a button
     *
     * @param g      is the Graphics component
     * @param x      is the X coordinate
     * @param y      is the Y coordinate fo the button
     * @param width  is the width of the button
     * @param height is the height of the button
     * @param Name   is the name of the button
     * @param i      is the position of the button in the button array
     * @author Sabin Anton
     */

    public static void makeButton(Graphics g, int x, int y, int width, int height, String Name, int i) {

        Menu.X_hovered = MouseInfo.getPointerInfo().getLocation().x - Menu.XFrame - Setari.FrameBarX;
        Menu.Y_hovered = MouseInfo.getPointerInfo().getLocation().y - Menu.YFrame - Setari.FrameBarY;

        if (Menu.X_hovered >= x && Menu.X_hovered <= x + width && Menu.Y_hovered >= y && Menu.Y_hovered <= y + height)
            Bhovered[i] = true;
        ReadButtons();
        if (Menu.MousePressed == true && Bhovered[i] == true) {
            if (copyMousePressed2 != Menu.MousePressed && copyMousePressed2 == false) {
                Setari.ButtonSound("button_click.wav");
            }

            g.setColor(new Color(55, 53, 53));
            g.fillRect(x, y, width, height);
            g.setColor(new Color(220, 220, 220));
            g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else if (Bhovered[i] == true) {

            copyMousePressed = Menu.MousePressed;
            g.setColor(new Color(231, 198, 63));
            g.fillRect(x, y, width, height);
            g.setColor(new Color(255, 231, 63));
            g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(255, 231, 63));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else {
            g.setColor(new Color(255, 253, 253));
            g.fillRect(x, y, width, height);
            g.setColor(new Color(0, 0, 0));
            g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }
        Bhovered[i] = false;
    }

    public static void makeExitButton(Graphics g, int x, int y, int width, int height, String Name) {


        Menu.X_hovered = MouseInfo.getPointerInfo().getLocation().x - Menu.XFrame - Setari.FrameBarX;
        Menu.Y_hovered = MouseInfo.getPointerInfo().getLocation().y - Menu.YFrame - Setari.FrameBarY;
        if (Menu.X_hovered >= x && Menu.X_hovered <= x + width && Menu.Y_hovered >= y && Menu.Y_hovered <= y + height)
            Exithovered = true;

        if (Menu.MousePressed == true && Exithovered == true) {
            if (copyMousePressed3 != Menu.MousePressed && copyMousePressed3 == false) {
                Setari.ButtonSound("button_click.wav");
            }
            g.setColor(new Color(55, 53, 53));
            g.fillRoundRect(x, y, width, height, 15, 15);
            g.setColor(new Color(220, 220, 220));
            //g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else if (Exithovered == true) {

            if (Menu.MousePressed == false && copyMousePressed3 == true && Name == "Iesire") System.exit(0);
            else if (Menu.MousePressed == false && copyMousePressed3 == true && Name == "Deconectare") {
                button_start = -NrButtons * ButtonHeight;
                UserImput.Login = true;
            }
            g.setColor(new Color(231, 198, 63));
            g.fillRoundRect(x, y, width, height, 15, 15);
            g.setColor(new Color(255, 231, 63));
            //   g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(255, 231, 63));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else {
            g.setColor(new Color(255, 253, 253));
            g.fillRoundRect(x, y, width, height, 15, 15);
            g.setColor(new Color(0, 0, 0));
            // g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }
        Exithovered = false;
    }

    /**
     * This method creates the buttons and sets their position and size.
     *
     * @param g is the Graphics component
     * @author Sabin Anton
     */

    public static void generateButtons(Graphics g) {

        for (int i = 0; i < NrButtons; i++) {
            makeButton(g, PanelHeight / 200, button_start + ScreenHeight / 10 + i * ButtonHeight, ButtonWidth - PanelHeight / 100, ButtonHeight, Menubutton_name[i + 1], i + 1);
        }
        copyMousePressed2 = Menu.MousePressed;
        makeExitButton(g, ScreenWidth * 5 / 6, ScreenHeight * 4 / 5, ScreenWidth / 10, ScreenHeight / 12, "Iesire");
        makeExitButton(g, ScreenWidth / 4 + ScreenWidth / 50, ScreenHeight * 4 / 5, ScreenWidth / 5, ScreenHeight / 12, "Deconectare");
        copyMousePressed3 = Menu.MousePressed;
    }

    /**
     * This method generates a text on the screen that welcomes the user
     *
     * @param g is the Graphics component
     * @author Sabin Anton
     */

    public static void generateTitle(Graphics g) {

        Username = UserImput.student.getFirstName() + " " + UserImput.student.getLastName();

        Font small = new Font("Futura", Font.BOLD, FontSize * 2);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(0, 0, 0));
        g.setFont(small);
        g.drawString(String.valueOf("Bine ai venit,"), (ScreenWidth - PanelWidth) / 2 + PanelWidth - metricsx.stringWidth(String.valueOf("Bine ai venit,")) / 2, ScreenHeight / 4 + metricsy.getHeight() / 4);
        g.drawString(String.valueOf(Username + "!"), (ScreenWidth - PanelWidth) / 2 + PanelWidth - metricsx.stringWidth(String.valueOf(Username + "!")) / 2, ScreenHeight / 4 + metricsy.getHeight() + metricsy.getHeight() / 4);

    }

    /**
     * This is the main Graphics method and it calls all other methods in order
     *
     * @param g is the Graphics component
     * @auhtor Sabin Anton
     */

    public static void Paint(Graphics g) {

        if (UserImput.Login == true) {

            UserImput.Paint(g);

        } else {
            generateBackground(g);
            generateTitle(g);
            generateButtons(g);
            Draw_Choose(g);
        }
    }

    /**
     * This method updates for each frame the dimensions used in this class
     *
     * @author Sabin Anton
     */

    public static void initializeDimensions() {

        ScreenHeight = Menu.ScreenHeight;
        ScreenWidth = Menu.ScreenWidth;
        PanelWidth = ScreenWidth / 4;
        PanelHeight = ScreenHeight;
        ButtonWidth = PanelWidth;
        FontSize = 60 * ScreenWidth / 1920;
        if (FontSize > 60 * ScreenHeight / 1080) FontSize = 60 * ScreenHeight / 1080;
        ButtonHeight = ScreenHeight * 8 / 10 / NrButtons;
        if (ButtonHeight > ScreenHeight / 10) ButtonHeight = ScreenHeight / 10;
        Username = UserImput.USERNAME;
    }

    /**
     * This method checks if other classes are called and it exists this one if so.
     *
     * @author Sabin Anton
     */

    public static void ReadButtons() {

        if (Bhovered[1] == true && Menu.MousePressed == false && copyMousePressed == true) {

            Start_GeoEdu = false;
            button_start = -NrButtons * ButtonHeight;

        }
        copyMousePressed = Menu.MousePressed;
    }

    /**
     * This method calls all methods needed for each frame
     *
     * @author Sabin Anton
     */

    public static void Run() {

        if (UserImput.Login == true) {

            UserImput.Run();

        } else {
            UserImput.username.setVisible(false);
            UserImput.password.setVisible(false);
            for (int i = 1; i <= 2; i++) {
                for (int j = 1; j <= 5; j++) {
                    if (i == 1) {
                        UserImput.Tbox[j].setVisible(false);
                    } else if (i == 2 && j <= 1) {
                        UserImput.Tbox[5 + j].setVisible(false);
                    } else if (i == 2 && j < 4) {
                        UserImput.Pbox[j].setVisible(false);
                    }
                }
            }
            initializeDimensions();
            AnimateButtons();
        }
    }

}
