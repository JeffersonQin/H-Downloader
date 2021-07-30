package edu.sfls.Jeff.JavaDev.HDownloader.UILib;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

public class JQMultiFileChooser extends JQFileChoosingHandler {

    public JQMultiFileChooser() { super(); }

    public JQMultiFileChooser(JQLogArea textArea) { super(textArea); }

    public JQMultiFileChooser(TreeMap<String, String> filter) { super(filter); }

    public JQMultiFileChooser(JQLogArea textArea, TreeMap<String, String> filter) { super(textArea, filter); }

    public ArrayList<String> getAndShow() {
        this.setMultiSelectionEnabled(true);
        this.initFilter();
        if (this.jqShow()) {
            ArrayList<String> pathList = new ArrayList<>();
            File files[] = this.getSelectedFiles();
            for (int i = 0; i < files.length; i ++)
                pathList.add(files[i].getPath());
            return pathList;
        } else return null;
    }

}
