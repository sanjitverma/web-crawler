package verma.sanjit.webcrawler.main;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CrawlerWorker {

    //public final String YEAR_LINK_SELECTOR = "[href~=(?i)(" + YEAR + ")]";
    private List<String> links = new ArrayList<>();
    private Document htmlDocument = null;
    private String year = null;

    public CrawlerWorker(String year) {
        this.year = year;
    }

    public List<String> getEmailLink(String url) {

        List<String> monthlyEmailLink = null;
        try {
            Connection connection = Jsoup.connect(url);
            htmlDocument = connection.get();
            if (this.checkIfMailArchivePage(htmlDocument)) {
                monthlyEmailLink = this.getEmailDownloadLink(htmlDocument);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return monthlyEmailLink;
    }


    private List<String> getEmailDownloadLink(Document htmlDocument) {
        Elements emailLinks = htmlDocument.select("a[href~=" + Constants.EMAIL_PATTERN + "]");
        List<String> monthlyEmailLink = new ArrayList<>();
        for (Element emailLink : emailLinks) {
            monthlyEmailLink.add(emailLink.absUrl("href"));
        }
        return monthlyEmailLink;
    }

    private void getYearByMailingList(Document htmlDocument) {
        Elements linksOnPage = htmlDocument.select(Constants.AUTHOR_LINK_SELECTOR + "[href~=(?i)(" + year + ")]");
        System.out.println("Found (" + linksOnPage.size() + ") links");
        for (Element link : linksOnPage) {
            this.links.add(link.absUrl("href"));
        }
    }


    private boolean checkIfMailArchivePage(Document htmlDocument) {
        return htmlDocument.getElementById(Constants.MESSAGE_LIST_CONSTANT) != null;
    }

    public List<String> getYearWaiseMailList(String currentURL) {
        Connection connection = Jsoup.connect(currentURL);
        try {
            htmlDocument = connection.get();
            this.getYearByMailingList(htmlDocument);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return links;
    }
}
