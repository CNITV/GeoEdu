package VianuEdu.GUI;


import VianuEdu.backend.DatabaseHandling.DatabaseHandler;
import VianuEdu.backend.Identification.Account;
import VianuEdu.backend.Identification.Student;
import VianuEdu.backend.Identification.Teacher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import static VianuEdu.GUI.StartMenu.Username;
import static VianuEdu.GUI.StartMenu.clock;


public class UserImput extends JPanel {

    public static int ScreenHeight = Menu.ScreenHeight;
    public static int ScreenWidth = Menu.ScreenWidth;
    public static int NrButtons = 2;
    public static int size = 50;
    public static int FontSize = 60 * ScreenWidth / 1920*(size+50)/100;
    public static boolean Login = true;
    public static boolean showMessage = false;
    public static boolean Register = true;
    public static boolean copyMousePressed = false;
    public static boolean copyMousePressed2 = false;
    public static boolean copyMousePressed3 = false;
    public static boolean Bhovered[] = new boolean[10];
    public static boolean Bpressed[] = new boolean[10];
    public static boolean Submithovered = false;
    public static boolean optionsShowed[] = new boolean[10];
    public static boolean Ohovered = false;
    public static String ButtonName[] = new String[10];
    public static String BoxName[] = new String[10];
    public static String PName[] = new String[10];
    public static JTextField username = new JTextField(30);
    public static JPasswordField password = new JPasswordField(30);
    public static JTextField Tbox[] = new JTextField[10];
    public static JPasswordField Pbox[] = new JPasswordField[10];
    public static String gradeName[] = new String[10];
    public static String genderName[] = new String[10];
    public static String USERNAME;
    public static String PASSWORD;
    public static String dialogText;
    public static String cookie = "a", cookie2 = "a";
    public static long startMessage = 0;
    public static Student student;
    public static Teacher teacher;
    public static Image background;

    static ClassLoader loader = Menu.class.getClassLoader();

    public static void initialzeTBox() {

        BoxName[1] = "Nume:";
        BoxName[2] = "Prenume:";
        BoxName[3] = "Initiala Tatalui:";
        BoxName[4] = "Clasa (+litera):";
        BoxName[5] = "Sex:";
        BoxName[6] = "ID Utilizator:";
        BoxName[7] = "Esti Profesor?";
        PName[2] = "Parola:";
        PName[3] = "Confirma Parola:";

    }

