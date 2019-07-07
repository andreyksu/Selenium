package ru.andreyksu.annikonenkov.webdrivers.tests.gosuslugitests.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by andrey on 02.04.2017.
 */
public class MainMenuHelper {
    WebDriver webDriver;

    @FindBy(css = "body>div#art")
    public WebElement mainMenu;

    @FindBy(css = "a[title*='Официальный портал']")
    public WebElement officialPortal;

    @FindBy(css = "a[title*='Президент Республики']")
    public WebElement presidentRT;

    @FindBy(css = "a[title*='Государственный Совет']")
    public WebElement governmentCouncilRT;

    @FindBy(css = "a[title*='Правительство Республики']")
    public WebElement governmentRT;

    @FindBy(css = "a[title*='Портал муниципальных']")
    public WebElement cityDistrictRT;

    @FindBy(css = "a[title*='Государственные услуги']")
    public WebElement governmentService;

    public By getContainerMainMenu() {
        return By.cssSelector("div#ar");
    }

    public By getListElements() {
        return By.cssSelector("li");
    }

    public By getLogoElementOfPage() {
        return By.cssSelector("div#header a.logo");
    }

    public MainMenuHelper(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

}
