package VianuEdu.GUI;

import VianuEdu.backend.TestLibrary.AnswerSheet;
import VianuEdu.backend.TestLibrary.Question;
import VianuEdu.backend.TestLibrary.Test;
import javafx.scene.layout.VBox;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class TestEditor {

    public static int currentQuestion=1;
    public static int NumarVariante = 0;
    public static int NrVar[] = new int[101];
    public static int NrQuestions = 1;
    public static int PAnswers[] = new int[101];
    public static boolean createTest = true;
    public static boolean changeQuestion = false;
    public static boolean planTest = false;
    public static boolean stage1 =true;
    public static boolean stage2 = false;
    public static boolean Next = false;
    public static boolean check = false;
    public static boolean copyMousePressed= false;
    public static boolean isInitialised = false;
    public static String TestName;
    public static String TestClass;
    public static String TestDate;
    public static String TestStart;
    public static String TestEnd;
    public static int TestPercentage;
    public static int MPQuestions;
    public static String[][] plannedTests = new String[101][101];
    public static String Questions[] = new String[101];
    public static String Answers[][] = new String[101][15];
    public static String CAnswers[] = new String[101];
    public static JTextField Tbox[] = new JTextField[15];
    public static JTextField NrBox = new JTextField();
    public static JTextArea Qbox = new JTextArea();
    public static JTextField Abox[] = new JTextField[12];

    public static void initialiseTextboxes(){

        for(int i=1;i<=8;i++){
            Tbox[i] = new JTextField(30);
            Font f = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 3);
            Tbox[i].setFont(f);
            Window.frame.add(Tbox[i]);
        }
        for(int i=1;i<=10;i++){
            Abox[i] = new JTextField();
            Window.frame.add(Abox[i]);
        }
        NrBox.setText("3");
        Window.frame.add(Qbox);
        Window.frame.add(NrBox);
        for(int i=1;i<=100;i++)NrVar[i]=3;
    }

    public static void drawBackground(Graphics g){

        g.drawImage(UserImput.background,(Menu.ScreenWidth-UserImput.background.getWidth(null))/2,(Menu.ScreenHeight-UserImput.background.getHeight(null))/2,null);
        g.setColor(new Color(0,0,0,200));
        g.fillRect(0,0,Menu.ScreenWidth,Menu.ScreenHeight);
        g.setColor(Color.BLACK);
        g.fillRect(Menu.ScreenWidth/20,Menu.ScreenHeight/10,Menu.ScreenWidth*9/10,Menu.ScreenHeight*9/10-Menu.ScreenHeight/20);
        g.setColor(Color.WHITE);
        g.drawRect(Menu.ScreenWidth/20,Menu.ScreenHeight/10,Menu.ScreenWidth*9/10,Menu.ScreenHeight*9/10-Menu.ScreenHeight/20);
    }

    public static void drawPanelButton(Graphics g,int x, int y, int width, int height, String Name){

        if (Menu.X_hovered>x&&Menu.X_hovered<x+width&&Menu.Y_hovered>y&&Menu.Y_hovered<y+height&&Menu.MousePressed==true) {

            if (Name.equals("Creeaza test")) {
                createTest = true;System.out.println("c");
                planTest = false;
            } else if(Name.equals("Teste Planificate")){
                createTest = false;
                planTest = true;
            }
            if(copyMousePressed==false){
                Setari.ButtonSound("button_click.wav");
            }
        }
        if((createTest == true && Name.equals("Creeaza test"))|| (planTest == true && Name.equals("Teste Planificate"))){
            g.setColor(new Color(0, 0, 0));
            g.fill3DRect(x, y, width, height, true);

            Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);


        } else if (Menu.X_hovered>x&&Menu.X_hovered<x+width&&Menu.Y_hovered>y&&Menu.Y_hovered<y+height) {

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

    public static void generatePanelButtons(Graphics g){

        drawPanelButton(g,Menu.ScreenWidth/20,Menu.ScreenHeight/20,Menu.ScreenWidth*9/20,Menu.ScreenHeight/15,"Creeaza test");
        drawPanelButton(g,Menu.ScreenWidth*10/20,Menu.ScreenHeight/20,Menu.ScreenWidth*9/20,Menu.ScreenHeight/15,"Teste Planificate");

    }

    public static void makeSubmitButton(Graphics g, int x, int y, int width, int height, String Name) {


        if (Menu.X_hovered >= x && Menu.X_hovered <= x + width && Menu.Y_hovered >= y && Menu.Y_hovered <= y + height)
            Next = true;

        if (Menu.MousePressed == false && copyMousePressed == true && Next == true) {

                if(stage1==true){
                    try{
                        submitData();
                        check = true;
                    }catch(java.lang.NumberFormatException e){
                        check = true;
                    }
                }
                else if(stage2==true){
                    Questions[currentQuestion] = Qbox.getText();
                    for(int i=1;i<=10;i++){
                        Answers[currentQuestion][i]=Abox[i].getText();
                    }
                    uploadData();
                }
        }

        if (Menu.MousePressed == true && Next == true) {
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

        } else if (Next == true) {


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
        Next = false;
    }

    public static void create_stage1(Graphics g, int startX, int startY, int width, int height){

        String Nume[] = new String[10];
        Nume[1] = "Numele testului:";
        Nume[2] = "Clasa(Numar+Litera):";
        Nume[3] = "Data(zz/mm/aaaa):";
        Nume[4] = "Timpul inceperii(oo:mm:ss):";
        Nume[5] = "Timpul incheierii(oo:mm:ss):";
        Nume[6] = "Numar intrebari tip grila:";
        Nume[7] = "Numar intrebari cu eseu:";
        Nume[8] = "Procentaj eseuri(%):";

        Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize/2);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(220, 220, 220));
        g.setFont(small);

        for(int i=1;i<=8;i++){
            g.drawString(Nume[i],startX+(i-1)/4*width/2,startY+(i-1)%4*height/4-height/16);
            Tbox[i].setBounds(startX+(i-1)/4*width/2,startY+(i-1)%4*height/4,width*2/9,height/16);
            Font f = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 2);
            Tbox[i].setFont(f);
            Tbox[i].setVisible(true);
        }
        Qbox.setVisible(false);
        makeSubmitButton(g,startX+width*7/9,startY+height*7/9,width/8,height/12,"Continua");

    }

    public static  void create_stage2(Graphics g){

        for(int i=1;i<=8;i++){
            Tbox[i].setVisible(false);
        }
        g.setColor(Color.WHITE);
        if(currentQuestion<=MPQuestions) {
            g.drawRect(Menu.ScreenWidth / 4 - Menu.ScreenWidth / 80, Menu.ScreenHeight / 3 - Menu.ScreenHeight / 10, Menu.ScreenWidth / 2 + Menu.ScreenWidth / 40, Menu.ScreenHeight / 10 + Menu.ScreenHeight / 9);
        }
        else{
            g.drawRect(Menu.ScreenWidth / 4 - Menu.ScreenWidth / 80, Menu.ScreenHeight / 3 - Menu.ScreenHeight / 10, Menu.ScreenWidth / 2 + Menu.ScreenWidth / 40, Menu.ScreenHeight / 4 + Menu.ScreenHeight / 9);
        }
        Font f = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 2);
        Font small = new Font("Futura", Font.PLAIN, UserImput.FontSize/2);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(220, 220, 220));
        g.setFont(small);
        g.drawString("Imtrebarea "+currentQuestion+":",Menu.ScreenWidth/4,Menu.ScreenHeight/3-Menu.ScreenHeight/20);
        if(currentQuestion<=MPQuestions) {

            g.drawString("Numar de variante(3-10):",(Menu.ScreenWidth-metricsx.stringWidth("Numar de variante(3-10):"))/2,Menu.ScreenHeight/3+Menu.ScreenHeight/6);
            Qbox.setBounds(Menu.ScreenWidth / 4, Menu.ScreenHeight / 3, Menu.ScreenWidth / 2, Menu.ScreenHeight / 10);
        }
        else{
            Qbox.setBounds(Menu.ScreenWidth / 4, Menu.ScreenHeight / 3, Menu.ScreenWidth / 2, Menu.ScreenHeight / 4);
        }
        Qbox.setLineWrap(true);
        Qbox.setFont(f);
        Qbox.setVisible(true);
        if(currentQuestion<=MPQuestions) {
            NrBox.setBounds(Menu.ScreenWidth / 2 - Menu.ScreenWidth / 40, Menu.ScreenHeight / 3 + Menu.ScreenHeight / 5, Menu.ScreenWidth / 20, Menu.ScreenHeight / 20);
            NrBox.setFont(f);
            NrBox.setHorizontalAlignment(0);
            NrBox.setVisible(true);
            if (NrBox.getText().length() > 0) NumarVariante = Integer.valueOf(NrBox.getText());
            if (NumarVariante > 10) NumarVariante = 10;
            if (NumarVariante < 3) NumarVariante = 3;
            drawVariants(g, Menu.ScreenWidth / 3, Menu.ScreenHeight * 3 / 5, Menu.ScreenWidth / 3, Menu.ScreenHeight / 4, NumarVariante);
        }
        else{
            NrBox.setVisible(false);
            for(int i=1;i<=10;i++)Abox[i].setVisible(false);
        }
        if(currentQuestion<NrQuestions)drawNextButton(g);
        else if(currentQuestion == NrQuestions)makeSubmitButton(g,Menu.ScreenWidth * 3 / 4 + Menu.ScreenWidth / 20,Menu.ScreenHeight /3,Menu.ScreenWidth / 15,Menu.ScreenHeight / 14,"Incarca");
        if(currentQuestion>1)drawPreviousButton(g);

    }

    public static void drawCell(Graphics g, int x, int y, int widht, int height, int p){

        Font f = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 2);
        Abox[p].setBounds(x+height,y,widht-height,height);
        Abox[p].setFont(f);
        Abox[p].setVisible(true);

        int mx = Menu.X_hovered;
        int my = Menu.Y_hovered;

        if(mx>x&&mx<x+widht&&my>y&&my<y+height&& Menu.MousePressed ==true){

            PAnswers[currentQuestion] = p;
            CAnswers[currentQuestion] = Abox[p].getText();
            if(copyMousePressed==false) Setari.ButtonSound("button_click.wav");
        }

        if(PAnswers[currentQuestion]==p){
            g.setColor(new Color(255, 236, 52));
            g.fill3DRect(x, y, widht, height, true);
            g.setColor(new Color(255,236,52));
            g.drawRect(x, y, widht, height);
            g.setColor(new Color(255,236,52));
            g.fill3DRect(x, y, height, height, true);
            g.setColor(new Color(255,236,52));
            g.drawRect(x, y, height, height);
            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 2);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(2, 2, 2));
            g.setFont(small);
            g.drawString(String.valueOf(p), x + (height - metricsx.stringWidth(String.valueOf(p))) / 2, y + metricsy.getHeight());
        }
        else {
            g.setColor(new Color(20, 20, 20));
            g.fill3DRect(x, y, widht, height, false);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, widht, height);
            g.setColor(Color.LIGHT_GRAY);
            g.fill3DRect(x, y, height, height, false);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, height, height);
            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 2);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(2, 2, 2));
            g.setFont(small);
            g.drawString(String.valueOf(p), x + (height - metricsx.stringWidth(String.valueOf(p))) / 2, y + metricsy.getHeight());
        }
    }

    public static void drawVariants(Graphics g, int x, int y, int width, int height, int Nr){

        NrVar[currentQuestion] = NumarVariante;
        if(NumarVariante<6) {
            for (int i = 1; i <= NumarVariante; i++) {
                drawCell(g, Menu.ScreenWidth / 3, Menu.ScreenHeight * 3 / 5 + (i - 1) * Menu.ScreenHeight / 20, Menu.ScreenWidth / 3, Menu.ScreenHeight / 20, i);
            }
            for (int i = NumarVariante + 1; i <= 10; i++) {
                Abox[i].setVisible(false);
            }
        }
        else{

            for (int i = 1; i <= 5; i++) {
                drawCell(g, Menu.ScreenWidth / 9, Menu.ScreenHeight * 3 / 5 + (i - 1) * Menu.ScreenHeight / 20, Menu.ScreenWidth / 3, Menu.ScreenHeight / 20, i);
            }
            for (int i = 6; i <= NumarVariante; i++) {
                drawCell(g, Menu.ScreenWidth - Menu.ScreenWidth/9-Menu.ScreenWidth/3, Menu.ScreenHeight * 3 / 5 + (i - 6) * Menu.ScreenHeight / 20, Menu.ScreenWidth / 3, Menu.ScreenHeight / 20, i);
            }
            for (int i = NumarVariante + 1; i <= 10; i++) {
                Abox[i].setVisible(false);
            }

        }
    }

    public static void drawPreviousButton(Graphics g) {
        int x = Menu.X_hovered;
        int y = Menu.Y_hovered;
        int ButtonHeight = Menu.ScreenHeight / 14;
        int ButtonWidth = Menu.ScreenWidth / 60;
        int ButtonX = Menu.ScreenWidth - Menu.ScreenWidth *3 / 4 - Menu.ScreenWidth / 14;
        int ButtonY = Menu.ScreenHeight /3;

        if (Menu.MousePressed == true && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(200, 200, 200));
            if (copyMousePressed == false){
                Setari.ButtonSound("button_click.wav");
                Questions[currentQuestion] = Qbox.getText();
                for(int i=1;i<=10;i++){
                    Answers[currentQuestion][i]=Abox[i].getText();
                }
            }
        } else if (x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(85, 64, 11));
            if (copyMousePressed == true) {
                currentQuestion--;
                for(int i=1;i<=10;i++){
                    Abox[i].setText(Answers[currentQuestion][i]);
                }
                Qbox.setText(Questions[currentQuestion]);
                NrBox.setText(String.valueOf(NrVar[currentQuestion]));
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
        int ButtonX = Menu.ScreenWidth * 3 / 4 + Menu.ScreenWidth / 20;
        int ButtonY = Menu.ScreenHeight /3;

        if (Menu.MousePressed == true && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(200, 200, 200));
            if (copyMousePressed == false) {
                Setari.ButtonSound("button_click.wav");
                Questions[currentQuestion] = Qbox.getText();
                for(int i=1;i<=10;i++){
                    Answers[currentQuestion][i]=Abox[i].getText();
                }
            }
        } else if (x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(85, 64, 11));
            if (copyMousePressed == true) {
                currentQuestion++;
                for(int i=1;i<=10;i++){
                    Abox[i].setText(Answers[currentQuestion][i]);
                }
                Qbox.setText(Questions[currentQuestion]);
                NrBox.setText(String.valueOf(NrVar[currentQuestion]));
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

    public static void Paint(Graphics g){
        drawBackground(g);
        generatePanelButtons(g);
        if(createTest ==true){
            if(stage1 == true){
                create_stage1(g,Menu.ScreenWidth/6,Menu.ScreenHeight/4,Menu.ScreenWidth*5/6,Menu.ScreenHeight*3/4);
                if(check==true)chackdata(g);
            }
            else if(stage2 == true){
                create_stage2(g);
            }
        }
        else if(planTest== true){
            for(int i=1;i<=8;i++){
                Tbox[i].setVisible(false);
            }
            Qbox.setVisible(false);
            for(int i=1;i<=NumarVariante;i++)Abox[i].setVisible(false);
            NrBox.setVisible(false);
        }

        copyMousePressed = Menu.MousePressed;
    }

    public static void updateDimensions(){

        UserImput.initilaizeDimensions();

    }

    public  static void memoriseData(){

        changeQuestion = false;

    }

    public static void chackdata(Graphics g){

        boolean DataChecked = true;
        for(int i=1;i<=8;i++){
            if(Tbox[i].getText().length()<1){
                UserImput.DialogBox(g,"Casuta goala!",Menu.ScreenWidth/8+(i-1)/4*Menu.ScreenWidth*5/6/2,Menu.ScreenHeight/4+(i-1)%4*Menu.ScreenHeight*3/16+Menu.ScreenHeight/60,UserImput.FontSize/2);System.out.println("uuu");
                DataChecked = false;
            }
            else{
                char c[] = Tbox[i].getText().toCharArray();
                if(i>5){
                    boolean DataChecked2 = true;
                    for(int j=0;j<c.length;j++){
                        if(c[j]!=0&&(c[j]<'0'||c[j]>'9')){DataChecked2 = false;DataChecked=false;}
                    }
                    if(DataChecked2==false) UserImput.DialogBox(g,"Introdu un numar!",Menu.ScreenWidth/8+(i-1)/4*Menu.ScreenWidth*5/6/2,Menu.ScreenHeight/4+(i-1)%4*Menu.ScreenHeight*3/16+Menu.ScreenHeight/60,UserImput.FontSize/2);
                }
                else if(i==3){
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("MMMM d, yyyy hh:mm:ss",Locale.ENGLISH);
                    try{
                        date = format.parse(getDate(Tbox[3].getText()+'/'+Tbox[4].getText()));
                        Date date2 = new Date();
                        if(Tbox[i].getText().length()<10){
                            DataChecked = false;
                            UserImput.DialogBox(g,"Data incorecta!",Menu.ScreenWidth/8+(i-1)/4*Menu.ScreenWidth*5/6/2,Menu.ScreenHeight/4+(i-1)%4*Menu.ScreenHeight*3/16+Menu.ScreenHeight/60,UserImput.FontSize/2);
                        }
                        else if(date.compareTo(date2)<0){
                            DataChecked = false;
                            UserImput.DialogBox(g,"Data incorecta!",Menu.ScreenWidth/8+(i-1)/4*Menu.ScreenWidth*5/6/2,Menu.ScreenHeight/4+(i-1)%4*Menu.ScreenHeight*3/16+Menu.ScreenHeight/60,UserImput.FontSize/2);
                        }
                    } catch (ParseException e) {
                        DataChecked = false;
                        UserImput.DialogBox(g,"Data incorecta!",Menu.ScreenWidth/8+(i-1)/4*Menu.ScreenWidth*5/6/2,Menu.ScreenHeight/4+(i-1)%4*Menu.ScreenHeight*3/16+Menu.ScreenHeight/60,UserImput.FontSize/2);
                    }
                }
                else if(i==4||i==5){

                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("MMMM d, yyyy hh:mm:ss",Locale.ENGLISH);
                    try{
                        date = format.parse(getDate(Tbox[3].getText()+'/'+Tbox[4].getText()));
                        if(Tbox[i].getText().length()<8){
                            DataChecked = false;
                            UserImput.DialogBox(g,"Timp incorect!",Menu.ScreenWidth/8+(i-1)/4*Menu.ScreenWidth*5/6/2,Menu.ScreenHeight/4+(i-1)%4*Menu.ScreenHeight*3/16+Menu.ScreenHeight/60,UserImput.FontSize/2);
                        }
                    } catch (ParseException e) {
                        DataChecked = false;
                        UserImput.DialogBox(g,"Timp incorect!",Menu.ScreenWidth/8+(i-1)/4*Menu.ScreenWidth*5/6/2,Menu.ScreenHeight/4+(i-1)%4*Menu.ScreenHeight*3/16+Menu.ScreenHeight/60,UserImput.FontSize/2);
                    }

                }
                else if(i==2){

                    if(Tbox[i].getText().length()<3){
                        DataChecked = false;
                        UserImput.DialogBox(g,"Clasa incorecta!",Menu.ScreenWidth/8+(i-1)/4*Menu.ScreenWidth*5/6/2,Menu.ScreenHeight/4+(i-1)%4*Menu.ScreenHeight*3/16+Menu.ScreenHeight/60,UserImput.FontSize/2);
                    }
                    else if(c[0]<'0'||c[0]>'9'||c[1]<'0'||c[1]>'9'||c[2]<'A'||c[2]>'Z'){
                        DataChecked = false;
                        UserImput.DialogBox(g,"Clasa incorecta!",Menu.ScreenWidth/8+(i-1)/4*Menu.ScreenWidth*5/6/2,Menu.ScreenHeight/4+(i-1)%4*Menu.ScreenHeight*3/16+Menu.ScreenHeight/60,UserImput.FontSize/2);
                    }
                }

            }
        }

        if(DataChecked==true){
            check = false;
            stage1 = false;
            stage2 = true;
        }
    }

    public static void submitData(){

        NrQuestions = Integer.valueOf(Tbox[6].getText())+Integer.valueOf(Tbox[7].getText());
        MPQuestions = Integer.valueOf(Tbox[6].getText());

            TestName = Tbox[1].getText();
            TestClass = Tbox[2].getText();
            TestDate = Tbox[3].getText();
            TestStart = Tbox[4].getText();
            TestEnd = Tbox[5].getText();
            TestPercentage = Integer.valueOf(Tbox[8].getText());

    }

    public static String getDate(String str){

        char date[] = str.toCharArray();
        int year=0,mounth = 0,day=0,hour=0,minute=0,second=0,c=0,k=0;
        char aux[] = new char[30];
        int n=2;
        for(int i = 0;i<date.length;i++){

            if(date[i]=='/'||date[i] == ':'||i==date.length-1){
                k++;
                if(k==3){
                    for(int j=0;j<n;j++){
                        year= year*10+(aux[j]-'0');
                    }
                    n=2;
                }
                else if(k==2){
                    for(int j=0;j<n;j++){
                        mounth= mounth*10+(aux[j]-'0');
                    }
                    n=4;
                }
                else if(k==1){
                    for(int j=0;j<n;j++){
                        day= day*10+(aux[j]-'0');
                    }
                    n=2;
                }
                else if(k==4){
                    for(int j=0;j<n;j++){
                        hour= hour*10+(aux[j]-'0');
                    }
                    n=2;
                }
                else if(k==5){
                    aux[c++]=date[i];
                    for(int j=0;j<n;j++){
                        minute= minute*10+(aux[j]-'0');
                    }
                    n=2;
                }
                c=0;
                for(int j=0;j<=10;j++)aux[j]=0;
            }
            else{
                aux[c++]=date[i];
            }
        }
        String Month=new String();
        if(mounth==1){
            Month = "January";
        }
        else if(mounth == 2){
            Month = "February";
        }
        else if(mounth==3){
            Month = "March";
        }
        else if(mounth == 4){
            Month = "April";
        }
        else if(mounth == 5){
            Month = "May";
        }
        else if(mounth==6){
            Month = "June";
        }
        else if(mounth == 7){
            Month = "July";
        }
        else if(mounth == 8){
            Month = "August";
        }
        else if(mounth==9){
            Month = "September";
        }
        else if(mounth==10){
            Month = "October";
        }
        else if(mounth == 11){
            Month = "November";
        }
        else if(mounth==12){
            Month = "December";
        }
        String Minute = new String();
        if(minute<10)Minute = '0'+String.valueOf(minute);
        else Minute = String.valueOf(minute);
        String Second = new String();
        if(second<10)Second = '0'+String.valueOf(second);
        else Second = String.valueOf(second);
        str = Month+' '+String .valueOf(day)+", "+String.valueOf(year)+' '+String.valueOf(hour)+':'+Minute+':'+Second;
        System.out.println(str);
        return str;

    }

    public static void uploadData(){


        Date date = new Date();
        Date date2 = new Date();
        try {
            String TestID = Menu.Maner.getNextTestID();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DateFormat format = new SimpleDateFormat("MMM d, yyyy hh:mm:ss",Locale.ENGLISH);
        try {
            date = format.parse(getDate(TestDate +'/'+TestStart));
            date2 = format.parse(getDate(TestDate+'/'+TestEnd));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        char letter = 0;
        int clasa = 0;
        char Class[] = TestClass.toCharArray();
        for(int i=0;i<Class.length;i++){
            if(Class[i]>'0'&&Class[i]<'9'){
                clasa = clasa * 10 + (Class[i]-'0');
            }
            else{
                letter = Class[i];
            }
        }
        HashMap<Integer,Question> map = new HashMap<>();
        for(int i=1;i<=NrQuestions;i++){

            ArrayList<String> a = new ArrayList<>();
            for(int j=1;j<=NrVar[i];j++){
                a.add(Answers[i][j]);
            }System.out.println(Answers[i][1]);
            Question question;
            if(i<=MPQuestions) question = new Question(Questions[i],a,Answers[i][PAnswers[i]]);
            else  question = new Question(Questions[i],Answers[i][PAnswers[i]]);
            map.put(i,question);

        }
        try {
            Test test = new Test(Menu.Maner.getNextTestID(),TestName,"Geo",date,date2,clasa,String.valueOf(letter),map);
            System.out.println(test.toString());
            Menu.Maner.createTest(test,UserImput.teacher);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void Run(){

        updateDimensions();
        if(changeQuestion == true)memoriseData();

    }
}
