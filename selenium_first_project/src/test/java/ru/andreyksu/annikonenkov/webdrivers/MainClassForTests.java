package ru.andreyksu.annikonenkov.webdrivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.*;
import ru.andreyksu.annikonenkov.webdrivers.utils.listners.AttachLogListenerForError;
import ru.andreyksu.annikonenkov.webdrivers.utils.listners.ConfigPrepare;
import ru.andreyksu.annikonenkov.webdrivers.utils.listners.ScreenShotOnFailure;
import ru.andreyksu.annikonenkov.utils.ConfigProperties;
import ru.andreyksu.annikonenkov.utils.MakerPathAndFolderForArtifacts;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by andrey on 23.05.17.
 */
@Listeners({ScreenShotOnFailure.class, AttachLogListenerForError.class, ConfigPrepare.class,})
public class MainClassForTests {

    protected WebDriver webDriver;

    protected WebDriverWait wait;

    private static Logger logger = LogManager.getLogger(MainClassForTests.class);

    public static Logger getLogger() {
        return logger;
    }

    public MainClassForTests() {
        // TODO: Это вынести в framework определение и инициализация должно веститьс там. К тестам не относится.
        // TODO: Так же создание временной дирректории итд.
        System.setProperty("webdriver.chrome.driver", ConfigProperties.getProperties("path.chrome"));
        System.setProperty("webdriver.gecko.driver", ConfigProperties.getProperties("path.firefox"));
        MakerPathAndFolderForArtifacts makerPathAndFolderForArtifacts = new MakerPathAndFolderForArtifacts();
        try {
            String path = makerPathAndFolderForArtifacts.createAndGetPathToFolder();
            ConfigProperties.setProperies("path.to.ArtifactFolder", path);
        } catch (Exception e) {
            logger.error("Не удалось создать каталог для артефактов. Тесты будут завершены.", new RuntimeException(e));
            System.exit(1);
        }
    }

    @BeforeSuite
    public void beforeSuit(ITestContext testContext) {
        String dir = testContext.getOutputDirectory();
    }

    @BeforeTest
    @Parameters({"instanceOfWebDriver"})
    // TODO: Тоже вынести
    public void setUpForSuit(@Optional("firefox") String instanceWebDriver) {
        if (instanceWebDriver.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            webDriver = new ChromeDriver(options);
            webDriver.manage().window().maximize();
        } else {
            webDriver = new FirefoxDriver();
            webDriver.manage().window().maximize();
        }
        int duration = Integer.parseInt(ConfigProperties.getProperties("imp.wait"));
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(duration));
    }

    @AfterTest
    public void cleanUp() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
