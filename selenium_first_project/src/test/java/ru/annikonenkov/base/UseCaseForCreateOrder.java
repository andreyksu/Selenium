package ru.annikonenkov.base;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ru.annikonenkov.citilink.pages.MainPageOfCitilink;
import ru.annikonenkov.common.exceptions.UnavailableEntity;
import ru.annikonenkov.common.exceptions.UnavailableParentWebElement;
import ru.annikonenkov.common.exceptions.UnavailableTargetWebElement;

public class UseCaseForCreateOrder extends BaseTestClass {

    private final static Logger _logger = LogManager.getLogger(UseCaseForCreateOrder.class);

    private MainPageOfCitilink mainPageOfCitilinkAfterLogin;

    @DataProvider(name = "forAuth")
    public Object[][] dataForAuth() {
        return new Object[][] {{"login", "pass", "ФИО"}};

    }

    @DataProvider(name = "forSearch")
    public Object[][] dataForSearch() {
        return new Object[][] {{"Клавиатура A4 KR-83", "A4 KR-83"}};

    }

    // TODO: !!!!!Временное решение, в последующему либо випилить либо оформить нормально!!!!!
    public void prepare() {
        List<WebElement> webElementsListForOldVersion =
                _searcher.getWebDriver().findElements(By.cssSelector("div.MainWrapper div.MainLayout form button.SiteVersionSwitcher__button"));
        if (webElementsListForOldVersion.size() > 0) {
            WebElement we = webElementsListForOldVersion.get(0);
            we.click();
        } else {
            _logger.error("Количество элементов для перехода на старую версию сайта равно = {}. ", webElementsListForOldVersion.size(),
                    webElementsListForOldVersion);
        }

    }

    @Test(timeOut = 120000, dataProvider = "forAuth")
    public void login(String login, String pass, String nameForVerify) throws UnavailableEntity {
        MainPageOfCitilink mainPageOfCitilinkAsStartPage = new MainPageOfCitilink(_searcher);
        mainPageOfCitilinkAsStartPage.openPage();
        prepare();

        boolean resultOfCheck = mainPageOfCitilinkAsStartPage.isPresentAllElementsInFullDepth();
        _logger.debug("Результат проверки всех элементов на стартовой странице после запуска = {}", resultOfCheck);

        var part = mainPageOfCitilinkAsStartPage.HEADER_PART.UNDER_HEADER_PART;
        mainPageOfCitilinkAfterLogin = part.login(login, pass);

        boolean resultOfCheckElemensOnNewPage = mainPageOfCitilinkAfterLogin.isPresentAllElementsInFullDepth();
        _logger.debug("Результат проверки элементов после авторизации = {}", resultOfCheckElemensOnNewPage);

        boolean isSaccessfulAuth = mainPageOfCitilinkAfterLogin.HEADER_PART.UNDER_HEADER_PART.isAuthorizationSuccessful(nameForVerify);

        Assert.assertTrue(isSaccessfulAuth);
    }

    @Test(dependsOnMethods = "login", dataProvider = "forSearch")
    public void search(String mask, String forVerify) throws UnavailableEntity {
        try {
            var pageWithResultOfSearch = mainPageOfCitilinkAfterLogin.HEADER_PART.UNDER_HEADER_PART.searchCommodity(mask);
            boolean isPresentAllElementsOnPage = pageWithResultOfSearch.isPresentAllElementsInFullDepth();
            _logger.info("isPresentAllElementsOnPageAfterSearch = {}", isPresentAllElementsOnPage);

            pageWithResultOfSearch.getSearcherAndAnalуzerElements().getWebElementWithinParent(
                    pageWithResultOfSearch.CONTENT_PART.PART_CONTENT_AFTER_SEARCH.getElementForPart(), pageWithResultOfSearch.CONTENT_PART.getElementForPart(),
                    1);

            pageWithResultOfSearch.CONTENT_PART.PART_CONTENT_AFTER_SEARCH.isPresentAllElementsInFullDepth();

            boolean isPresentCommodityInResult = pageWithResultOfSearch.CONTENT_PART.PART_CONTENT_AFTER_SEARCH.isPresentTargetElement(mask);
            _logger.info("isPresentCommodityInResult = {}", isPresentCommodityInResult);
            Assert.assertTrue(isPresentCommodityInResult);
        } catch (UnavailableTargetWebElement e) {
            throw new UnavailableEntity("Не найден элемент поиска на странице", e);
        } catch (UnavailableParentWebElement e) {
            throw new UnavailableEntity("Не найден родительский элемент на странице (элемент в рамках которого производился поиск)", e);
        }

    }
}
