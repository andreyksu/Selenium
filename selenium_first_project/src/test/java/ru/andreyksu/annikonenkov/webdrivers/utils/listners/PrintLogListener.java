package ru.andreyksu.annikonenkov.webdrivers.utils.listners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class PrintLogListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        //TODO добавить вывод информации (т.е для вывода информационных сообщений) сюда на страте и по аналогии для остальных методов.
    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
