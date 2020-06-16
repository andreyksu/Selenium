package ru.andreyksu.annikonenkov.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Created by andrey on 27.05.17.
 *
 * Класс с общими для всех страниц действиями. Такими как, проверка элементов на странице, по которым можно идентифицировать, что страница уже загрузилась.
 *
 */
public class CommonOperationWorker {
    private WebDriver _webDriver;
    private WebDriverWait _explicitWait;
    private final static Logger logger = LogManager.getLogger(CommonOperationWorker.class);

    public CommonOperationWorker(WebDriver driver, WebDriverWait wait) {
        _webDriver = driver;
        _explicitWait = wait;
    }

    /**
     * Проверяет наличие элемента на "уже загруженной" странице.
     *
     * @param element опорный элемент на странице
     * @param pageWorker абстрактный класс для всех страниц
     * @return boolean true - если опорный элмент найден, false - если опрный элемент не найден.
     */
    public boolean isPresentTargetElementOnCurrentPage(By element, PageWorker pageWorker) {
        try {
            _webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            _explicitWait.until(ExpectedConditions.presenceOfElementLocated(element));
            return true;
        } catch (Exception e) {
            logger.error(String.format("На странице %s не найден элемент %s \n", pageWorker.getNameOfCurrentPage(), element.toString()));
            logger.error(String.format("Имя класса старницы/элемента %s \n", pageWorker.getClass().getSimpleName().toString()));
            String nameScreenShot = String.format("Class_%s_Page_%s", pageWorker.getClass().getSimpleName().toString(), pageWorker.getNameOfCurrentPage());
            logger.error(String.format("Будет сделан сриншот %s \n", nameScreenShot));
            MakerScreenShot.makeScreenShot(_webDriver, nameScreenShot);
            return false;
        } finally {
            _webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Метод проверки на странице с уже загруженными элементами.
     *
     * @param locator
     * @return
     */
    public boolean isElementPresent(By locator) {
        try {
            _webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            _webDriver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return true;
        } finally {
            _webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }

    }
}
