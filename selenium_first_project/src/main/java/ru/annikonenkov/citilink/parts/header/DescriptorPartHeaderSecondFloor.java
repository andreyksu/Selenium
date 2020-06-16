package ru.annikonenkov.citilink.parts.header;

import org.openqa.selenium.By;

import ru.annikonenkov.citilink.pages.MainPageOfCitilinkAfterSearch;
import ru.annikonenkov.citilink.parts.header.lmd.LMDForLogin;
import ru.annikonenkov.citilink.parts.header.lmd.LMDForPersonal;
import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.worker.IContainerWorker;

public class DescriptorPartHeaderSecondFloor extends APartDescriptor {

    private final String selectroSearchElement = ".search_header input#global_search";

    public final Element<MainPageOfCitilinkAfterSearch> SEARCH_ELEMENT =
            new Element<>("SEARCH_ELEMENT", selectroSearchElement, By::cssSelector, ETypeOfElement.FIELD, MainPageOfCitilinkAfterSearch::new);

    public final Element<IContainerWorker> TROLLY_ELEMENT = new Element<>("TROLLY_ELEMENT", ".right_side_header", By::cssSelector, ETypeOfElement.LINK);

    /*--- Контейнер для DropDownMenue - авторизации/выхода, также для работы с пользователем! ---*/
    private final String selectroForAuthContainerElement = ".main_links ";

    private final String selectorForLoginRegistrationElement = selectroForAuthContainerElement + "#login_form_show_js";

    private final String selectorForPersonalElement = selectroForAuthContainerElement + ".auth-user-popup";

    private final String selectorForDesiredListElement = selectroForAuthContainerElement + "span[data-link-type='Wishlist'] a";

    /*--------*/
    public final Element<IContainerWorker> AUTHORIZATION_CONTAINER_ELEMENT =
            new Element<>("AUTHORIZATION_CONTAINER_ELEMENT", selectroForAuthContainerElement, By::cssSelector, ETypeOfElement.CONTAINER);

    public final Element<LMDForLogin> LOGIN_REGISTRATION_ELEMENT =
            new Element<>("LOGIN_REGISTRATION_ELEMENT", selectorForLoginRegistrationElement, By::cssSelector, ETypeOfElement.BUTTON, LMDForLogin::new);

    public final Element<LMDForPersonal> PERSONAL_ELEMENT =
            new Element<LMDForPersonal>("PERSONAL_ELEMENT", selectorForPersonalElement, By::cssSelector, ETypeOfElement.BUTTON, LMDForPersonal::new);

    public final Element<IContainerWorker> NAME_OF_USER = new Element<IContainerWorker>("NAME_OF_USER", "span span", By::cssSelector, ETypeOfElement.TEXT);

    public final Element<IContainerWorker> DESIRED_LIST_ELEMENT =
            new Element<>("DESIRED_LIST_ELEMENT", selectorForDesiredListElement, By::cssSelector, ETypeOfElement.LINK);
    /*-----------------------------------------*/

    public DescriptorPartHeaderSecondFloor(String partName) {
        super(new Element<>(partName, ".header_inner__second-floor", By::cssSelector, ETypeOfElement.PART));

        addElementToPart(SEARCH_ELEMENT);

        addElementToPart(TROLLY_ELEMENT);

        addElementToPart(AUTHORIZATION_CONTAINER_ELEMENT);

        addElementToPart(DESIRED_LIST_ELEMENT);
    }
}
