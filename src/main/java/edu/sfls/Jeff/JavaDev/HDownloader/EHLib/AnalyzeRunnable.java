package edu.sfls.Jeff.JavaDev.HDownloader.EHLib;

import edu.sfls.Jeff.JavaDev.HDownloader.UILib.JQLogArea;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AnalyzeRunnable implements Runnable {

    private String webURL;
    private JQLogArea textArea;
    private List<String> ls_url;
    private List<String> ls_url_list;
    private File file;
    /**
     * type_index = 0 ---> Download using url;
     * type_index = 1 ---> Download using html for e-hentai;
     * type_index = 2 ---> Download using html for erocool;
     */
    private int type_index = 0;

    public AnalyzeRunnable(String webURL, JQLogArea textArea, List<String> ls_url, List<String> ls_url_list) {
        this.webURL = webURL;
        this.textArea = textArea;
        this.ls_url = ls_url;
        this.ls_url_list = ls_url_list;
    }

    public AnalyzeRunnable(File file, JQLogArea textArea, List<String> ls_url, List<String> ls_url_list, int type_index) {
        this.textArea = textArea;
        this.ls_url = ls_url;
        this.ls_url_list = ls_url_list;
        this.file = file;
        this.type_index = type_index;
    }

    private void analyze_E_Hentai() {
        try {
            Document doc;
            if (type_index == 0)
                doc = Jsoup.connect(webURL).get();
            else
                doc = Jsoup.parse(file, null);
            Element div_inf_table = doc.select("div[id=gdd]").get(0).selectFirst("tbody");
            String language = div_inf_table.select("tr").get(3).select("td").get(1).text();
            String file_size = div_inf_table.select("tr").get(4).select("td").get(1).text();
            Scanner scanner = new Scanner(div_inf_table.select("tr").get(5).select("td").get(1).text());
            int length = scanner.nextInt();
            scanner.close();
            String favorate = div_inf_table.select("tr").get(6).select("td").get(1).text();

            textArea.AppendInformationLn("\n\n----Basic Information----\n");
            textArea.Append("Language: ");
            textArea.AppendInformationLn(language);
            textArea.Append("File Size: ");
            textArea.AppendInformationLn(file_size);
            textArea.Append("Length: ");
            textArea.AppendInformationLn("" + length);
            textArea.Append("Favourite: ");
            textArea.AppendInformationLn(favorate);

            String title = doc.selectFirst("h1[id=gn]").text();
            String subTitle = doc.selectFirst("h1[id=gj]").text();

            textArea.AppendInformationLn("Title: " + title);
            textArea.AppendInformationLn("SubTitle: " + subTitle);

            textArea.AppendLn("\n----Web URLs----\n");
            Elements pics_table = doc.select("div[id=gdt]").get(0).select("div[class=gdtm]");
            for (int i = 0; i < length; i++) {
                ls_url.add(pics_table.get(i).select("a").get(0).attr("href"));
                textArea.AppendLinkLn(ls_url.get(i));
            }

            textArea.AppendLn("\n----Raw URLs----\n");
            for (String url : ls_url) {
                Document sub_doc = Jsoup.connect(url).get();
                ls_url_list.add(sub_doc.select("div[id=i3]").get(0).selectFirst("img").attr("src"));
                textArea.AppendLinkLn(ls_url_list.get(ls_url_list.size() - 1));
            }
        } catch (IOException e1) {
            textArea.AppendErrorLn(e1.getMessage() + "\n" + e1.getLocalizedMessage());
            e1.printStackTrace();
        }
    }

    private void analyze_Erocool() {
        try {
            Document doc;
            if (type_index == 0)
                doc = Jsoup.connect(webURL).get();
            else
                doc = Jsoup.parse(file, null);
            String title = doc.select("h1").first().text();
            String subTitle = doc.select("h2[class=breadtitle]").first().text();
            textArea.AppendInformationLn("\n\n----Basic Information----\n");
            textArea.AppendInformationLn("Title: " + title);
            textArea.AppendInformationLn("SubTitle: " + subTitle);
            Elements ld_heads = doc.select("div[class=ld_head]");
            Elements ld_bodys = doc.select("div[class=ld_body]");
            for (Element ld_head : ld_heads) {
                int index = ld_heads.indexOf(ld_head);
                Element ld_body = ld_bodys.get(index);
                textArea.Append(ld_head.text() + ": ");
                textArea.AppendInformationLn(ld_body.text());
            }
            textArea.AppendLn("\n----Raw URLs----\n");
            Elements img_html = doc.select("img[class=vimg lazyload]");
            for (Element e : img_html) {
                ls_url_list.add(e.attr("data-src"));
                textArea.AppendLinkLn(ls_url_list.get(ls_url_list.size() - 1));
            }
        } catch (IOException e1) {
            textArea.AppendErrorLn(e1.getMessage() + "\n" + e1.getLocalizedMessage());
            e1.printStackTrace();
        }
    }

    @Override
    public void run() {
        textArea.AppendLn("Confirm: " + webURL);
        if (type_index == 0) {
            if (webURL.startsWith("https://e-hentai.org/")) {
                textArea.AppendLn("Correct Format... Analyzing E-Hentai URL...");
                this.analyze_E_Hentai();
            } else if (webURL.startsWith("https://ja.erocool.com/detail/")) {
                textArea.AppendLn("Correct Format... Analyzing Erocool URL...");
                this.analyze_Erocool();
            } else {
                textArea.AppendLn("Error: Wrong Format... Please Try Again...");
                return;
            }
        } else if (type_index == 1) {
            textArea.AppendLn("Using HTML File... Analyzing E-Hentai Website...");
            this.analyze_E_Hentai();
        } else if (type_index == 2) {
            textArea.AppendLn("Using HTML File... Analyzing Erocool Website...");
            this.analyze_Erocool();
        } else {
            textArea.AppendLn("Error: Wrong TYPE_INDEX Format... Please Try Again...");
            return;
        }
    }

}
