package ru.annikonenkov.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;
import ru.annikonenkov.common.utils.SearchAndAnalyzeElements;

public class BaseTestClass {

    private final static Logger _logger = LogManager.getLogger(BaseTestClass.class);

    protected WebDriver _webDriver = null;

    protected ISearchAndAnalyzeElement _searcher = null;

    /**
     * Значение по умолчанию = 30<br>
     * Оставляем как есть.
     */
    protected int timeForWait = 15;

    // TODO: Нужно сделать получение пути до WebDriver через property файл.
    @Parameters({"instanceOfWebDriver", "locationOfWebDriver"})
    @BeforeTest
    public void setUp(String instanceOfWebDriver, String locationOfWebDriver) {
        if (instanceOfWebDriver.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", locationOfWebDriver);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            _webDriver = new ChromeDriver(options);
            _webDriver.manage().window().maximize();
        } else if (instanceOfWebDriver.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", locationOfWebDriver);
            _webDriver = new FirefoxDriver();
            _webDriver.manage().window().maximize();
        } else {
            throw new RuntimeException("Указан неподдерживаемый WebDriver. Текущие поддерживаемые 'chrome' и 'firefox'");
        }
        _searcher = new SearchAndAnalyzeElements(_webDriver, timeForWait);
    }

    /**
     * Метод, что перед тестом создает каталог, где будут сохраняться все ратефакты теста.<br>
     * Наименование каталога формируется исходя из наименования теста и метода.
     * 
     * @param testContext - беру из этой переменной наименование теста (TestNG сам подставляет эту переменную)
     * @param method - беру из этой переменной наименование теста (TestNG сам подставляет эту переменную)
     * @param startPath - начальный путь - для создания каталога. Сейчас находится/берется из xml TestNG.
     */
    @Parameters({"startPath"})
    @BeforeMethod
    public void createFolder(ITestContext testContext, Method method, String startPath) {
        Path path = null;
        String dateStartTest = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date());
        String separator = FileSystems.getDefault().getSeparator();
        String name =
                new StringBuilder().append(testContext.getName()).append(separator).append(method.getName()).append(separator).append(dateStartTest).toString();
        String fullPath = new StringBuilder().append(startPath).append(separator).append(name).append(separator).toString();
        System.out.println("fullPath = " + fullPath);
        try {
            path = Files.createDirectories(Paths.get(fullPath));
        } catch (IOException e) {
            _logger.error("Произошла ошибка при создании дирректории для хранения артефактов теста", e);
            throw new RuntimeException(e);
        }
        _searcher.setPathToFolderForSaveScreen(path);

    }

    // -------------------------------------------------------------------------------------------------//

    /**
     * Метод, что все файлы в каталоге для теста добавляет в Allure отчет.
     */
    @AfterMethod
    public void addScreenShotToAllureReport() {
        Path pathWhereSavedScreens = _searcher.getPathToFolderForSaveScreen();
        File pathAsFile = new File(pathWhereSavedScreens.toString());
        String[] listOfFiles = pathAsFile.list();
        String absolutePath = pathAsFile.toString();

        for (String targetFile : listOfFiles) {
            String pathForCopy = absolutePath + FileSystems.getDefault().getSeparator() + targetFile;
            Path content = Paths.get(pathForCopy);
            try (InputStream inputStream = Files.newInputStream(content)) {
                Allure.addAttachment(targetFile, inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Закрывает драйвер.
     */
    @AfterTest
    public void closeDriver() {
        _webDriver.quit();
    }

    /**
     * Добавляет ScreenShot. Сразу как будет сделан скриншот - будет добавлен к отчету Allure.
     * 
     * @return - скриншот, как массив байтов.
     */
    @Attachment(value = "ScreenShot_From_Test", type = "image/png")
    public byte[] saveScreenshot() {
        return ((TakesScreenshot) _webDriver).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Добавляет ScreenShot из файловой системы - к отчету Allure. Заменил на - Allure.addAttachment(targetFile, inputStream);
     * 
     * @param pathToFile - путь до файла - абсолютный путь в файловой системе
     * @return - скриншот, как массив байтов.
     */
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] attacheScreenFromFolder(String pathToFile) {
        byte[] bytes = null;
        try (FileInputStream fil = new FileInputStream(pathToFile)) {
            bytes = fil.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

}
