package ru.annikonenkov.citilink.parts.content;

import org.openqa.selenium.By;

import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;

public class DescriptorPartContentOnTrolleyPage extends APartDescriptor {

    public DescriptorPartContentOnTrolleyPage(String partName) {
        super(new Element<>(partName, "div.main_content_wrapper", By::cssSelector, ETypeOfElement.PART));
    }

}
