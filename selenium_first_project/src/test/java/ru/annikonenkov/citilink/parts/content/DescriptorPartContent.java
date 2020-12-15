package ru.annikonenkov.citilink.parts.content;

import org.openqa.selenium.By;

import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;

public class DescriptorPartContent extends APartDescriptor {

    public DescriptorPartContent(String partName) {
        super(new Element<>(partName, "div#page>div#layout>div.main_content", By::cssSelector, ETypeOfElement.PART));
    }
}
