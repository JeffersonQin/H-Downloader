package edu.sfls.Jeff.JavaDev.HDownloader.EHLib;

import edu.sfls.Jeff.JavaDev.HDownloader.UILib.JQLogArea;
import edu.sfls.Jeff.JavaDev.HDownloader.WebLib.WebFileDownloader;

import java.io.File;
import java.util.HashMap;
import java.util.TreeMap;

public class DownloadRunnable implements Runnable {

    private String url;
    private String path;
    private int index;
    private JQLogArea textArea;
    private TreeMap<Integer, File> download;
    private int[] finishedCount;

    public DownloadRunnable(String url, int index, String path, JQLogArea textArea, TreeMap<Integer, File> download, int[] finishedCount) {
        this.url = url;
        this.index = index;
        this.path = path;
        this.textArea = textArea;
        this.download = download;
        this.finishedCount = finishedCount;
    }

    @Override
    public void run() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36 Edg/92.0.902.55");
        // Add Headers, fuck old code
        if (this.url.startsWith("https://ja.erocool.com/")) {
            headers.put("referer", "https://ja.erocool.com/");
        }
        WebFileDownloader wbDownload = new WebFileDownloader(url, url.substring(url.lastIndexOf("/") + 1), path, textArea, headers);
        wbDownload.downloadFromUrl();
        textArea.AppendProcessLn("Download Thread " + index
                + " Complete...\nFile Location: "
                + path + File.separator + url.substring(url.lastIndexOf("/") + 1));
        download.put(index, new File(path + (path.endsWith(File.separator) ? "" : File.separator) + url.substring(url.lastIndexOf("/") + 1)));
        finishedCount[0] ++;
    }
}
