package ru.andreyksu.annikonenkov.webdrivers.utils.listners;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import ru.andreyksu.annikonenkov.webdrivers.MainClassForTests;
import ru.yandex.qatools.allure.annotations.Attachment;

public class AttachLogListenerForError extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
        String str = result.getMethod().getMethodName();
        String sf = String.format("Стартовал метод %s", str);
        MainClassForTests.getLogger().info(sf);
        attachLog(sf);
    }

    @Attachment("Info Log")
    public String attachLog(String log) {
        return log;
    }
}
