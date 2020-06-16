package ru.annikonenkov.pages.mainpage.parts;

import org.openqa.selenium.By;
import java.util.function.Function;

public enum MainNavigationCategoriesHelperEnum {

    MENU_CATEGORIES("menu.menu.menu_categories", By::cssSelector), 
    PHONES("li.menu-item_cat_phones", By::cssSelector),
    COMPUTERS("li.menu-item_cat_computer", By::cssSelector), 
    TV("li.menu-item_cat_tv", By::cssSelector), 
    DOMESTIC("li.menu-item_cat_domestic_appliances", By::cssSelector),
    TOOLS("li.menu-item_cat_cottage_tools", By::cssSelector),
    PHOTO("li.menu-item_cat_photo", By::cssSelector),
    AUTO("li.menu-item_cat_auto", By::cssSelector),
    OFFICE("li.menu-item_cat_office", By::cssSelector),
    BEAUTY("li.menu-item_cat_beauty", By::cssSelector),
    SPORT("li.menu-item_cat_active_sport", By::cssSelector),
    GAME("li.menu-item_cat_game", By::cssSelector);

    private String _value;
    private Function<String, By> _function;

    MainNavigationCategoriesHelperEnum(String value, Function<String, By> function) {
        _value = value;
        _function = function;
    }

    public By getValue() {
        return _function.apply(_value);
    }    
    
    public By getCurrentPart() {
        return MainNavigationCategoriesHelperEnum.values()[0].getValue();
    }
    
    public String toString() {
        return _value;
    }

}
