package ru.annikonenkov.tests;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import ru.annikonenkov.citilink.pages.MainPageOfCitilink;
import ru.annikonenkov.citilink.pages.MainPageOfCitilinkAfterSearch;
import ru.annikonenkov.citilink.pages.TrolleyPage;
import ru.annikonenkov.citilink.parts.content.PartContent;
import ru.annikonenkov.citilink.parts.content.PartContentAfterSearch;
import ru.annikonenkov.citilink.parts.content.PartContetntOnTrolleyPageItems;
import ru.annikonenkov.citilink.parts.header.PartHeaderSecondFloor;
import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.exceptions.BussinesException;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.exceptions.UnavailableEntity;
import ru.annikonenkov.common.exceptions.UnavailableParentElement;
import ru.annikonenkov.common.exceptions.UnavailableTargetElement;
import ru.annikonenkov.common.source_provider.SourceProviderForBuildContainer;
import ru.annikonenkov.tests.BaseTestClass;

@Listeners({ru.annikonenkov.tests.ScreenShotOnFailure.class})
public class UseCaseForCreateOrder extends BaseTestClass {

    private final static Logger _logger = LogManager.getLogger(UseCaseForCreateOrder.class);

    private MainPageOfCitilink mainPageOfCitilinkAfterLogin;

    private MainPageOfCitilinkAfterSearch pageWithResultOfSearch;

    private PartContetntOnTrolleyPageItems partContetntOnTrolleyPageItems;

    @DataProvider(name = "forAuth")
    public Object[][] dataForAuth() {
        return new Object[][] {{"an@gmail.com", "pass", "Ф А"}};
    }

    @DataProvider(name = "forSearch")
    public Object[][] dataForSearch() {
        /*
         * TODO: В моем случае, лучше делать через цикл внутри метода. А данные прочитать из Проперти. При таком
         * подходе, получается нужно переделать метод поиска (связано с, бизнес процессом - открытием и переход по
         * страницам). return new Object[][] {{"Клавиатура A4 KR-83", "A4 KR-83"}, {"Клавиатура A4 KR-83",
         * "A4 KRS-8372"}};
         */
        return new Object[][] {{"Клавиатура A4 KR-83", "KRS-8372"}};
    }

    /*
     * TODO: Временное решение, в последующем либо випилить, либо оформить нормально. Добавил, так как делал изначально
     * для старого стиля сайта, потом появился новый ситль. Соответственно добавил переход на старый стиль сайта.
     */
    public void prepare() {
        List<WebElement> webElementsListForOldVersion = _searcher.getWebDriver()
                .findElements(By.cssSelector("div.MainWrapper div.MainLayout form button.SiteVersionSwitcher__button"));
        if (webElementsListForOldVersion.size() > 0) {
            WebElement we = webElementsListForOldVersion.get(0);
            we.click();
        } else {
            _logger.error(
                    "Количество элементов для перехода на старую версию сайта равно = {}. Переход выполнен не будет.",
                    webElementsListForOldVersion.size(), webElementsListForOldVersion);
        }
    }

