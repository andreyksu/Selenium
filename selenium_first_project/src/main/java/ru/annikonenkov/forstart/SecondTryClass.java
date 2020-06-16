package ru.annikonenkov.forstart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import ru.annikonenkov.pages.IPage;
import ru.annikonenkov.pages.mainpage.MainPageOfCitilink;

public class SecondTryClass {

    private static WebDriver webDriver = null;

    private static void sutUpWebDriver() {
        System.setProperty("webdriver.chrome.driver", "/opt/webDrivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        webDriver = new ChromeDriver(options);
        webDriver.manage().window().maximize();

        Navigation nav = webDriver.navigate();
    }

    public static void main(String[] args) {
        sutUpWebDriver();
        webDriver.get("https://www.citilink.ru/");
        IPage mainPageOfSitilink = new MainPageOfCitilink(webDriver);
        String string = mainPageOfSitilink.getName();
        webDriver.quit();

    }

}
