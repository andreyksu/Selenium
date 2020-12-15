package ru.annikonenkov.common.descriptions;

import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ru.annikonenkov.common.exceptions.UnavailableTargetElement;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.worker.IContainerWorker;

public interface IElement<T extends IContainerWorker> {

    String getName();

    By getBy();

    ETypeOfElement getTypeOfElement();

    boolean hasProducedContainer();

    Optional<IContainerFabric<T>> getProducedContainer();

    void setWebElement(WebElement webElement);

    WebElement getWebElementOrThrow() throws UnavailableTargetElement;

    Optional<WebElement> getWebElement();

    String getSelectorOfElementAsString();
}
