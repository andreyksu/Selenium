package ru.andreyksu.annikonenkov;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.andreyksu.annikonenkov.utils.CommonOperationWorker;

/**
 * Created by andrey on 17.05.17.
 */
public abstract class PageWorker {
    protected WebDriver webDriver;
    protected WebDriverWait explicitWait;
    protected CommonOperationWorker commonOperationWorker;

    public PageWorker(WebDriver driver, WebDriverWait wait){
        webDriver = driver;
        explicitWait = wait;
        commonOperationWorker = new CommonOperationWorker(webDriver, explicitWait);
    }

    public abstract String getNameOfCurrentPage ();

    public abstract boolean isOpenedCurrentPage();
}
