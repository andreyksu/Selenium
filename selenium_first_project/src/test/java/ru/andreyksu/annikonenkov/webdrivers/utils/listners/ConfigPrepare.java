package ru.andreyksu.annikonenkov.webdrivers.utils.listners;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class ConfigPrepare implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if(method.getTestMethod().isTest()){
            Object instance = testResult.getInstance();

        }

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

    }
}