    /**
     * TODO: Добавил, так как после перехода на старый стиль страницы, просходит загрузка страницы ДВАЖДЫ. Видимо поиск
     * производится для первой загрузки, а после перезагрузки страницы, уже элементов не сущетвует И в результате падаю
     * на StaleElementReferenceException. Вынести в отдельный метод или обработчик. По сути не сильно отличается от идеи
     * WebDriver - так как происходит polling каждый 500ms.
     * <p>
     * TODO: mainPageOfCitilinkAsStartPage.getWebDriver().wait(1000); не работает. Видимо стопует сам драйвер, а не
     * текущий поток.
     * <p>
     * TODO: Можно не так. Вероятно лучеш проверить все Part на все глубину. А потом получив конкретный Part для него
     * проверсти проверку элементов.
     * 
     * @param login
     * @param pass
     * @param nameForVerify
     * @throws UnavailableEntity
     * @throws CriticalException
     */
    @Test(timeOut = 120000, dataProvider = "forAuth")
    public void login(String login, String pass, String nameForVerify) throws UnavailableEntity, CriticalException {
        MainPageOfCitilink mainPageOfCitilinkAsStartPage =
                new MainPageOfCitilink.PageFabric().getContainer(new SourceProviderForBuildContainer(_searcher));

        mainPageOfCitilinkAsStartPage.openPage();
        prepare();

        boolean isPresentAllElementsOnPage = false;
        for (int i = 0; i < 10; i++) {
            try {
                isPresentAllElementsOnPage = mainPageOfCitilinkAsStartPage.isPresentAllElementsInFullDepth();
            } catch (StaleElementReferenceException e) {
                _logger.catching(e);
                try {
                    Thread.currentThread().sleep(500);
                } catch (InterruptedException e1) {
                    _logger.catching(e1);
                }
                continue;
            }
            break;
        }
        _logger.debug(
                "Результат проверки всех обязательных элементов на стартовой странице после открытия сайта: resultOfCheck = {}",
                isPresentAllElementsOnPage);

        PartHeaderSecondFloor part =
                (PartHeaderSecondFloor) mainPageOfCitilinkAsStartPage.getPart(PartCitilinkRegistry.UNDER_PART_HEADER);

        mainPageOfCitilinkAfterLogin = part.login(login, pass);

        boolean resultOfCheckElemensOnNewPage = mainPageOfCitilinkAfterLogin.isPresentAllElementsInFullDepth();
        _logger.debug("Результат проверки элементов после авторизации resultOfCheckElemensOnNewPage = {}",
                resultOfCheckElemensOnNewPage);

        PartHeaderSecondFloor underHeaderPArt =
                (PartHeaderSecondFloor) mainPageOfCitilinkAfterLogin.getPart(PartCitilinkRegistry.UNDER_PART_HEADER);

        boolean isSaccessfulAuth = underHeaderPArt.isAuthorizationSuccessful(nameForVerify);

        Assert.assertTrue(isSaccessfulAuth);
    }

    /**
     * Добавил, так как после поиска падаю на StaleElementReferenceException. Причина тому - обновление после загрзуки
     * страницы, или даже двойная загрузка страницы. Это характерно для Citilink.
     */
    @Test(dependsOnMethods = "login", dataProvider = "forSearch")
    public void search(String mask, String stringForVerify) throws UnavailableEntity, CriticalException {
        try {
            PartHeaderSecondFloor part = (PartHeaderSecondFloor) mainPageOfCitilinkAfterLogin
                    .getPart(PartCitilinkRegistry.UNDER_PART_HEADER);
            pageWithResultOfSearch = part.searchCommodity(mask);

            boolean isPresentAllElementsOnPage = false;
            for (int i = 0; i < 10; i++) {
                try {
                    isPresentAllElementsOnPage = pageWithResultOfSearch.isPresentAllElementsInFullDepth();
                } catch (StaleElementReferenceException e) {
                    _logger.catching(e);
                    try {
                        Thread.currentThread().sleep(500);
                    } catch (InterruptedException e1) {
                        _logger.catching(e1);
                    }
                    continue;
                }
                break;
            }
            _logger.info(
                    "Результат проверки элементов на странице, после поиска товара: isPresentAllElementsOnPage = {}",
                    isPresentAllElementsOnPage);

            PartContent contentPart = (PartContent) pageWithResultOfSearch.getPart(PartCitilinkRegistry.PART_CONTENT);

            PartContentAfterSearch contentPartAfterSearch = (PartContentAfterSearch) pageWithResultOfSearch
                    .getPart(PartCitilinkRegistry.PART_CONTENT_AFTER_SEARCH);

            //TODO: Вероятно это нужно от сюда вынести - и сделать мол вот тебе отец, инициализируйся.
            pageWithResultOfSearch.getSearcherAndAnalуzerElements().getWebElementWithinParent(
                    contentPartAfterSearch.getElementForPart(), contentPart.getElementForPart(), 1);
            contentPartAfterSearch.isPresentAllElementsInFullDepth();

            boolean isPresentCommodityInResult =
                    contentPartAfterSearch.getTargetProductIfPresent(stringForVerify).isPresent();
            _logger.info("isPresentCommodityInResult = {}", isPresentCommodityInResult);

            Assert.assertTrue(isPresentCommodityInResult);

        } catch (UnavailableTargetElement e) {
            throw new UnavailableEntity("Не найден элемент поиска на странице", e);
        } catch (UnavailableParentElement e) {
            throw new UnavailableEntity(
                    "Не найден родительский элемент на странице (элемент в рамках которого производился поиск)", e);
        }

    }

