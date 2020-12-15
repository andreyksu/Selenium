package ru.annikonenkov.citilink.parts.header;

import org.openqa.selenium.By;

import ru.annikonenkov.citilink.pages.MainPageOfCitilinkAfterSearch;
import ru.annikonenkov.citilink.pages.TrolleyPage;
import ru.annikonenkov.citilink.parts.header.lmd.LMDForLogin;
import ru.annikonenkov.citilink.parts.header.lmd.LMDForPersonal;
import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.worker.IContainerWorker;

/*
 * TODO: По сути Element параметризован из за возвращаемого контейнера. Можно все вынести в Enum - имя, локатор, функцию, типЭлемента, а здесь оставить лишь
 * возвращаемый контейнер и параметризацию для него. Т.к. сейчас довольно сильно нагромаждено при создании объекта.
 */
public class DescriptorPartHeaderSecondFloor extends APartDescriptor {

    private final String selectroSearchElement = ".search_header input#global_search";

    public final Element<MainPageOfCitilinkAfterSearch> SEARCH_ELEMENT =
            new Element<>("SEARCH_ELEMENT", selectroSearchElement, By::cssSelector, ETypeOfElement.FIELD, new MainPageOfCitilinkAfterSearch.PageFabric());

    public final Element<TrolleyPage> TROLLEY_ELEMENT =
            new Element<>("TROLLEY_ELEMENT", ".right_side_header", By::cssSelector, ETypeOfElement.LINK, new TrolleyPage.PageFabric());

    /**
     * Контейнер для DropDownMenu - авторизации/выхода, также для работы с пользователем!<br>
     * Т.е. идея состоит в том, что все что внутри Part ищем относительно этого Part. Т.е. в противовес примеру см. класс DescriptorPartContentAfterSearch, где
     * поиск идет относительно не только Part но и относительно вложенных элементов.
     */
    private final String selectroForAuthContainerElement = ".main_links";

    private final String selectorForLoginRegistrationElement = selectroForAuthContainerElement + " " + "#login_form_show_js";

    private final String selectorForPersonalElement = selectroForAuthContainerElement + " " + ".auth-user-popup";

    private final String selectorForDesiredListElement = selectroForAuthContainerElement + " " + "span[data-link-type='Wishlist'] a";

    public final Element<IContainerWorker> AUTHORIZATION_CONTAINER_ELEMENT =
            new Element<>("AUTHORIZATION_CONTAINER_ELEMENT", selectroForAuthContainerElement, By::cssSelector, ETypeOfElement.CONTAINER);

    public final Element<LMDForLogin> LOGIN_REGISTRATION_ELEMENT = new Element<>("LOGIN_REGISTRATION_ELEMENT", selectorForLoginRegistrationElement,
            By::cssSelector, ETypeOfElement.BUTTON, new LMDForLogin.LMDFabric());

    public final Element<LMDForPersonal> PERSONAL_ELEMENT =
            new Element<LMDForPersonal>("PERSONAL_ELEMENT", selectorForPersonalElement, By::cssSelector, ETypeOfElement.BUTTON, new LMDForPersonal.LMDFabric());

    public final Element<IContainerWorker> DESIRED_LIST_ELEMENT =
            new Element<>("DESIRED_LIST_ELEMENT", selectorForDesiredListElement, By::cssSelector, ETypeOfElement.LINK);

    /*------*/
    public final Element<IContainerWorker> NAME_OF_USER = new Element<IContainerWorker>("NAME_OF_USER", "span span", By::cssSelector, ETypeOfElement.TEXT);

    /*-----------------------------------------*/

    public DescriptorPartHeaderSecondFloor(String partName) {
        super(new Element<>(partName, ".header_inner__second-floor", By::cssSelector, ETypeOfElement.PART));

        addElementToPart(SEARCH_ELEMENT);

        addElementToPart(TROLLEY_ELEMENT);

        addElementToPart(AUTHORIZATION_CONTAINER_ELEMENT);

        addElementToPart(DESIRED_LIST_ELEMENT);
    }
}
