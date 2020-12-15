package ru.annikonenkov.tests;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import ru.annikonenkov.citilink.pages.MainPageOfCitilink;
import ru.annikonenkov.citilink.parts.header.PartHeaderSecondFloor;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.exceptions.UnavailableParentElement;
import ru.annikonenkov.common.exceptions.UnavailableReturndeContainer;
import ru.annikonenkov.common.exceptions.UnavailableTargetElement;
import ru.annikonenkov.common.source_provider.SourceProviderForBuildContainer;

//@Listeners({ScreenShotOnFailure.class})
//public class VerifyMainPageOfCitilink extends BaseTestClass {
public class VerifyMainPageOfCitilink{
/*
    private final static Logger _logger = LogManager.getLogger(VerifyMainPageOfCitilink.class);

    @DataProvider(name = "forSearch")
    public Object[][] createData1() {
        /*
         * return new Object[][] { {"ПЛАНШЕТ", "планшет"}, {"МЫШКА", "мышка"}, {"МАТЕРИНСКАЯ ПЛАТА", "материнская плата"}, {"DDR-3", "DDR-3"}, {"НОУТБУК",
         * "Ноутбук"}};
         
        return new Object[][] {{"ПЛАНШЕТ", "планшет"}};

    }

    @Test(groups = {"first_group"}, priority = 0, description = "TestNG: Проверка всех Part!")
    @Description("Allure>Description: Тест проверки всех Parent на странице")
    @Step("Allure>Step: Шаг для проверки Part на странице")
    @Severity(SeverityLevel.BLOCKER)
    @Epic(value = "Epic for Test > verifyAllParts()")
    public void verifyAllParts() throws CriticalException {
        MainPageOfCitilink mainPageOfCitilink = new MainPageOfCitilink.PageFabric().getContainer(new SourceProviderForStart(_searcher));
        mainPageOfCitilink.openPage();
        boolean res = mainPageOfCitilink.isPresentAllSubPartsInFullDepth();
        Assert.assertTrue(res);
    }

    @Test(groups = {"second_group"}, priority = 1, description = "TestNG: Проверка всех элементов на странице!")
    @Description("Allure>Description: Тест проверки всех элементов на странице")
    @Step("Allure>Step: Шаг для проверки Element на странице")
    public void verifyAllElements(ITestContext testContext, Method method) throws CriticalException {
        MainPageOfCitilink mainPageOfCitilink = new MainPageOfCitilink.PageFabric().getContainer(new SourceProviderForStart(_searcher));
        mainPageOfCitilink.openPage();
        boolean res = mainPageOfCitilink.isPresentAllElementsInFullDepth();
        Assert.assertTrue(res);
    }

    @Test(groups = {"third_group"}, priority = 2, description = "TestNG: Поиск товаров!", timeOut = 25000, dataProvider = "forSearch")
    @Description("Тест проверки ввода элементов в поле поиска")
    @Step("Allure>Step: Шаг по вводу полей param = {0} и mask = {1}")
    public void typeTextInSearchField(String param, String mask) throws CriticalException {
        boolean res = true;

        MainPageOfCitilink mainPageOfCitilink = new MainPageOfCitilink.PageFabric().getContainer(new SourceProviderForStart(_searcher));
        mainPageOfCitilink.openPage();
        if (mainPageOfCitilink.isPresentAllSubPartsInFullDepth() == false)
            _logger.error("При проверке элементов не все элементы были найдены!");
        PartHeaderSecondFloor part = mainPageOfCitilink.HEADER_PART.UNDER_HEADER_PART;
        try {
            part.searchCommodity(mask);
        } catch (UnavailableTargetWebElement | UnavailableParentWebElement e) {
            _logger.error("Ошибка в результате поиска элементов!", e);
            res = false;
            saveScreenshot();
        } catch (UnavailableReturndeContainer e) {
            _logger.error("Ошибка при попытке получить страницу с результатом поиска!", e);
            res = false;
            saveScreenshot();
        }
        Assert.assertTrue(res);
    }

    // TODO: Метод проверки линок в том виде больше нет. Нужно делать через передачу объекта в метод verify() - что и будет определять правило проверки.
    @Test
    @Description("Тест проверки перехода по ссылкам")
    @Step("Allure>Step: Шаг проверки перехода по ссылкам")
    public void goThrowLinks() throws CriticalException {
        MainPageOfCitilink mainPageOfCitilink = new MainPageOfCitilink.PageFabric().getContainer(new SourceProviderForStart(_searcher));
        mainPageOfCitilink.openPage();
        mainPageOfCitilink.isPresentAllElementsInFullDepth();
        //boolean res2 = mainPageOfCitilink.verify();
        //Assert.assertTrue(res2);
    }
*/
}