    public static void DialogBox(Graphics g, String Message, int x, int y, int size) {

        Font small = new Font("Futura", Font.PLAIN, size / 2);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(255, 247, 33));
        g.setFont(small);
        g.drawString(String.valueOf(Message), x - metricsx.stringWidth(String.valueOf(Message)) / 2, y + metricsy.getHeight() / 2);

    }

    public static void initializeOptions() {

        gradeName[1] = "clasa a 9-a";
        gradeName[2] = "clasa a 10-a";
        gradeName[3] = "clasa a 11-a";
        gradeName[4] = "clasa a 12-a";
        genderName[1] = "M";
        genderName[2] = "F";

    }

    public static void ImportImages() {

        try {
            background = ImageIO.read(loader.getResourceAsStream("gui_assets/StartBackground.jpg"));
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
        initializeOptions();
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

    public static void checkData(Graphics g) {

        if (Bpressed[1] == true) {
            USERNAME = username.getText();
            PASSWORD = String.valueOf(password.getPassword());

            Login = false;
            if (USERNAME.length() < 1 || PASSWORD.length() < 1) {
                showMessage = true;
                startMessage = clock.millis();
                dialogText = "ID utilizator sau parola incorecta!";
                Login = true;
            }
            else {
                try {
                    cookie = Menu.Maner.studentLogin(new Account(USERNAME, PASSWORD));

                    student = Menu.Maner.getStudent(cookie);

                    Menu.isTeacher = false;
                } catch (IOException e) {
                    //e.printStackTrace();
                    // afiseaza ca nu e net
                } catch (IllegalAccessException e) {
                    try {
                        cookie2 = Menu.Maner.teacherLogin(new Account(USERNAME, PASSWORD));

                        teacher = Menu.Maner.getTeacher(cookie2);
                        Menu.isTeacher = true;

                    } catch (IOException e1) {
                        Login=true;
                        startMessage = clock.millis();
                        dialogText = "Nu s-a putut conecta la server!";
                    } catch (IllegalAccessException e1) {
                        Login = true;
                        startMessage = clock.millis();
                        dialogText = "ID utilizator sau parola incorecta!";
                        USERNAME=null;
                        PASSWORD=null;
                        StartMenu.Username=null;

                    }
                }
                try {
                    StartMenu.Username = student.getFirstName() + " " + student.getLastName();System.out.println(StartMenu.Username);


                } catch (java.lang.NullPointerException e) {
                    try{
                        StartMenu.Username = teacher.getFirstName() + " " + teacher.getLastName();
                    }catch(java.lang.NullPointerException e1){
                        showMessage = true;
                        startMessage = clock.millis();
                        dialogText = "ID utilizator sau parola incorecta!";
                        Login = true;
                    }
                }

                if (cookie2.length() < 9 && cookie.length() < 9) {

                    Login = true;
                    showMessage = true;
                    startMessage = clock.millis();
                    dialogText = "ID utilizator sau parola incorecta!";
                }
            }
        } else {

            Register = true;
            for (int i = 1; i <= 6; i++) {
                if (Tbox[i].getText().length() < 1) Register = false;
            }
            for (int i = 2; i <= 3; i++) {
                if (Pbox[i].getPassword().length < 1) Register = false;
            }
            if (Register == true) {
                String clasa = Tbox[4].getText();

                int grade = 0;
                String letter = String.valueOf(clasa.charAt(clasa.length() - 1));
                for (int i = clasa.length() - 1; i >= 0; i--) {
                    if (clasa.charAt(i) >= '0' && clasa.charAt(i) <= '9') {
                        grade = grade * 10 + clasa.charAt(i) - '0';
                    }
                }
                String password = new String(Pbox[2].getPassword());

                Student elev2 = new Student(Tbox[1].getText(), Tbox[3].getText(), Tbox[2].getText(), Tbox[5].getText(), grade, letter, "active", new Account(Tbox[6].getText(), password));
                DatabaseHandler Maner = new DatabaseHandler();

                try {
                    Maner.registerStudent(elev2);
                } catch (IOException e) {

                }
                Bpressed[1] = true;
                Bpressed[2] = false;
                showMessage = true;
                startMessage = clock.millis();
                dialogText = "Inregistrare reusita!";
            }


        }
    }

    public static void makeSubmitButton(Graphics g, int x, int y, int width, int height, String Name) {

        Menu.X_hovered = MouseInfo.getPointerInfo().getLocation().x - Menu.XFrame - Setari.FrameBarX;
        Menu.Y_hovered = MouseInfo.getPointerInfo().getLocation().y - Menu.YFrame - Setari.FrameBarY;

        Submithovered = false;
        if (Menu.X_hovered >= x && Menu.X_hovered <= x + width && Menu.Y_hovered >= y && Menu.Y_hovered <= y + height)
            Submithovered = true;

        if (((Menu.MousePressed == false && copyMousePressed2 == true && Submithovered == true) || Menu.ENTER == true) && Login == true) {

            USERNAME=null;
            PASSWORD=null;
            checkData(g);
            Menu.ENTER = false;
        }

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
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
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
        password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Menu.ENTER = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Menu.ENTER = false;
                }
            }
        });
        Window.frame.add(username);
        Window.frame.add(password);
    }

    public static void register() {

        Font f = new Font("Consolas", Font.PLAIN, FontSize / 3);
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 5; j++) {
                if (i == 1) {
                    Tbox[j] = new JTextField(30);
                    Tbox[j].setBounds(ScreenWidth / 2 - ScreenWidth / 8, ScreenHeight / 4 + ScreenHeight / 40 + j * ScreenHeight / 13, ScreenWidth / 12, ScreenHeight / 30);
                    Tbox[j].setFont(f);
                    Window.frame.add(Tbox[j]);
                    Tbox[j].setVisible(false);
                } else if (i == 2 && j <= 1) {
                    Tbox[5 + j] = new JTextField(30);
                    Tbox[5 + j].setBounds(ScreenWidth * 3 / 4 - ScreenWidth / 8, ScreenHeight / 4 + ScreenHeight / 40 + j * ScreenHeight / 13, ScreenWidth / 12, ScreenHeight / 30);
                    Tbox[5 + j].setFont(f);
                    Window.frame.add(Tbox[5 + j]);
                    Tbox[5 + j].setVisible(false);
                } else if (i == 2 && j < 4) {
                    Pbox[j] = new JPasswordField(30);
                    Pbox[j].setBounds(ScreenWidth * 3 / 4 - ScreenWidth / 8, ScreenHeight / 4 + ScreenHeight / 40 + j * ScreenHeight / 13, ScreenWidth / 12, ScreenHeight / 30);
                    Pbox[j].setFont(f);
                    Window.frame.add(Pbox[j]);
                    Pbox[j].setVisible(false);
                }
            }
        }

    }

    public static void makeOptionsButton(Graphics g, int x, int y, int width, int height, String Name, int i) {

        Menu.X_hovered = MouseInfo.getPointerInfo().getLocation().x - Menu.XFrame - Setari.FrameBarX;
        Menu.Y_hovered = MouseInfo.getPointerInfo().getLocation().y - Menu.YFrame - Setari.FrameBarY;

        if (Menu.X_hovered >= x && Menu.X_hovered <= x + width && Menu.Y_hovered >= y && Menu.Y_hovered <= y + height)
            Ohovered = true;

        if (Menu.MousePressed == true && Ohovered == true) {
            if (copyMousePressed3 != Menu.MousePressed && copyMousePressed3 == false) {
                Setari.ButtonSound("button_click.wav");
            }
            g.setColor(new Color(55, 53, 53));
            g.fill3DRect(x, y, width, height, true);
            g.setColor(new Color(220, 220, 220));
            //g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, FontSize / 3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

            if (optionsShowed[1] == true) {
                //GRADE = i + 8;
                Tbox[4].setText(gradeName[i]);
            } else if (optionsShowed[2] == true) {
                // GENDER = i;
                Tbox[5].setText(genderName[i]);
            }

        } else if (Ohovered == true) {

            if (Menu.MousePressed == false && copyMousePressed == true) System.exit(0);
            g.setColor(new Color(231, 198, 63));
            g.fill3DRect(x, y, width, height, true);
            g.setColor(new Color(255, 231, 63));
            //   g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, FontSize / 3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(255, 231, 63));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else {
            g.setColor(new Color(255, 253, 253));
            g.fill3DRect(x, y, width, height, false);
            g.setColor(new Color(0, 0, 0));
            // g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, FontSize / 3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }

        Ohovered = false;
    }

    public static void createOptions(Graphics g, int x, int y, int NrOptions, String Name[], int i) {

        int X_hovered = MouseInfo.getPointerInfo().getLocation().x - Menu.XFrame - Setari.FrameBarX;
        int Y_hovered = MouseInfo.getPointerInfo().getLocation().y - Menu.YFrame - Setari.FrameBarY;

        if (X_hovered >= x && X_hovered <= x + ScreenWidth / 12 && Y_hovered >= y && Y_hovered <= y + ScreenHeight / 30) {

            optionsShowed[i] = true;
        }
        if (optionsShowed[1] == true) {
            optionsShowed[2] = false;
            Tbox[5].setVisible(false);
        }
        if (optionsShowed[3] == true) {
            Pbox[3].setVisible(false);
        }
        if (optionsShowed[i] == true) {
            if (X_hovered >= x && X_hovered <= x + ScreenWidth / 12 && Y_hovered >= y && Y_hovered <= y + ScreenHeight / 30 * (NrOptions + 1)) {
                for (int j = 1; j <= NrOptions; j++) {

                    makeOptionsButton(g, x, y + j * ScreenHeight / 30, ScreenWidth / 12, ScreenHeight / 30, Name[j], j);
                }
                copyMousePressed3 = Menu.MousePressed;
            } else {
                optionsShowed[i] = false;
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
            makeSubmitButton(g, ScreenWidth * 3 / 4 - ScreenWidth / 6, ScreenHeight * 3 / 4 - ScreenHeight / 7, ScreenWidth / 10, ScreenHeight / 15, "Inregistrare");
    }

    public static void updateTextboxes(Graphics g) {

        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 5; j++) {
                if (i == 1) {
                    Tbox[j].setVisible(false);
                } else if (i == 2 && j <= 1) {
                    Tbox[j + 5].setVisible(false);
                } else if (i == 2 && j < 4) {
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
            for (int j = 1; j <= 5; j++) {
                if (i == 1) {
                    g.drawString(String.valueOf(BoxName[j]), ScreenWidth / 2 - ScreenWidth / 7 - metricsx.stringWidth(String.valueOf(BoxName[j])), ScreenHeight / 4 + ScreenHeight / 40 + j * ScreenHeight / 13 + metricsy.getHeight() / 2 * 4 / 3);
                    Tbox[j].setVisible(true);
                    Tbox[j].setBounds(ScreenWidth / 2 - ScreenWidth / 8, ScreenHeight / 4 + ScreenHeight / 50 + j * ScreenHeight / 13, ScreenWidth / 12, ScreenHeight / 30);
                    // Tbox[j].revalidate();
                    // Tbox[j].repaint();
                } else if (i == 2 && j <= 1) {
                    Tbox[5 + j].setVisible(true);
                    g.drawString(String.valueOf(BoxName[5 + j]), ScreenWidth * 3 / 4 - ScreenWidth / 7 - metricsx.stringWidth(String.valueOf(BoxName[5 + j])), ScreenHeight / 4 + ScreenHeight / 40 + j * ScreenHeight / 13 + metricsy.getHeight() / 2 * 4 / 3);
                    Tbox[5 + j].setBounds(ScreenWidth * 3 / 4 - ScreenWidth / 8, ScreenHeight / 4 + ScreenHeight / 50 + j * ScreenHeight / 13, ScreenWidth / 12, ScreenHeight / 30);

                    //Tbox[j+5].revalidate();
                    // Tbox[j+5].repaint();
                } else if (i == 2 && j < 4) {
                    g.drawString(String.valueOf(PName[j]), ScreenWidth * 3 / 4 - ScreenWidth / 7 - metricsx.stringWidth(String.valueOf(PName[j])), ScreenHeight / 4 + ScreenHeight / 40 + j * ScreenHeight / 13 + metricsy.getHeight() / 2 * 4 / 3);
                    Pbox[j].setVisible(true);
                    Pbox[j].setBounds(ScreenWidth * 3 / 4 - ScreenWidth / 8, ScreenHeight / 4 + ScreenHeight / 50 + j * ScreenHeight / 13, ScreenWidth / 12, ScreenHeight / 30);
                    //Pbox[j].revalidate();
                    //Pbox[j].repaint();
                }
            }
        }

    }

    public static void callOptions(Graphics g) {

        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 5; j++) {
                if (i == 1 && j == 4)
                    createOptions(g, ScreenWidth / 2 - ScreenWidth / 8, ScreenHeight / 4 + ScreenHeight / 50 + j * ScreenHeight / 13, 4, gradeName, 1);
                else if (i == 1 && j == 5)
                    createOptions(g, ScreenWidth / 2 - ScreenWidth / 8, ScreenHeight / 4 + ScreenHeight / 50 + j * ScreenHeight / 13, 2, genderName, 2);
            }
        }

    }

    public static void showDialog(Graphics g, String Message) {

        if (showMessage == true && clock.millis() - startMessage < 1000) {
            DialogBox(g, Message, Menu.ScreenWidth / 2, Menu.ScreenHeight / 6,FontSize/2);
        } else {
            showMessage = false;
            startMessage = 0;
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
            callOptions(g);
        }
        showDialog(g, dialogText);
    }

    public static void initilaizeDimensions() {

        ScreenWidth = Menu.ScreenWidth;
        ScreenHeight = Menu.ScreenHeight;
        FontSize = 60 * ScreenWidth / 1920*(50+size)/100;
        if (60 * ScreenHeight / 1080 < FontSize) FontSize = 60 * ScreenHeight / 1080*(50+size)/100;
    }

    public static void MouseState() {

        Menu.X_hovered = MouseInfo.getPointerInfo().getLocation().x - Menu.XFrame - Setari.FrameBarX;
        Menu.Y_hovered = MouseInfo.getPointerInfo().getLocation().y - Menu.YFrame - Setari.FrameBarY;

        if (Menu.Y_hovered >= ScreenHeight / 4 && Menu.Y_hovered <= ScreenHeight / 4 + ScreenHeight / 12) {

            if (Menu.X_hovered >= ScreenWidth / 4 && Menu.X_hovered <= ScreenWidth * 3 / 4) {

                Bhovered[(Menu.X_hovered - ScreenWidth / 4) / (ScreenWidth / 4) + 1] = true;
            }

        }
    }

    public static void Run() {

        initilaizeDimensions();
        initializeButtons();
        MouseState();
    }

}
