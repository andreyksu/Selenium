package ru.annikonenkov.common.worker;

import ru.annikonenkov.common.registry.IPageRegistry;

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
     * Открывает страницу. Как правило используется для этого URL - что возвращается методом getURLOfPage()
     * 
     * @return
     */
    IPageWorker openPage();

    @Override
    IPageRegistry getRegistry();
}
