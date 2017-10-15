package verma.sanjit.webcrawler.main;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebCrawler {

    private static final String BASE_URL = "http://mail-archives.apache.org/mod_mbox/maven-users/";
    private List<String> pageToVisit = null;
    private ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws IOException {

        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the year to download email in YYYY format");
        final String year = sc.next();
        WebCrawler crawler = new WebCrawler();
        crawler.search(BASE_URL,year);
    }

    private void search(String url, String year) throws IOException {
        CrawlerWorker worker = new CrawlerWorker(year);
        EmailDownloader emailDownloader = null;
        pageToVisit = worker.getYearWaiseMailList(url);
        for (String emailURL: pageToVisit) {
            List<String> emailLinkToDownload = worker.getEmailLink(emailURL);
            emailDownloader = new EmailDownloader(emailLinkToDownload,executorService, year);
            emailDownloader.downloadEmail();
        }
        System.out.println("** Downloading Email, please wait...**");
    }
}
