package ru.andreyksu.annikonenkov.webdrivers.tests.gosuslugitests.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.andreyksu.annikonenkov.PageWorker;

import java.util.List;

/**
 * Created by andrey on 17.05.17.
 */
public class MainMenuWorker extends PageWorker {
    private MainMenuHelper _mainMenuHelper;
    private static final String _nameOfPage = "Официальная_страница_ГУ";
    private final static Logger logger = LogManager.getLogger(MainMenuWorker.class);

    public MainMenuWorker(WebDriver driver, WebDriverWait wait){
        super(driver, wait);
        _mainMenuHelper = new MainMenuHelper(driver);

    }

    public boolean isPresentAllReferenceInMainMenu() {
        if (commonOperationWorker.isPresentTargetElementOnCurrentPage(_mainMenuHelper.getLogoElementOfPage(),this)) {
            List<WebElement> listOfWebElements = null;
            try{
            listOfWebElements = webDriver.findElement(_mainMenuHelper.getContainerMainMenu()).findElements(_mainMenuHelper.getListElements());}
            catch (Exception e){
                logger.error("Не можем найти элементы на странице");
                return false;
            }
            if (listOfWebElements.size() == 6) {
                return true;
            } else return false;
        } else return false;
    }

    public PageWorker goToOfficialPortal() {
        _mainMenuHelper.officialPortal.click();
        return new OfficialPortalWorker(webDriver, explicitWait);
    }

    public void goToPresidentRT() {
        _mainMenuHelper.presidentRT.click();
    }

    public void goToGovernmentCouncilRT() {
        _mainMenuHelper.governmentCouncilRT.click();
    }

    public void goToGovernmentRT() {
        _mainMenuHelper.governmentRT.click();
    }

    public void goToCityDistrictRT() {
        _mainMenuHelper.cityDistrictRT.click();
    }

    public void goToGovernmentService() {
        _mainMenuHelper.governmentService.click();
    }

    @Override
    public String getNameOfCurrentPage() {
        return _nameOfPage;
    }

    @Override
    public boolean isOpenedCurrentPage() {
        return commonOperationWorker.isPresentTargetElementOnCurrentPage(_mainMenuHelper.getLogoElementOfPage(), this);
    }
}
