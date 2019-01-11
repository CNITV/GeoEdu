package VianuEdu.GUI;

import VianuEdu.backend.Identification.Student;
import VianuEdu.backend.TestLibrary.AnswerSheet;
import VianuEdu.backend.TestLibrary.Grade;
import VianuEdu.backend.TestLibrary.Question;
import VianuEdu.backend.TestLibrary.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class Catalog {

    public static String Class[] = new String[101];
    public static String Letter[] = new String[101];
    public static String Questions[] = new String[101];
    public static String Answers[][] = new String[101][101];
    public static String StudentID[] = new String[101];
    public static String StudentName[] = new String[101];
    public static String currentTestID;
    public static int currentClass;
    public static int currentLetter;
    public static int NrStudents2 = 0;
    public static Image img[] = new Image[101];
    public static BufferedImage lb;
    public static int Marks[] = new int[101];
    public static Double Grades[][] = new Double[101][101];
    public static int LetterNumber = 9;
    public static int NrQuestions;
    public static int MPQuestions;
    public static int NrStudents=-1;
    public static int NrGrades = 4;
    public static int copyScreenWidth = Menu.ScreenWidth;
    public static int currentQuestion = 1;
    public static int currentStudent = 0;
    public static ArrayList<String> uncorrectedTestsID[][] = new ArrayList[101][101];
    public static ArrayList<String> uncorrectedTestsName[][] = new ArrayList[101][101];
    public static boolean correctTest = true;
    public static boolean catalog = false;
    public static boolean copyMousePressed = Menu.MousePressed;
    public static boolean TestChosen = false;
    public static boolean classChosen = false;
    public static boolean NextQuestion = false;
    public static JLabel label = new JLabel();
    public static JTextArea essay = new JTextArea(10,20);
    public static JTextField mark = new JTextField();
    public static JScrollPane scroll = new JScrollPane(essay);
    public static Test test = null;

    public static void drawBackground(Graphics g) {

        g.drawImage(UserImput.background, (Menu.ScreenWidth - UserImput.background.getWidth(null)) / 2, (Menu.ScreenHeight - UserImput.background.getHeight(null)) / 2, null);
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Menu.ScreenWidth, Menu.ScreenHeight);
        g.setColor(Color.BLACK);
        g.fillRect(Menu.ScreenWidth / 20, Menu.ScreenHeight / 10, Menu.ScreenWidth * 9 / 10, Menu.ScreenHeight * 9 / 10 - Menu.ScreenHeight / 20);
        g.setColor(Color.WHITE);
        g.drawRect(Menu.ScreenWidth / 20, Menu.ScreenHeight / 10, Menu.ScreenWidth * 9 / 10, Menu.ScreenHeight * 9 / 10 - Menu.ScreenHeight / 20);
    }

    public static void drawPanelButton(Graphics g, int x, int y, int width, int height, String Name) {

        if (Menu.X_hovered > x && Menu.X_hovered < x + width && Menu.Y_hovered > y && Menu.Y_hovered < y + height && Menu.MousePressed ) {

            if (Name.equals("Teste Necorectate")) {
                correctTest = true;
                catalog = false;
                currentClass=0;
                currentLetter=0;
                classChosen=false;
                if(copyMousePressed == false && TestChosen == false){
                    for(int i=0;i<=NrQuestions;i++){
                        Marks[i]=0;
                    }
                }
                findUncorrectedTests();
            } else if (Name.equals("Catalog")) {
                correctTest = false;
                catalog = true;
                essay.setVisible(false);
                scroll.setVisible(false);
                if(currentClass!=0&&currentLetter!=0){
                    findClass(currentClass,Letter[currentLetter]);
                }
            }
            if (copyMousePressed == false) {
                Setari.ButtonSound("button_click.wav");
            }
        }
        if ((correctTest  && Name.equals("Teste Necorectate")) || (catalog  && Name.equals("Catalog"))) {
            g.setColor(new Color(0, 0, 0));
            g.fill3DRect(x, y, width, height, true);

            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);


        } else if (Menu.X_hovered > x && Menu.X_hovered < x + width && Menu.Y_hovered > y && Menu.Y_hovered < y + height) {

            g.setColor(new Color(231, 198, 63));
            g.fill3DRect(x, y, width, height, false);

            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(255, 231, 63));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else {
            g.setColor(new Color(235, 233, 233));
            g.fill3DRect(x, y, width, height, false);

            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }

    }

    public static void generatePanelButtons(Graphics g) {

        drawPanelButton(g, Menu.ScreenWidth / 20, Menu.ScreenHeight / 20, Menu.ScreenWidth * 9 / 20, Menu.ScreenHeight / 15, "Teste Necorectate");
        drawPanelButton(g, Menu.ScreenWidth * 10 / 20, Menu.ScreenHeight / 20, Menu.ScreenWidth * 9 / 20, Menu.ScreenHeight / 15, "Catalog");

    }

    public static void drawPlannedTest(Graphics g, int x, int y, int width, int height, int i, int j, int k) {

        int size = width / uncorrectedTestsID[i][j].get(k).length()/2;

        Font small = new Font("Calibri", Font.PLAIN, size);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);

        if (Menu.X_hovered > x && Menu.X_hovered < x + width && Menu.Y_hovered > y && Menu.Y_hovered < y + metricsy.getHeight() && Menu.MousePressed ) {
            g.setColor(Color.DARK_GRAY);
            g.fill3DRect(x, y, width, metricsy.getHeight(), true);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, width, metricsy.getHeight());


            g.setFont(small);
            g.drawString(String.valueOf(uncorrectedTestsName[i][j].get(k)), x + width / 2 - metricsx.stringWidth(String.valueOf(uncorrectedTestsName[i][j].get(k))) / 2, y + metricsy.getHeight() / 2 + metricsy.getHeight() / 4);

        }
        else if (Menu.X_hovered > x && Menu.X_hovered < x + width && Menu.Y_hovered > y && Menu.Y_hovered < y + metricsy.getHeight()) {

            if(copyMousePressed){
                Setari.ButtonSound("button_click.wav");
                TestChosen = true;
                currentTestID = uncorrectedTestsID[i][j].get(k);
                findCorrectioncontent(currentTestID);
            }
            small = new Font("Calibri", Font.BOLD, size);
            FontMetrics metricsy1 = g.getFontMetrics(small);
            FontMetrics metricsx1 = g.getFontMetrics(small);
            g.setColor(new Color(255,214,71));
            g.fill3DRect(x, y, width, metricsy1.getHeight(), true);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, width, metricsy1.getHeight());

            g.setFont(small);
            g.drawString(String.valueOf(uncorrectedTestsName[i][j].get(k)), x + width / 2 - metricsx1.stringWidth(String.valueOf(uncorrectedTestsName[i][j].get(k))) / 2, y + metricsy1.getHeight() / 2 + metricsy1.getHeight() / 4);
        }
        else {
            g.setColor(Color.GRAY);
            g.fill3DRect(x, y, width, metricsy.getHeight(), true);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, width, metricsy.getHeight());


            g.setFont(small);
            g.drawString(String.valueOf(uncorrectedTestsName[i][j].get(k)), x + width / 2 - metricsx.stringWidth(String.valueOf(uncorrectedTestsName[i][j].get(k))) / 2, y + metricsy.getHeight() / 2 + metricsy.getHeight() / 4);
        }

    }

    public static void drawBox(Graphics g, int x, int y, int width, int height, int letter, int clasa){

        g.setColor(new Color(40,40,40));
        g.fillRect(x,y,width,height);
        if(letter == 0 && clasa == 0){
            g.setColor(Color.WHITE);
            g.drawRect(x,y,width,height);
        }
        else if(letter == 0){

            String grade = Class[clasa];
            g.setColor(Color.WHITE);
            g.drawRect(x,y,width,height);

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 2);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setFont(small);
            g.drawString(String.valueOf(grade), x + width / 2 - metricsx.stringWidth(String.valueOf(grade)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }
        else if(clasa == 0){

            String litera = Letter[letter];
            g.setColor(Color.WHITE);
            g.drawRect(x,y,width,height);

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 2);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setFont(small);
            g.drawString(String.valueOf(litera), x + width / 2 - metricsx.stringWidth(String.valueOf(litera)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }
        else{

            g.setColor(Color.WHITE);
            g.drawRect(x,y,width,height);

            try {
                for (int i = 0; i < uncorrectedTestsID[letter][clasa].size(); i++) {
                    drawPlannedTest(g, x, y + i * height / uncorrectedTestsID[letter][clasa].size(), width, height / uncorrectedTestsID[letter][clasa].size()/12, letter, clasa, i);
                }
            }catch (java.lang.NullPointerException e){

            }
        }

    }

    public static void drawCorrectTable(Graphics g, int StartX, int StartY, int Width, int Height){

        int width = Width/(5);
        int height = Height/(11);
        Class[1] = "Clasa a 9-a";
        Class[2] = "Clasa a 10-a";
        Class[3] = "Clasa a 11-a";
        Class[4] = "Clasa a 12-a";
        Letter[1] = "A";
        Letter[2] = "B";
        Letter[3] = "C";
        Letter[4] = "D";
        Letter[5] = "E";
        Letter[6] = "F";
        Letter[7] = "G";
        Letter[8] = "H";
        Letter[9] = "I";
        for(int i = 0;i<=LetterNumber;i++){
            for(int j = 0;j<=4;j++){
                drawBox(g,StartX+j*width,StartY+i*height,width,height,i,j);
            }
        }
    }

    public static void drawStudentBox(Graphics g, int x, int y, int width, int height, String Name, int st){
        if(st == currentStudent){
            g.setColor(Color.BLACK);
            g.fill3DRect(x, y, width, height, false);
            g.setColor(Color.WHITE);
            g.drawRect(x,y,width,height);
            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize / 3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }
        else {
            g.setColor(new Color(220,220,220));
            g.fill3DRect(x, y, width, height, false);
            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize / 3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }

    }

    public static void drawStudentMap(Graphics g, int x, int y, int width, int height){

        g.setColor(Color.GRAY);
        g.fill3DRect(x,y,width,height,true);
        g.setColor(Color.WHITE);
        g.drawRect(x,y,width,height);
        int BoxHeight = height/2;
        if(BoxHeight>Menu.ScreenHeight/16)BoxHeight = Menu.ScreenHeight/16;
        for(int i=0;i<NrStudents2;i++){
            drawStudentBox(g,x,y+i*BoxHeight,width,BoxHeight,StudentName[i],i);
        }
    }

    public static BufferedImage labeltoImage(Component component) {
        BufferedImage img = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = img.getGraphics();
        g2.setColor(component.getForeground());
        g2.setFont(component.getFont());
        component.paintAll(g2);
        Rectangle region = new Rectangle(0, 0, img.getWidth(), img.getHeight());
        return img.getSubimage(region.x, region.y, region.width, region.height);
    }

    public static void drawQuestion(Graphics g, String question) {
        Font f = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 2);
        label = new JLabel(question, SwingConstants.CENTER);
        label.setLayout(null);
        label.setBounds(Menu.ScreenWidth / 4, Menu.ScreenHeight/12, Menu.ScreenWidth / 2, Menu.ScreenHeight / 2);
        label.setSize(Menu.ScreenWidth / 2, Menu.ScreenHeight / 4);
        if (NextQuestion == false || Menu.ScreenWidth != copyScreenWidth) {
            label.setFont(f);
            label.setForeground(new Color(255, 255, 255));
            essay.setText(Answers[currentStudent][currentQuestion]);
            Window.frame.add(label);
            lb = labeltoImage(label);
            NextQuestion = true;
            mark.setText(String.valueOf(Marks[currentQuestion]));
        }
        copyScreenWidth = Menu.ScreenWidth;
        if (NextQuestion ) g.drawImage(lb, label.getX(), label.getY(), null);
    }

    public static void drawMark(Graphics g){

        int x = Menu.ScreenWidth*31/40;
        int y = Menu.ScreenHeight/2;

        g.setColor(Color.WHITE);
        g.drawRect(x-Menu.ScreenWidth/60,y-Menu.ScreenWidth/20,Menu.ScreenWidth/30+Menu.ScreenWidth/20,Menu.ScreenWidth/15+Menu.ScreenWidth/20);

        Font f = new Font("Calibri", Font.PLAIN, UserImput.FontSize *2/3);
        FontMetrics metricsy = g.getFontMetrics(f);
        FontMetrics metricsx = g.getFontMetrics(f);
        g.setFont(f);
        g.drawString(String.valueOf("Punctaj:"), x + Menu.ScreenWidth/40-metricsx.stringWidth("Punctaj:")/2, y -Menu.ScreenWidth/35);
        f = new Font("Calibri", Font.PLAIN, UserImput.FontSize /2);
        metricsx = g.getFontMetrics(f);
        g.setFont(f);
        g.drawString(String.valueOf("(1-10)"), x + Menu.ScreenWidth/40-metricsx.stringWidth("(1-10)")/2, y -Menu.ScreenWidth/100);
        f = new Font("Calibri", Font.PLAIN, UserImput.FontSize *3/4);
        metricsx = g.getFontMetrics(f);
        g.setFont(f);
        mark.setBounds(x,y,Menu.ScreenWidth/20,Menu.ScreenWidth/20);
        mark.setFont(f);
        mark.setHorizontalAlignment(0);
        mark.setVisible(true);

    }

    public static void drawEssay(Graphics g,int x, int y, int width, int height){

        g.setColor(Color.BLACK);
        g.fill3DRect(x-width/40,y-width/10,width+width/20,height+width/20+width*3/40,true);
        g.setColor(Color.WHITE);
        g.drawRect(x-width/40,y-width/10,width+width/20,height+width/20+width*3/40);
        Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 3);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setFont(small);
        g.drawString(String.valueOf("Raspunsul elevului / elevei:"), x + width /40, y -width/30);
        g.drawRect(x,y,width,height);
        if(Setari.SettingsOn==false) essay.setVisible(true);
        Font f = new Font("Calibri", Font.PLAIN, UserImput.FontSize * 3 / 8);
        essay.setLineWrap(true);
        scroll.setBackground(Color.LIGHT_GRAY);
        essay.setFont(f);
        scroll.setWheelScrollingEnabled(true);
        scroll.setAutoscrolls(true);
        scroll.setWheelScrollingEnabled(true);
        essay.setEditable(false);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(x,y,width,height);
        if(Setari.SettingsOn==false)scroll.setVisible(true);
        else scroll.setVisible(false);
    }

    public static void drawImage(Graphics g){

        try {
            g.drawImage(img[currentQuestion],(Menu.ScreenWidth-img[currentQuestion].getWidth(null))/2,Menu.ScreenHeight/4+Menu.ScreenHeight/20,null);
        }
        catch (java.lang.NullPointerException e){

        }

    }

    public static void drawNextButton(Graphics g) {

        int x = Menu.X_hovered;
        int y = Menu.Y_hovered;
        int ButtonHeight = Menu.ScreenHeight / 14;
        int ButtonWidth = Menu.ScreenWidth / 60;
        int ButtonX = Menu.ScreenWidth * 2 / 3 + Menu.ScreenWidth / 20;
        int ButtonY = Menu.ScreenHeight * 2 / 3 - Menu.ScreenHeight / 40;

        if (Menu.MousePressed  && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(200, 200, 200));
            if (copyMousePressed == false) Setari.ButtonSound("button_click.wav");
        } else if (x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(85, 64, 11));
            if (copyMousePressed ) {
                Marks[currentQuestion] = Integer.valueOf(mark.getText());
                currentQuestion++;
                NextQuestion = false;
            }
        } else {
            g.setColor(Color.BLACK);
        }
        g.fill3DRect(ButtonX - ButtonWidth / 2, ButtonY - ButtonHeight / 4, ButtonWidth * 2 + ButtonHeight / 10, ButtonHeight * 3 / 2, true);
        if (Menu.MousePressed  && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(10, 10, 10));
        } else if (x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(251, 214, 71));
        } else {
            g.setColor(Color.WHITE);
        }

        for (int i = 0; i <= ButtonWidth / 4; i++) {
            g.drawLine(ButtonX + i, ButtonY, ButtonX + ButtonWidth + i, ButtonY + ButtonHeight / 2);
            g.drawLine(ButtonX + ButtonWidth + i, ButtonY + ButtonHeight / 2, ButtonX + i, ButtonY + ButtonHeight);
        }


    }

    public static void drawPreviousButton(Graphics g) {
        int x = Menu.X_hovered;
        int y = Menu.Y_hovered;
        int ButtonHeight = Menu.ScreenHeight / 14;
        int ButtonWidth = Menu.ScreenWidth / 60;
        int ButtonX = Menu.ScreenWidth - Menu.ScreenWidth * 2 / 3 - Menu.ScreenWidth / 14;
        int ButtonY = Menu.ScreenHeight * 2 / 3 - Menu.ScreenHeight / 40;

        if (Menu.MousePressed  && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(200, 200, 200));
            if (copyMousePressed == false) Setari.ButtonSound("button_click.wav");
        } else if (x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(85, 64, 11));
            if (copyMousePressed ) {
                Marks[currentQuestion] = Integer.valueOf(mark.getText());
                currentQuestion--;
                NextQuestion = false;
            }
        } else {
            g.setColor(Color.BLACK);
        }
        g.fill3DRect(ButtonX - ButtonWidth / 2, ButtonY - ButtonHeight / 4, ButtonWidth * 2 + ButtonHeight / 10, ButtonHeight * 3 / 2, true);
        if (Menu.MousePressed  && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(10, 10, 10));
        } else if (x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(251, 214, 71));
        } else {
            g.setColor(Color.WHITE);
        }

        for (int i = 0; i <= ButtonWidth / 4; i++) {
            g.drawLine(ButtonX + ButtonWidth + i, ButtonY, ButtonX + i, ButtonY + ButtonHeight / 2);
            g.drawLine(ButtonX + i, ButtonY + ButtonHeight / 2, ButtonX + ButtonWidth + i, ButtonY + ButtonHeight);
        }

    }

    public static void drawMap(Graphics g, int content) {

        int width = (Menu.ScreenWidth - 2 * GeoEduMenu.PanelWidth * 5 / 4) / content;
        if (width > Menu.ScreenWidth / 50) width = Menu.ScreenWidth / 50;
        int height = width;
        int x = (Menu.ScreenWidth - content * width) / 2;
        int y = Menu.ScreenHeight / 7;

        for (int i = 0; i < content; i++) {
            drawCell(g, x + i * width, y, width, height, i + 1);
        }

    }

    public static void drawCell(Graphics g, int x, int y, int width, int height, int Name) {

        int xm = Menu.X_hovered;
        int ym = Menu.Y_hovered;


        if (xm > x && xm < x + width && ym > y && ym < y + height && Menu.MousePressed ) {

            if (copyMousePressed == false){

                Setari.ButtonSound("button_click.wav");

            }
        } else if (xm > x && xm < x + width && ym > y && ym < y + height) {


            if (copyMousePressed ) {
                System.out.println("daaaa");
                Marks[currentQuestion] = Integer.valueOf(mark.getText());
                currentQuestion = Name;
                NextQuestion = false;

            }
        }
        if (Name == currentQuestion) {
            g.setColor(new Color(60, 60, 60));
            g.fill3DRect(x, y, width, height, true);
            g.setColor(Color.LIGHT_GRAY);
            g.fill3DRect(x + 2, y + 2, width - 4, height - 4, true);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, width, height);

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else if (xm > x && xm < x + width && ym > y && ym < y + height) {
            g.setColor(new Color(255, 230, 31));
            g.fill3DRect(x, y, width, height, false);
            g.setColor(Color.BLACK);
            g.fill3DRect(x + 2, y + 2, width - 4, height - 4, false);

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(255, 231, 170));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        } else {
            g.setColor(new Color(60, 60, 60));
            g.fill3DRect(x, y, width, height, false);
            g.setColor(Color.BLACK);
            g.fill3DRect(x + 2, y + 2, width - 4, height - 4, false);

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }

    }

    public static void drawNextStudent(Graphics g, int x, int y, int width, int height, String Name) {

        int xm = Menu.X_hovered;
        int ym = Menu.Y_hovered;


        if (xm > x && xm < x + width && ym > y && ym < y + height && Menu.MousePressed ) {

            if (copyMousePressed == false) Setari.ButtonSound("button_click.wav");
        } else if (xm > x && xm < x + width && ym > y && ym < y + height) {
            if (copyMousePressed ) {
                Marks[currentQuestion] = Integer.valueOf(mark.getText());
                NextQuestion = false;
                sendData();
                currentStudent++;
                currentQuestion = 1;
                for(int i=1;i<=NrQuestions;i++)Marks[i]=0;
            }
        }
        if (xm > x && xm < x + width && ym > y && ym < y + height && Menu.MousePressed ) {
            g.setColor(new Color(60, 60, 60));
            g.fill3DRect(x, y, width, height, true);
            g.setColor(Color.LIGHT_GRAY);
            g.fill3DRect(x + 2, y + 2, width - 4, height - 4, true);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, width, height);

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize /3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else if (xm > x && xm < x + width && ym > y && ym < y + height) {
            g.setColor(new Color(255, 230, 31));
            g.fill3DRect(x, y, width, height, false);
            g.setColor(Color.BLACK);
            g.fill3DRect(x + 2, y + 2, width - 4, height - 4, false);

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize /3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(255, 231, 170));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        } else {
            g.setColor(new Color(60, 60, 60));
            g.fill3DRect(x, y, width, height, false);
            g.setColor(Color.BLACK);
            g.fill3DRect(x + 2, y + 2, width - 4, height - 4, false);

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize /3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }

    }

    public static void drawExitStudent(Graphics g, int x, int y, int width, int height, String Name) {

        int xm = Menu.X_hovered;
        int ym = Menu.Y_hovered;


        if (xm > x && xm < x + width && ym > y && ym < y + height && Menu.MousePressed ) {

            if (copyMousePressed == false) Setari.ButtonSound("button_click.wav");
        } else if (xm > x && xm < x + width && ym > y && ym < y + height) {
            if (copyMousePressed ) {
                Marks[currentQuestion] = Integer.valueOf(mark.getText());
                NextQuestion = false;
                sendData();
                currentStudent=0;
                currentQuestion = 1;
                TestChosen = false;
                deleteData();
                findUncorrectedTests();
                for(int i=1;i<=NrQuestions;i++)Marks[i]=0;
            }
        }
        if (xm > x && xm < x + width && ym > y && ym < y + height && Menu.MousePressed ) {
            g.setColor(new Color(60, 60, 60));
            g.fill3DRect(x, y, width, height, true);
            g.setColor(Color.LIGHT_GRAY);
            g.fill3DRect(x + 2, y + 2, width - 4, height - 4, true);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, width, height);

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize /3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else if (xm > x && xm < x + width && ym > y && ym < y + height) {
            g.setColor(new Color(255, 230, 31));
            g.fill3DRect(x, y, width, height, false);
            g.setColor(Color.BLACK);
            g.fill3DRect(x + 2, y + 2, width - 4, height - 4, false);

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize /3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(255, 231, 170));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        } else {
            g.setColor(new Color(60, 60, 60));
            g.fill3DRect(x, y, width, height, false);
            g.setColor(Color.BLACK);
            g.fill3DRect(x + 2, y + 2, width - 4, height - 4, false);

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize /3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }

    }

    public static void drawCorrectEditor(Graphics g){

        drawStudentMap(g,Menu.ScreenWidth/16,Menu.ScreenHeight/6,Menu.ScreenWidth/8,Menu.ScreenHeight*3/4);
        drawQuestion(g,Questions[currentQuestion]);
        drawImage(g);
        drawMap(g,NrQuestions);
        drawMark(g);
        drawEssay(g,Menu.ScreenWidth/3,Menu.ScreenHeight*3/5,Menu.ScreenWidth/3,Menu.ScreenHeight/4);
        if(currentQuestion>1)drawPreviousButton(g);
        if(currentQuestion<NrQuestions)drawNextButton(g);
        if(currentQuestion==NrQuestions && currentStudent<NrStudents2-1){
            drawNextStudent(g,Menu.ScreenWidth*3/4,Menu.ScreenHeight*2/3,Menu.ScreenWidth/12,Menu.ScreenHeight/15,"Incarca punctaje");
        }
        if(currentQuestion==NrQuestions){
            drawExitStudent(g,Menu.ScreenWidth*3/4,Menu.ScreenHeight*2/3+Menu.ScreenHeight/10,Menu.ScreenWidth/9,Menu.ScreenHeight/15,"Incarca punctaje si iesi");
        }


    }

    public static void drawCatalogBox(Graphics g, int x, int y, int width, int height, int letter, int clasa){

        if(Menu.X_hovered>x&&Menu.X_hovered<x+width&&Menu.Y_hovered>y&&Menu.Y_hovered<y+height &&Menu.MousePressed){

            g.setColor(Color.BLACK);
            g.fill3DRect(x,y,width,height,true);
            g.setColor(Color.BLACK);
            g.fill3DRect(x+3,y+3,width-6,height-6,true);
            g.setColor(Color.WHITE);
            g.drawRect(x,y,width,height);

            Font small = new Font("Calibri", Font.BOLD, UserImput.FontSize );
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(Color.WHITE);
            g.setFont(small);
            g.drawString(String.valueOf(Class[clasa]+" "+Letter[letter]), x + width / 2 - metricsx.stringWidth(String.valueOf(Class[clasa]+" "+Letter[letter])) / 2, y + height / 2 + metricsy.getHeight() / 4);


        }
        else if(Menu.X_hovered>x&&Menu.X_hovered<x+width&&Menu.Y_hovered>y&&Menu.Y_hovered<y+height){

            if(copyMousePressed){
                Setari.ButtonSound("button_click.wav");
                classChosen = true;
                currentClass = clasa;
                currentLetter = letter;
                findClass(clasa+8,Letter[letter]);
            }

            g.setColor(new Color(241,207,98));
            g.fill3DRect(x,y,width,height,false);
            g.setColor(new Color(241,207,98));
            g.fill3DRect(x+3,y+3,width-6,height-6,true);
            g.setColor(Color.BLACK);
            g.drawRect(x,y,width,height);

            Font small = new Font("Calibri", Font.BOLD, UserImput.FontSize );
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Class[clasa]+" "+Letter[letter]), x + width / 2 - metricsx.stringWidth(String.valueOf(Class[clasa]+" "+Letter[letter])) / 2, y + height / 2 + metricsy.getHeight() / 4);

        }
        else {
            g.setColor(Color.GRAY);
            g.fill3DRect(x, y, width, height, false);
            g.setColor(Color.LIGHT_GRAY);
            g.fill3DRect(x + 3, y + 3, width - 6, height - 6, true);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);

            Font small = new Font("Calibri", Font.BOLD, UserImput.FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Class[clasa] + " " + Letter[letter]), x + width / 2 - metricsx.stringWidth(String.valueOf(Class[clasa] + " " + Letter[letter])) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }

    }

    public static void drawCatalogTable(Graphics g, int StartX, int StartY, int Width, int Height){

        int width = Width/(4);
        int height = Height/(10);
        Class[1] = "IX";
        Class[2] = "X";
        Class[3] = "XI";
        Class[4] = "XII";
        Letter[1] = "A";
        Letter[2] = "B";
        Letter[3] = "C";
        Letter[4] = "D";
        Letter[5] = "E";
        Letter[6] = "F";
        Letter[7] = "G";
        Letter[8] = "H";
        Letter[9] = "I";
        for(int i = 1;i<=LetterNumber;i++){
            for(int j = 1;j<=4;j++){
                drawCatalogBox(g,StartX+(j-1)*width,StartY+(i-1)*height,width,height,i,j);
            }
        }
    }

    public static void drawCatalogCell(Graphics g, int x, int y, int width, int height, String Name){

        g.setColor(Color.WHITE);
        g.drawRect(x,y,width,height);
        Font small;
        if(Name=="Numele elevului"||Name == "Notele elevului"){
            small = new Font("Calibri", Font.BOLD, UserImput.FontSize *10/30);
        }
        else {
            small = new Font("Calibri", Font.PLAIN, UserImput.FontSize*10/30);
        }
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setFont(small);
        g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
    }

    public static void drawCatalogExit(Graphics g, int x ,int y, int width, int height, String Name){

        if (Menu.MousePressed  && Menu.X_hovered>x&&Menu.X_hovered<x+width&&Menu.Y_hovered>y&&Menu.Y_hovered<y+height) {
            if (GeoEduMenu.copyMousePressed != Menu.MousePressed && copyMousePressed == false) {
                Setari.ButtonSound("button_click.wav");
            }
            g.setColor(new Color(55, 53, 53));
            g.fillRoundRect(x, y, width, height, 15, 15);
            g.setColor(new Color(220, 220, 220));
            //g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize / 2);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else if (Menu.X_hovered>x&&Menu.X_hovered<x+width&&Menu.Y_hovered>y&&Menu.Y_hovered<y+height) {


            if(copyMousePressed ){
                for(int i=1;i<=NrStudents;i++){
                    StudentName[i]=null;
                    for(int j=0;j<=NrGrades;j++){
                        Grades[i][j]=null;
                    }
                }
                classChosen=false;
                NrStudents = -1;
            }

            g.setColor(new Color(231, 198, 63));
            g.fillRoundRect(x, y, width, height, 15, 15);
            g.setColor(new Color(255, 231, 63));
            //   g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize / 2);
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

            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize / 2);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }

    }

    public static void drawExit(Graphics g, int x ,int y, int width, int height, String Name){

        if (Menu.MousePressed  && Menu.X_hovered>x&&Menu.X_hovered<x+width&&Menu.Y_hovered>y&&Menu.Y_hovered<y+height) {
            if (GeoEduMenu.copyMousePressed != Menu.MousePressed && copyMousePressed == false) {
                Setari.ButtonSound("button_click.wav");
            }
            g.setColor(new Color(55, 53, 53));
            g.fillRoundRect(x, y, width, height, 15, 15);
            g.setColor(new Color(220, 220, 220));
            //g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize / 2);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else if (Menu.X_hovered>x&&Menu.X_hovered<x+width&&Menu.Y_hovered>y&&Menu.Y_hovered<y+height) {


            if(copyMousePressed ){
                for(int i=1;i<=NrStudents;i++){
                    StudentName[i]=null;
                }

                classChosen=false;
                TestChosen = false;
                GeoEduMenu.correctTest=false;
            }

            g.setColor(new Color(231, 198, 63));
            g.fillRoundRect(x, y, width, height, 15, 15);
            g.setColor(new Color(255, 231, 63));
            //   g.drawRect(x, y, width, height);

            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize / 2);
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

            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize / 2);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }

    }

    public static void drawCatalog(Graphics g, int x, int y, int Width, int Height, String clasa){

        int width = Width/5;
        int height;
        if (NrStudents > 0) {
            height = Height/(NrStudents+1);
        } else {
            height = Menu.ScreenHeight/10;
        }
        GeoEduMenu.drawLoadingScreen=false;
        drawCatalogCell(g, x , y , width, height, "Numele elevului");
        drawCatalogCell(g,x+width,y,4*width,height,"Notele elevului");
        for(int i=1;i<=NrStudents;i++){
                drawCatalogCell(g,x,y+i*height,width,height,StudentName[i]);
                for (int j = 0; j < NrGrades; j++) {

                    if (Grades[i-1][j] != null)
                        drawCatalogCell(g, x + (j + 1) * width, y + i * height, width, height, Grades[i-1][j].toString());
                    else {
                        drawCatalogCell(g, x + (j + 1) * width, y + i * height, width, height, "");
                    }
                }
        }

    }

    public static void Paint(Graphics g){

        drawBackground(g);
        generatePanelButtons(g);
        if(correctTest ){

            if(TestChosen == false){
                drawCorrectTable(g,Menu.ScreenWidth/6,Menu.ScreenHeight/7,Menu.ScreenWidth*2/3,Menu.ScreenHeight*5/6);
                drawExit(g,Menu.ScreenWidth*26/30,Menu.ScreenHeight*5/6,Menu.ScreenWidth/20,Menu.ScreenHeight/20,"Iesire");
            }
            else{
                drawCorrectEditor(g);
            }

        }
        else if (catalog ) {

            mark.setVisible(false);
            essay.setVisible(false);
            if(classChosen){
                drawCatalog(g,Menu.ScreenWidth/6,Menu.ScreenHeight/7,Menu.ScreenWidth*2/3,Menu.ScreenHeight*45/60,String.valueOf(Class[currentClass]+" "+Letter[currentLetter]));
                drawCatalogExit(g,Menu.ScreenWidth*26/30,Menu.ScreenHeight*5/6,Menu.ScreenWidth/20,Menu.ScreenHeight/20,"Iesire");
            }
            else{
                drawCatalogTable(g,Menu.ScreenWidth/6,Menu.ScreenHeight/7,Menu.ScreenWidth*2/3,Menu.ScreenHeight*5/6);
                drawExit(g,Menu.ScreenWidth*26/30,Menu.ScreenHeight*5/6,Menu.ScreenWidth/20,Menu.ScreenHeight/20,"Iesire");
            }

        }
        copyMousePressed = Menu.MousePressed;
    }

    public static void readImages(int n, byte[][] image){
        for(int i=1;i<=n;i++){
            try {
                img[i] = ImageIO.read(new ByteArrayInputStream(image[i]));
                img[i] = img[i].getScaledInstance(Menu.ScreenWidth/5, Menu.ScreenHeight/5,2);
            } catch (IOException e) {
                e.printStackTrace();
            }catch(java.lang.NullPointerException e){

            }
            System.out.println("scaled");
        }

    }

    public static void sendData(){


        Grade grade = null;
        try {
            System.out.println(Menu.Maner.getAnswerSheet(currentTestID,StudentID[currentStudent]));
            grade = new Grade(Menu.Maner.getAnswerSheet(currentTestID,StudentID[currentStudent]), test.getAnswerKey(), UserImput.teacher, test.getNormalQuestionPercentage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for(int i=1;i<=NrQuestions;i++){
            grade.EvaluateAnswer(MPQuestions+i, (double) Marks[i]*10);
            System.out.println(Marks[i]);
        }
        try {
            Menu.Maner.submitGrade(grade);
        } catch (IOException | IllegalAccessException | java.lang.NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void deleteData(){

        test = null;
        currentTestID = null;
        essay.setText(null);
        essay.setVisible(false);
        scroll.setVisible(false);
        mark.setText(null);
        mark.setVisible(false);
        for(int i=1;i<=NrQuestions;i++){
            Questions[i] = null;
        }
        for(int i=0;i<NrStudents2;i++){
            for(int j=1;j<=NrQuestions;j++){
                Answers[i][j]=null;
            }
            StudentID[i]=null;
            StudentName[i]=null;
        }
        NrQuestions=1;
        NrStudents2=-1;

    }

    public static void findCorrectioncontent(String testID){

        byte Img[][] = new byte[101][];

        int ind = 0;
        try {
            test = Menu.Maner.viewTest(testID, UserImput.teacher);
            ArrayList<String> student = Menu.Maner.getAnswerSheetsForTest(testID);
            NrStudents2 = student.size();
            System.out.println(NrStudents2 + "numar de elevi 1");
            HashMap<Integer, Question> q = test.getContents();
            for(int i=0;i<student.size();i++){
                StudentID[i]=student.get(i);
                StudentName[i] =Menu.Maner.getStudent(StudentID[i]).getFirstName()+" "+Menu.Maner.getStudent(StudentID[i]).getLastName();
                HashMap<Integer, String> answers = Menu.Maner.getAnswerSheet(testID,student.get(i)).getAnswers();
                ind = 0;
                for(int j=1;j<=answers.size();j++){

                    if(test.isMultipleAnswer(j)==false)Answers[i][++ind] = answers.get(j);

                }
            }
            ind = 0;
            for(int i=1;i<=q.size();i++){
                if(test.isMultipleAnswer(i)==false){
                    Questions[++ind] =  "<html>"+q.get(i).getQuestion()+ "<html>";
                    Img[ind] = q.get(i).getImage();
                }

            }
            MPQuestions = q.size()-ind;
            readImages(ind,Img);
            NrQuestions = ind;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            NrQuestions=1;
        }



    }

    public static void findUncorrectedTests(){

        for(int i=0;i<=100;i++){
            for(int j=0;j<=100;j++){
                uncorrectedTestsID[i][j] = new ArrayList<>();
                uncorrectedTestsName[i][j] = new ArrayList<>();
            }
        }

        ArrayList<String> testID = new ArrayList<>();
        try {
            testID = Menu.Maner.getUncorrectedTests(UserImput.teacher,"Geo");
            System.out.println(testID.size() + "nr elevi");
            for(int i=0;i<testID.size();i++){
                try {
                    String TestID = testID.get(i).split(" ")[0];
                    //System.out.println(testID);
                    Test test = Menu.Maner.viewTest(TestID,UserImput.teacher);
                    String clasa = test.getGrade();
                    char Clasa[] = clasa.toCharArray();
                    char letter = Clasa[Clasa.length-1];
                    int clasaNr = 0;
                    for(int j=0;j<Clasa.length-1;j++){
                        clasaNr = clasaNr*10+(Clasa[j]-'0');
                    }
                    // plannedTests[letter-'A'+1][clasaNr-8] = new ArrayList<>();
                    uncorrectedTestsID[letter-'A'+1][clasaNr-8].add(TestID);
                    uncorrectedTestsName[letter-'A'+1][clasaNr-8].add(test.getTestName());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {

                }
            }
        } catch (IllegalAccessException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void findClass(int grade, String letter){

        ArrayList<Student> cls = new ArrayList<>();
        try {
            cls = Menu.Maner.listClassbook(grade, letter);
            cls.sort(new SortbyName());
            System.out.println(cls.get(0));
            NrStudents=cls.size();
            for(int i=0;i<NrStudents;i++){
                StudentName[i+1] = cls.get(i).getFirstName()+" "+cls.get(i).getLastName();
                try {
                    ArrayList<Grade> note = Menu.Maner.getCurrentGrades(cls.get(i), "Geo");
                    for (int j = 0; j < note.size(); j++) {
                        Grades[i][j] = note.get(j).getCurrentGrade();
                    }
                } catch (IllegalAccessException e){

                }
            }
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void Run(){

        UserImput.initilaizeDimensions();

    }
    static class SortbyName implements Comparator<Student>
    {

        @Override
        public int compare(Student o1, Student o2) {
            return o1.getFirstName().compareTo(o2.getFirstName());
        }
    }
}
