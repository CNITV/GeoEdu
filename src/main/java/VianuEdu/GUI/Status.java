package VianuEdu.GUI;

import VianuEdu.backend.TestLibrary.Grade;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Status {

    public static String PInfo[] = new String[101];
    public static String PInfoName[] = new String[101];
    public static String newPassword;
    public static String Mark[] = new String[101];
    public static boolean copyMousePressed = Menu.MousePressed;
    public static boolean changePassword=false;
    public static JPasswordField Npassword = new JPasswordField();

    public static void drawBackGround(Graphics g){
        g.drawImage(UserImput.background, (Menu.ScreenWidth - UserImput.background.getWidth(null)) / 2, (Menu.ScreenHeight - UserImput.background.getHeight(null)) / 2, null);
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Menu.ScreenWidth, Menu.ScreenHeight);
    }

    public static void drawInfoBox(Graphics g, int x, int y, int width, int height, int i){

        Font small = new Font("Calibri", Font.BOLD, UserImput.FontSize *15/20);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setFont(small);
        g.drawString(String.valueOf(PInfoName[i]), x + width / 8- metricsx.stringWidth(String.valueOf(PInfoName[i])) / 2, y + height / 2 + metricsy.getHeight() / 4);
        small = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 2);
        g.drawString(String.valueOf(PInfo[i]), x + width *27/50 - metricsx.stringWidth(String.valueOf(PInfo[i])) / 2, y + height / 2 + metricsy.getHeight() / 4);

    }

    public static void drawChangePassword(Graphics g, int x, int y, int width, int height){

        if(Menu.X_hovered>x&&Menu.X_hovered<x+width&&Menu.Y_hovered>y&&Menu.Y_hovered<y+height &&Menu.MousePressed){

            g.setColor(Color.BLACK);
            g.fill3DRect(x,y,width,height,true);
            g.setColor(Color.BLACK);
            g.fill3DRect(x+3,y+3,width-6,height-6,true);
            g.setColor(Color.WHITE);
            g.drawRect(x,y,width,height);

            Font small = new Font("Calibri", Font.BOLD, UserImput.FontSize/2 );
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(Color.WHITE);
            g.setFont(small);
            g.drawString(String.valueOf("Schimba parola"), x + width / 2 - metricsx.stringWidth(String.valueOf("Schimba parola")) / 2, y + height / 2 + metricsy.getHeight() / 4);


        }
        else if(Menu.X_hovered>x&&Menu.X_hovered<x+width&&Menu.Y_hovered>y&&Menu.Y_hovered<y+height){

            if(copyMousePressed==true){
                Setari.ButtonSound("button_click.wav");
              changePassword = true;
            }

            g.setColor(new Color(241,207,98));
            g.fill3DRect(x,y,width,height,false);
            g.setColor(new Color(241,207,98));
            g.fill3DRect(x+3,y+3,width-6,height-6,true);
            g.setColor(Color.BLACK);
            g.drawRect(x,y,width,height);

            Font small = new Font("Calibri", Font.BOLD, UserImput.FontSize/2 );
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf("Schimba parola"), x + width / 2 - metricsx.stringWidth(String.valueOf("Schimba parola")) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }
        else {
            g.setColor(Color.GRAY);
            g.fill3DRect(x, y, width, height, false);
            g.setColor(Color.LIGHT_GRAY);
            g.fill3DRect(x + 3, y + 3, width - 6, height - 6, true);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);

            Font small = new Font("Calibri", Font.BOLD, UserImput.FontSize/2);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf("Schimba parola"), x + width / 2 - metricsx.stringWidth(String.valueOf("Schimba parola")) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }

    }

    public static void drawExit(Graphics g, int x ,int y, int width, int height, String Name){

        if (Menu.MousePressed == true && Menu.X_hovered>x&&Menu.X_hovered<x+width&&Menu.Y_hovered>y&&Menu.Y_hovered<y+height) {
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


            if(copyMousePressed == true){
                for(int i=1;i<=5;i++)Mark[i]=null;
                GeoEduMenu.profil=false;
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

    public static void drawProfileInfo(Graphics g, int x, int y, int width, int height){

        g.setColor(Color.WHITE);
        g.drawRect(x,y,width,height);

        int Height = height/5;
        for(int i=0;i<5;i++){
            drawInfoBox(g,x,y+i*Height,width,Height,i);
        }
        drawChangePassword(g,x+width*28/40,y+height*11/30,width/5,height/5);

    }

    public static void drawOKButton(Graphics g, int x, int y, int width, int height, String Name){
        if (Menu.MousePressed == true && Menu.X_hovered>x&&Menu.X_hovered<x+width&&Menu.Y_hovered>y&&Menu.Y_hovered<y+height) {
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


            if(copyMousePressed == true){

                try {
                    newPassword = new String(Npassword.getPassword());
                    Menu.Maner.changeStudentPassword(UserImput.student,newPassword);
                    Npassword.setVisible(false);
                    System.out.println(newPassword);
                    changePassword=false;
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                }catch(java.lang.NullPointerException e){
                   e.printStackTrace();
                }

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

    public static void drawPasswordBox(Graphics g, int x, int y, int width, int height){

        Font small = new Font("Calibri", Font.BOLD, UserImput.FontSize);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(Color.BLACK);
        g.fill3DRect(x,y,width,height,false);
        g.setColor(new Color(255, 255, 255));
        g.drawRect(x,y,width,height);
        g.setFont(small);
        g.drawString(String.valueOf("Parola noua:"), x + width / 2 - metricsx.stringWidth(String.valueOf("Parola noua:")) / 2, y + height / 4 + metricsy.getHeight() / 4);
        small = new Font("Calibri", Font.PLAIN, UserImput.FontSize/2);

        Npassword.setBounds(x+width/6,y+height*2/5,width*2/3,height/5);
        Npassword.setFont(small);
        Npassword.setHorizontalAlignment(0);
        Npassword.setVisible(true);

        drawOKButton(g,x+width/2-width/10,y+height*3/4,width/5,height/6,"Schimba");
    }

    public static void drawMarkBox(Graphics g, int x, int y, int width, int height, String Name){
        g.setColor(Color.WHITE);
        g.drawRect(x,y,width,height);
        Font small;
        if(Name=="Note:"){
            small = new Font("Calibri", Font.BOLD, UserImput.FontSize *10/15);
        }
        else {
            small = new Font("Calibri", Font.PLAIN, UserImput.FontSize*10/15);
        }
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setFont(small);
        g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
    }

    public static void drawMarks(Graphics g, int x, int y, int width, int height){

        int Width = width/5;
        for(int i=0;i<5;i++){
            if(i==0){
                drawMarkBox(g,x+i*Width,y,Width,height,"Note:");
            }else if(Mark[i]==null){
                drawMarkBox(g,x + i * Width, y, Width, height, "");
            }else {
                drawMarkBox(g,x + i * Width, y, Width, height, Mark[i]);
            }
        }

    }

    public static void Paint(Graphics g){

        drawBackGround(g);
        if(changePassword==true){
            drawPasswordBox(g, Menu.ScreenWidth/3,Menu.ScreenHeight/3,Menu.ScreenWidth/3,Menu.ScreenHeight/3);
        }
        else {
            drawProfileInfo(g, Menu.ScreenWidth / 10, Menu.ScreenHeight / 10, Menu.ScreenWidth * 4 / 5, Menu.ScreenHeight * 2 / 5);
            drawMarks(g,Menu.ScreenWidth/10,Menu.ScreenHeight*3/5,Menu.ScreenWidth*4/5,Menu.ScreenHeight/5);
            drawExit(g,Menu.ScreenWidth*5/6,Menu.ScreenHeight*5/6,Menu.ScreenWidth/16,Menu.ScreenHeight/16,"Iesire");
        }
        copyMousePressed = Menu.MousePressed;
    }

    public static void initializeNames(){

        PInfoName[0] = "Nume:";
        PInfoName[1] = "Prenume:";
        PInfoName[2] = "Initiala tatalui:";
        PInfoName[3] = "ID utilizator:";
        PInfoName[4] = "Clasa:";


    }

    public static void findInfo(){

        PInfo[0] = UserImput.student.getFirstName();
        PInfo[1] = UserImput.student.getLastName();
        PInfo[2] = UserImput.student.getFathersInitial();
        PInfo[3] = UserImput.student.getAccount().getUserName();
        PInfo[4] = UserImput.student.getGrade()+UserImput.student.getGradeLetter();
        ArrayList<Grade> a;
        try {
            a=Menu.Maner.getCurrentGrades(UserImput.student,"Geo");
            for(int i=0;i<a.size();i++){
                Mark[i+1]=String.valueOf(a.get(i).getCurrentGrade());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void Run(){

        initializeNames();
        UserImput.initilaizeDimensions();

    }

}
