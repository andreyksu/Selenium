package ru.annikonenkov.citilink.parts.header;

import org.openqa.selenium.By;

import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.worker.IContainerWorker;

public class DescriptorPartHeaderFirstFloor extends APartDescriptor {

    public final Element<IContainerWorker> PHONE_ELEMENT =
            new Element<>("PHONE_ELEMENT", ".header_inner__phone .dropdown__contacts-phone", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> CITY_ELEMENT = new Element<>("CITY_ELEMENT", ".header_inner__phone .city", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> ARSENAL_ELEMENT =
            new Element<>("ARSENAL_ELEMENT", ".header_inner__section-list .item_arsenal", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> PROMOTIONS_ELEMENT =
            new Element<>("PROMOTIONS_ELEMENT", ".header_inner__section-list div[data-link-type='Promotions']", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> INFO_ELEMENT =
            new Element<>("INFO_ELEMENT", ".header_inner__section-list div[data-link-type='Info']", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> BUSINESS_ELEMENT =
            new Element<>("BUSINESS_ELEMENT", ".header_inner__section-list div[data-link-type='Business']", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> DELIVERY_ELEMENT =
            new Element<>("DELIVERY_ELEMENT", ".header_inner__section-list div[data-link-type='Delivery']", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> FEEDBACK_ELEMENT =
            new Element<>("FEEDBACK_ELEMENT", ".header_inner__section-list div[data-link-type='']", By::cssSelector, ETypeOfElement.LINK);

    public DescriptorPartHeaderFirstFloor(String partName) {
        super(new Element<>(partName, ".header_inner__first-floor", By::cssSelector, ETypeOfElement.PART));

        addElementToPart(PHONE_ELEMENT);

        addElementToPart(CITY_ELEMENT);

        addElementToPart(ARSENAL_ELEMENT);

        addElementToPart(PROMOTIONS_ELEMENT);

        addElementToPart(INFO_ELEMENT);

        addElementToPart(BUSINESS_ELEMENT);

        addElementToPart(DELIVERY_ELEMENT);

        addElementToPart(FEEDBACK_ELEMENT);

    }

}
