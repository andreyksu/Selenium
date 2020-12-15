package ru.annikonenkov.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import io.qameta.allure.Attachment;

public class ScreenShotOnFailure extends TestListenerAdapter {

    private final static Logger _logger = LogManager.getLogger(ScreenShotOnFailure.class);

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        _logger.error("--------------------------------------Ошииииииибка--------------------------------------");
        /*
         * Object currentClass = tr.getInstance(); WebDriver driver = ((BaseTestClass) currentClass)._webDriver; byte[] srcFile = ((TakesScreenshot)
         * driver).getScreenshotAs(OutputType.BYTES); saveScreenshot(srcFile);
         */
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        _logger.info("+++++++++++++++++++++++++++++++++++++Успех+++++++++++++++++++++++++++++++++++++");
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] saveScreenshot(byte[] screenshot) {
        return screenshot;
    }

}
