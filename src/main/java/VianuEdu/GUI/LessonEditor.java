package VianuEdu.GUI;

import VianuEdu.backend.LessonLibrary.Lesson;
import VianuEdu.backend.TestLibrary.Test;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static VianuEdu.GUI.TestEditor.*;

public class LessonEditor {

    private static String[] Letter = new String[11];
    public static String Class[] = new String[11];
    static boolean Editor = true;
    static boolean Calendar = false;
    static boolean stage1 = true;
    static boolean stage2 = false;
    static boolean loadLesson = false;
    static boolean upload = true;
    static boolean copyMousePressed = false;
    static JTextField Tbox[] = new JTextField[2];
    static String Name[] = new String[10];
    public static int currentSlide = 1;
    static int NumberofSlides = 1;
    public static int Grade;
    public static String GradeLetter;
    public static String Title;
    public static BufferedImage Slides[] = new BufferedImage[101];
    public static Image PreviewSlides[] = new Image[101];
    public static ArrayList<Lesson> plannedLessons[] = new ArrayList[101];
    public static Lesson currentLesson;

    public LessonEditor(){

    }

    public static void initialiseTextBoxes(){
        for (int i = 0; i < 2; i++) {
            Tbox[i] = new JTextField(30);
            Font f = new Font("Calibri", Font.PLAIN, GeoEduMenu.Relativesize / 3);
            Tbox[i].setFont(f);
            Tbox[i].setHorizontalAlignment(0);
            Window.frame.add(Tbox[i]);
        }
    }

