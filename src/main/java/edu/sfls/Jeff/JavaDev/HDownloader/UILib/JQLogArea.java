package edu.sfls.Jeff.JavaDev.HDownloader.UILib;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class JQLogArea extends JScrollPane {

    public JTextPane logArea;
    private SimpleAttributeSet ErrorAttributes = new SimpleAttributeSet();
    private SimpleAttributeSet NormalAttributes = new SimpleAttributeSet();
    private SimpleAttributeSet InformationAttributes = new SimpleAttributeSet();
    private SimpleAttributeSet ProcessAttributes = new SimpleAttributeSet();
    private SimpleAttributeSet LinkAttributes = new SimpleAttributeSet();

    private void initScroll() {
        this.logArea = new JTextPane();
        setLayout(new ScrollPaneLayout.UIResource());
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setViewport(createViewport());
        setVerticalScrollBar(createVerticalScrollBar());
        setHorizontalScrollBar(createHorizontalScrollBar());
        if (this.logArea != null) {
            setViewportView(this.logArea);
        }
        this.setOpaque(true);
        updateUI();

        if (!this.getComponentOrientation().isLeftToRight()) {
            viewport.setViewPosition(new Point(Integer.MAX_VALUE, 0));
        }
    }

    private void initStyle() {
        StyleConstants.setForeground(ErrorAttributes, Color.RED);
        StyleConstants.setBold(ErrorAttributes, true);
        StyleConstants.setUnderline(ErrorAttributes, false);
        StyleConstants.setItalic(ErrorAttributes, false);
        StyleConstants.setForeground(NormalAttributes, Color.BLACK);
        StyleConstants.setUnderline(NormalAttributes, false);
        StyleConstants.setBold(NormalAttributes, false);
        StyleConstants.setItalic(NormalAttributes, false);
        StyleConstants.setForeground(InformationAttributes, Color.ORANGE);
        StyleConstants.setItalic(InformationAttributes, true);
        StyleConstants.setBold(InformationAttributes, false);
        StyleConstants.setUnderline(InformationAttributes, false);
        StyleConstants.setForeground(ProcessAttributes, Color.MAGENTA);
        StyleConstants.setItalic(ProcessAttributes, true);
        StyleConstants.setUnderline(ProcessAttributes, false);
        StyleConstants.setBold(ProcessAttributes, false);
        StyleConstants.setForeground(LinkAttributes, Color.BLUE);
        StyleConstants.setUnderline(LinkAttributes, true);
        StyleConstants.setBold(LinkAttributes, false);
        StyleConstants.setItalic(LinkAttributes, false);
    }

    public JQLogArea() {
        initScroll();
        initStyle();
    }

    public void scrollToBottom() {
        JScrollBar scrollBar = this.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());
    }

    public void Append(String s, SimpleAttributeSet attributeSet) {
        int len = this.logArea.getDocument().getLength();
        this.logArea.setCaretPosition(len);
        this.logArea.setCharacterAttributes(attributeSet, false);
        this.logArea.replaceSelection(s);
    }

    public void Append(String s) { this.Append(s, this.NormalAttributes); }

    public void AppendLn(String s) { this.Append(s + "\n"); }

    public void AppendError(String s) { this.Append(s, this.ErrorAttributes); }

    public void AppendErrorLn(String s) { this.AppendError(s + "\n"); }

    public void AppendInformation(String s) { this.Append(s, this.InformationAttributes); }

    public void AppendInformationLn(String s) { this.AppendInformation(s + "\n"); }

    public void AppendProcess(String s) { this.Append(s, this.ProcessAttributes); }

    public void AppendProcessLn(String s) { this.AppendProcess(s + "\n"); }

    public void AppendLink(String s) { this.Append(s, this.LinkAttributes); }

    public void AppendLinkLn(String s) { this.AppendLink(s + "\n"); }

}
