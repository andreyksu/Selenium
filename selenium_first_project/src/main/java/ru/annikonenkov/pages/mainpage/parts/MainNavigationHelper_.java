package ru.annikonenkov.pages.mainpage.parts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainNavigationHelper_ {

    public MainNavigationHelper_(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(css = "div.main-navigation")
    public WebElement currentPart;

    public By getCurrentPart() {
        return By.cssSelector("div.main-navigation");
    }

    // ----------------------------------//
    public By getLogo() {
        return By.cssSelector("div.logo");
    }
}
