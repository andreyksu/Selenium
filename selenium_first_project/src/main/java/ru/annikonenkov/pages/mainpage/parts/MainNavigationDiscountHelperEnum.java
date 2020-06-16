package ru.annikonenkov.pages.mainpage.parts;

import java.util.function.Function;

import org.openqa.selenium.By;

public enum MainNavigationDiscountHelperEnum {

    MENU_CATEGORIES("menu.menu.menu_discount", By::cssSelector),
    SERVICES("//span/span[contains(., 'Сервисы и услуги')]", By::xpath),
    COMMODITY("//span/span[contains(., 'Уцененные товары')]", By::xpath),
    SERTIFICAT("//span/span[contains(., 'Подарочные сертификаты')]", By::xpath);

    private String _value;

    private Function<String, By> _function;

    MainNavigationDiscountHelperEnum(String value, Function<String, By> function) {
        _value = value;
        _function = function;
    }

    public By getValue() {
        return _function.apply(_value);
    }

    public By getCurrentPart() {
        return MainNavigationDiscountHelperEnum.values()[0].getValue();
    }

    public String toString() {
        return _value;
    }

}

// xx = $x("//div[@class='main-navigation']//menu[contains(@class, 'menu_discount')]//span/a[contains(text(), ' ')]");
// xxx = xx.filter(function(item){return item.innerText.includes("ffff");})
// xx = $x("//div[@class='main-navigation']//menu[contains(@class, 'menu_discount')]//span/span[contains(., 'Сервисы
// и услуги')]");
