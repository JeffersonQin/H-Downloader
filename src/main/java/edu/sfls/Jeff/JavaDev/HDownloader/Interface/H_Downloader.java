package edu.sfls.Jeff.JavaDev.HDownloader.Interface;

import edu.sfls.Jeff.JavaDev.HDownloader.PDFLib.PDFUtil;
import edu.sfls.Jeff.JavaDev.HDownloader.UILib.JQDirectoryChooser;
import edu.sfls.Jeff.JavaDev.HDownloader.EHLib.AnalyzeRunnable;
import edu.sfls.Jeff.JavaDev.HDownloader.EHLib.DownloadRunnable;
import edu.sfls.Jeff.JavaDev.HDownloader.UILib.JQLogArea;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class H_Downloader {

    private JFrame frame = new JFrame("H-Downloader");
    private JPanel controlPanel = new JPanel();
    private JPanel urlPanel = new JPanel();
    private JPanel urlInputPanel = new JPanel();
    private JTextField urlTextField = new JTextField();
    private JLabel urlLabel = new JLabel("Source URL: ");
    private JButton analyzeButton = new JButton("Analyze");
    private JPanel filePanel = new JPanel();
    private JPanel fileInputPanel = new JPanel();
    private JTextField directoryTextField = new JTextField("/");
    private JLabel directoryLabel = new JLabel("Path to save: ");
    private JButton directoryChoosingButton = new JButton("Choose a Directory");
    private JPanel downloadPanel = new JPanel();
    private JButton downloadButton = new JButton("Download");
    private JQLogArea textArea = new JQLogArea();
    private JCheckBox pdfCheckBox = new JCheckBox("Automatically Create PDF");

    private List<String> ls_url = new ArrayList<>();
    private List<String> ls_url_list = new ArrayList<>();

    private TreeMap<Integer, File> downloadImages = new TreeMap<>();

    private Thread analyzeThread;
    private int[] downloadThreadFinishedCount = new int[1];
    private Integer totalCountForDownloadThread = 0;

    private final int MAX_CONCURRENT_THREAD = 5;

    private class analyzeActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            analyzeButton.setEnabled(false);
            analyzeThread = new Thread(new AnalyzeRunnable(urlTextField.getText(), textArea, ls_url, ls_url_list));
            analyzeThread.start();
            new Thread(new AnalyzeThreadCheckRunnable()).start();
        }

    }

    private class fileChooserActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JQDirectoryChooser fileChooser = new JQDirectoryChooser(textArea);
            String path = fileChooser.getAndShow();
            if (path != null) directoryTextField.setText(path);
