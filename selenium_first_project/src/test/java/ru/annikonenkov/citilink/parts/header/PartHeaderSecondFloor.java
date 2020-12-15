package ru.annikonenkov.citilink.parts.header;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;

import ru.annikonenkov.citilink.pages.MainPageOfCitilink;
import ru.annikonenkov.citilink.pages.MainPageOfCitilinkAfterSearch;
import ru.annikonenkov.citilink.pages.TrolleyPage;
import ru.annikonenkov.citilink.parts.header.lmd.LMDForLogin;
import ru.annikonenkov.citilink.parts.header.lmd.PartForLMDAuthorization;
import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.exceptions.UnavailableParentElement;
import ru.annikonenkov.common.exceptions.UnavailableReturndeContainer;
import ru.annikonenkov.common.exceptions.UnavailableTargetElement;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.source_provider.SourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;

public class PartHeaderSecondFloor extends APartWorker {

    private DescriptorPartHeaderSecondFloor _partDescriptor;

    private final static Logger _logger = LogManager.getLogger(PartHeaderSecondFloor.class);

    public PartHeaderSecondFloor(IContainerWorker owner, IPartRegistry partRegistry,
            DescriptorPartHeaderSecondFloor partDescriptor) {
        super(owner, partRegistry, partDescriptor);
        _partDescriptor = partDescriptor;
    }

    public static class PartFabric implements IContainerFabric<PartHeaderSecondFloor> {

        @Override
        public PartHeaderSecondFloor getContainer(ISourceProviderForBuildContainer sourceProvider)
                throws CriticalException {
            PartCitilinkRegistry partRegistry = PartCitilinkRegistry.UNDER_PART_HEADER;
            DescriptorPartHeaderSecondFloor descriptor = new DescriptorPartHeaderSecondFloor(partRegistry.getName());
            return new PartHeaderSecondFloor(sourceProvider.getParentPart().get(), partRegistry, descriptor);
        }
    }

    public MainPageOfCitilinkAfterSearch searchCommodity(String someText)
            throws UnavailableParentElement, UnavailableTargetElement, UnavailableReturndeContainer, CriticalException {
        _logger.info("Начинаем выполнять поиск товара = '{}'", someText);
        getSearcherAndAnalуzerElements().getWebElementWithinParent(_partDescriptor.SEARCH_ELEMENT, getElementForPart(),
                1);
        _partDescriptor.SEARCH_ELEMENT.getWebElement().map((elem) -> {
            elem.clear();
            elem.sendKeys(someText);
            elem.click();
            // elem.submit();
            elem.sendKeys(Keys.ENTER);
            /*
             * TODO: После клика Enter страница все еще грузится, а поиск по элементам уже запущен и падаем на
             * StaleElementReferenceException. Да в тесте есть решение, через попытки. Но по хорошему нужно добавить
             * здесь некое решение.
             */
            return Optional.of(elem);
        }).orElseThrow(() -> new UnavailableTargetElement(
                "Не найден " + getFullDescription() + "----->" + _partDescriptor.SEARCH_ELEMENT.toString()));
        Optional<IContainerFabric<MainPageOfCitilinkAfterSearch>> pageCitilinkAfterSearch =
                _partDescriptor.SEARCH_ELEMENT.getProducedContainer();
        IContainerFabric<MainPageOfCitilinkAfterSearch> page =
                pageCitilinkAfterSearch.orElseThrow(() -> new UnavailableReturndeContainer(
                        "При попытке получить Старинцу с результатом поиска продукта/товара!"));
        return page.getContainer(new SourceProviderForBuildContainer(getSearcherAndAnalуzerElements()));
    }

    public MainPageOfCitilink login(String login, String password)
            throws UnavailableParentElement, UnavailableTargetElement, UnavailableReturndeContainer, CriticalException {

        _logger.info("Начинаем выполнять авторизацию = '{}'! Получение ЛМД авторизации!", login);

        Element<IContainerWorker> elementOfLoginContainer = _partDescriptor.AUTHORIZATION_CONTAINER_ELEMENT;

        Element<LMDForLogin> elementOfLogin = _partDescriptor.LOGIN_REGISTRATION_ELEMENT;
        Optional<IContainerFabric<LMDForLogin>> lmdForlogin = elementOfLogin.getProducedContainer();

        IContainerFabric<LMDForLogin> fabric = lmdForlogin.orElseThrow(
                () -> new UnavailableReturndeContainer("При попытке получить ЛМД для авторизации, получили null!"));

        LMDForLogin lmd =
                fabric.getContainer(new SourceProviderForBuildContainer(getSearcherAndAnalуzerElements(), this));

        _logger.info("Ищем элемент, при нажатии на который откроется ЛМД авторизации = '{}'", login);
        getSearcherAndAnalуzerElements().getWebElementWithinParent(elementOfLogin, elementOfLoginContainer, 1);

        elementOfLogin.getWebElement().map((opt) -> {
            opt.click();
            return Optional.of(opt);
        });
        lmd.isPresentAllElementsInFullDepth();
        PartForLMDAuthorization lmdLogin =
                (PartForLMDAuthorization) lmd.getPart(PartCitilinkRegistry.PART_FOR_LMD_AUTHRIZATION);

        return lmdLogin.login(login, password);
    }

    public boolean isAuthorizationSuccessful(String name) throws UnavailableParentElement, UnavailableTargetElement {

        getSearcherAndAnalуzerElements().getWebElementWithinParent(_partDescriptor.PERSONAL_ELEMENT,
                getElementForPart(), 1);
        getSearcherAndAnalуzerElements().getWebElementWithinParent(_partDescriptor.NAME_OF_USER,
                _partDescriptor.PERSONAL_ELEMENT, 1);
        String nameOfUser = _partDescriptor.NAME_OF_USER.getWebElementOrThrow().getText();
        return nameOfUser.contains(name);
    }

    /**
     * TODO: Снова вопрос, как определять проинициализирован ли этот элемент. Как понять какие элементы
     * проинициализрованы, а какие нет. В данном моетоде я уже получаю элемент, при этом я не знаю а при проверке
     * страницы была ли попытка проинициализировать.
     * 
     * @return
     * @throws CriticalException
     * @throws UnavailableParentElement
     */
    public TrolleyPage openBascket() throws CriticalException, UnavailableParentElement {
        Element<TrolleyPage> trolley = _partDescriptor.TROLLEY_ELEMENT;
        var optionalTrolley = trolley.getWebElement();
        if (optionalTrolley.isPresent()) {
            optionalTrolley.get().click();
        } else {
            var trolleyWebElement =
                    getSearcherAndAnalуzerElements().getWebElementWithinParent(trolley, getElementForPart(), 1);
            // TODO: Может быть NULL
            trolleyWebElement.click();
        }

        return trolley.getProducedContainer().get()
                .getContainer(new SourceProviderForBuildContainer(getSearcherAndAnalуzerElements()));
    }

}
