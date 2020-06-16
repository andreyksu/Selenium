package ru.annikonenkov.citilink.type.page_and_parts;

import ru.annikonenkov.common.registry.LMDRegistry;

public enum LMDCitilinkRgistry implements LMDRegistry {

    LMD_FOR_LOGIN("LMD для авторизации. Ввод логин/пароль."),

    LMD_FOR_PERSONAL_INFO("LMD с информацией о пользователе, после авторизации. Бонусы/Заказы/Мой Кабинет/Клубная карта/Выйти"),;

    private String _description;

    private LMDCitilinkRgistry(String description) {
        _description = description;
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
