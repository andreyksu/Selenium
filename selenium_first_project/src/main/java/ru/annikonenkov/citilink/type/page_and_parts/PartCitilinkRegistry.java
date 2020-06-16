package ru.annikonenkov.citilink.type.page_and_parts;

import ru.annikonenkov.common.registry.PartRegistry;

public enum PartCitilinkRegistry implements PartRegistry {

    // ---Заголовок---
    PART_HEADER("Заголовок (Верхняя часть страницы)"),
    UPPER_PART_HEADER("Часть заголовка с информацией и телефоном"),
    UNDER_PART_HEADER("Часть заголовка с поиском/корзиной"),
    UNDER_SUB_PART_HEADER("Часть заголовка с авторизацией и информацией о пользователе"),
    // ---Содержимое страницы---
    PART_CONTENT("Контент страницы"),
    PART_CONTENT_AFTER_START_PAGE("Контент гостевой страницы!"),
    PART_CONTENT_AFTER_SEARCH("Содержимое контента после поиска!"),
    // ---Подвал---
    PART_FOOTER("Подвал"),
    // ---LMD Авторизации---
    PART_FOR_LMD_AUTHRIZATION("Главный Part для LMD авторизации"),
    // ---Навигационная панель---
    PART_MAIN_NAVIGATION("Навигационная панель"),
    PART_MAIN_NAVIGATION_CATEGORIES("Основная категория"),
    PART_MAIN_NAVIGATION_DISCOUNTER("Категория скидок")

    ;

    private String _description;

    private PartCitilinkRegistry(String desciption) {
        _description = desciption;
    }

    @Override
    public String getDescription() {
        return _description;
    }

    @Override
    public String getName() {
        return this.name();
    }

}