//            TreeMap<String, String> filters = new TreeMap<>();
//            filters.put("Excel Files", ".xlsx");
//            filters.put("97-2003 Excel Files", ".xls");
//            filters.put("Text", ".txt");
//            JQMultiFileChooser fileChooser = new JQMultiFileChooser(textArea, filters);
//            ArrayList<String> paths = fileChooser.getAndShow();
//            for (String path : paths) textArea.logln(path);
        }

    }

    private class DownloadThreadCheckRunnable implements Runnable {
        @Override
        public void run() {
            while (downloadThreadFinishedCount[0] < totalCountForDownloadThread) {
                System.out.println(downloadThreadFinishedCount[0]);
                try { Thread.sleep(50); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            textArea.AppendLn("\n----Download Complete----");
            if (pdfCheckBox.isValid()) {
                PDFUtil pdfGenerator =
                        new PDFUtil(directoryTextField.getText(), "file.pdf", downloadImages, textArea);
                pdfGenerator.imagesToPdf();
                textArea.AppendLn("\n----PDF Building Complete----");
            }
            downloadButton.setEnabled(true);
            analyzeButton.setEnabled(true);
            ls_url.clear(); ls_url_list.clear();
        }
    }

    private class AnalyzeThreadCheckRunnable implements Runnable {
        @Override
        public void run() {
            while (analyzeThread.isAlive()) {
                try { Thread.sleep(50); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            textArea.AppendLn("----Analyze Complete----");
            downloadButton.setEnabled(true);
            analyzeButton.setEnabled(true);
        }
    }

    private class DownloadActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (ls_url_list.size() == 0) {
                textArea.AppendErrorLn("\n\nPlease Analyze First."); return;
            }
            downloadButton.setEnabled(false);
            analyzeButton.setEnabled(false);
            textArea.AppendLn("\n\n\n----Start Downloading----");
            downloadThreadFinishedCount[0] = 0;
            totalCountForDownloadThread = ls_url_list.size();
            downloadImages.clear();
            new Thread(() -> {
                for (int i = 1; i <= ls_url_list.size(); i ++) {
                    while (i - downloadThreadFinishedCount[0] >= MAX_CONCURRENT_THREAD) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
                    String url = ls_url_list.get(i - 1);
                    textArea.AppendProcessLn(i + " Thread Start: \n" + url + "\n");
                    new Thread(new DownloadRunnable(url, i, directoryTextField.getText(), textArea, downloadImages, downloadThreadFinishedCount)).start();
                }
                new Thread(new DownloadThreadCheckRunnable()).start();
            }).start();
        }

    }

    public class IncreaseFontSizeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Font font = textArea.logArea.getFont();
            textArea.logArea.setFont(new Font(font.getName(), Font.PLAIN, font.getSize() + 1));
        }
    }

    public class DecreaseFontSizeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Font font = textArea.logArea.getFont();
            textArea.logArea.setFont(new Font(font.getName(), Font.PLAIN, font.getSize() == 1 ? 1 : font.getSize() - 1));
        }
    }

    private class urlChangeListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) { downloadButton.setEnabled(false); }

        @Override
        public void removeUpdate(DocumentEvent e) { downloadButton.setEnabled(false); }

        @Override
        public void changedUpdate(DocumentEvent e) { downloadButton.setEnabled(false); }
    }

    private void setInputUnitLayout(JPanel wholePanel, JPanel inputPanel, JLabel inputLabel, JTextField inputTextField, JButton actionButton) {
        wholePanel.setLayout(new BorderLayout());
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputLabel, BorderLayout.WEST);
        inputPanel.add(inputTextField, BorderLayout.CENTER);
        wholePanel.add(inputPanel, BorderLayout.NORTH);
        wholePanel.add(actionButton, BorderLayout.SOUTH);
    }

    public void init() {
        setInputUnitLayout(urlPanel, urlInputPanel, urlLabel, urlTextField, analyzeButton);
        setInputUnitLayout(filePanel, fileInputPanel, directoryLabel, directoryTextField, directoryChoosingButton);
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(urlPanel, BorderLayout.NORTH);
        controlPanel.add(filePanel, BorderLayout.CENTER);
        downloadPanel.setLayout(new BorderLayout());
        downloadPanel.add(pdfCheckBox, BorderLayout.WEST);
        downloadPanel.add(downloadButton, BorderLayout.CENTER);
        controlPanel.add(downloadPanel, BorderLayout.SOUTH);
        frame.setLayout(new BorderLayout());
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(textArea, BorderLayout.CENTER);
        analyzeButton.addActionListener(new analyzeActionListener());
        downloadButton.setEnabled(false);
        directoryChoosingButton.addActionListener(new fileChooserActionListener());
        downloadButton.addActionListener(new DownloadActionListener());
        urlTextField.getDocument().addDocumentListener(new urlChangeListener());
        pdfCheckBox.setSelected(true);
        H_DownloaderMenuBar menuBar = new H_DownloaderMenuBar(this);
        menuBar.setDecreaseFontSizeAction(new DecreaseFontSizeActionListener());
        menuBar.setIncreaseFontSizeAction(new IncreaseFontSizeActionListener());
        frame.setMenuBar(menuBar);
        frame.pack();
    }

    public H_Downloader() { init(); }

    public void launch() { frame.setVisible(true); }

    public void dispose() { frame.dispose(); }

}
