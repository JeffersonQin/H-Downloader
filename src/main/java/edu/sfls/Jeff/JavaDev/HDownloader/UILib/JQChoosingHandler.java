package edu.sfls.Jeff.JavaDev.HDownloader.UILib;

import javax.swing.*;
import java.awt.*;

public class JQChoosingHandler extends JFileChooser {

    private JQLogArea textArea;

    public JQChoosingHandler() {}

    public JQChoosingHandler(JQLogArea textArea) { setTextArea(textArea); }

    public void setTextArea(JQLogArea textArea) { this.textArea = textArea; }

    protected boolean jqShow() {
        int flag = 0;
        try {
            flag = this.showOpenDialog(null);
        } catch (HeadlessException head) {
            head.printStackTrace();
            if (this.textArea != null) textArea.AppendErrorLn("Open File Chooser ERROR!");
            if (this.textArea != null) textArea.AppendErrorLn(head.getMessage() + "\n" + head.getLocalizedMessage());
        }
        return flag == JFileChooser.APPROVE_OPTION;
    }

}
