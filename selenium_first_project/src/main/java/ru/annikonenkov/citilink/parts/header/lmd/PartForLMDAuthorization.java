package ru.annikonenkov.citilink.parts.header.lmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import ru.annikonenkov.citilink.pages.MainPageOfCitilink;
import ru.annikonenkov.citilink.type.page_and_parts.PartCitilinkRegistry;
import ru.annikonenkov.common.exceptions.UnavailableReturndeContainer;
import ru.annikonenkov.common.exceptions.UnavailableTargetWebElement;
import ru.annikonenkov.common.worker.ALMDWorker;
import ru.annikonenkov.common.worker.APartWorker;

public class PartForLMDAuthorization<T extends ALMDWorker> extends APartWorker<T, DescriptorPartForLMDAuthorization> {

    private final static PartCitilinkRegistry _partregistry = PartCitilinkRegistry.PART_FOR_LMD_AUTHRIZATION;

    private final static Logger _logger = LogManager.getLogger(PartForLMDAuthorization.class);

    public PartForLMDAuthorization(T owner) {
        super(owner, _partregistry, new DescriptorPartForLMDAuthorization(_partregistry.getName()));
    }

    public MainPageOfCitilink login(String login, String password) throws UnavailableTargetWebElement, UnavailableReturndeContainer {
        _logger.info("Непосредственный ввод login/password = '{}' !", login);
        WebElement fieldForLogin = partDescriptor.LOGIN_FIELD_ELEMENT.getWebElementOrThrow();
        WebElement fieldForPassword = partDescriptor.PASSWORD_FIELD_ELEMENT.getWebElementOrThrow();
        WebElement submitButton = partDescriptor.SUBMIT_BUTTON_ELEMENT.getWebElementOrThrow();
        fieldForLogin.clear();
        fieldForLogin.sendKeys(login);
        fieldForPassword.clear();
        fieldForPassword.sendKeys(password);

        // TODO: УБРАТЬ---Добавлено временно, для возможности ввести capture при авторизации на странице---УБРАТЬ
        try {

            Thread.currentThread().sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        submitButton.click();
        return partDescriptor.SUBMIT_BUTTON_ELEMENT.getProducedContainer(getSearcherAndAnalуzerElements())
                .orElseThrow(() -> new UnavailableReturndeContainer("Кнопка Авторизации не возвращает Page."));
    }
}
