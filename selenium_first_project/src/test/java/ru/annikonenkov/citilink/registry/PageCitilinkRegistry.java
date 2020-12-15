package ru.annikonenkov.citilink.registry;

import ru.annikonenkov.common.registry.IPageRegistry;

public enum PageCitilinkRegistry implements IPageRegistry {

    MAIN_PAGE_OF_CITILINK("Главная страница Citilink до авторизации", "https://www.citilink.ru/"),

    MAIN_PAGE_OF_CITILINK_AFTER_SEARCH("Страница Citilink после выполнения поиска", "https://www.citilink.ru/search?"),

    PAGE_MOBILE_CATALOG("Страница мобильных телефонов", "https://www.citilink.ru/catalog/mobile/"),

    TROLLEY_PAGE("Страничка корзины", "https://www.citilink.ru/order/");

    private String _url;

    private String _description;

    private PageCitilinkRegistry(String description, String url) {
        _url = url;
        _description = description;
    }

    @Override
    public String getURL() {
        return _url;
    }

    @Override
    public String getDescription() {
        return _description;
    }

    public String getName() {
        return this.name();
    }

}
