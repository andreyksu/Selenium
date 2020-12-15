package ru.annikonenkov.citilink.parts.content;

import org.openqa.selenium.By;

import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;

public class DescriptorPartContentOnMainPage extends APartDescriptor {

    public DescriptorPartContentOnMainPage(String partName) {
        super(new Element<>(partName, "#content.home_content", By::cssSelector, ETypeOfElement.PART));
    }

}
