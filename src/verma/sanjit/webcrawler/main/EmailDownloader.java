package verma.sanjit.webcrawler.main;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class EmailDownloader implements Runnable {

    List<String> emailLinks;
    ExecutorService executorService = null;
    String year = null;

    public EmailDownloader(List<String> emailURLs, ExecutorService executorService, String year) {
        this.emailLinks = emailURLs;
        this.executorService = executorService;
        this.year = year;

    }


    @Override
    public void run() {
        String from = null;
        String date = null;
        Elements subject = null;
        String fileName = null;
        Connection conn = null;
        String emailURL = null;
        try {

            Iterator<String> emaiLink = emailLinks.iterator();
            while(emaiLink.hasNext()) {
                emailURL = emaiLink.next();
                conn = Jsoup.connect(emailURL);
                Document doc = conn.get();
                from = doc.getElementsByClass("from").select("td[class*=right]").text();
                date = doc.getElementsByClass("date").select("td[class*=right]").text();
                subject = doc.getElementsByClass("subject");
                fileName = (from + date).concat(".html");

                byte[] bytes = Jsoup.connect(emailURL)
                        .header("Accept-Encoding", "gzip, deflate")
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                        .ignoreContentType(true)
                        .maxBodySize(0)
                        .timeout(600000)
                        .execute()
                        .bodyAsBytes();
                String path = "emails" + File.separator + year + File.separator + fileName;
                File file = new File(path);
                File pdir = file.getParentFile();
                pdir.mkdirs();
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void downloadEmail() {
       executorService.submit(this);
    }
}
