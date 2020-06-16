package ru.annikonenkov.common.worker;

import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.exceptions.UnavailableParentWebElement;

/**
 * Интерфейс описывает составную часть страницы, либо части из которой состоит страница. <br>
 * Как правило, Part на первом уровне описывает - логическое разделение страницы (навигационное дерево, контент,
 * заголовок, подвал итд).<br>
 * На более низком уровне, Part описывает контейнер в котором содержатся другие контейнеры или элементы.<br>
 * 
 * @param <T> - этот параметр указывает на родителя. Т.е. обозначает тип родителя.<br>
 *            Для {@link IPartWorker} родителем может быть {@link IPageWorker} или {@link IPartWorker}
 */

public interface IPartWorker<T extends IContainerWorker> extends IContainerWorker {

    /**
     * Возвращает родителя. Вернет либо Страницу либо Часть страницы.
     * 
     * @return
     */
    T getOwner();

    /**
     * Добавляет составную часть к текущей части страницы.
     * 
     * @param <K>
     * @param namePart
     * @param part
     */
    <K extends IPartWorker<?>> void addSubPart(IPartWorker<K> part);

    /**
     * Проверяет наличие самого себя.
     * 
     * @return
     */

    boolean isPresentCurrentPart();

    /**
     * Проверяет наличие только своих элементов - без проверки вниз по иерархии(т.е. только тех, что находится в тек.
     * <i>Part</i>). По сути аналог <i>isPresentChildParts()</i> но для элементов.
     * 
     * @return
     */

    boolean isPresentItsOwnElements();

    /**
     * Возвращает WebElement текущего Part. При использовании тек. метода - для Part его webElement будет найден
     * повторно.
     * 
     * @return - возвращает Element для целевого Part.
     * @throws UnavailableParentWebElement - если родительский Part или LMD в своем Element содержит webElement = null.
     */
    Element<? extends IContainerWorker> getElementForPart() throws UnavailableParentWebElement;

    /**
     * @param doNewSearch - маркер, выполнения повторного писка webElement данного Part. false - поиск не будет выполнен
     *            повторно. true - поиск будет выполняться повторно.
     * @return - Элемент.
     * @throws UnavailableParentWebElement - если родительский Part или LMD в своем Element содержит webElement = null.
     */
    Element<? extends IContainerWorker> getElementForPart(boolean doNewSearch) throws UnavailableParentWebElement;
}
