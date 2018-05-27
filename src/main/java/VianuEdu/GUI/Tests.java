package VianuEdu.GUI;

import VianuEdu.backend.TestLibrary.AnswerSheet;
import VianuEdu.backend.TestLibrary.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Clock;



public class Tests {

    // public static Map<Integer,Pair<String,String>> test[] = new Map[10001];
    public static String Question[] = new String[1001];
    public static String Answers[][] = new String[101][101];
    public static String SAnswers[] = new String[1001];
    public static String RAnswers[] = new String[1001];
    public static String TestName;
    public static String[] Essays = new String[101];
    public static String TestID;
    public static boolean QuestionGenerated = false;
    public static boolean beginTest = false;
    public static boolean copyMousePressed;
    public static boolean endTest = false;
    public static boolean scaled = false;
    public static boolean Test_finished;
    public static boolean isCalculated = false;
    public static boolean ChoiceQuestion[] = new boolean[101];
    public static int CAnswer[] = new int[1001];
    public static int NrQuestions = 3;
    public static int copyScreenWidth = Menu.ScreenWidth;
    public static int currentQuestion = 1;
    public static int NrAnswers[] = new int[1001];
    public static float result;
    public static JLabel label;
    public static Image img[] = new Image[101] ;
    public static byte Image[][] = new byte[101][101];
    public static BufferedImage lb;
    public static long beginTime;
    public static Clock clock = Clock.systemUTC();
    public static JTextArea essay = new JTextArea(10,20);
    public static JScrollPane scroll  = new JScrollPane(essay);

