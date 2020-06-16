package ru.annikonenkov.pages;

import java.util.Map;

/**
 * Интерфейс - описывает страницу.
 */

public interface IPage extends IContainer {

    /**
     * Возвращает URL страницы. Т.е. URL по которому можно получить данную страницу.
     * 
     * @return
     */
    String getURLOfPage();

    /**
     * Метод возвращает составные части страницы. Страница состоит из частей - это может быть навигационное меню,
     * заголовок, подвал итд. Т.е. в совю очередь могут содержать подчасти.
     * 
     * @return
     */

    Map<String, IPart<? extends IPage>> getPartsOfPageAsMap();

    /**
     * Добавляет к странице составные части.
     * 
     * @param namePart
     * @param part
     */
    void addPartOfPage(String namePart, IPart<? extends IPage> part);

    /**
     * Открывает страницу. Как правило используется для этого URL - что возвращается методом getURLOfPage()
     * 
     * @return
     */
    IPage openItselfPage();
}
