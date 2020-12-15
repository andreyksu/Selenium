package ru.annikonenkov.citilink.parts.header;

import org.openqa.selenium.By;

import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;

public class DescriptorPartHeader extends APartDescriptor {

    public DescriptorPartHeader(String partName) {
        super(new Element<>(partName, "div#page>div#layout>header>.header_inner", By::cssSelector, ETypeOfElement.PART));
    }
}