    public static void findTest(int Class, String Name) {

        STimer();
        endTest = false;
        beginTest = false;
        ContentBrowser.showTest = true;
        Test_finished = false;
        isCalculated = false;
        currentQuestion = 1;
        essay.setVisible(false);
        scroll.setVisible(false);
        for(int i=0;i<= NrQuestions;i++){
            CAnswer[i]=0;
            ChoiceQuestion[i]=false;
            RAnswers[i] = null;
            SAnswers[i]=null;
            Essays[i] = null;
        }

        try {System.out.println("dddd");
            Test test;
            if(Menu.isTeacher==true){
                 test = Menu.Maner.viewTest(Name,UserImput.teacher);
            }
            else {
                 test = Menu.Maner.getTest(Name);
            }
            TestID = Name;
            TestName = test.getTestName();
            for (Integer index = 1; index <= test.getContents().size(); index++) {
                Question[index] = "<html>"+test.getContents().get(index).getQuestion()+"<html>";
                RAnswers[index] = test.getContents().get(index).getAnswer();
                Image[index] = test.getContents().get(index).getImage();
                if(test.isMultipleAnswer(index)) {
                    ChoiceQuestion[index]=true;
                    NrAnswers[index] = test.getContents().get(index).getQuestionChoices().size();
                    for (Integer i = 0; i < test.getContents().get(index).getQuestionChoices().size(); i++) {
                        Answers[index][i+1] = test.getContents().get(index).getQuestionChoices().get(i);
                    }
                }
            }
            readImages(test.getContents().size());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void readImages(int n){
        for(int i=1;i<=n;i++){
            try {
                img[i] = ImageIO.read(new ByteArrayInputStream(Image[i]));
                img[i] = img[i].getScaledInstance(Menu.ScreenWidth/5, Menu.ScreenHeight/5,2);
            } catch (IOException e) {
                e.printStackTrace();
            }catch(java.lang.NullPointerException e){

            }
            System.out.println("scaled");
        }

    }

    public static void drawbackground(Graphics g) {
        g.setColor(new Color(60, 60, 60));
        g.fillRect(GeoEduMenu.PanelWidth, 0, Menu.ScreenWidth * 3 / 5, Menu.ScreenHeight);
        g.setColor(new Color(255, 255, 255));
        g.drawRect(GeoEduMenu.PanelWidth, 0, Menu.ScreenWidth * 3 / 5 - 1, Menu.ScreenHeight);
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
        label.setBounds(Menu.ScreenWidth / 4, 0, Menu.ScreenWidth / 2, Menu.ScreenHeight / 2);
        label.setSize(Menu.ScreenWidth / 2, Menu.ScreenHeight / 4);
        if (QuestionGenerated == false || Menu.ScreenWidth != copyScreenWidth) {
            label.setFont(f);
            label.setForeground(new Color(255, 255, 255));
            essay.setText(Essays[currentQuestion]);
            Window.frame.add(label);
            lb = labeltoImage(label);
            scaled=false;
            QuestionGenerated = true;
        }
        copyScreenWidth = Menu.ScreenWidth;
        if (QuestionGenerated == true) g.drawImage(lb, label.getX(), label.getY(), null);
    }

    public static void drawImage(Graphics g){

        try {
            g.drawImage(img[currentQuestion],(Menu.ScreenWidth-img[currentQuestion].getWidth(null))/2,Menu.ScreenHeight*10/45,null);
        }
        catch (java.lang.NullPointerException e){

        }

    }

    public static void drawAnswer(Graphics g, String answers, int p) {

        int AnswerHeight = Menu.ScreenHeight / 20;
        int AnswerWidth = Menu.ScreenWidth / 3;
        Color color = new Color(255, 231, 78);


        int x = Menu.X_hovered;
        int y = Menu.Y_hovered;
        if (x > Menu.ScreenWidth / 3 && x < 2 * Menu.ScreenWidth / 3 && y > Menu.ScreenHeight / 2 + p * AnswerHeight && y < Menu.ScreenHeight / 2 + (p + 1) * AnswerHeight && Menu.MousePressed == true) { {
            CAnswer[currentQuestion] = p;
        }
            if (Menu.MousePressed == true && GeoEduMenu.copyMousePressed == false)
                Setari.ButtonSound("button_click.wav");
        }

        g.setColor(new Color(10, 10, 10));
        g.fill3DRect(Menu.ScreenWidth / 3, Menu.ScreenHeight / 2 + p * AnswerHeight, AnswerWidth, AnswerHeight, false);
        if (CAnswer[currentQuestion] == p) {
            g.setColor(color);
            g.fill3DRect(Menu.ScreenWidth / 3, Menu.ScreenHeight / 2 + p * AnswerHeight, AnswerHeight, AnswerHeight, true);
        } else {
            g.setColor(Color.WHITE);
            g.fill3DRect(Menu.ScreenWidth / 3, Menu.ScreenHeight / 2 + p * AnswerHeight, AnswerHeight, AnswerHeight, true);
        }

        g.drawRect(Menu.ScreenWidth / 3, Menu.ScreenHeight / 2 + p * AnswerHeight, AnswerWidth, AnswerHeight);
        g.setColor(Color.BLACK);

        Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 3);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(220, 220, 220));
        g.setFont(small);
        g.drawString(String.valueOf(answers), Menu.ScreenWidth / 3 + AnswerHeight + AnswerHeight / 4, Menu.ScreenHeight / 2 + (p + 1) * AnswerHeight - AnswerHeight / 4);
        g.setColor(new Color(0, 0, 0));
        Font small2 = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 2);
        g.setFont(small2);
        g.drawString(String.valueOf((char) ('A' + p - 1)), Menu.ScreenWidth / 3 + (AnswerHeight - metricsx.stringWidth(String.valueOf((char) ('A' + p - 1)))) / 2, Menu.ScreenHeight / 2 + (p + 1) * AnswerHeight - metricsy.stringWidth(String.valueOf((char) ('A' + p - 1))) / 4 - AnswerHeight / 4);

    }


    public static void STimer() {

        beginTime = clock.millis();

    }

    public static void drawPreviousButton(Graphics g) {
        int x = Menu.X_hovered;
        int y = Menu.Y_hovered;
        int ButtonHeight = Menu.ScreenHeight / 14;
        int ButtonWidth = Menu.ScreenWidth / 60;
        int ButtonX = Menu.ScreenWidth - Menu.ScreenWidth * 2 / 3 - Menu.ScreenWidth / 14;
        int ButtonY = Menu.ScreenHeight * 2 / 3 - Menu.ScreenHeight / 40;

        if (Menu.MousePressed == true && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(200, 200, 200));
            if (GeoEduMenu.copyMousePressed == false) Setari.ButtonSound("button_click.wav");
        } else if (x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(85, 64, 11));
            if (GeoEduMenu.copyMousePressed == true) {
                currentQuestion--;
                QuestionGenerated = false;
            }
        } else {
            g.setColor(Color.BLACK);
        }
        g.fill3DRect(ButtonX - ButtonWidth / 2, ButtonY - ButtonHeight / 4, ButtonWidth * 2 + ButtonHeight / 10, ButtonHeight * 3 / 2, true);
        if (Menu.MousePressed == true && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
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

    public static void drawNextButton(Graphics g) {

        int x = Menu.X_hovered;
        int y = Menu.Y_hovered;
        int ButtonHeight = Menu.ScreenHeight / 14;
        int ButtonWidth = Menu.ScreenWidth / 60;
        int ButtonX = Menu.ScreenWidth * 2 / 3 + Menu.ScreenWidth / 20;
        int ButtonY = Menu.ScreenHeight * 2 / 3 - Menu.ScreenHeight / 40;

        if (Menu.MousePressed == true && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(200, 200, 200));
            if (GeoEduMenu.copyMousePressed == false) Setari.ButtonSound("button_click.wav");
        } else if (x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(85, 64, 11));
            if (GeoEduMenu.copyMousePressed == true) {
                currentQuestion++;
                QuestionGenerated = false;
            }
        } else {
            g.setColor(Color.BLACK);
        }
        g.fill3DRect(ButtonX - ButtonWidth / 2, ButtonY - ButtonHeight / 4, ButtonWidth * 2 + ButtonHeight / 10, ButtonHeight * 3 / 2, true);
        if (Menu.MousePressed == true && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
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

    public static void drawSlideBar(Graphics g, int nr) {

    }

    public static void drawStartButton(Graphics g, int x, int y, int width, int height, String Name) {

        int xm = Menu.X_hovered;
        int ym = Menu.Y_hovered;


        if (xm > x && xm < x + width && ym > y && ym < y + height && Menu.MousePressed == true) {

            if (GeoEduMenu.copyMousePressed == false) Setari.ButtonSound("button_click.wav");
        } else if (xm > x && xm < x + width && ym > y && ym < y + height) {
            if (GeoEduMenu.copyMousePressed == true) {
                QuestionGenerated = false;
                beginTest = true;
                endTest = false;
                STimer();
            }
        }
        if (xm > x && xm < x + width && ym > y && ym < y + height && Menu.MousePressed == true) {
            g.setColor(new Color(60, 60, 60));
            g.fill3DRect(x, y, width, height, true);
            g.setColor(Color.LIGHT_GRAY);
            g.fill3DRect(x + 2, y + 2, width - 4, height - 4, true);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, width, height);

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize * 2 / 3);
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

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize * 2 / 3);
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

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize * 2 / 3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }

    }

    public static void drawEndButton(Graphics g, int x, int y, int width, int height, String Name) {

        int xm = Menu.X_hovered;
        int ym = Menu.Y_hovered;


        if (xm > x && xm < x + width && ym > y && ym < y + height && Menu.MousePressed == true) {

            if (GeoEduMenu.copyMousePressed == false) Setari.ButtonSound("button_click.wav");
        } else if (xm > x && xm < x + width && ym > y && ym < y + height) {
            if (GeoEduMenu.copyMousePressed == true) {
                QuestionGenerated = false;
                endTest = true;
                STimer();
            }
        }
        if (xm > x && xm < x + width && ym > y && ym < y + height && Menu.MousePressed == true) {
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

    public static void drawExitButton(Graphics g, int x, int y, int width, int height, String Name) {

        int xm = Menu.X_hovered;
        int ym = Menu.Y_hovered;


        if (xm > x && xm < x + width && ym > y && ym < y + height && Menu.MousePressed == true) {

            if (GeoEduMenu.copyMousePressed == false) Setari.ButtonSound("button_click.wav");
        } else if (xm > x && xm < x + width && ym > y && ym < y + height) {
            if (GeoEduMenu.copyMousePressed == true) {
                Test_finished = true;
                ContentBrowser.showTest = false;
                beginTest = false;
                essay.setText(null);
               ContentBrowser.getInfo();
                for(int i=1; i<=NrQuestions;i++){
                    Question[i]=null;
                    RAnswers[i]=null;
                    SAnswers[i]=null;
                    NrAnswers[i]=0;
                    CAnswer[i] = 0;
                    if(ChoiceQuestion[i]==false){
                        Essays[i]=null;
                    }
                }
                NrQuestions = 0;
            }
        }
        if (xm > x && xm < x + width && ym > y && ym < y + height && Menu.MousePressed == true) {
            g.setColor(new Color(60, 60, 60));
            g.fill3DRect(x, y, width, height, true);
            g.setColor(Color.LIGHT_GRAY);
            g.fill3DRect(x + 2, y + 2, width - 4, height - 4, true);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, width, height);

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize );
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

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize );
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

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }

    }

    public static void drawMap(Graphics g, int content) {

        int width = (Menu.ScreenWidth - 2 * GeoEduMenu.PanelWidth * 5 / 4) / content;
        if (width > Menu.ScreenWidth / 50) width = Menu.ScreenWidth / 50;
        int height = width;
        int x = (Menu.ScreenWidth - content * width) / 2;
        int y = Menu.ScreenHeight / 30;

        for (int i = 0; i < content; i++) {
            drawCell(g, x + i * width, y, width, height, i + 1);
        }

    }

    public static void drawCell(Graphics g, int x, int y, int width, int height, int Name) {

        int xm = Menu.X_hovered;
        int ym = Menu.Y_hovered;


        if (xm > x && xm < x + width && ym > y && ym < y + height && Menu.MousePressed == true) {

            if (GeoEduMenu.copyMousePressed == false){

                Setari.ButtonSound("button_click.wav");

            }
        } else if (xm > x && xm < x + width && ym > y && ym < y + height) {


            if (GeoEduMenu.copyMousePressed == true) {
                System.out.println("daaaa");
                currentQuestion = Name;
                QuestionGenerated = false;

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

    public static void drawBeginScreen(Graphics g, String SName) {

        String Name = "Esti pe cale sa dai testul din lectia:  " + SName + ".";
        g.setColor(new Color(10, 10, 10));
        g.fillRect(GeoEduMenu.PanelWidth, 0, Menu.ScreenWidth * 3 / 5, Menu.ScreenHeight);
        g.setColor(new Color(255, 255, 255));
        g.drawRect(GeoEduMenu.PanelWidth, 0, Menu.ScreenWidth * 3 / 5 - 1, Menu.ScreenHeight);

        Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize * 2 / 3);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(250, 250, 250));
        g.setFont(small);
        g.drawString(String.valueOf(Name), Menu.ScreenWidth / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, Menu.ScreenHeight / 3 + metricsy.getHeight() / 4);

        Name = "Testul dureaza 50 de minute. Succes!";
        g.drawString(String.valueOf(Name), Menu.ScreenWidth / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, Menu.ScreenHeight / 3 + metricsy.getHeight() * 3 / 2);
        drawStartButton(g, Menu.ScreenWidth * 5 / 12, Menu.ScreenHeight * 3 / 4, Menu.ScreenWidth / 6, Menu.ScreenHeight / 12, "Incepe testul");

    }

    public static void drawTimer(Graphics g) {

        int second = 60 - ((int) (clock.millis() - beginTime) / 1000) % 60;
        int minute = 49 - ((int) (clock.millis() - beginTime) / 1000 / 60) % 60;
        if (second == 60) {
            minute++;
            second = 0;
        }
        String time;
        if (second < 10) {
            time = String.valueOf(minute) + " : 0" + String.valueOf(second);
        } else {
            time = String.valueOf(minute) + " : " + String.valueOf(second);
        }
        if(minute == 0 && second == 0 ){
            endTest = true;
            beginTest =false;
        }
        int x = Menu.ScreenWidth * 5 / 12;
        int y = Menu.ScreenHeight * 7 / 8;
        int width = Menu.ScreenWidth / 6;
        int height = Menu.ScreenHeight / 20;

        g.setColor(new Color(60, 60, 60));
        g.fill3DRect(x, y, width, height, false);
        g.setColor(Color.BLACK);
        g.fill3DRect(x + 2, y + 2, width - 4, height - 4, false);

        Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize * 2 / 3);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(220, 220, 220));
        g.setFont(small);
        g.drawString(String.valueOf(time), x + width / 2 - metricsx.stringWidth(String.valueOf(time)) / 2, y + height / 2 + metricsy.getHeight() / 4);

    }

    public static void drawEssay(Graphics g,int x, int y, int width, int height){

        g.setColor(Color.BLACK);
        g.fill3DRect(x-width/40,y-width/10,width+width/20,height+width/20+width*3/40,true);
        g.setColor(Color.WHITE);
        Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 3);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setFont(small);
        g.drawString(String.valueOf("Scrie raspunsul aici:"), x + width /20, y -width/30);
        g.drawRect(x,y,width,height);
       if(Setari.SettingsOn==false) essay.setVisible(true);
        Font f = new Font("Calibri", Font.PLAIN, UserImput.FontSize * 3 / 8);
        essay.setLineWrap(true);
        scroll.setBackground(Color.LIGHT_GRAY);
        essay.setFont(f);
        scroll.setWheelScrollingEnabled(true);
        scroll.setAutoscrolls(true);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(x,y,width,height);
        if(Setari.SettingsOn==false)scroll.setVisible(true);
        else scroll.setVisible(false);
    }

    public static void calculateResult(){
        int total =0;
        int correct = 0;
        for(int i=1;i<= NrQuestions; i++){
            try{
                if(ChoiceQuestion[i]==true)
                    total++;
                if(SAnswers[i].equals(RAnswers[i]))correct++;
            }catch(java.lang.NullPointerException e){

            }
        }
        result = 10*(float)(correct)/(float)(total);
    }

    public static void drawEndScreen(Graphics g){

        drawbackground(g);
        essay.setVisible(false);
        scroll.setVisible(false);
        Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize );
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(220, 220, 220));
        g.setFont(small);
        g.drawString(String.valueOf("Testul s-a terminat!"), Menu.ScreenWidth / 2 - metricsx.stringWidth(String.valueOf("Testul s-a incheiat!"))/2, Menu.ScreenHeight / 2 - metricsy.getHeight() );
        g.drawString(String.valueOf("Ai obtinut nota partiala: ")+String.valueOf(result), Menu.ScreenWidth / 2 - metricsx.stringWidth(String.valueOf("Ai obtinut nota partiala: ")+String.valueOf(result))/2, Menu.ScreenHeight / 2 );
        drawExitButton(g,Menu.ScreenWidth*3/8,Menu.ScreenHeight*3/4,Menu.ScreenWidth/4,Menu.ScreenHeight/12,"Iesire");

    }

    public static void drawContent(Graphics g, int currentQuestion, int nrAnswers) {

        if (beginTest == false&& endTest ==false) drawBeginScreen(g, TestName);
        else if(endTest ==true){
            drawEndScreen(g);
        }
        else {

            drawbackground(g);
            drawImage(g);
            try {
                drawQuestion(g, Question[currentQuestion]);
            }
            catch (java.lang.NullPointerException e){

            }
            if(ChoiceQuestion[currentQuestion]==true) {
                for (int i = 1; i <= nrAnswers; i++) {
                    if (CAnswer[currentQuestion] != i) drawAnswer(g, Answers[currentQuestion][i], i);
                }
                if (CAnswer[currentQuestion] != 0) {
                    drawAnswer(g, Answers[currentQuestion][CAnswer[currentQuestion]], CAnswer[currentQuestion]);
                    SAnswers[currentQuestion] = Answers[currentQuestion][CAnswer[currentQuestion]];
                }
                essay.setVisible(false);
                scroll.setVisible(false);
            }
            else {
                drawEssay(g,Menu.ScreenWidth/3,Menu.ScreenHeight/2,Menu.ScreenWidth/3,Menu.ScreenHeight/3);
                Essays[currentQuestion] = essay.getText();
            }
            if(Setari.SettingsOn==true)essay.setVisible(false);
            if (currentQuestion < NrQuestions) drawNextButton(g);
            else if(currentQuestion == NrQuestions)drawEndButton(g,Menu.ScreenWidth * 2 / 3 +Menu.ScreenWidth/60 ,Menu.ScreenHeight * 2 / 3 - Menu.ScreenHeight / 40,Menu.ScreenWidth / 10, Menu.ScreenHeight / 14,"Termina Testul!");
            if (currentQuestion > 1) drawPreviousButton(g);
            drawSlideBar(g, NrQuestions);
            drawMap(g, NrQuestions);
            drawTimer(g);
        }
    }

    public static void sendData(){

        AnswerSheet sheet = new AnswerSheet(UserImput.student,TestID,NrQuestions);

        for(int i=1;i<=NrQuestions;i++){
            System.out.println("DAaa");
            if(ChoiceQuestion[i]==false)sheet.addAnswer(i,Essays[i]);
            else{
                sheet.addMultipleChoiceAnswer(i,SAnswers[i]);
            }
        }

        try {
            Menu.Maner.submitAnswerSheet(UserImput.student, sheet);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void updateQuestionNumber() {

        int i = 1;
        while (Question[i] != null) i++;
        NrQuestions = i - 1;
    }



    public static void Paint(Graphics g) {

        if(Test_finished==false) drawContent(g, currentQuestion, NrAnswers[currentQuestion]);
    }

    public static void Run() {

        updateQuestionNumber();
        UserImput.initilaizeDimensions();
        if (endTest == true && isCalculated==false) {
            calculateResult();
            if(Menu.isTeacher==false)sendData();
            isCalculated = true;
        }
    }

}
