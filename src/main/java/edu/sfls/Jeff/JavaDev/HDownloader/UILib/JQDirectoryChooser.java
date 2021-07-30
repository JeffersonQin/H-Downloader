package edu.sfls.Jeff.JavaDev.HDownloader.UILib;

import javax.swing.*;
import java.awt.*;

public class JQDirectoryChooser extends JQChoosingHandler {

    private JQLogArea textArea;

    public JQDirectoryChooser() { super(); }
    public JQDirectoryChooser(JQLogArea textArea) { super(textArea); }

    public String getAndShow() {
        this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (this.jqShow()) {
            return this.getSelectedFile().getPath();
        } else return null;
    }

}
