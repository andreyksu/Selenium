package ru.annikonenkov.pages.mainpage;

import org.openqa.selenium.WebDriver;

import ru.annikonenkov.pages.Page;
import ru.annikonenkov.pages.mainpage.parts.Content;
import ru.annikonenkov.pages.mainpage.parts.Footer;
import ru.annikonenkov.pages.mainpage.parts.Header;
import ru.annikonenkov.pages.mainpage.parts.MainNavigation;

public class MainPageOfCitilink extends Page {

    private static String _nameOfCurrentPage = "Главная страница Citilink";

    private static String _urlOfCurrentPage = "https://www.citilink.ru/";

    public MainPageOfCitilink(WebDriver driver) {
        super(_nameOfCurrentPage, driver, _urlOfCurrentPage);
        initParts();
    }

    private void initParts() {
        addPartOfPage("header", new Header<MainPageOfCitilink>(webDriver, this));
        addPartOfPage("navigation_menu", new MainNavigation<MainPageOfCitilink>(webDriver, this));
        addPartOfPage("content", new Content<MainPageOfCitilink>(webDriver, this));
        addPartOfPage("footer", new Footer<MainPageOfCitilink>(webDriver, this));
    }
}
