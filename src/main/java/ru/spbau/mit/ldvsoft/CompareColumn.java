package ru.spbau.mit.ldvsoft;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CompareColumn {
    private static final By LIKE_BUTTON_SELECTOR = By.xpath("//a[contains(@class, \"compare-head__favorite\")]");
    private static final By DELETE_BUTTON_SELECTOR = By.xpath("//span[contains(@class, \"compare-head__close\")]");
    private static final String PROPERTY_SELECTOR_FORMAT = "//div[contains(@class, \"compare-content\")]/div[text() = \"%s\"]/..";
    private static final By NAME_SELECTOR = By.xpath("//a[contains(@class, \"compare-head__name\")]");
    private static final By PRICE_SELECTOR = By.xpath("//div[contains(@class, \"price\")]");
    private static final String BODY = "innerHTML";
    private static final By PICTURE_SELECTOR = By.xpath("//div[contains(@class, \"compare-cell_product\")]");

    private static By getPropertySelector(String property) {
        return By.xpath(String.format(PROPERTY_SELECTOR_FORMAT, property));
    }

    private final WebDriver driver;
    private final WebDriverWait driverWait;
    private final int columnId;

    private final WebElement likeButton;
    private final WebElement deleteButton;
    private final WebElement picture;
    private final String itemName;
    private final String startPrice;

    CompareColumn(WebDriver driver, int columnId) {
        this.driver = driver;
        this.columnId = columnId;

        driverWait = new WebDriverWait(driver, 10);
        likeButton = driver.findElements(LIKE_BUTTON_SELECTOR).get(columnId);
        deleteButton = driver.findElements(DELETE_BUTTON_SELECTOR).get(columnId);
        picture = driver.findElements(PICTURE_SELECTOR).get(columnId);
        itemName = driver.findElements(NAME_SELECTOR).get(columnId).getAttribute(BODY);
        startPrice = driver.findElements(PRICE_SELECTOR).get(columnId).getAttribute(BODY);
    }

    public String getProperty(String property) {
        return getContentOfProperty(property)
                .findElement(By.xpath(String.format("div[%d]", columnId + 2)))
                .getAttribute(BODY)
                .trim();
    }

    public String getItemName() {
        return itemName;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void likeItem() {
        mouseToItemPicture();
        waitToAppear(deleteButton);
        likeButton.click();
    }

    public void deleteItem() {
        mouseToItemPicture();
        waitToAppear(deleteButton);
        deleteButton.click();
    }

    public boolean isPropertyDisplayed(String property) {
        return getContentOfProperty(property).isDisplayed();
    }

    private void waitToAppear(WebElement element) {
        driverWait.until(webDriver -> element.isDisplayed());
    }

    private WebElement getContentOfProperty(String property) {
        return driver.findElement(getPropertySelector(property));
    }

    private void mouseToItemPicture() {
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0, 0);");
        new Actions(driver).moveToElement(picture).build().perform();
    }
}
