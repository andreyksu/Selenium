package ru.andreyksu.annikonenkov.webdrivers.tests.gosuslugitests.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by andrey on 17.05.17.
 */
public class OfficialPortalHelper {

    WebDriver webDriver;

    @FindBy(css = "div.header a.png_bg")
    public WebElement mainElementofPage;

    public By getLogoElementOfPage(){
        return  By.cssSelector("div.header a.png_bg");
    }

    public OfficialPortalHelper(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }
}
