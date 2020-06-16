package ru.annikonenkov.pages.mainpage.parts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ContentHelper{

    public ContentHelper(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

}
