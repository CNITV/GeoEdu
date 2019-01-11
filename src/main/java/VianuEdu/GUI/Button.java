package VianuEdu.GUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Button {

    int x;
    int y;
    int width;
    int height;
    Color color = new Color(0,0,0);
    static Color color_hovered = new Color(255, 171, 66);
    static Color color_pressed = new Color(221, 221, 221);
    String name;
    String font;
    int font_size;
    static boolean isPressed = false;
    static boolean setPressed = false;
    static boolean MousePressed = false;
    static boolean copyMousePressed = false;


    static void draw(Graphics g, int x, int y, int width, int height, Color color, String name, int font_size){
        int X_hovered = MouseInfo.getPointerInfo().getLocation().x - Menu.XFrame - Setari.FrameBarX;
        int Y_hovered = MouseInfo.getPointerInfo().getLocation().y - Menu.YFrame - Setari.FrameBarY;
        isPressed = false;
        color_pressed = new Color(255-color.getRed(),255-color.getGreen(),255-color.getBlue());
        if(setPressed || Menu.MousePressed && X_hovered >= x && X_hovered<= x + width && Y_hovered >= y && Y_hovered <= y + height){
            if(copyMousePressed != Menu.MousePressed){
                Setari.ButtonSound("button_click.wav");
                isPressed = true;
            }else isPressed = false;
            g.setColor(color_pressed);
            g.fill3DRect(x,y,width,height, false);
            Font small = new Font("Futura", Font.PLAIN, font_size);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(color);
            g.setFont(small);
            g.drawString(String.valueOf(name), x + width / 2 - metricsx.stringWidth(String.valueOf(name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
            copyMousePressed = Menu.MousePressed;
        }
        else if(X_hovered >= x && X_hovered<= x + width && Y_hovered >= y && Y_hovered <= y + height){
            g.setColor(color_hovered);
            g.fill3DRect(x,y,width,height, false);
            Font small = new Font("Futura", Font.PLAIN, font_size);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(color_pressed);
            g.setFont(small);
            g.drawString(String.valueOf(name), x + width / 2 - metricsx.stringWidth(String.valueOf(name)) / 2, y + height / 2 + metricsy.getHeight() / 4);
            copyMousePressed = Menu.MousePressed;
        }
        else{
            g.setColor(color);
            g.fill3DRect(x,y,width,height, false);
            Font small = new Font("Futura", Font.PLAIN, font_size);
            FontMetrics metricsy = g.getFontMetrics(small);
            FontMetrics metricsx = g.getFontMetrics(small);
            g.setColor(color_pressed);
            g.setFont(small);
            g.drawString(String.valueOf(name), x + width / 2 - metricsx.stringWidth(String.valueOf(name)) / 2, y + height / 2 + metricsy.getHeight() / 4);

        }

    }

}
