package VianuEdu.GUI;

import VianuEdu.backend.TestLibrary.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Clock;


public class Tests {

    // public static Map<Integer,Pair<String,String>> test[] = new Map[10001];
    public static String Question[] = new String[1001];
    public static String Answers[] = new String[1001];
    public static String SAnswers[] = new String[1001];
    public static String TestName;
    public static boolean QuestionGenerated = false;
    public static boolean beginTest = false;
    public static int CAnswer[] = new int[1001];
    public static int NrQuestions = 5;
    public static int copyScreenWidth = Menu.ScreenWidth;
    public static int currentQuestion = 1;
    public static int NrAnswers = 5;
    public static JLabel label;
    public static Icon Image[] = new Icon[1001];
    public static BufferedImage lb;
    public static long beginTime;
    public static Clock clock = Clock.systemUTC();

    public static void findTest(int Class, String Name) {

        try {
            Test test = Menu.Maner.getTest(Name);
            System.out.println(test.getContents().size());
            for (Integer index = 1; index <= test.getContents().size(); index++) {
                Question[index] = test.getContents().get(index).getQuestion();
                Answers[index] = test.getContents().get(index).getAnswer();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void drawbackground(Graphics g) {
        g.setColor(new Color(60, 60, 60));
        g.fillRect(GeoEduMenu.PanelWidth, 0, Menu.ScreenWidth * 3 / 5, Menu.ScreenHeight);
        g.setColor(new Color(255, 255, 255));
        g.drawRect(GeoEduMenu.PanelWidth, 0, Menu.ScreenWidth * 3 / 5 - 1, Menu.ScreenHeight);
    }

    public static String getFormatedString(String text) {

        return "<html>" + text + "<html>";
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

    public static void drawQuestion(Graphics g, String question, Icon img) {
        Font f = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 2);
        label = new JLabel(question, img, SwingConstants.CENTER);
        label.setLayout(null);
        label.setBounds(Menu.ScreenWidth / 4, 0, Menu.ScreenWidth / 2, Menu.ScreenHeight / 2);
        label.setSize(Menu.ScreenWidth / 2, Menu.ScreenHeight / 4);
        if (QuestionGenerated == false || Menu.ScreenWidth != copyScreenWidth) {
            label.setFont(f);
            label.setForeground(new Color(255, 255, 255));
            Window.frame.add(label);
            lb = labeltoImage(label);
            QuestionGenerated = true;
        }
        copyScreenWidth = Menu.ScreenWidth;
        if (QuestionGenerated == true) g.drawImage(lb, label.getX(), label.getY(), null);
    }

    public static void drawAnswer(Graphics g, String answers, int p) {

        int AnswerHeight = Menu.ScreenHeight / 20;
        int AnswerWidth = Menu.ScreenWidth / 3;
        Color color = new Color(255, 231, 78);


        int x = Menu.X_hovered;
        int y = Menu.Y_hovered;
        if (x > Menu.ScreenWidth / 3 && x < 2 * Menu.ScreenWidth / 3 && y > Menu.ScreenHeight / 2 + p * AnswerHeight && y < Menu.ScreenHeight / 2 + (p + 1) * AnswerHeight && Menu.MousePressed == true) {
            CAnswer[currentQuestion] = p;
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

    public static void generateAnswers(String Sir, String answers[]) {

        char sir[] = Sir.toCharArray();
        int len = sir.length;
        int i = 0, n = 0, w = 0;
        char c[] = new char[101];
        while (i < len) {
            if (sir[i] == '/' || i == len - 1) {
                w = 0;
                answers[++n] = String.valueOf(c);
            } else {
                c[w] = sir[i];
                w++;
            }
            i++;
        }
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
            currentQuestion = Name;
            if (GeoEduMenu.copyMousePressed == false) Setari.ButtonSound("button_click.wav");
        } else if (xm > x && xm < x + width && ym > y && ym < y + height) {
            if (GeoEduMenu.copyMousePressed == true) {
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

        String Name = "Esti pe cale sa dai testul din lectia: " + SName + ".";
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

    public static void drawContent(Graphics g, int currentQuestion, int nrAnswers) {

        if (beginTest == false) drawBeginScreen(g, TestName);
        else {
            drawbackground(g);
            drawQuestion(g, Question[currentQuestion], Image[currentQuestion]);
            for (int i = 1; i <= nrAnswers; i++) {
                if (CAnswer[currentQuestion] != i) drawAnswer(g, SAnswers[i], i);
            }
            if (CAnswer[currentQuestion] != 0)
                drawAnswer(g, SAnswers[CAnswer[currentQuestion]], CAnswer[currentQuestion]);
            if (currentQuestion < NrQuestions) drawNextButton(g);
            if (currentQuestion > 1) drawPreviousButton(g);
            drawSlideBar(g, NrQuestions);
            drawMap(g, NrQuestions);
            drawTimer(g);
        }
    }

    public static void updateQuestionNumber() {

        int i = 1;
        while (Question[i] != null) i++;
        NrQuestions = i - 1;
    }

    public static void Paint(Graphics g) {

        drawContent(g, currentQuestion, NrAnswers);
    }

    public static void Run() {
        updateQuestionNumber();
        if (QuestionGenerated == false) {
            generateAnswers(Answers[currentQuestion], SAnswers);
        }
    }

}
