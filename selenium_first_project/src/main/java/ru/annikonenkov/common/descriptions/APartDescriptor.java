package ru.annikonenkov.common.descriptions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ru.annikonenkov.common.worker.IContainerWorker;

public abstract class APartDescriptor {

    private final Map<String, Element<? extends IContainerWorker>> _mapOfElement = new HashMap<>();

    private final Element<? extends IContainerWorker> _elementRepresentsCurrentPart;

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

    public Collection<Element<? extends IContainerWorker>> getCollectionOfElements() {
        return _mapOfElement.values();
    }

    protected void addElementToPart(Element<? extends IContainerWorker> element) {
        _mapOfElement.put(element.toString(), element);
    }

    public Element<? extends IContainerWorker> getElementByName(String key) {
        return _mapOfElement.get(key);
    }

}
