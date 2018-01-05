package VianuEdu.GUI;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class UserImput extends JPanel {

    public static int ScreenHeight = Menu.ScreenHeight;
    public static int ScreenWidth = Menu.ScreenWidth;
    public static int NrButtons = 2;
    public static int FontSize = 60 * ScreenWidth / 1920;
    public static boolean Login = true;
    public static boolean copyMousePressed = false;
    public static boolean copyMousePressed2 = false;
    public static boolean Bhovered[] = new boolean[10];
    public static boolean Bpressed[] = new boolean[10];
    public static boolean Submithovered = false;
    public static String ButtonName[] = new String[10];
    public static String BoxName[] = new String[10];
    public static String PName[] = new String[10];
    public static JTextField username = new JTextField(30);
    public static JPasswordField password = new JPasswordField(30);
    public static JTextField Tbox[] = new JTextField[10];
    public static JPasswordField Pbox[] = new JPasswordField[10];
    public static String USERNAME;
    public static String PASSWORD;
    public static String CPASSWORD;
    public static String FNAME;
    public static String LNAME;
    public static String FINITIALS;
    public static int GRADE;
    public static Image background;

    static ClassLoader loader = Menu.class.getClassLoader();

    public static void initialzeTBox() {

        BoxName[1] = "Nume:";
        BoxName[2] = "Prenume:";
        BoxName[3] = "Initiala Tatalui:";
        BoxName[4] = "Clasa:";
        PName[1] = "Parola:";
        PName[2] = "Confirma Parola:";

    }

    public static void ImportImages() {

        try {
            background = ImageIO.read(new File(loader.getResource("gui_assets/StartBackground.jpg").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int newWidth = background.getWidth(null) * ScreenWidth / 1920;
        int newHeight = background.getHeight(null) * ScreenHeight / 1080;
        background = background.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        Bpressed[1] = true;
        login();
        register();
        initialzeTBox();
    }

    public static void generateBackground(Graphics g) {

        g.drawImage(background, (ScreenWidth - background.getWidth(null)) / 2 - Setari.FrameBarX, (ScreenHeight - background.getHeight(null)) / 2, null);
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, ScreenWidth, ScreenHeight);
    }

    public static void generatePanel(Graphics g) {

        g.setColor(new Color(0, 0, 0));
        g.fillRect(ScreenWidth / 4, ScreenHeight / 4, ScreenWidth / 2, ScreenHeight / 2);
        g.setColor(new Color(255, 255, 255));
        g.drawRect(ScreenWidth / 4, ScreenHeight / 4, ScreenWidth / 2, ScreenHeight / 2);

    }

    public static void makeButton(Graphics g, int x, int y, int width, int height, String Name, int i) {

        if (Menu.MousePressed == true && copyMousePressed == false && Bhovered[i] == true) {
            for (int j = 1; j <= NrButtons; j++) {
                Bpressed[j] = false;
            }
            Setari.ButtonSound("button_click.wav");
            Bpressed[i] = true;

        }

        if (Bpressed[i] == true) {

            g.setColor(new Color(0, 0, 0));
            g.fill3DRect(x, y, width, height, true);

            Font small = new Font("Futura", Font.PLAIN, FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);


        } else if (Bhovered[i] == true) {

            g.setColor(new Color(231, 198, 63));
            g.fill3DRect(x, y, width, height, false);

            Font small = new Font("Futura", Font.PLAIN, FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(255, 231, 63));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else {
            g.setColor(new Color(235, 233, 233));
            g.fill3DRect(x, y, width, height, false);

            Font small = new Font("Futura", Font.PLAIN, FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }
        Bhovered[i] = false;
    }

    public static void initializeButtons() {

        ButtonName[1] = "Autentificare";
        ButtonName[2] = "Inregistrare";
    }

    public static void checkData() {

        USERNAME = username.getText();
        PASSWORD = String.valueOf(password.getPassword());
        if (USERNAME.length() <= 5) {

        } else if (PASSWORD.length() <= 5) {

        } else {
            Login = false;
        }
        System.out.println(USERNAME);
    }

    public static void makeSubmitButton(Graphics g, int x, int y, int width, int height, String Name) {

        if (Menu.MousePressed == false && copyMousePressed2 == true && Submithovered == true) checkData();

        if (Menu.MousePressed == true && Submithovered == true) {
            if (copyMousePressed2 != Menu.MousePressed && copyMousePressed2 == false) {
                Setari.ButtonSound("button_click.wav");
            }
            g.setColor(new Color(55, 53, 53));
            g.fillRoundRect(x, y, width, height, 15, 15);
            g.setColor(new Color(220, 220, 220));
            //g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, FontSize / 2);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else if (Submithovered == true) {

            if (Menu.MousePressed == false && copyMousePressed == true) System.exit(0);
            g.setColor(new Color(231, 198, 63));
            g.fillRoundRect(x, y, width, height, 15, 15);
            g.setColor(new Color(255, 231, 63));
            //   g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, FontSize / 2);
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

            Font small = new Font("Futura", Font.PLAIN, FontSize / 2);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf("Autentificate")) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }
        Submithovered = false;
        copyMousePressed2 = Menu.MousePressed;
    }

    public static void login() {


        Font f = new Font("Consolas", Font.PLAIN, FontSize / 2);
        username.setBounds(ScreenWidth / 2, ScreenHeight / 4 + ScreenHeight / 6, ScreenWidth / 5, ScreenHeight / 40);
        username.setFont(f);
        password.setBounds(ScreenWidth / 2, ScreenHeight / 4 + ScreenHeight / 6, ScreenWidth / 5, ScreenHeight / 40);
        password.setFont(f);
        Window.frame.add(username);
        Window.frame.add(password);
    }

    public static void register() {

        Font f = new Font("Consolas", Font.PLAIN, FontSize / 3);
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 4; j++) {
                if (i == 1) {
                    Tbox[j] = new JTextField(30);
                    Tbox[j].setBounds(ScreenWidth / 2 - ScreenWidth / 8, ScreenHeight / 4 + ScreenHeight / 50 + j * ScreenHeight / 10, ScreenWidth / 12, ScreenHeight / 30);
                    Tbox[j].setFont(f);
                    Window.frame.add(Tbox[j]);
                    Tbox[j].setVisible(false);
                } else if (i == 2 && j < 3) {
                    Pbox[j] = new JPasswordField(30);
                    Pbox[j].setBounds(ScreenWidth * 3 / 4 - ScreenWidth / 8, ScreenHeight / 4 + ScreenHeight / 50 + j * ScreenHeight / 10, ScreenWidth / 12, ScreenHeight / 30);
                    Pbox[j].setFont(f);
                    Window.frame.add(Pbox[j]);
                    Pbox[j].setVisible(false);
                }
            }
        }

    }

    public static void generateButtons(Graphics g) {

        for (int i = 1; i <= NrButtons; i++) {
            makeButton(g, i * ScreenWidth / 4, ScreenHeight / 4, ScreenWidth / 4, ScreenHeight / 12, ButtonName[i], i);
        }
        copyMousePressed = Menu.MousePressed;
        if (Bpressed[1] == true)
            makeSubmitButton(g, ScreenWidth * 3 / 4 - ScreenWidth / 6, ScreenHeight * 3 / 4 - ScreenHeight / 8, ScreenWidth / 10, ScreenHeight / 15, "Autentificare");
        else if (Bpressed[2] == true)
            makeSubmitButton(g, ScreenWidth * 3 / 4 - ScreenWidth / 6, ScreenHeight * 3 / 4 - ScreenHeight / 8, ScreenWidth / 10, ScreenHeight / 15, "Inregistrare");
    }

    public static void updateTextboxes(Graphics g) {

        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 4; j++) {
                if (i == 1) {
                    Tbox[j].setVisible(false);
                }
                if (i == 2 && j < 3) {
                    Pbox[j].setVisible(false);
                }
            }
        }

        username.revalidate();
        username.repaint();
        username.setBounds(ScreenWidth / 2, ScreenHeight / 4 + ScreenHeight / 6, ScreenWidth / 5, ScreenHeight / 20);
        password.revalidate();
        password.repaint();
        password.setBounds(ScreenWidth / 2, ScreenHeight / 4 + ScreenHeight / 6 + ScreenHeight / 10, ScreenWidth / 5, ScreenHeight / 20);

        Font small = new Font("Futura", Font.PLAIN, FontSize);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(220, 220, 220));
        g.setFont(small);
        g.drawString(String.valueOf("ID Utilizator:"), ScreenWidth / 4 + ScreenWidth / 8 - metricsx.stringWidth("ID Utilizator:") / 2, ScreenHeight / 4 + ScreenHeight / 6 + ScreenHeight / 25);
        g.drawString(String.valueOf("Parola:"), ScreenWidth / 4 + ScreenWidth / 8 - metricsx.stringWidth("Parola:") / 2, ScreenHeight / 4 + ScreenHeight / 6 + ScreenHeight / 7);
    }

    public static void updateRegister(Graphics g) {

        Font small = new Font("Futura", Font.PLAIN, FontSize / 2);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(220, 220, 220));
        g.setFont(small);

        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 4; j++) {
                if (i == 1) {
                    g.drawString(String.valueOf(BoxName[j]), ScreenWidth / 2 - ScreenWidth / 7 - metricsx.stringWidth(String.valueOf(BoxName[j])), ScreenHeight / 4 + ScreenHeight / 50 + j * ScreenHeight / 10 + metricsy.getHeight() / 2 * 4 / 3);
                    Tbox[j].setVisible(true);
                    Tbox[j].setBounds(ScreenWidth / 2 - ScreenWidth / 8, ScreenHeight / 4 + ScreenHeight / 50 + j * ScreenHeight / 10, ScreenWidth / 12, ScreenHeight / 30);
                    Tbox[j].revalidate();
                    Tbox[j].repaint();
                } else if (i == 2 && j < 3) {
                    g.drawString(String.valueOf(PName[j]), ScreenWidth * 3 / 4 - ScreenWidth / 7 - metricsx.stringWidth(String.valueOf(PName[j])), ScreenHeight / 4 + ScreenHeight / 50 + j * ScreenHeight / 10 + metricsy.getHeight() / 2 * 4 / 3);
                    Pbox[j].setVisible(true);
                    Pbox[j].setBounds(ScreenWidth * 3 / 4 - ScreenWidth / 8, ScreenHeight / 4 + ScreenHeight / 50 + j * ScreenHeight / 10, ScreenWidth / 12, ScreenHeight / 30);
                    Pbox[j].revalidate();
                    Pbox[j].repaint();
                }
            }
        }

    }

    public static void Paint(Graphics g) {

        generateBackground(g);
        generatePanel(g);
        generateButtons(g);
        if (Bpressed[1] == true) {
            username.setVisible(true);
            password.setVisible(true);
            updateTextboxes(g);
        } else if (Bpressed[2] == true) {

            username.setVisible(false);
            password.setVisible(false);
            updateRegister(g);

        }
    }

    public static void initilaizeDimensions() {

        ScreenWidth = Menu.ScreenWidth;
        ScreenHeight = Menu.ScreenHeight;
        FontSize = 60 * ScreenWidth / 1920;
        if (60 * ScreenHeight / 1080 < FontSize) FontSize = 60 * ScreenHeight / 1080;

    }

    public static void MouseState() {

        Menu.X_hovered = MouseInfo.getPointerInfo().getLocation().x - Menu.XFrame - Setari.FrameBarX;
        Menu.Y_hovered = MouseInfo.getPointerInfo().getLocation().y - Menu.YFrame - Setari.FrameBarY;

        if (Menu.Y_hovered >= ScreenHeight / 4 && Menu.Y_hovered <= ScreenHeight / 4 + ScreenHeight / 12) {

            if (Menu.X_hovered >= ScreenWidth / 4 && Menu.X_hovered <= ScreenWidth * 3 / 4) {

                Bhovered[(Menu.X_hovered - ScreenWidth / 4) / (ScreenWidth / 4) + 1] = true;
            }

        }

        if (Menu.X_hovered >= ScreenWidth * 3 / 4 - ScreenWidth / 6 && Menu.X_hovered <= ScreenWidth * 3 / 4 - ScreenWidth / 6 + ScreenWidth / 10) {

            if (Menu.Y_hovered >= ScreenHeight * 3 / 4 - ScreenHeight / 8 && Menu.Y_hovered <= ScreenHeight * 3 / 4 - ScreenHeight / 8 + ScreenHeight / 15) {
                Submithovered = true;
            }

        }

    }

    public static void Run() {

        initilaizeDimensions();
        initializeButtons();
        MouseState();
    }

}
