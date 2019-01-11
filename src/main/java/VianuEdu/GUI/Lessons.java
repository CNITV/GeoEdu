package VianuEdu.GUI;

import VianuEdu.backend.LessonLibrary.Lesson;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Lessons {

    static Lesson currentLesson;
    static int currentSlide = 1;
    static int NumberofSlides = 0;
    static boolean copyMousePressed = false;
    static Image slides[] = new Image[101];

    public static void findLesson(int grade, String ID){
        try {
            currentLesson  = Menu.Maner.getLesson("Geo",ID);
            ArrayList<byte[]> pages = currentLesson.getContent();
            NumberofSlides = pages.size();
            ContentBrowser.showLesson = true;
            for(int i=0;i<NumberofSlides;i++){
                slides[i+1] = ImageIO.read(new ByteArrayInputStream(pages.get(i)));
                slides[i+1] = getImage(slides[i+1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image getImage(Image image){
        double scale = 1;
        if(Math.abs(Menu.ScreenHeight - image.getHeight(null)) < Math.abs(Menu.ScreenWidth - image.getWidth(null))){
            scale = (double)Menu.ScreenHeight/(double)image.getHeight(null);
        }
        else{
            scale = (double)(Menu.ScreenWidth)/(double)image.getWidth(null);
        }
        System.out.println(scale);
        return image.getScaledInstance((int)(image.getWidth(null)*scale),(int)(image.getHeight(null)*scale),3);
    }

    public static void drawSlide(Graphics g){
        g.drawImage(slides[currentSlide],(Menu.ScreenWidth-slides[currentSlide].getWidth(null))/2,(Menu.ScreenHeight-slides[currentSlide].getHeight(null))/2,null);
    }

    public static void drawPreviousButton(Graphics g) {
        int x = Menu.X_hovered;
        int y = Menu.Y_hovered;
        int ButtonHeight = Menu.ScreenHeight / 10;
        int ButtonWidth = Menu.ScreenWidth / 60;
        int ButtonX = Menu.ScreenWidth - Menu.ScreenWidth * 5 / 6 - Menu.ScreenWidth / 10;
        int ButtonY = Menu.ScreenHeight / 2 - Menu.ScreenHeight / 30;

        if (Menu.MousePressed == true && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(200, 200, 200));
            if (copyMousePressed == false) Setari.ButtonSound("button_click.wav");
        } else if (x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(85, 64, 11));
            if (copyMousePressed == true) {
                currentSlide--;
            }
        } else {
            g.setColor(new Color(0,0,0,20));
        }
        g.fill3DRect(ButtonX - ButtonWidth / 2, ButtonY - ButtonHeight / 4, ButtonWidth * 2 + ButtonHeight / 10, ButtonHeight * 3 / 2, true);
        if (Menu.MousePressed == true && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(10, 10, 10));
        } else if (x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(251, 214, 71));
        } else {
            g.setColor(new Color(255,255,255,80));
    }

        for (int i = 0; i <= ButtonWidth / 4; i++) {
            g.drawLine(ButtonX + ButtonWidth + i, ButtonY, ButtonX + i, ButtonY + ButtonHeight / 2);
            g.drawLine(ButtonX + i, ButtonY + ButtonHeight / 2, ButtonX + ButtonWidth + i, ButtonY + ButtonHeight);
        }

    }

    public static void drawNextButton(Graphics g) {

        int x = Menu.X_hovered;
        int y = Menu.Y_hovered;
        int ButtonHeight = Menu.ScreenHeight / 10;
        int ButtonWidth = Menu.ScreenWidth / 60;
        int ButtonX = Menu.ScreenWidth * 5 / 6 + Menu.ScreenWidth / 20;
        int ButtonY = Menu.ScreenHeight /2 - Menu.ScreenHeight / 30;

        if (Menu.MousePressed == true && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(200, 200, 200));
            if (copyMousePressed == false) Setari.ButtonSound("button_click.wav");
        } else if (x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(85, 64, 11));
            if (copyMousePressed == true) {
                currentSlide++;

            }
        } else {
            g.setColor(new Color(0,0,0,20));
        }
        g.fill3DRect(ButtonX - ButtonWidth / 2, ButtonY - ButtonHeight / 4, ButtonWidth * 2 + ButtonHeight / 10, ButtonHeight * 3 / 2, true);
        if (Menu.MousePressed == true && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(10, 10, 10));
        } else if (x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(251, 214, 71));
        } else {
            g.setColor(new Color(255,255,255,80));
        }

        for (int i = 0; i <= ButtonWidth / 4; i++) {
            g.drawLine(ButtonX + i, ButtonY, ButtonX + ButtonWidth + i, ButtonY + ButtonHeight / 2);
            g.drawLine(ButtonX + ButtonWidth + i, ButtonY + ButtonHeight / 2, ButtonX + i, ButtonY + ButtonHeight);
        }


    }

    public static void drawExitButton(Graphics g){

        Button.draw(g,Menu.ScreenWidth*5/6,Menu.ScreenHeight*4/5+ Menu.ScreenHeight/15,Menu.ScreenWidth/12,Menu.ScreenHeight/22,Color.WHITE,"Iesire",GeoEduMenu.Relativesize/3);
        if(Button.isPressed){
            deleteData();
                ContentBrowser.showLesson = false;

        }
    }

    public static void Paint(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,Menu.ScreenWidth,Menu.ScreenHeight);
        drawSlide(g);
        if(currentSlide<NumberofSlides)drawNextButton(g);
        if(currentSlide>1)drawPreviousButton(g);
        //drawMap(g);
        drawExitButton(g);
        copyMousePressed = Menu.MousePressed;
    }

    public static void deleteData(){
        slides = new Image[101];
        currentLesson = null;
        currentSlide = 1;
        NumberofSlides = 0;

    }

    public static void Run(){
        UserImput.initilaizeDimensions();
    }
}
