package ru.annikonenkov.citilink.parts.header.lmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import ru.annikonenkov.citilink.pages.MainPageOfCitilink;
import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.exceptions.UnavailableReturndeContainer;
import ru.annikonenkov.common.exceptions.UnavailableTargetElement;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.source_provider.SourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;

public class PartForLMDAuthorization extends APartWorker {

    private DescriptorPartForLMDAuthorization _partDescriptor;

    private final static Logger _logger = LogManager.getLogger(PartForLMDAuthorization.class);

    public <O extends IContainerWorker, D extends DescriptorPartForLMDAuthorization> PartForLMDAuthorization(O owner, IPartRegistry partRegistry,
            D partDescriptor) {
        super(owner, partRegistry, partDescriptor);
        _partDescriptor = partDescriptor;
    }

    public static class PartFabric implements IContainerFabric<PartForLMDAuthorization> {

        @Override
        public PartForLMDAuthorization getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            PartCitilinkRegistry partRegistry = PartCitilinkRegistry.PART_FOR_LMD_AUTHRIZATION;

            DescriptorPartForLMDAuthorization descriptor = new DescriptorPartForLMDAuthorization(partRegistry.getName());

            return new PartForLMDAuthorization(sourceProvider.getRootParent().get(), partRegistry, descriptor);
        }

    }

    public MainPageOfCitilink login(String login, String password) throws UnavailableTargetElement, UnavailableReturndeContainer, CriticalException {
        _logger.info("Непосредственный ввод login/password = '{}' !", login);
        WebElement fieldForLogin = _partDescriptor.LOGIN_FIELD_ELEMENT.getWebElementOrThrow();
        WebElement fieldForPassword = _partDescriptor.PASSWORD_FIELD_ELEMENT.getWebElementOrThrow();
        WebElement submitButton = _partDescriptor.SUBMIT_BUTTON_ELEMENT.getWebElementOrThrow();
        fieldForLogin.clear();
        fieldForLogin.sendKeys(login);
        fieldForPassword.clear();
        fieldForPassword.sendKeys(password);

        // TODO: УБРАТЬ---Добавлено временно, для возможности ввести CAPTURE при авторизации на странице---УБРАТЬ
        try {
            Thread.currentThread().sleep(15000);
        } catch (InterruptedException e) {
            _logger.error(e);
        }

        submitButton.click();
        IContainerFabric<MainPageOfCitilink> page = _partDescriptor.SUBMIT_BUTTON_ELEMENT.getProducedContainer()
                .orElseThrow(() -> new UnavailableReturndeContainer("Кнопка Авторизации не возвращает Page."));
        return page.getContainer(new SourceProviderForBuildContainer(getSearcherAndAnalуzerElements()));

    }
}
