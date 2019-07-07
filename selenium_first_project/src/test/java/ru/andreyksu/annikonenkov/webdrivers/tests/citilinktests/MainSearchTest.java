package ru.andreyksu.annikonenkov.webdrivers.tests.citilinktests;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.andreyksu.annikonenkov.webdrivers.MainClassForTests;
import ru.andreyksu.annikonenkov.webdrivers.tests.citilinktests.page.MPageWorker;
import ru.andreyksu.annikonenkov.utils.ConfigProperties;

/**
 * Created by andrey on 27.05.17.
 */
public class MainSearchTest extends MainClassForTests {
    private MPageWorker citilinkPageWorker;
    private final static Logger logger = MainClassForTests.getLogger();

    @DataProvider(name = "test")
    public Object[][] createData1() {
        return new Object[][]{
                {"планшет", "планшет"},
                {"мышка", "мышка"},
        };
    }

    @BeforeClass
    public void setUp() {
        Actions builder = new Actions(webDriver);
        citilinkPageWorker = new MPageWorker(webDriver, wait);
    }

    @BeforeMethod
    public void goToCitilinkPage() {
        webDriver.get(ConfigProperties.getProperties("citilink.url"));
    }

    @Test(description = "isOpenedCurrenPage", priority = 1, timeOut = 60000)
    public void isOpenedCurrenPage() {
        Exception ex = new Exception();
        String nameTest = ex.getStackTrace()[0].getMethodName();
        logger.info(String.format("Start test %s", nameTest));

        logger.info("Проверяем, открылась ли официальная страница Ситилинк!");
        Assert.assertTrue(citilinkPageWorker.isOpenedCurrentPage());
        logger.info("Официальная страница ситилнк открылась успешно!");
    }

    @Test(description = "engineSearch", priority = 2, timeOut = 60000, dataProvider = "test")
    public void engineSearch(String param, String value) {
        Exception ex = new Exception();
        String nameTest = ex.getStackTrace()[0].getMethodName();
        logger.info(String.format("Start test %s", nameTest));
        logger.info("Вводим в поле поиск - маску");
        citilinkPageWorker.searchEngine(value);
        logger.info("Проверяем, что после выполнения поиска, элементы присутствют");
        Assert.assertTrue(citilinkPageWorker.verifyNumberOfResult());
        logger.info("Все требуемыые элементы на странице, после поиска присутствуют");
    }
}
