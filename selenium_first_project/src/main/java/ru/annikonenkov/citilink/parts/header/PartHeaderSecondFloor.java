package ru.annikonenkov.citilink.parts.header;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;

import ru.annikonenkov.citilink.pages.MainPageOfCitilink;
import ru.annikonenkov.citilink.pages.MainPageOfCitilinkAfterSearch;
import ru.annikonenkov.citilink.parts.header.lmd.LMDForLogin;
import ru.annikonenkov.citilink.parts.header.lmd.PartForLMDAuthorization;
import ru.annikonenkov.citilink.type.page_and_parts.PartCitilinkRegistry;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.exceptions.UnavailableParentWebElement;
import ru.annikonenkov.common.exceptions.UnavailableReturndeContainer;
import ru.annikonenkov.common.exceptions.UnavailableTargetWebElement;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;
import ru.annikonenkov.common.worker.IPartWorker;

public class PartHeaderSecondFloor<T extends IPartWorker<?>> extends APartWorker<T, DescriptorPartHeaderSecondFloor> {

    private final static PartCitilinkRegistry _partregistry = PartCitilinkRegistry.UNDER_PART_HEADER;

    private final static Logger _logger = LogManager.getLogger(PartHeaderSecondFloor.class);

    public PartHeaderSecondFloor(T owner) {
        super(owner, _partregistry, new DescriptorPartHeaderSecondFloor(_partregistry.getName()));
    }

    public MainPageOfCitilinkAfterSearch searchCommodity(String someText)
            throws UnavailableParentWebElement, UnavailableTargetWebElement, UnavailableReturndeContainer {
        _logger.info("Начинаем выполнять поиск товара = '{}'", someText);
        getSearcherAndAnalуzerElements().getWebElementWithinParent(partDescriptor.SEARCH_ELEMENT, getElementForPart(), 1);
        partDescriptor.SEARCH_ELEMENT.getWebElement().map((elem) -> {
            elem.clear();
            elem.sendKeys(someText);
            elem.click();
            // elem.submit();
            elem.sendKeys(Keys.ENTER);
            return Optional.of(elem);
        }).orElseThrow(() -> new UnavailableTargetWebElement("Не найден " + getFullDescription() + "----->" + partDescriptor.SEARCH_ELEMENT.toString()));
        Optional<MainPageOfCitilinkAfterSearch> pageCitilinkAfterSearch = partDescriptor.SEARCH_ELEMENT.getProducedContainer(getSearcherAndAnalуzerElements());
        MainPageOfCitilinkAfterSearch page = pageCitilinkAfterSearch
                .orElseThrow(() -> new UnavailableReturndeContainer("При попытке получить Старинцу с результатом поиска продукта/товара!"));
        return page;
    }

    public MainPageOfCitilink login(String login, String password)
            throws UnavailableParentWebElement, UnavailableTargetWebElement, UnavailableReturndeContainer {
        _logger.info("Начинаем выполнять авторизацию = '{}'! Получение ЛМД авторизации!", login);

        Element<IContainerWorker> elementOfLoginContainer = partDescriptor.AUTHORIZATION_CONTAINER_ELEMENT;

        Element<LMDForLogin> elementOfLogin = partDescriptor.LOGIN_REGISTRATION_ELEMENT;
        LMDForLogin lmdForlogin = elementOfLogin.getProducedContainer(getSearcherAndAnalуzerElements())
                .orElseThrow(() -> new UnavailableReturndeContainer("При попытке получить ЛМД для авторизации, получили null!"));

        _logger.info("Ищем элемент, при нажатии на который откроется ЛМД авторизации = '{}'", login);
        getSearcherAndAnalуzerElements().getWebElementWithinParent(elementOfLogin, elementOfLoginContainer, 1);

        elementOfLogin.getWebElement().map((opt) -> {
            opt.click();
            return Optional.of(opt);
        });

        lmdForlogin.setElementAsStartPointForThisLMD(partDescriptor.getElementForCurrentPart());
        lmdForlogin.isPresentAllElementsInFullDepth();
        PartForLMDAuthorization<LMDForLogin> lmdLogin = lmdForlogin.AUTHORIZATION_PART;

        return lmdLogin.login(login, password);
    }

    public boolean isAuthorizationSuccessful(String name) throws UnavailableParentWebElement, UnavailableTargetWebElement {
        getSearcherAndAnalуzerElements().getWebElementWithinParent(partDescriptor.PERSONAL_ELEMENT, getElementForPart(), 1);
        getSearcherAndAnalуzerElements().getWebElementWithinParent(partDescriptor.NAME_OF_USER, partDescriptor.PERSONAL_ELEMENT, 1);
        String nameOfUser = partDescriptor.NAME_OF_USER.getWebElementOrThrow().getText();
        return nameOfUser.contains(name);
    }

}
