package ru.annikonenkov.pages.mainpage.parts;

import org.openqa.selenium.WebDriver;

import ru.annikonenkov.pages.IPart;
import ru.annikonenkov.pages.Part;

public class MainNavigationDiscounter<T extends IPart<?>> extends Part<T> {
    
    private static String _nameOfCurrentPart = "Категория скидок";

    public MainNavigationDiscounter(WebDriver driver, T owner) {
        super(driver, owner, _nameOfCurrentPart);
    }

    @Override
    public boolean isPresentCurrentPart() {
        return false;
    }

    @Override
    public boolean isPresentCurrentElements() {
        return false;
    }

}
