package verma.sanjit.webcrawler.test;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.Mockito;
import verma.sanjit.webcrawler.main.CrawlerWorker;
/**
 * Created by SANJIT on 13/10/17.
 */
public class CrawlerWorkerTest {

    @Test
    public void testIFWordIsNotFound(){
        CrawlerWorker crawlerWorker = mock(CrawlerWorker.class);
        //when(crawlerWorker.searchWord(Mockito.anyString())).thenReturn(false);
        //assertFalse(crawlerWorker.searchWord("Sanjit"));
    }


}