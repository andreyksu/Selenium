package ru.annikonenkov.pages;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

public abstract class Part<T extends IContainer> implements IPart<T> {

    private final String _nameOfPart;

    private T _owner;

    private Map<String, IPart<? extends IContainer>> _subPartsAsMap = new HashMap<>();

    protected WebDriver webDriver;

    public Part(WebDriver driver, T owner, String nameOfPart) {
        _owner = owner;
        _nameOfPart = nameOfPart;
        webDriver = driver;
    }

    @Override
    public String getName() {
        StringBuilder sb = new StringBuilder().append(_owner.getName()).append("--->").append(_nameOfPart);
        return sb.toString();
    }

    @Override
    public T getOwner() {
        return _owner;
    }

    @Override
    public Map<String, IPart<? extends IContainer>> getPartsAsMap() {
        return _subPartsAsMap;
    }

    @Override
    public <K extends IContainer> void addSubPart(String namePart, IPart<K> part) {
        _subPartsAsMap.put(namePart, part);
    }

    @Override
    public boolean isPresentParsts() {
        Collection<IPart<? extends IContainer>> subParts = _subPartsAsMap.values();
        boolean present = true;
        for (IPart<? extends IContainer> subPart : subParts) {
            present &= subPart.isPresentCurrentPart();
        }
        return present;
    }

    @Override
    public boolean isPresentAllSubParts() {
        Collection<IPart<? extends IContainer>> subParts = _subPartsAsMap.values();
        boolean present = true;
        present &= isPresentParsts();
        for (IPart<? extends IContainer> subPart : subParts) {
            present &= subPart.isPresentAllSubParts();
        }
        return present;

    }

    @Override
    public boolean isPresentAllElements() {
        Collection<IPart<? extends IContainer>> parts = _subPartsAsMap.values();
        boolean present = true;
        present &= isPresentCurrentElements();
        for (IPart<? extends IContainer> iPart : parts) {
            present &= iPart.isPresentAllElements();
        }
        return present;
    }
}
