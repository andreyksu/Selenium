package ru.annikonenkov.citilink.parts.footer;

import org.openqa.selenium.By;

import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;

public class DescriptorPartFooter extends APartDescriptor {

    public DescriptorPartFooter(String partName) {
        super(new Element<>(partName, "footer.footer", By::cssSelector, ETypeOfElement.PART));
    }
}
