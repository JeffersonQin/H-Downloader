package edu.sfls.Jeff.JavaDev.HDownloader.UILib;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Set;
import java.util.TreeMap;

public class JQFileChoosingHandler extends JQChoosingHandler {

    private TreeMap<String, String> filter = new TreeMap<>();

    public JQFileChoosingHandler() { super(); }

    public JQFileChoosingHandler(JQLogArea textArea) { super(textArea); }

    public void addFilter(TreeMap<String, String> filter) {
        Set<String> nameSet = filter.keySet();
        for (String name : nameSet) {
            this.filter.put(name, filter.get(name));
        }
    }

    public void setFilter(TreeMap<String, String> filter) {
        this.filter.clear();
        addFilter(filter);
    }

    public JQFileChoosingHandler(TreeMap<String, String> filter) {
        this.setFilter(filter);
    }

    public JQFileChoosingHandler(JQLogArea textArea, TreeMap<String, String> filter) {
        this.setTextArea(textArea);
        this.setFilter(filter);
    }

    protected void initFilter() {
        Set<String> nameSet = filter.keySet();
        for (String name : nameSet) {
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(filter.get(name));
                }

                @Override
                public String getDescription() {
                    return name;
                }
            };
            this.addChoosableFileFilter(fileFilter);
        }
    }

}
