package ru.annikonenkov.citilink.type.page_and_parts;

import ru.annikonenkov.common.registry.PageRegistry;

public enum PageCitilinkRegistry implements PageRegistry {

    // ---Главная страница без именений---
    MAIN_PAGE_OF_CITILINK("Главная страница Citilink до авторизации", "https://www.citilink.ru/"),

    // ---Главная страница после поиска (опять же результат поиска до авторизации, результат поиска после авторизации. Набор будет разным)---
    MAIN_PAGE_OF_CITILINK_AFTER_SEARCH("Страница Citilink после выполнения поиска", "https://www.citilink.ru/search?"),

    // ---Главная страница после поиска (опять же до и после авториазации - либо это как-то игнорировать. Т.е. в Part пропускаем этот момент, а только при
    // обращении к методам логин/логаут)---
    PAGE_MABILE_CATALOG("Страница мобильных телефонов", "https://www.citilink.ru/catalog/mobile/"),

    ;

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
