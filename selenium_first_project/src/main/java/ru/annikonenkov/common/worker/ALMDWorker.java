package ru.annikonenkov.common.worker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.registry.LMDRegistry;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;

/**
 * Класс описывает ЛокальноМодальныйДиалог - LMD.<br>
 * По сути LMD - это контейнер для Part - он не обязательно должен иметь родителя - наверное. И само ЛМД наверное не должно являться каким то webElement. Т.е.
 * его верхне-уровневый Part - вероятно и является определителем положения. При этом этот Part должен быть самостоятельно определяемым от корня страницы.
 */
public abstract class ALMDWorker implements IContainerWorker {

    private final String _descriptionOfLMD;

    private ISearchAndAnalyzeElement _searcher;

    private final Map<String, IPartWorker<? extends ALMDWorker>> _partsAsMap = new HashMap<>();

    /**
     * Описывает WebElement - который представляет собой стартовую позицию для ЛМД.<br>
     * Когда открываем LMD - он появляется в каком-то узле - в частности у Citilink - в Part что его породил.<br>
     * Монно оставить пустым - допутим, когда LMD описан абсолютным путем - т.е. полностью обнаружимым (но данный подходет очень верный, так как если поменяется
     * путь у такого Part - то придется менять его везде.
     */
    private Element<? extends IContainerWorker> _elemAsStartPoint = null;

    private LMDRegistry _lmdRegistry;

    public ALMDWorker(LMDRegistry lmdRegistry, ISearchAndAnalyzeElement searcher) {
        _lmdRegistry = lmdRegistry;
        _searcher = searcher;
        _descriptionOfLMD = _lmdRegistry.getDescription();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return _lmdRegistry.getName();
    }

    @Override
    public String getFullDescription() {
        return _descriptionOfLMD;
    }

    public void addPartToLMD(IPartWorker<? extends ALMDWorker> part) {
        _partsAsMap.put(part.toString(), part);
    }

    public void setElementAsStartPointForThisLMD(Element<? extends IContainerWorker> webElem) {
        _elemAsStartPoint = webElem;
    }

    public Element<? extends IContainerWorker> getElementAsStartPointForThisLMD() {
        return _elemAsStartPoint;
    }

    @Override
    public boolean isPresentChildParts() {
        Collection<IPartWorker<? extends ALMDWorker>> childParts = _partsAsMap.values();
        boolean isPresent = true;
        for (IPartWorker<? extends ALMDWorker> childPart : childParts) {
            isPresent &= childPart.isPresentCurrentPart();
        }
        return isPresent;
    }

    @Override
    public boolean isPresentAllSubPartsInFullDepth() {
        Collection<IPartWorker<? extends ALMDWorker>> childParts = _partsAsMap.values();
        boolean isPresent = isPresentChildParts();
        for (IPartWorker<? extends ALMDWorker> childPart : childParts) {
            isPresent &= childPart.isPresentAllSubPartsInFullDepth();
        }
        return isPresent;
    }

    @Override
    public boolean isPresentAllElementsInFullDepth() {
        Collection<IPartWorker<? extends ALMDWorker>> childParts = _partsAsMap.values();
        boolean isPresent = true;
        for (IPartWorker<? extends ALMDWorker> childPart : childParts) {
            isPresent &= childPart.isPresentAllElementsInFullDepth();
        }
        return isPresent;
    }

    @Override
    public boolean verifyLinks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public IPartWorker<? extends IContainerWorker> getPartByName(String nameOfPart) {
        return _partsAsMap.get(nameOfPart);
    }

    @Override
    public ISearchAndAnalyzeElement getSearcherAndAnalуzerElements() {
        return _searcher;
    }

    @Override
    public WebDriver getWebDriver() {
        return _searcher.getWebDriver();
    }

    @Override
    public IContainerWorker getRootContainer() {
        return this;
    }

}
