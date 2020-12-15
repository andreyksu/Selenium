package ru.annikonenkov.citilink.registry;

import java.util.Optional;

import ru.annikonenkov.citilink.parts.content.PartContentOnMainPage;
import ru.annikonenkov.citilink.parts.content.PartContent;
import ru.annikonenkov.citilink.parts.content.PartContentAfterSearch;
import ru.annikonenkov.citilink.parts.content.PartContentOnTrolleyPage;
import ru.annikonenkov.citilink.parts.content.PartContentOnTrolleyPageCalculation;
import ru.annikonenkov.citilink.parts.content.PartContetntOnTrolleyPageItems;
import ru.annikonenkov.citilink.parts.footer.PartFooter;
import ru.annikonenkov.citilink.parts.header.PartHeader;
import ru.annikonenkov.citilink.parts.header.PartHeaderFirstFloor;
import ru.annikonenkov.citilink.parts.header.PartHeaderSecondFloor;
import ru.annikonenkov.citilink.parts.header.lmd.PartForLMDAuthorization;
import ru.annikonenkov.citilink.parts.mainnavigation.PartMainNavigation;
import ru.annikonenkov.citilink.parts.mainnavigation.PartMainNavigationCategories;
import ru.annikonenkov.citilink.parts.mainnavigation.PartMainNavigationDiscounter;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.registry.TypeInfo;
import ru.annikonenkov.common.worker.IPartWorker;

public enum PartCitilinkRegistry implements IPartRegistry {

    // ----------------------------------------Заголовок----------------------------------------
    @TypeInfo(PartHeader.class)
    PART_HEADER("Заголовок (Верхняя часть страницы)"),

    @TypeInfo(PartHeaderFirstFloor.class)
    UPPER_PART_HEADER("Часть заголовка с информацией и телефоном", PART_HEADER),

    @TypeInfo(PartHeaderSecondFloor.class)
    UNDER_PART_HEADER("Часть заголовка с поиском/корзиной", PART_HEADER),
    // UNDER_SUB_PART_HEADER("Часть заголовка с авторизацией и информацией о пользователе", PART_HEADER),

    // ----------------------------------------Содержимое страницы----------------------------------------
    @TypeInfo(PartContent.class)
    PART_CONTENT("Контент страницы"),

    @TypeInfo(PartContentOnMainPage.class)
    PART_CONTENT_AFTER_START_PAGE("Контент гостевой страницы!", PART_CONTENT),

    @TypeInfo(PartContentAfterSearch.class)
    PART_CONTENT_AFTER_SEARCH("Содержимое контента после поиска!", PART_CONTENT),

    @TypeInfo(PartContentOnTrolleyPage.class)
    TROLLEY_CONTENT("Корзина (родительский Part для содержимого корзины и счета)", PART_CONTENT),

    @TypeInfo(PartContentOnTrolleyPageCalculation.class)
    CALCULATION_CONTENT_ON_TROLLY_PAGE("Работа с заказом 'счет и кнопка продолжения выполнения заказа'", TROLLEY_CONTENT),

    @TypeInfo(PartContetntOnTrolleyPageItems.class)
    ITEMS_CONTENT_ON_TROLLY_PAGE("Part для работы с добавленными в корзину товарами", TROLLEY_CONTENT),

    // ----------------------------------------Подвал----------------------------------------
    @TypeInfo(PartFooter.class)
    PART_FOOTER("Подвал"),

    // ----------------------------------------LMD Авторизации----------------------------------------
    @TypeInfo(PartForLMDAuthorization.class)
    PART_FOR_LMD_AUTHRIZATION("Главный Part для LMD авторизации"),

    // ----------------------------------------Навигационная панель----------------------------------------
    @TypeInfo(PartMainNavigation.class)
    PART_MAIN_NAVIGATION("Навигационная панель"),

    @TypeInfo(PartMainNavigationCategories.class)
    PART_MAIN_NAVIGATION_CATEGORIES("Основная категория", PART_MAIN_NAVIGATION),

    @TypeInfo(PartMainNavigationDiscounter.class)
    PART_MAIN_NAVIGATION_DISCOUNTER("Категория скидок", PART_MAIN_NAVIGATION);

    private String _description;

    private IPartRegistry _partRegistry;

    // private Class<? extends IPartWorker> _classOfPartWorker;

    private <T extends IPartWorker> PartCitilinkRegistry(String desciption) {
        _description = desciption;
    }

    private <T extends IPartWorker> PartCitilinkRegistry(String desciption, IPartRegistry partRegistry) {
        _description = desciption;
        _partRegistry = partRegistry;
    }

    @Override
    public String getDescription() {
        return _description;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public Optional<IPartRegistry> getParentPartRegistry() {
        return Optional.ofNullable(_partRegistry);
    }

    @Override
    public boolean hasParentPart() {
        return _partRegistry == null ? false : true;

    }
}
