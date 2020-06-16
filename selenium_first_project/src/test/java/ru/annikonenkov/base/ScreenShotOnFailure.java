package ru.annikonenkov.base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import io.qameta.allure.Attachment;

public class ScreenShotOnFailure extends TestListenerAdapter {

    /*@Override
    public void onTestFailure(ITestResult tr) {
        Object currentClass = tr.getInstance();
        WebDriver driver = ((VerifyFirstPage) currentClass).webDriver;
        byte[] srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        saveScreenshot(srcFile);
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] saveScreenshot(byte[] screenshot) {
        return screenshot;
    }*/

}