    public static void drawPanelButton(Graphics g, int x, int y, int width, int height, String Name) {

        if (Menu.X_hovered > x && Menu.X_hovered < x + width && Menu.Y_hovered > y && Menu.Y_hovered < y + height && Menu.MousePressed == true) {

            if (Name.equals("Creeaza Lectie")) {
                Editor = true;
                Calendar = false;
            } else if (Name.equals("Tabel Lectii")) {
                Editor = false;
                Calendar = true;
            }
            if (copyMousePressed == false) {
                Setari.ButtonSound("button_click.wav");
                if(Calendar==true){
                    findLessons();
                }
            }
        }
        if ((Editor == true && Name.equals("Creeaza Lectie")) || (Calendar == true && Name.equals("Tabel Lectii"))) {
            g.setColor(new Color(0, 0, 0));
            g.fill3DRect(x, y, width, height, true);

            Font small = new Font("Futura", Font.PLAIN, GeoEduMenu.Relativesize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(220, 220, 220));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);


        } else if (Menu.X_hovered > x && Menu.X_hovered < x + width && Menu.Y_hovered > y && Menu.Y_hovered < y + height) {

            g.setColor(new Color(231, 198, 63));
            g.fill3DRect(x, y, width, height, false);

            Font small = new Font("Futura", Font.PLAIN, GeoEduMenu.Relativesize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(255, 231, 63));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        } else {
            g.setColor(new Color(235, 233, 233));
            g.fill3DRect(x, y, width, height, false);

            Font small = new Font("Futura", Font.PLAIN, GeoEduMenu.Relativesize);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(new Color(0, 0, 0));
            g.setFont(small);
            g.drawString(String.valueOf(Name), x + width / 2 - metricsx.stringWidth(String.valueOf(Name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }
    }

    public static void generatePanelButtons(Graphics g) {

        drawPanelButton(g, Menu.ScreenWidth / 20, Menu.ScreenHeight / 20, Menu.ScreenWidth * 9 / 20, Menu.ScreenHeight / 15, "Creeaza Lectie");
        drawPanelButton(g, Menu.ScreenWidth * 10 / 20, Menu.ScreenHeight / 20, Menu.ScreenWidth * 9 / 20, Menu.ScreenHeight / 15, "Tabel Lectii");

    }

    public static void drawTextBoxes(Graphics g){

        Name[0]= "Numele Lectiei:";
        Name[1] = "Clasa:";
        Font small = new Font("Futura", Font.PLAIN, GeoEduMenu.Relativesize);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(220, 220, 220));
        g.setFont(small);

        for(int i=0; i<2; i++){
            g.drawString(Name[i],Menu.ScreenWidth/2-metricsx.stringWidth(Name[i])-Menu.ScreenWidth/40,Menu.ScreenHeight/4 + i*Menu.ScreenHeight/6 + Menu.ScreenHeight/10);
            Tbox[i].setBounds(Menu.ScreenWidth/2,Menu.ScreenHeight/4 + i*Menu.ScreenHeight/6,Menu.ScreenWidth/5,Menu.ScreenHeight/10);
            Tbox[i].setVisible(true);
            Font f = new Font("Calibri", Font.PLAIN, GeoEduMenu.Relativesize / 2);
            Tbox[i].setFont(f);
        }
    }

    public static void drawExitButton(Graphics g){
        Button.draw(g,Menu.ScreenWidth/3, Menu.ScreenHeight*2/3+Menu.ScreenHeight/8, Menu.ScreenWidth/3, Menu.ScreenHeight/10, Color.LIGHT_GRAY,"Iesire",GeoEduMenu.Relativesize);
        if(Button.isPressed){
            GeoEduMenu.uploadLesson = false;
            Tbox[0].setVisible(false);
            Tbox[1].setVisible(false);
        }
    }
    public static void drawContinueButton(Graphics g){
        Button.draw(g,Menu.ScreenWidth/3, Menu.ScreenHeight*2/3, Menu.ScreenWidth/3, Menu.ScreenHeight/10, Color.LIGHT_GRAY,"Continua",GeoEduMenu.Relativesize);
        if(Button.isPressed && checkBoxes()){
            stage1 = false;
            stage2 = true;
            Title = Tbox[0].getText();
            Grade = Integer.valueOf(Tbox[1].getText());
        }
    }
    public static void drawNextButton(Graphics g) {

        int x = Menu.X_hovered;
        int y = Menu.Y_hovered;
        int ButtonHeight = Menu.ScreenHeight / 14;
        int ButtonWidth = Menu.ScreenWidth / 60;
        int ButtonX = Menu.ScreenWidth * 3 / 4 + Menu.ScreenWidth / 20;
        int ButtonY = Menu.ScreenHeight / 3;

        if (Menu.MousePressed == true && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(200, 200, 200));
            if (copyMousePressed == false) {
                Setari.ButtonSound("button_click.wav");
            }
        } else if (x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(85, 64, 11));
            if (copyMousePressed == true) {

               if(Slides[currentSlide]!=null){
                   if(currentSlide == NumberofSlides)NumberofSlides++;
                   currentSlide++;
               }
               else{
                   if(currentSlide<NumberofSlides)currentSlide++;
               }
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
    public static void drawPreviousButton(Graphics g) {
        int x = Menu.X_hovered;
        int y = Menu.Y_hovered;
        int ButtonHeight = Menu.ScreenHeight / 14;
        int ButtonWidth = Menu.ScreenWidth / 60;
        int ButtonX = Menu.ScreenWidth - Menu.ScreenWidth * 3 / 4 - Menu.ScreenWidth / 14;
        int ButtonY = Menu.ScreenHeight / 3;

        if (Menu.MousePressed == true && x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(200, 200, 200));
            if (copyMousePressed == false) {
                Setari.ButtonSound("button_click.wav");
            }
        } else if (x > ButtonX - ButtonWidth / 2 && x < ButtonX + ButtonWidth * 2 + ButtonWidth / 10 && y > ButtonY - ButtonHeight / 4 && y < ButtonY + ButtonHeight * 3 / 2) {
            g.setColor(new Color(85, 64, 11));
            if (copyMousePressed == true) {
                if(currentSlide>1)currentSlide--;
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
    public static void drawMap(Graphics g) {

        int width = (Menu.ScreenWidth - GeoEduMenu.PanelWidth) / NumberofSlides;
        if (width > Menu.ScreenWidth / 50) width = Menu.ScreenWidth / 50;
        int height = width;
        int x = (Menu.ScreenWidth - NumberofSlides * width) / 2;
        int y = Menu.ScreenHeight / 7;

        for (int i = 0; i < NumberofSlides; i++) {
            drawCell2(g, x + i * width, y, width, height, i + 1);
        }

    }
    public static void drawCell2(Graphics g, int x, int y, int width, int height, int Name) {

        int xm = Menu.X_hovered;
        int ym = Menu.Y_hovered;


        if (xm > x && xm < x + width && ym > y && ym < y + height && Menu.MousePressed == true) {
            if (copyMousePressed == false) {
                Setari.ButtonSound("button_click.wav");
            }
            currentSlide = Name;
        } else if (xm > x && xm < x + width && ym > y && ym < y + height) {
            if (copyMousePressed == true) {
                if (Name == currentSlide) {
                }
            }
        }
        if (Name == currentSlide) {
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

        }  else if (xm > x && xm < x + width && ym > y && ym < y + height) {
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
    public static void drawImage(Graphics g){
        int x = Menu.ScreenWidth/4;
        int y = Menu.ScreenHeight/4;
        int width = Menu.ScreenWidth/2;
        int height = Menu.ScreenHeight*2/3-Menu.ScreenHeight/8;
        if(PreviewSlides[currentSlide].getWidth(null)>width || PreviewSlides[currentSlide].getHeight(null)>height){
            double scale ;
            if(PreviewSlides[currentSlide].getWidth(null)-width>PreviewSlides[currentSlide].getHeight(null)-height) scale = (double)(width)/(double)(PreviewSlides[currentSlide].getWidth(null));
            else scale = (double)(height)/(double)(Slides[currentSlide].getHeight(null));
            System.out.println(Slides[currentSlide].getHeight(null));
            PreviewSlides[currentSlide] = PreviewSlides[currentSlide].getScaledInstance((int) (PreviewSlides[currentSlide].getWidth(null)*scale),(int)(PreviewSlides[currentSlide].getHeight(null)*scale),3);
        }
         g.drawImage(PreviewSlides[currentSlide],Menu.ScreenWidth/2-PreviewSlides[currentSlide].getWidth(null)/2,y,null);

        if(Menu.X_hovered>=x && Menu.X_hovered<= x+width && Menu.Y_hovered>=y && Menu.Y_hovered<=y+height && Menu.MousePressed ){
            if(!copyMousePressed){
                PreviewSlides[currentSlide] = null;
                Slides[currentSlide] = null;
                uploadPhotoMenu(currentSlide);
            }
        }
        else{
            upload = true;
        }

    }
    public static void drawEmptySlot(Graphics g){
        int x = Menu.ScreenWidth/4;
        int y = Menu.ScreenHeight/5;
        int width = Menu.ScreenWidth/2;
        int height = Menu.ScreenHeight*2/3;
        g.setColor(Color.WHITE);
        g.draw3DRect(x,y,width,height,true);
        Font small = new Font("Futura", Font.PLAIN, GeoEduMenu.Relativesize);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        g.setColor(new Color(255, 231, 63));
        g.setFont(small);
        g.drawString(String.valueOf("+ Adauga slide"), x + width / 2 - metricsx.stringWidth(String.valueOf("+ Adauga slide")) / 2, y + height / 2 + metricsy.getHeight() / 4);
        if(Menu.X_hovered>=x && Menu.X_hovered<= x+width && Menu.Y_hovered>=y && Menu.Y_hovered<=y+height && Menu.MousePressed ){
            if(!copyMousePressed){
                uploadPhotoMenu(currentSlide);
            }
        }
    }
    public static void uploadPhotoMenu(int p){

        if(upload) {
            upload = false;
            JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            fc.setCurrentDirectory(new java.io.File("C:/"));
            fc.setDialogTitle("Alege imaginea:");
            fc.setFileFilter(new FileNameExtensionFilter("JPEG File", "jpg"));
            int rv = fc.showOpenDialog(null);
            if (rv == JFileChooser.APPROVE_OPTION) {
                File image = fc.getSelectedFile();
                try {
                    Slides[p] = ImageIO.read(image);
                    PreviewSlides[p] = ImageIO.read(image);
                    upload = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}
    public static void drawSubmitButton(Graphics g){

        Button.draw(g,Menu.ScreenWidth*4/5,Menu.ScreenHeight*3/4,Menu.ScreenWidth/10,Menu.ScreenHeight/20,Color.WHITE,"Incarca lectia",GeoEduMenu.Relativesize/3);
        if(Button.isPressed){
            if(checkData()){
                submitData();
                for(int i=1;i<=NumberofSlides;i++){
                    Slides[i] = null;
                    PreviewSlides[i] = null;
                }
                NumberofSlides = 1;
                currentSlide = 1;
                stage1 = true;
                stage2 = false;
                Tbox[0].setText(null);
                Tbox[1].setText(null);
                Title = null;
                Grade = 0;
                GradeLetter = null;
                GeoEduMenu.uploadLesson = false;
            }
        }
    }
    public static void drawExit1Button(Graphics g){

        Button.draw(g,Menu.ScreenWidth*4/5,Menu.ScreenHeight*3/4+ Menu.ScreenHeight/15,Menu.ScreenWidth/10,Menu.ScreenHeight/20,Color.WHITE,"Iesire",GeoEduMenu.Relativesize/3);
        if(Button.isPressed){
            if(checkData()){
                GeoEduMenu.uploadLesson = false;
            }
        }
    }
    public static void drawEditor(Graphics g){
        Tbox[0].setVisible(false);
        Tbox[1].setVisible(false);
        drawMap(g);
        if(Slides[currentSlide]!=null)drawImage(g);
        else drawEmptySlot(g);
        drawNextButton(g);
        drawPreviousButton(g);
        drawSubmitButton(g);
        drawExit1Button(g);
    }

    public static void drawEditBox(Graphics g, int x, int y, int width, int height, Lesson lesson, int i){

        editHovered = false;
        if(Menu.X_hovered>x&&Menu.X_hovered<x+width&&Menu.Y_hovered>y&&Menu.Y_hovered<y+height && Menu.MousePressed==true){
            g.setColor(Color.LIGHT_GRAY);
            g.fill3DRect(x,y,width,height,true);
            g.setColor(Color.BLACK);
            g.drawRect(x,y,width,height);

            Font small = new Font("Calibri", Font.PLAIN, GeoEduMenu.Relativesize/6);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.drawString(String.valueOf(editMenuName[i]), x + width / 2 - metricsx.stringWidth(String.valueOf(editMenuName[i]))*2/3 , y + height / 2 + metricsy.getHeight() / 4);
            editHovered = true;
        }
        else if(Menu.X_hovered>x&&Menu.X_hovered<x+width&&Menu.Y_hovered>y&&Menu.Y_hovered<y+height){
            if(copyMousePressed==true){
                Setari.ButtonSound("button_click.wav");
                editMenu = false;
                if(i==0) {
                    loadLesson=true;
                    // System.out.println(testID);
                    loadData(lesson);

                }
                else if(i==1){
                    loadData(lesson);
                }
            }
            editHovered = true;
            g.setColor(new Color(255,214,71));
            g.fill3DRect(x,y,width,height,false);
            g.setColor(Color.WHITE);
            g.drawRect(x,y,width,height);

            Font small = new Font("Calibri", Font.PLAIN, GeoEduMenu.Relativesize/6);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.drawString(String.valueOf(editMenuName[i]), x + width / 2 - metricsx.stringWidth(String.valueOf(editMenuName[i]))*2/3 , y + height / 2 + metricsy.getHeight() / 4);
        }
        else {
            g.setColor(Color.BLACK);
            g.fill3DRect(x, y, width, height, false);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, width, height);

            Font small = new Font("Calibri", Font.PLAIN, GeoEduMenu.Relativesize / 6);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.drawString(String.valueOf(editMenuName[i]), x + width / 2 - metricsx.stringWidth(String.valueOf(editMenuName[i]))*2/3, y + height / 2 + metricsy.getHeight() / 4);
        }
    }

    public static void drawEditMenu(Graphics g){
        for(int i=0;i<1;i++){
            drawEditBox(g,editMenuX*Menu.ScreenWidth/currentScreenWidth,editMenuY*Menu.ScreenHeight/currentScreenHeight+i*Menu.ScreenHeight/20,Menu.ScreenWidth/10,Menu.ScreenHeight/20,currentLesson,i);
        }
        if( Menu.X_hovered>editMenuX*Menu.ScreenWidth/currentScreenWidth && Menu.X_hovered<editMenuX*Menu.ScreenWidth/currentScreenWidth+Menu.ScreenWidth/10 && Menu.Y_hovered>editMenuY*Menu.ScreenHeight/currentScreenHeight &&Menu.Y_hovered<editMenuY*Menu.ScreenHeight/currentScreenHeight+Menu.ScreenHeight/20*(editMenuLengh)){
            editHovered = true;
        }
        else if(Menu.MousePressed == true ){
            editMenu = false;
            editHovered = false;
        }
    }

    public static void drawPlannedLessons(Graphics g, int x, int y, int width, int height, int i, int k) {

        int size = width / plannedLessons[i].get(k).getTitle().length() ;
        if(size>GeoEduMenu.Relativesize/4)size = GeoEduMenu.Relativesize/4;
        Font small = new Font("Calibri", Font.PLAIN, size);
        FontMetrics metricsy = g.getFontMetrics(small);
        FontMetrics metricsx = g.getFontMetrics(small);
        if(height>Menu.ScreenHeight/20)height = Menu.ScreenHeight/20;

        if (Menu.X_hovered > x && Menu.X_hovered < x + width && Menu.Y_hovered > y && Menu.Y_hovered < y + height) {
            System.out.println(editHovered);
            if(Menu.MousePressed == false && copyMousePressed == true && editHovered == false){
                editMenuX = Menu.X_hovered ;
                editMenuY = Menu.Y_hovered ;
                currentScreenWidth = Menu.ScreenWidth;
                currentScreenHeight = Menu.ScreenHeight;
                editMenu = true;
                currentLesson = plannedLessons[i].get(k);
            }
            editHovered = false;
            int size2 = width / plannedLessons[i].get(k).getTitle().length();
            Font small2 = new Font("Calibri", Font.BOLD, size*4/3);
            FontMetrics metricsy1 = g.getFontMetrics(small2);
            FontMetrics metricsx1 = g.getFontMetrics(small2);
            g.setColor(Color.GRAY);
            g.fill3DRect(x, y, width, height, true);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, width, height);


            g.setFont(small2);
            g.drawString(String.valueOf(plannedLessons[i].get(k).getTitle()), x + width / 2 - metricsx1.stringWidth(String.valueOf(plannedLessons[i].get(k).getTitle())) / 2, y +height / 2 + metricsy1.getHeight() / 4);
        } else {
            g.setColor(Color.GRAY);
            g.fill3DRect(x, y, width, height, true);
            g.setColor(Color.WHITE);
            g.drawRect(x, y, width, height);
            metricsx = g.getFontMetrics(small);

            g.setFont(small);
            g.drawString(String.valueOf(plannedLessons[i].get(k).getTitle()), x + width / 2 - metricsx.stringWidth(String.valueOf(plannedLessons[i].get(k).getTitle())) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }

    }
    
    public static void drawBox(Graphics g, int x, int y, int width, int height, int letter, int clasa){

        g.setColor(new Color(40,40,40));
        g.fillRect(x,y,width,height);

       if(letter == 0){

            String grade = Class[clasa];
            g.setColor(Color.WHITE);
            g.drawRect(x,y,width,height);

            Font small = new Font("Calibri", Font.PLAIN, UserImput.FontSize / 2);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setFont(small);
            g.drawString(String.valueOf(grade), x + width / 2 - metricsx.stringWidth(String.valueOf(grade)) / 2, y + height / 2 + metricsy.getHeight() / 4);
        }
        else{

            g.setColor(Color.WHITE);
            g.drawRect(x,y,width,height);

            try {
                int h;
                try {
                    h = height / plannedLessons[clasa].size();
                    if(h>Menu.ScreenHeight/20)h = Menu.ScreenHeight/20;
                    for (int i = 0; i < plannedLessons[clasa].size(); i++) {
                        drawPlannedLessons(g, x, y + i * h, width, height / plannedLessons[clasa].size(), clasa, i);
                    }
                }catch(java.lang.ArithmeticException e){

                }
            }catch (java.lang.NullPointerException e){

            }
        }

    }
    
    public static void drawTable(Graphics g, int StartX, int StartY, int Width, int Height){

        int width = Width/(4);
        int height = Height/(11);
        Class[1] = "Clasa a 9-a";
        Class[2] = "Clasa a 10-a";
        Class[3] = "Clasa a 11-a";
        Class[4] = "Clasa a 12-a";
        editMenuName[0] = "Editeaza";
        editMenuName[1] = "Copiaza";
        editMenuName[2] = "Sterge";

            for(int j = 0;j<=3;j++){
                drawBox(g,StartX+j*width,StartY,width,height,0,j+1);
            }
        for(int j = 0;j<=3;j++){
            drawBox(g,StartX+j*width,StartY + height,width,height*8,1,j+1);
        }
        if(editMenu==true)drawEditMenu(g);
    }
    
    public static void Paint(Graphics g){
        TestEditor.drawBackground(g);
        generatePanelButtons(g);
        if(Editor){
            if(stage1){
                drawTextBoxes(g);
                drawExitButton(g);
                drawContinueButton(g);
            }
            else if(stage2){
                drawEditor(g);
            }

        }
        else if(Calendar){
            Tbox[0].setVisible(false);
            Tbox[1].setVisible((false));
            drawTable(g,Menu.ScreenWidth/10,Menu.ScreenHeight/7,Menu.ScreenWidth*4/5,Menu.ScreenHeight*5/6);
        }
        copyMousePressed = Menu.MousePressed;
    }

    public static void Run(){

    }

    public static boolean checkBoxes(){

        if(Tbox[0]==null || Tbox[1] == null){
            return false;
        }
        char c[] =Tbox[1].getText().toCharArray();
        try{
            if(c[0]<'0'|| c[0]>'9')return false;
        }catch(java.lang.ArrayIndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    public static void findLessons(){
        for(int i=1;i<= 90; i ++){
                plannedLessons[i] = new ArrayList<>();
                plannedLessons[i].clear();
        }

        for(int i=1;i<=4;i++){
            try {
                ArrayList<String> lesson = Menu.Maner.listLessons("Geo",i+8);
                System.out.println(lesson.toString());
                    for(int j=0;j<lesson.size();j++){
                        try {
                            System.out.println(lesson.get(j).split(" ")[0]);
                            plannedLessons[i].add(Menu.Maner.getLesson("Geo", lesson.get(j).split(" ")[0]));
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
    
    public static boolean checkData(){
        for(int i=1;i<NumberofSlides;i++){
            if(Slides[i] == null)return false;
        }
        return true;
    }
    public static void submitData(){
        Lesson lesson = new Lesson(Title,UserImput.teacher,Grade);
        for(int i=1;i<=NumberofSlides;i++){
            if(Slides[i]!=null){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    ImageIO.write(Slides[i], "jpg", baos);
                    baos.flush();
                    lesson.addPage(baos.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            Menu.Maner.uploadLesson("Geo",lesson,UserImput.teacher);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadData( Lesson lesson){
        Tbox[0].setText(lesson.getTitle());
        Tbox[1].setText(String.valueOf(lesson.getGrade()));
        NumberofSlides = lesson.getContent().size();
        ArrayList<byte[]> slides = lesson.getContent();
        for(int i=1;i<=NumberofSlides;i++){
            try {
                Slides[i] = ImageIO.read(new ByteArrayInputStream(slides.get(i-1)));
                PreviewSlides[i] = ImageIO.read(new ByteArrayInputStream(slides.get(i-1)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stage1 = true;
        stage2 = false;
        Calendar = false;
        Editor = true;
    }
}
