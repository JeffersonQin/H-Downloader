package edu.sfls.Jeff.JavaDev.HDownloader.UILib;

import java.util.TreeMap;

public class JQFileChooser extends JQFileChoosingHandler {

    public JQFileChooser() { super(); }

    public JQFileChooser(JQLogArea textArea) { super(textArea); }

    public JQFileChooser(TreeMap<String, String> filter) { super(filter); }

    public JQFileChooser(JQLogArea textArea, TreeMap<String, String> filter) { super(textArea, filter); }

    public String getAndShow() {
        this.initFilter();
        if (this.jqShow()) {
            return this.getSelectedFile().getPath();
        } else return null;
    }

}