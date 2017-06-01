package ru.spbau.mit.ldvsoft;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.Assert.*;

public class ComparePageTest {
    private static final int INTERACTIVE_TIMEOUT = 3000;
    private static final String PROP_TYPE = "Тип";
    private static final String PROP_TYPE_VALUE = "смартфон";
    private WebDriver webDriver;
    private boolean wasException = false;

    @Before
    public void setupDriver() {
        webDriver = new ChromeDriver();
    }

    @After
    public void shutdownDriver() {
        if (!wasException)
            webDriver.quit();
    }

    @Test
    public void testComparePhones() throws InterruptedException {
        try {
            MarketPage marketPage = new MarketPage(webDriver);
            SearchResultPage resultPage = marketPage.searchFor("смартфон HTC One");
            List<SearchResultPage.ResultItem> resultItems = resultPage.getResultItems();
            if (resultItems.size() <= 3)
                throw new RuntimeException("Got too few results :/");
            resultItems.stream()
                    .limit(5)
                    .forEach(SearchResultPage.ResultItem::addToComparison);
            ComparePage comparePage = resultPage.compare();
            CompareColumn first = comparePage.getColumn(0);
            CompareColumn second = comparePage.getColumn(1);


            comparePage.compareByAll();
            assertTrue(first.isPropertyDisplayed(PROP_TYPE));
            assertEquals(PROP_TYPE_VALUE, first.getProperty(PROP_TYPE));
            Thread.sleep(INTERACTIVE_TIMEOUT);
            comparePage.compareByDiff();
            assertFalse(first.isPropertyDisplayed(PROP_TYPE));
            Thread.sleep(INTERACTIVE_TIMEOUT);

            first.likeItem();
            Thread.sleep(INTERACTIVE_TIMEOUT);
            first.likeItem();
            Thread.sleep(INTERACTIVE_TIMEOUT);

            first.deleteItem();
            Thread.sleep(INTERACTIVE_TIMEOUT);
        } catch (RuntimeException e) {
            wasException = true;
            throw e;
        }
    }
}