    /**
     * PartContentAfterSearch contentPartAfterSearch = (PartContentAfterSearch)
     * pageWithResultOfSearch.getPart(PartCitilinkRegistry.PART_CONTENT_AFTER_SEARCH);
     * 
     * @param mask
     * @param forVerify
     * @throws UnavailableParentElement
     * @throws BussinesException
     */
    @Test(dependsOnMethods = "search", dataProvider = "forSearch")
    public void addToTrolley(String mask, String forVerify) throws UnavailableParentElement, BussinesException {
        List<PartContentAfterSearch> listContentPartAfterSearch =
                pageWithResultOfSearch.getPartByClass(PartContentAfterSearch.class);

        // TODO: Вероятно это нужно вынести в исходные классы, так как повторяется.
        if (listContentPartAfterSearch.size() < 1) {
            String message = "По данному классу не найден ни один Part";
            throw new BussinesException(message);
        }
        // TODO: По факту может быть несколько одинаковых Part (вероятно для Page и по бизнесу это нормально). Подумать
        // как с этим работать.
        PartContentAfterSearch contentPartAfterSearch = listContentPartAfterSearch.get(0);

        Optional<WebElement> elementForProduct = contentPartAfterSearch.addToTrolley(forVerify);
        contentPartAfterSearch.getDeleteButtonFromBasket(elementForProduct.get());
    }

    @Test(dependsOnMethods = "addToTrolley", dataProvider = "forSearch")
    public void isPresentProductInTrolley(String mask, String forVerify)
            throws BussinesException, CriticalException, UnavailableParentElement {

        List<PartHeaderSecondFloor> listOfPartHeaderSecondFloor =
                pageWithResultOfSearch.getPartByClass(PartHeaderSecondFloor.class);
        if (listOfPartHeaderSecondFloor.size() < 1) {
            String message = "По данному классу не найден ни один Part. Дальнейшная работа не возможна.";
            throw new BussinesException(message);
        }
        PartHeaderSecondFloor partHeaderSecondFloor = listOfPartHeaderSecondFloor.get(0);
        TrolleyPage trolleyPage = partHeaderSecondFloor.openBascket();

        boolean isPresentAllElementsOnTrolleyPage = trolleyPage.isPresentAllElementsInFullDepth();
        _logger.debug("isPresentAllElementsOnTrolleyPage = '{}'", isPresentAllElementsOnTrolleyPage);

        List<PartContetntOnTrolleyPageItems> listPartContetntOnTrolleyPageItems =
                trolleyPage.getPartByClass(PartContetntOnTrolleyPageItems.class);
        if (listOfPartHeaderSecondFloor.size() < 1) {
            String message = "По данному классу не найден ни один Part";
            throw new BussinesException(message);
        }
        partContetntOnTrolleyPageItems = listPartContetntOnTrolleyPageItems.get(0);

        boolean result = partContetntOnTrolleyPageItems.isPresentItemOnBasketPage(forVerify);
        _logger.debug("Результат проверки добавленного элемента в корзину = '{}'", result);
    }

    @Test(dependsOnMethods = "isPresentProductInTrolley", dataProvider = "forSearch")
    public void excludeProductFromTrolley(String mask, String forVerify) throws UnavailableParentElement {
        boolean result = partContetntOnTrolleyPageItems.removeItemFromBasketPage(forVerify);
        _logger.debug("Присутствует ли удаленный элемент в корзине = '{}'", result);

    }

}
