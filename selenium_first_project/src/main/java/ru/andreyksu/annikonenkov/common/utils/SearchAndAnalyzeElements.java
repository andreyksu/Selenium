package ru.andreyksu.annikonenkov.common.utils;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchAndAnalyzeElements {

    private final static Logger _logger = LogManager.getLogger(SearchAndAnalyzeElements.class);

    private WebDriver _driver;

    public SearchAndAnalyzeElements(WebDriver driver) {
        _driver = driver;
    }

    /**
     * Ищет элемент без ожидания - применим когда мы хотем проверить элемент на уже загруженной странице.
     * 
     * @param locator
     * @return
     */
    public boolean checkWithoutWait(By locator, Integer seconds) {
        WebElement firstElement = null;
        try {
            _driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
            firstElement = _driver.findElement(locator);
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            _driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
        return true;
    }

}
