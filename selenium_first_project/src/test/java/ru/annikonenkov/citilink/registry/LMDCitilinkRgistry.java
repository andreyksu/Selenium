package ru.annikonenkov.citilink.registry;

import ru.annikonenkov.common.registry.ILMDRegistry;

public enum LMDCitilinkRgistry implements ILMDRegistry {

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
