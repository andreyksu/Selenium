package ru.annikonenkov.common.worker;

import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.registry.ILMDRegistry;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;

/**
 * Класс описывает ЛокальноМодальныйДиалог - LMD.<br>
 * По сути LMD - это контейнер для Part - он не обязательно должен иметь родителя - наверное. И само ЛМД наверное не должно являться каким то webElement. Т.е.
 * его верхне-уровневый Part - вероятно и является определителем положения. При этом этот Part должен быть самостоятельно определяемым от корня страницы.
 */
public abstract class ALMDWorker extends AHightLevelContainer implements ILMDWorker {

    /**
     * Описывает WebElement - который представляет собой стартовую позицию для ЛМД.<br>
     * Когда открываем LMD - он появляется в каком-то узле - в частности у Citilink - в Part что его породил.<br>
     * Монно оставить пустым - допутим, когда LMD описан абсолютным путем - т.е. полностью обнаружимым (но данный подходет очень верный, так как если поменяется
     * путь у такого Part - то придется менять его везде.
     */
    private Element<? extends IContainerWorker> _elemAsStartPoint = null;

    private ILMDRegistry _lmdRegistry;

    public ALMDWorker(ILMDRegistry lmdRegistry, ISearchAndAnalyzeElement searcher) {
        super(lmdRegistry, searcher);
        _lmdRegistry = lmdRegistry;
    }

    @Override
    public void setElementAsStartPointForThisLMD(Element<? extends IContainerWorker> webElem) {
        _elemAsStartPoint = webElem;
    }

    @Override
    public Element<? extends IContainerWorker> getElementAsStartPointForThisLMD() {
        return _elemAsStartPoint;
    }

    @Override
    public ILMDRegistry getRegistry() {
        return _lmdRegistry;
    }
}
