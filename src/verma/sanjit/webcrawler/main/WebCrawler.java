package verma.sanjit.webcrawler.main;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebCrawler {

    private static final String BASE_URL = "http://mail-archives.apache.org/mod_mbox/maven-users/";
    private List<String> pageToVisit = null;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    CrawlerWorker worker = null;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the year to download email in YYYY format");
        final String year = sc.next();
        WebCrawler crawler = new WebCrawler();
        crawler.search(BASE_URL, year);
        crawler.downloadEmail(year);
    }

    private void search(String url, String year) throws IOException {
        worker = new CrawlerWorker(year);
        pageToVisit = worker.getYearWaiseMailList(url);
        System.out.println("** Downloading Email, please wait...**");
    }

    private void downloadEmail(String year) {
        EmailDownloader emailDownloader = null;
        for (String emailURL : pageToVisit) {
            List<String> emailLinkToDownload = worker.getEmailLink(emailURL);
            emailDownloader = new EmailDownloader(emailLinkToDownload, executorService, year);
            emailDownloader.downloadEmail();
        }
    }
}
