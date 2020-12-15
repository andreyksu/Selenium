package ru.annikonenkov.common.worker;

import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.registry.ILMDRegistry;

public interface ILMDWorker extends IContainerWorker {

    public void setElementAsStartPointForThisLMD(Element<? extends IContainerWorker> webElem);

    public Element<? extends IContainerWorker> getElementAsStartPointForThisLMD();

    @Override
    ILMDRegistry getRegistry();
}
