package VianuEdu.GUI;

import java.awt.*;

public class ContentBrowser {

    public static int Lection[][] = new int[10][10001];
    public static int Test[][] = new int[10][10001];
    public static int ContentStart = Menu.ScreenHeight / 4;
    public static int ContentBrowserWidth = GeoEduMenu.PanelWidth;
    public static int ContentBrowserHeight = GeoEduMenu.PanelHeight / 2;
    public static int BrowserY = GeoEduMenu.PanelHeight / 4;
    public static int BrowserX = GeoEduMenu.Rightpanel;
    public static boolean Search = false;

    public static void initialiseDimensions() {

        ContentStart = Menu.ScreenHeight / 4;
        ContentBrowserWidth = GeoEduMenu.PanelWidth;
        ContentBrowserHeight = GeoEduMenu.PanelHeight / 2;
        BrowserY = GeoEduMenu.PanelHeight / 4;
        BrowserX = GeoEduMenu.Rightpanel;

    }

    public static void drawBar(Graphics g) {


    }

    public static void drawContent(Graphics g) {


    }

    public static void drawBrowser(Graphics g) {

        g.setColor(new Color(10, 10, 10));
        g.fillRect(GeoEduMenu.Rightpanel, 0, GeoEduMenu.PanelWidth, Menu.ScreenHeight / 4);
        g.fillRect(GeoEduMenu.Rightpanel, Menu.ScreenHeight * 3 / 4, GeoEduMenu.PanelWidth, Menu.ScreenHeight / 4);
        g.setColor(new Color(105, 105, 105));
        g.fillRect(BrowserX, BrowserY, ContentBrowserWidth, ContentBrowserHeight);
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

        drawContent(g);
        drawBrowser(g);
        drawBar(g);

    }

    public static void Run() {

        initialiseDimensions();

    }


}
