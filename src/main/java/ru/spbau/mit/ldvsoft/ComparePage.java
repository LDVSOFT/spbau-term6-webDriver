package ru.spbau.mit.ldvsoft;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ComparePage {
    private static final By COMPARE_BY_ALL_SELECTOR = By.xpath("//span[text() = \"все характеристики\"]");
    private static final By COMPARE_BY_DIFF_SELECTOR = By.xpath("//span[text() = \"различающиеся\"]");
    private static final String EXPECTED_TITLE = "Сравнение товаров — Яндекс.Маркет";

    private final WebDriver driver;
    private final WebElement compareByAllButton;
    private final WebElement compareByDiffButton;

    public ComparePage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait driverWait = new WebDriverWait(driver, 10);
        driverWait.until(ExpectedConditions.titleIs(EXPECTED_TITLE));

        compareByAllButton = driver.findElement(COMPARE_BY_ALL_SELECTOR);
        compareByDiffButton = driver.findElement(COMPARE_BY_DIFF_SELECTOR);
    }

    public void compareByAll() {
        compareByAllButton.click();
    }

    public void compareByDiff() {
        compareByDiffButton.click();
    }

    public CompareColumn getColumn(int id) {
        return new CompareColumn(driver, id);
    }
}
