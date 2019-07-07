package ru.andreyksu.annikonenkov.webdrivers.utils.listners;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import ru.andreyksu.annikonenkov.webdrivers.MainClassForTests;

/**
 * Класс который будет вызван листнером при возникновении ошибок в тесте.
 */
//TODO перенести сюда снятие скриншота.

public class ScreenShotOnFailure extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult tr) {
        String str = tr.getMethod().getMethodName();
        MainClassForTests.getLogger().error("Ошибка произошла в методе " + str);
    }


}
