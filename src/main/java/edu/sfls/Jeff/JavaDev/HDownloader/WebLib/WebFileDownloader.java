package edu.sfls.Jeff.JavaDev.HDownloader.WebLib;

import edu.sfls.Jeff.JavaDev.HDownloader.UILib.JQLogArea;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class WebFileDownloader {

    private String url;
    private String file_name;
    private String save_path;
    private Map<String, String> headers = null;
    private JQLogArea textArea;

    /**
     * Initializer for {@code WebFileDownloader}.
     * You can download file from web.
     * @param url
     *          The {@code URL} you are going to download file from.
     * @param file_name
     *          The file name you are gonna set.
     * @param save_path
     *          The path you are gonna save the file.
     * @author Jefferson Qin
     */
    public WebFileDownloader(String url, String file_name, String save_path) {
        this.url = url;
        this.save_path = save_path;
        if (this.save_path.endsWith(File.separator)) {
            this.save_path = save_path.substring(0, save_path.lastIndexOf(File.separator));
        }
        this.file_name = file_name;
    }

    /**
     * Initializer for {@code WebFileDownloader}.
     * You can download file from web.
     * @param url
     *          The {@code URL} you are going to download file from.
     * @param file_name
     *          The file name you are gonna set.
     * @param save_path
     *          The path you are gonna save the file.
     * @param headers
     * 			The headers in the request you wanna send to the server.
     * @author Jefferson Qin
     */
    public WebFileDownloader(String url, String file_name, String save_path, Map<String, String> headers) {
        this.url = url;
        this.save_path = save_path;
        this.file_name = file_name;
        this.headers = headers;
    }

    /**
     * Initializer for {@code WebFileDownloader}.
     * You can download file from web.
     * @param url
     *          The {@code URL} you are going to download file from.
     * @param file_name
     *          The file name you are gonna set.
     * @param save_path
     *          The path you are gonna save the file.
     * @param textArea
     *          The {@code JQLogArea} where you print the Exception Information.
     * @author Jefferson Qin
     */
    public WebFileDownloader(String url, String file_name, String save_path, JQLogArea textArea, Map<String, String> headers) {
        this.url = url;
        this.save_path = save_path;
        if (this.save_path.endsWith(File.separator)) {
            this.save_path = save_path.substring(0, save_path.lastIndexOf(File.separator));
        }
        this.file_name = file_name;
        this.textArea = textArea;
        this.headers = headers;
    }

    /**
     * Initializer for {@code WebFileDownloader}.
     * You can download file from web.
     * @param url
     *          The {@code URL} you are going to download file from.
     * @param file_name
     *          The file name you are gonna set.
     * @param save_path
     *          The path you are gonna save the file.
     * @param headers
     * 			The headers in the request you wanna send to the server.
     * @param textArea
     *          The {@code JQLogArea} where you print the Exception Information.
     * @author Jefferson Qin
     */
    public WebFileDownloader(String url, String file_name, String save_path, Map<String, String> headers, JQLogArea textArea) {
        this.url = url;
        this.save_path = save_path;
        this.file_name = file_name;
        this.headers = headers;
        this.textArea = textArea;
    }

    private File df_url() {

        URL url;
        try {
            url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // Set the time out time = 3 sec
            // conn.setConnectTimeout(10 * 1000);
            // Prevent 403 error from the website
            if (headers == null) {
                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            } else {
                for (String key : headers.keySet()) {
                    System.out.println("key: " + key + "\nvalue: " + headers.get(key));
                    conn.setRequestProperty(key, headers.get(key));
                }
            }
            // gain input stream
            InputStream inputStream = conn.getInputStream();
            // gain data array
            byte[] getData = readInputStream(inputStream);

            System.out.println(getData);

            // set save dir
            File saveDir = new File(save_path);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + file_name);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return file;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            if (this.textArea != null) textArea.AppendErrorLn(e.getMessage() + "\n" + e.getLocalizedMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            if (this.textArea != null) textArea.AppendErrorLn(e.getMessage() + "\n" + e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Download file from the {@code URL} to {@code save_path}
     * @return
     *          {@code save_path + "/" + file_name}
     * @author Jefferson Qin
     */
    public String downloadFromUrl() {
        try {
            this.df_url();
            // return file path
            return save_path + File.separator + file_name;
        } catch (Exception e) {
            e.printStackTrace();
            if (this.textArea != null) textArea.AppendErrorLn(e.getMessage() + "\n" + e.getLocalizedMessage());
        }
        return "";

    }

    /**
     * Download file from the {@code URL} to {@code save_path}
     * @return
     *          {@code File} of the download request.
     * @author Jefferson Qin
     */
    public File downloadFromUrl_file() {
        return this.df_url();
    }

    /**
     * You can get the input stream result via this function.
     * @param inputStream
     *          The {@code InputStream} you are gonna use.
     * @return
     *          The result from the {@code InputStream}.
     * @throws IOException
     * @author Jefferson Qin
     */
    private byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}