package ru.annikonenkov.citilink.parts.mainnavigation;

import org.openqa.selenium.By;

import ru.annikonenkov.citilink.pages.MainPageOfCitilink;
import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;

public class DescriptorPartMainNavigation extends APartDescriptor {

    public final Element<MainPageOfCitilink> LOGO_ELEMENT =
            new Element<>("LOGO_ELEMENT", "div.logo a", By::cssSelector, ETypeOfElement.LINK, MainPageOfCitilink::new);

    public DescriptorPartMainNavigation(String partName) {
        super(new Element<>(partName, "div#page>div#layout>div.main-navigation", By::cssSelector, ETypeOfElement.PART));

        addElementToPart(LOGO_ELEMENT);
    }
}
