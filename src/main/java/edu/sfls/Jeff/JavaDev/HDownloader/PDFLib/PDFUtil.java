package edu.sfls.Jeff.JavaDev.HDownloader.PDFLib;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import edu.sfls.Jeff.JavaDev.HDownloader.UILib.JQLogArea;

import java.io.File;
import java.io.FileOutputStream;
import java.util.TreeMap;

public class PDFUtil {

    private String savePath;
    private String saveName;
    private JQLogArea logArea;
    private TreeMap<Integer, File> images;

    public PDFUtil(String savePath, String saveName, TreeMap<Integer, File> images) {
        this.savePath = savePath; this.saveName = saveName; this.images = images;
    }

    public PDFUtil(String savePath, String saveName, TreeMap<Integer, File> images, JQLogArea logArea) {
        this.savePath = savePath; this.saveName = saveName; this.images = images; this.logArea = logArea;
    }

    public void imagesToPdf() {
        try {
            // Check Directory
            String fileDirectory =
                    this.savePath + (this.savePath.endsWith(File.separator) ? "" : File.separator) +
                            this.saveName + (this.saveName.toLowerCase().endsWith(".pdf") ? "" : ".pdf");
            File file = new File(fileDirectory);
            // Create a document
            Document document = new Document();
            document.setMargins(0, 0, 0, 0);

            // Create a PDF document, due to the saving directory
            PdfWriter.getInstance(document, new FileOutputStream(file));

            // Open the document
            document.open();

            // Adding the images to the document
            // Getting all the files in the image directory

            for (int i = 1; i <= images.size(); i ++) {
                File img = images.get(i);
                String imgPath = img.getPath();
                Image sImage = Image.getInstance(imgPath);
                sImage.setAlignment(Image.ALIGN_CENTER);
                // Set the dimension of the page before newPage(), according to the size of the image
                document.setPageSize(new Rectangle(sImage.getWidth(), sImage.getHeight()));
                document.newPage();
                document.add(sImage);
            }

            // Close the Document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (logArea != null) logArea.AppendErrorLn(e.getMessage() + "\n" + e.getLocalizedMessage());
        }
    }

}
