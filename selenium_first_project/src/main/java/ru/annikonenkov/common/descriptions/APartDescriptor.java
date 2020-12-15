package ru.annikonenkov.common.descriptions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ru.annikonenkov.common.worker.IContainerWorker;

public abstract class APartDescriptor {

    /**
     * Содержит Element_ы, что должны быть проверены, при выполнении метода {@link IContainerWorker#isPresentAllElementsInFullDepth()}. Т.е. по сути минимально
     * необходимый набор Element по которым можно определить, Part загрузился в полном объеме или нет.
     * <p>
     * Целенаправленно нигде не возвращаю Map. Так как однозначно будет попытка адресного обращения к элементу, через полученную Map. А адресное обращение будет
     * вестись через String. Понятно, что имя может поменяться и придется искать, где же обращение шло по имени. А от сюда много проблем. <br>
     * Нужно понимать, что здесь не Enum и именно String - и помененное имя отловить будет тяжело. Enum не удается применить из за параметризации у Element.
     * <p>
     * По этой причине возвращаю коллекцию элементов для перебора и не возвращаю Map.
     */
    private final Map<String, Element<? extends IContainerWorker>> _mapOfElement = new HashMap<>();

    /**
     * Описывает текущий Part. Т.е. для которого PartDescriptor создается.
     */
    private final Element<? extends IContainerWorker> _elementRepresentsCurrentPart;

    /**
     * Конструктор
     * 
     * @param elemetRepresentsCurrentPart - элемент, что писывает Part.
     */
    public APartDescriptor(Element<? extends IContainerWorker> elemetRepresentsCurrentPart) {
        _elementRepresentsCurrentPart = elemetRepresentsCurrentPart;
    }

    /**
     * Возвращает Element для текущего Part.
     * 
     * @return Element - для текущего Part
     */
    public Element<? extends IContainerWorker> getElementForCurrentPart() {
        return _elementRepresentsCurrentPart;
    };

    /**
     * Возвращает коллекцию элементов обязательных для проверки в текущем Part.<br>
     * Проверка выполняется методом вида {@link IContainerWorker#isPresentAllElementsInFullDepth()}.
     * 
     * @return
     */
    public Collection<Element<? extends IContainerWorker>> getCollectionOfElements() {
        return _mapOfElement.values();
    }

    /**
     * Добавляет в PartDescriptor новый Element.
     */
    protected void addElementToPart(Element<? extends IContainerWorker> element) {
        _mapOfElement.put(element.getName(), element);
    }
}
