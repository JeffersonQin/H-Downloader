package edu.sfls.Jeff.JavaDev.HDownloader.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class H_DownloaderMenuBar extends MenuBar {


    private static final long serialVersionUID = 1L;

    private Menu fileMenu = new Menu("File");
    private Menu viewMenu = new Menu("View");

    private MenuItem menuItem_newWindow = new MenuItem("New Window", new MenuShortcut(KeyEvent.VK_N, false));

    private MenuItem menuItem_closeWindow = new MenuItem("Close Window", new MenuShortcut(KeyEvent.VK_W, false));

    private Menu menu_fontSize = new Menu("Font Size");
    private MenuItem menuItem_fontSize_increase = new MenuItem("Increase", new MenuShortcut(KeyEvent.VK_EQUALS, false));
    private MenuItem menuItem_fontSize_decrease = new MenuItem("Decrease", new MenuShortcut(KeyEvent.VK_MINUS, false));

    private H_Downloader downloader;

    private void init() {

        ActionListener newWindow = e -> new H_Downloader().launch();
        ActionListener closeWindow = e -> downloader.dispose();

        menuItem_newWindow.addActionListener(newWindow);
        menuItem_closeWindow.addActionListener(closeWindow);

        fileMenu.add(menuItem_newWindow);
        fileMenu.add("-");
        fileMenu.add(menuItem_closeWindow);
        menu_fontSize.add(menuItem_fontSize_increase);
        menu_fontSize.add(menuItem_fontSize_decrease);
        viewMenu.add(menu_fontSize);

        this.add(fileMenu); this.add(menu_fontSize);
    }

    public H_DownloaderMenuBar(H_Downloader downloader) {
        this.downloader = downloader;
        init();
    }

    public void setIncreaseFontSizeAction(ActionListener l) { this.menuItem_fontSize_increase.addActionListener(l); }

    public void setDecreaseFontSizeAction(ActionListener l) { this.menuItem_fontSize_decrease.addActionListener(l); }

}