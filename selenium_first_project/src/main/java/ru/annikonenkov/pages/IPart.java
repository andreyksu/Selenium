package ru.annikonenkov.pages;

import java.util.Map;

/**
 * Интерфейс описывает составную часть страницы, либо части из которой состоит страница.
 * 
 * @param <T> - этот параметр указывает на родителя. Т.е. обозначает тип родителя.<br>
 *            IPart родителем может быть {@link IPage} или {@link IPart}
 */

public interface IPart<T extends IContainer> extends IContainer {

    /**
     * Возвращает родителя. Вернет либо Страницу либо Часть страницы.
     * 
     * @return
     */
    T getOwner();

    /**
     * Возвращает составные части - для текущей части старицы.
     * 
     * @param <L>
     * @return
     */
    Map<String, IPart<? extends IContainer>> getPartsAsMap();

    /**
     * Добавляет составную часть к текущей части страницы.
     * 
     * @param <K>
     * @param namePart
     * @param part
     */
    <K extends IContainer> void addSubPart(String namePart, IPart<K> part);

    /**
     * Проверяет наличие самого себя.
     * 
     * @return
     */

    boolean isPresentCurrentPart();

    /**
     * Проверяет наличие только своих элементов - без проверки вниз по иерархии.
     * 
     * @return
     */

    boolean isPresentCurrentElements();

}
