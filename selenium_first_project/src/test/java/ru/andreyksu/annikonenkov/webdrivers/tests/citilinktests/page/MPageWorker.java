package ru.andreyksu.annikonenkov.webdrivers.tests.citilinktests.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.andreyksu.annikonenkov.PageWorker;

import java.util.List;

/**
 * Created by andrey on 26.05.17.
 */
public class MPageWorker extends PageWorker {
    private MPageHelper _mPageHelper;
    private static final String _nameOfPage = "Главная_страница_Ситилинк";
    private final static Logger logger = LogManager.getLogger(MPageWorker.class);

    public MPageWorker(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        _mPageHelper = new MPageHelper(webDriver);
    }

    public void typeMaskInSearchField(String mask) {
        if (commonOperationWorker.isPresentTargetElementOnCurrentPage(_mPageHelper.getSearchContainer(), this)) {
            WebElement searchField = webDriver.findElement(_mPageHelper.getSearchContainer()).findElement(_mPageHelper.getSearchField());
            searchField.clear();
            searchField.sendKeys(mask);
        }
    }

    public void submitSearchButton() {
        WebElement searchButton = webDriver.findElement(_mPageHelper.getSearchContainer()).findElement(_mPageHelper.getSearchButton());
        searchButton.click();
    }

    public void searchEngine(String mask) {
        typeMaskInSearchField(mask);
        submitSearchButton();
    }

    public boolean verifyNumberOfResult() {
        if (commonOperationWorker.isPresentTargetElementOnCurrentPage(_mPageHelper.getContainerOfResultSearch(), this)) {
            WebElement resultListOfSeatch = webDriver.findElement(_mPageHelper.getContainerOfResultSearch());
            List<WebElement> elements = resultListOfSeatch.findElements(_mPageHelper.getItemOfResult());
            if (elements.size() == 20) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getNameOfCurrentPage() {
        return _nameOfPage;
    }

    @Override
    public boolean isOpenedCurrentPage() {
        return commonOperationWorker.isPresentTargetElementOnCurrentPage(_mPageHelper.getLogoElementOfPage(), this);
    }

}
