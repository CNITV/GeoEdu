package VianuEdu.GUI;

import VianuEdu.backend.TestLibrary.AnswerSheet;
import VianuEdu.backend.TestLibrary.Grade;

import java.awt.*;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ContentBrowser {

    public static int ContentStart = Menu.ScreenHeight / 4;
    public static int ContentBrowserWidth = GeoEduMenu.PanelWidth;
    public static int ContentBrowserHeight = GeoEduMenu.PanelHeight / 2;
    public static int BrowserY = GeoEduMenu.PanelHeight / 4;
    public static int BrowserX = GeoEduMenu.Rightpanel;
    public static int NumberContent=0;
    public static int currentClass;
    public static int Class[] = new int[101];
    public static boolean Chovered[] = new boolean[101];
    public static boolean Cpressed[] = new boolean[101];
    public static boolean Test = false;
    public static boolean lessons = false;
    public static boolean dTest = false;
    public static boolean dLessons = false;
    public static boolean Search = false;
    public static boolean showTest = false;
    public static boolean showLesson = false;
    public static ArrayList<String> ContentName = new ArrayList<>();
    public static ArrayList<String> ContentID = new ArrayList<>();


    public static void initialiseDimensions() {

        ContentBrowserWidth = GeoEduMenu.PanelWidth;
        ContentBrowserHeight = GeoEduMenu.PanelHeight / 2;
        BrowserY = GeoEduMenu.PanelHeight / 4;
        BrowserX = GeoEduMenu.Rightpanel;


    }

    public static void getInfo() {


        int p = 0;
        lessons = Test = false;
        if (GeoEduMenu.currentPressed == 2 && !Menu.isTeacher) {
            lessons = true;
            p = UserImput.student.getGrade();
        } else if (GeoEduMenu.currentPressed == 3) {
            Test = true;
            if(Menu.isTeacher==true){
                p = GeoEduMenu.currentClass;
            }
        }
        if (Search == true) {

        } else {
            if (Test == true  && Menu.MousePressed == true) {

                if (StartMenu.Start_GeoEdu == false && showTest==false) {
                    try {
                        if(Menu.isTeacher==true){
                            ArrayList<String> ID = Menu.Maner.getPlannedTests("Geo",UserImput.teacher);
                            NumberContent = 0;
                            ContentName.clear();
                            ContentID.clear();
                            for(int i=0;i<ID.size();i++) {
                                System.out.println(ID.get(i));
                                String id = ID.get(i).split(" ")[0];
                                String clasa = ID.get(i).split(" ")[2];
                                VianuEdu.backend.TestLibrary.Test test = null;

                                char Clasa[] = clasa.toCharArray();
                                int clasaNr = 0;
                                for(int j=0;j<Clasa.length-1;j++){
                                    clasaNr = clasaNr*10+(Clasa[j]-'0');
                                }
                                if (clasaNr==p) {
                                    test = Menu.Maner.viewTest(id,UserImput.teacher);
                                    ContentName.add(test.getTestName());
                                    ContentID.add(id);
                                    NumberContent++;
                                }
                            }
                        }
                        else {
                            ArrayList<String> ID = Menu.Maner.getTestQueue("Geo", UserImput.cookie);
                            VianuEdu.backend.TestLibrary.Test test = null;
                            ContentName.clear();
                            ContentID.clear();
                            NumberContent = 0;
                            for (String TestID : ID) {
                                test = Menu.Maner.getTest(TestID);
                                boolean ok = false;

                                try {
                                     Menu.Maner.getAnswerSheet(TestID, UserImput.cookie);

                                }catch(java.lang.IllegalAccessException e){
                                    ContentName.add(test.getTestName());
                                    ContentID.add(TestID);
                                    NumberContent++;
                                    try {
                                        Menu.Maner.getGrade(TestID, UserImput.cookie);
                                    }catch(java.lang.IllegalAccessException e1){

                                    }
                                }
                            }
                              }



                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                         e.printStackTrace();
                    }
                    dLessons = false;
                    dTest = true;
                }

            } else if (lessons == true && p >= 9 && Menu.MousePressed == true && Menu.isTeacher == false) {

                dLessons = true;
                dTest = false;
                NumberContent = 0;
                ContentName.clear();
                ContentID.clear();
                System.out.println("lectie");
                try {
                    ArrayList<String> lessons = Menu.Maner.listLessons("Geo", p);
                    NumberContent = lessons.size();
                    for(int i=0; i<lessons.size();i++){
                        ContentName.add(Menu.Maner.getLesson("Geo",lessons.get(i)).getTitle());
                        ContentID.add(lessons.get(i));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void drawBar(Graphics g) {

        int x = Menu.X_hovered;
        int y = Menu.Y_hovered;
        int xbar = BrowserX + ContentBrowserWidth * 8 / 9;
        int ybar = BrowserY;
        int width = ContentBrowserWidth / 9;
        int height = ContentBrowserHeight;
        if (x > xbar && x < xbar + width && y > ybar && y < ybar + height) {
           // g.setColor(new Color(200, 200, 200));
           // g.fillRect(xbar, ybar, width, height);
        }

    }

    public static void makeButton(Graphics g, int x, int y, int width, int height, String ID, String Name, int i) {

        int xhovered = Menu.X_hovered;
        int yhovered = Menu.Y_hovered;
        if (xhovered > x && xhovered < x + width && yhovered > y && yhovered < y + height && Menu.MousePressed == true) {
            if(dTest) {
                if (Chovered[i] == true && showTest == false) {
                    Setari.ButtonSound("button_click.wav");
                    Tests.findTest(currentClass, ID);
                    showLesson = false;
                }
            }else if(dLessons){
                if (Chovered[i] == true && showLesson == false) {
                    Setari.ButtonSound("button_click.wav");
                    Lessons.findLesson(UserImput.student.getGrade(), ID);
                    showTest= false;
                }
            }
            Cpressed[i] = true;
            Chovered[i] = false;
            g.setColor(new Color(200, 200, 200));
            g.fill3DRect(x, y, width, height, true);
            g.setColor(new Color(255, 255, 255));
            // g.drawRect(x,y,width,height);

            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize / 3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(10, 10, 10));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else if (xhovered > x && xhovered < x + width && yhovered > y && yhovered < y + height) {
            Chovered[i] = true;
            Cpressed[i] = false;
            g.setColor(new Color(241, 207, 98));
            g.fillRect(x, y, width, height);
            g.setColor(new Color(255, 250, 151));
            //g.drawRect(x,y,width,height);

            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize / 3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(255, 250, 151));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        } else {
            Chovered[i] = Cpressed[i] = false;
            g.setColor(new Color(50, 50, 50));
            g.fill3DRect(x, y, width, height, false);
            g.setColor(new Color(230, 230, 230));
            //g.drawRect(x,y,width,height);

            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize / 3);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(240, 240, 240));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }

    }

    public static void drawContent(Graphics g, ArrayList<String> ID, ArrayList<String> content, int NrContent) {


        for (int i = 0; i <  NrContent; i++) {
            makeButton(g, BrowserX, ContentStart * Menu.ScreenHeight / 1080 + (i) * ContentBrowserHeight / 6, ContentBrowserWidth, ContentBrowserHeight / 6, ID.get(i), content.get(i), i);
        }

    }

    public static void findContent(Graphics g) {

        getInfo();
        drawContent(g, ContentID, ContentName, ContentID.size());
    }

    public static void drawBrowser(Graphics g) {

        g.setColor(new Color(105, 105, 105));
        g.fillRect(BrowserX, BrowserY, ContentBrowserWidth, ContentBrowserHeight);
        findContent(g);
        g.setColor(new Color(10, 10, 10));
        g.fillRect(GeoEduMenu.Rightpanel, 0, GeoEduMenu.PanelWidth, ContentStart);
        g.fillRect(GeoEduMenu.Rightpanel, Menu.ScreenHeight * 3 / 4, GeoEduMenu.PanelWidth, Menu.ScreenHeight / 4);
        g.setColor(new Color(255, 255, 255));
        g.drawRect(BrowserX, BrowserY, ContentBrowserWidth, ContentBrowserHeight);
        g.setColor(new Color(255, 255, 255));
        g.drawRect(BrowserX, BrowserY - ContentBrowserHeight / 8, ContentBrowserWidth, ContentBrowserHeight / 8);
        Font small = new Font("Futura", Font.PLAIN, GeoEduMenu.Relativesize / 2);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setFont(small);
        g.drawString(String.valueOf("Continut:"), GeoEduMenu.Rightpanel + GeoEduMenu.PanelWidth / 2 - metricsx.stringWidth(String.valueOf("Continut:")) / 2, Menu.ScreenHeight / 4 - Menu.ScreenHeight / 16 + Menu.ScreenHeight / 32 + metricsy.getHeight() / 4);
    }

    public static void Paint(Graphics g) {


        drawBrowser(g);
        drawBar(g);

    }

    public static void Run() {

        initialiseDimensions();
        if (showTest == true) Tests.Run();
        else if(showLesson)Lessons.Run();
    }


}
