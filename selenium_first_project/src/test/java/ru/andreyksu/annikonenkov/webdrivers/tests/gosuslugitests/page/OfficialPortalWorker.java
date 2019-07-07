package ru.andreyksu.annikonenkov.webdrivers.tests.gosuslugitests.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.andreyksu.annikonenkov.PageWorker;

/**
 * Created by andrey on 17.05.17.
 */
public class OfficialPortalWorker extends PageWorker {
    private OfficialPortalHelper _officialPortalHelper;
    private static final String _nameOfPage = "Официальная_страница_татарстана";
    private final static Logger logger = LogManager.getLogger(OfficialPortalWorker.class);

    public OfficialPortalWorker(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        _officialPortalHelper = new OfficialPortalHelper(webDriver);
    }

    @Override
    public String getNameOfCurrentPage() {
        return _nameOfPage;
    }

    @Override
    public boolean isOpenedCurrentPage() {
        return commonOperationWorker.isPresentTargetElementOnCurrentPage(_officialPortalHelper.getLogoElementOfPage(), this);
    }

}