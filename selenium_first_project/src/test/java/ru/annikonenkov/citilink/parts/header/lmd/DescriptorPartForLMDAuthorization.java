package ru.annikonenkov.citilink.parts.header.lmd;

import org.openqa.selenium.By;

import ru.annikonenkov.citilink.pages.MainPageOfCitilink;
import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.worker.IContainerWorker;

public class DescriptorPartForLMDAuthorization extends APartDescriptor {

    public final Element<IContainerWorker> LOGIN_FIELD_ELEMENT = new Element<>("LOGIN_FIELD", "div input#login", By::cssSelector, ETypeOfElement.FIELD);

    public final Element<IContainerWorker> PASSWORD_FIELD_ELEMENT = new Element<>("PASSWORD_FIELD", "div input#pass", By::cssSelector, ETypeOfElement.FIELD);

    public final Element<MainPageOfCitilink> SUBMIT_BUTTON_ELEMENT =
            new Element<>("SUBMIT", "div button[type='submit']", By::cssSelector, ETypeOfElement.BUTTON, new MainPageOfCitilink.PageFabric());

    public DescriptorPartForLMDAuthorization(String partName) {
        super(new Element<>(partName, "div.main_links #authorization_popup form.auth-popup__form", By::cssSelector, ETypeOfElement.PART));

        addElementToPart(LOGIN_FIELD_ELEMENT);

        addElementToPart(PASSWORD_FIELD_ELEMENT);

        addElementToPart(SUBMIT_BUTTON_ELEMENT);
    }

}
