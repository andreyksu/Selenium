package ru.annikonenkov.common.worker;

import java.util.Map;

/**
 * Интерфейс - описывает страницу.
 */

public interface IPageWorker extends IContainerWorker {

    /**
     * Возвращает URL страницы. Т.е. URL по которому можно получить данную страницу.
     * 
     * @return
     */
    String getURLOfPage();

    /**
     * Метод возвращает составные части страницы.<br>
     * Каждая страница состоит из частей - к примеру навигационное меню, заголовок, подвал итд. Т.е. части в свою
     * очередь могут состоять из более специфичных частей (допустим часть - подвал содедиржит две части: часть с
     * контактами и вторая часть еще с чем-то).
     * 
     * @return
     */

    Map<String, IPartWorker<? extends IPageWorker>> getPartsOfPageAsMap();

    /**
     * Добавляет к странице составныую часть.
     * 
     * @param namePart
     * @param part
     */
    void addPartToPage(IPartWorker<? extends IPageWorker> part);

    /**
     * Открывает страницу. Как правило используется для этого URL - что возвращается методом getURLOfPage()
     * 
     * @return
     */
    IPageWorker openPage();
}
