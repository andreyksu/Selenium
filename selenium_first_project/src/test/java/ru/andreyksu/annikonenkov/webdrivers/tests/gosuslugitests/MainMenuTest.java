package ru.andreyksu.annikonenkov.webdrivers.tests.gosuslugitests;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import ru.andreyksu.annikonenkov.webdrivers.MainClassForTests;
import ru.andreyksu.annikonenkov.webdrivers.tests.gosuslugitests.page.MainMenuWorker;
import ru.andreyksu.annikonenkov.PageWorker;
import ru.andreyksu.annikonenkov.utils.ConfigProperties;

import org.apache.logging.log4j.Logger;

/**
 * Created by andrey on 17.05.17.
 */
@Test
//(testName = "MainMenuTest", suiteName = "Selenium")
public class MainMenuTest extends MainClassForTests {
    private MainMenuWorker mainPage;
    private static Logger logger = MainClassForTests.getLogger();

    @BeforeClass
    public void setUp(ITestContext testContext) {
        mainPage = new MainMenuWorker(webDriver, wait);
    }

    @BeforeMethod
    //TODO: вынести в testng.xml
    public void goToMainPage() {
        webDriver.get(ConfigProperties.getProperties("government.url"));

    }

    //@Test(description = "testPresentAllReferences", dependsOnMethods = "step01_openMedicalHistory", priority = 2, timeOut = 60000)
    public void testPresentAllReferences() {
        Exception ex = new Exception();

        String nameTest = ex.getStackTrace()[0].getMethodName();

        logger.info(String.format("Start test %s", nameTest));

        logger.info("Проверка наличия всех ссылок на официальном портале ГоссУслуг");

        Assert.assertTrue(mainPage.isPresentAllReferenceInMainMenu());

        logger.info("Все ссылки найдены");
    }

    //@Test(description = "testLinkToOfficalPortal", priority = 1, timeOut = 60000)
    public void testLinkToOfficalPortal() {
        Exception ex = new Exception();
        String nameTest = ex.getStackTrace()[0].getMethodName();

        logger.info(String.format("Start test %s", nameTest));
        logger.info("Переход на официальный портал");

        PageWorker pageWorker = mainPage.goToOfficialPortal();

        logger.info("Проверка, открылся ли официальный партал");

        Assert.assertTrue(pageWorker.isOpenedCurrentPage());

        logger.info("Официальный портал открылся успешно");
    }
}