package ru.annikonenkov.pages.mainpage.parts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class HeaderHelper {

    public HeaderHelper(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

}
