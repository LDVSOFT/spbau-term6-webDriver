package ru.spbau.mit.ldvsoft;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResultPage {
    private static final By SEARCH_RESULTS_SELECTOR = By.xpath("//div[contains(@class, \"filter-applied-results\")]");
    private static final By COMPARE_SELECTOR = By.xpath("//a[contains(@class, \"header2-menu__item_type_compare\")]");

    public static class ResultItem {
        private static final By ITEM_SELECTOR = By.xpath("//div[contains(@data-id, \"model-\")]");
        private static final By ADD_BUTTON_SELECTOR = By.xpath(".//div[contains(@class, \"product-action-compare\") or contains(@class, \"n-user-lists_type_compare\")]");
        private static final String ADDED_CLASS = "product-action-compare_in-list_yes";

        private WebDriverWait driverWait;
        private final WebElement element;

        private ResultItem(WebElement element, WebDriverWait driverWait) {
            this.element = element;
            this.driverWait = driverWait;
        }

        public void addToComparison() {
            //driverWait.until(ExpectedConditions.visibilityOfElementLocated(ADD_BUTTON_SELECTOR));
            WebElement addButton;
            try {
                addButton = element.findElement(ADD_BUTTON_SELECTOR);
            } catch (RuntimeException e) {
                // Bogus bug; sometimes we just can't find this button!
                return;
            }
            addButton.click();
            driverWait.until(webDriver -> addButton.getAttribute("class").contains(ADDED_CLASS));
        }
    }

    private final WebDriver driver;
    private final List<ResultItem> resultItems;
    private final WebElement compareButton;

    SearchResultPage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait driverWait = new WebDriverWait(driver, 10);
        driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(SEARCH_RESULTS_SELECTOR));

        resultItems = driver
                .findElements(ResultItem.ITEM_SELECTOR)
                .stream()
                .map(element -> new ResultItem(element, driverWait))
                .collect(Collectors.toList());
        compareButton = driver.findElement(COMPARE_SELECTOR);
    }

    public ComparePage compare() {
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0, 0);");
        compareButton.click();
        return new ComparePage(driver);
    }

    public List<ResultItem> getResultItems() {
        return resultItems;
    }
}
