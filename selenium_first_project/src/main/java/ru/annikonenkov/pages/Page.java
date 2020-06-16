package ru.annikonenkov.pages;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

public abstract class Page implements IPage {

    private final String _nameOfPage;

    private final String _urlOfPage;

    private final Map<String, IPart<? extends IPage>> _partsAsMap = new HashMap<>();

    protected final WebDriver webDriver;

    public Page(String nameOfPage, WebDriver driver, String urlOfPage) {
        _nameOfPage = nameOfPage;
        _urlOfPage = urlOfPage;
        webDriver = driver;
    }

    @Override
    public String getName() {
        return _nameOfPage;
    }

    @Override
    public Map<String, IPart<? extends IPage>> getPartsOfPageAsMap() {
        return _partsAsMap;
    }

    @Override
    public void addPartOfPage(String namePart, IPart<? extends IPage> part) {
        _partsAsMap.put(namePart, part);
    }

    @Override
    public String getURLOfPage() {
        return _urlOfPage;
    }

    @Override
    public IPage openItselfPage() {
        webDriver.get(_urlOfPage);
        return this;
    }

    @Override
    public boolean isPresentParsts() {
        Collection<IPart<? extends IPage>> parts = _partsAsMap.values();
        boolean present = true;
        for (IPart<? extends IPage> part : parts) {
            present &= part.isPresentCurrentPart();
        }
        return present;
    }

    @Override
    public boolean isPresentAllSubParts() {
        Collection<IPart<? extends IPage>> parts = _partsAsMap.values();
        boolean present = true;
        for (IPart<? extends IPage> part : parts) {
            present &= part.isPresentAllSubParts();
        }
        return present;
    }

    @Override
    public boolean isPresentAllElements() {
        Collection<IPart<? extends IPage>> parts = _partsAsMap.values();
        boolean present = true;
        for (IPart<? extends IPage> part : parts) {
            present &= part.isPresentAllElements();
        }
        return present;
    }

}
