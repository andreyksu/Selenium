package ru.annikonenkov.pages.mainpage.parts;

import java.util.function.Function;

import org.openqa.selenium.By;

public enum MainNavigationHelper {

    LOGO("div.logo", By::cssSelector);

    private static final By CURRENT_PART = By.cssSelector("div.main-navigatio");

    private String _value;

    private Function<String, By> _function;

    MainNavigationHelper(String value, Function<String, By> function) {
        _value = value;
        _function = function;
    }

    public By getValue() {
        return _function.apply(_value);
    }

    public static By getCurrentPart() {
        return CURRENT_PART;
    }

    public String toString() {
        return _value;
    }

}
