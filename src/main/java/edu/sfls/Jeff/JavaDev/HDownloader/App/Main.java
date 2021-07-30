package edu.sfls.Jeff.JavaDev.HDownloader.App;

import edu.sfls.Jeff.JavaDev.HDownloader.Interface.H_Downloader;

public class Main {

    public static void main(String args[]) {
        System.setProperty("java.awt.headless", "false");
        new H_Downloader().launch();
    }

}