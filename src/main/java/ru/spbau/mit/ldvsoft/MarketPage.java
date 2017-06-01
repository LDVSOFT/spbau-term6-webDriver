package ru.spbau.mit.ldvsoft;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MarketPage {
    private static final String MARKET_URL = "https://market.yandex.ru/";
    private static final By SEARCH_BOX_SELECTOR = By.id("header-search");

    private final WebDriver driver;
    private final WebElement searchBox;

    public MarketPage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait driverWait = new WebDriverWait(driver, 10);

        driver.get(MARKET_URL);
        searchBox = driverWait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_BOX_SELECTOR));
    }

    public SearchResultPage searchFor(String request) {
        searchBox.sendKeys(request);
        searchBox.submit();
        return new SearchResultPage(driver);
    }
}
