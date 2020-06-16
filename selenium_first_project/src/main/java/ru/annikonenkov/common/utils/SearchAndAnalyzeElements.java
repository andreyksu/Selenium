package ru.annikonenkov.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.exceptions.UnavailableParentWebElement;
import ru.annikonenkov.common.worker.IContainerWorker;

public class SearchAndAnalyzeElements implements ISearchAndAnalyzeElement {

    private final static Logger _logger = LogManager.getLogger(SearchAndAnalyzeElements.class);

    private WebDriver _driver;

    private Path _folderPathForSaveScreen = null;

    private WebDriverWait _explicitWait;

    /**
     * Переменная описывающая максимальное время ожидания элементов.<br>
     * Эта переменная должна быть передана - или проинициированна в ISearchAndAnalyzeElement.
     */
    private final int _waitTime;

    /**
     * Данный класс инициализируется в каждом конкретном тестовом-классе.<br>
     * Задается время ожиданий, webDriver итд - что свойствено конкретному тесту.
     * 
     * @param driver - экземпляр вебДрайвера.
     * @param waitTime - время ожидания элементов на странице.Задается в тесте - так как тест знает, какое ожидание необходимо для каждого конкретного случая.
     *            Если задается больше чем maxForTimeWait, то будет установлено значение maxForTimeWait.
     */
    public SearchAndAnalyzeElements(WebDriver driver, int waitTime) {
        _waitTime = (waitTime <= maxTimeWait) ? waitTime : maxTimeWait;
        _driver = driver;
        _explicitWait = new WebDriverWait(_driver, Duration.ofSeconds(waitTime));
    }

    @Override
    public boolean checkIsPresentWebElement(Element<? extends IContainerWorker> targetElement, int timeMultiplier) {
        return getWebElement(targetElement, timeMultiplier) != null ? true : false;
    }

    @Override
    public boolean checkIsPresentWebElementWithInParent(Element<? extends IContainerWorker> targetElement, Element<? extends IContainerWorker> parentElement,
            int timeMultiplier) throws UnavailableParentWebElement {
        return getWebElementWithinParent(targetElement, parentElement, timeMultiplier) != null ? true : false;
    }

    @Override
    public WebElement getWebElement(Element<? extends IContainerWorker> targetElement, int timeMultiplier) {
        _logger.debug("Ищем без родителя, следующий элемент '{}'", targetElement);
        return getWebElementInnerImpl(targetElement, null, timeMultiplier);
    }

    // TODO: Сам parentElement тоже может быть null - нужно ли с этим что-то делать? Или действительно должно быть NPE?
    @Override
    public WebElement getWebElementWithinParent(Element<? extends IContainerWorker> targetElement, Element<? extends IContainerWorker> parentElement, int timeMultiplier)
            throws UnavailableParentWebElement {
        _logger.debug("Ищем внутри родителя = '{}', следующий элемент = '{}'", parentElement, targetElement);
        if (parentElement == null) {
            throw new UnavailableParentWebElement("Exception: В качестве родительского Element передан null !");
        }
        parentElement.getWebElement().orElseThrow(() -> new UnavailableParentWebElement("Exception: Родительский webElement = null !"));
        return getWebElementInnerImpl(targetElement, parentElement, timeMultiplier);
    }

    /**
     * Реализация поиска элемента (конкретного элемента).<br>
     * Не использовать метод ля проверки отсутствия элементов. Для этого есть метод {@link getWebElementsImplement(...)} - причина указана в документацииы на
     * метод findElement(...)
     * 
     * @param targetElement - целевой Element, для которого будет искаться webElement.
     * @param parentElement - родительский Element внутри которого будет производиться поиск целевого Element.
     * @param timeMultiplier - временной множитель для времени ожидания. Само время ожидания здано при создании данного экземпляра.
     * @return - возвращает WebElement. Если элемент в результате поиска не был найден, то возвращает null.
     */
    private WebElement getWebElementInnerImpl(Element<? extends IContainerWorker> targetElement, Element<? extends IContainerWorker> parentElement, int timeMultiplier) {
        int multiplier = (timeMultiplier <= maxMultipier) ? timeMultiplier : maxMultipier;

        WebElement firstElementInResultOfSearch = null;

        try {
            _driver.manage().timeouts().implicitlyWait(_waitTime * multiplier, TimeUnit.SECONDS);
            if (parentElement != null) {
                firstElementInResultOfSearch = parentElement.getWebElement().map((parElem) -> parElem.findElement(targetElement.getBy())).orElse(null);
            } else {
                firstElementInResultOfSearch = _driver.findElement(targetElement.getBy());
            }
        } catch (NoSuchElementException e) {
            _logger.error("Exception: Element = '{}' не удалось найти!", targetElement);
            takeScreenShotAndSaveItInFolder(targetElement.toString());
        } finally {
            _driver.manage().timeouts().implicitlyWait(_waitTime, TimeUnit.SECONDS);
        }
        targetElement.setWebElement(firstElementInResultOfSearch);
        return firstElementInResultOfSearch;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WebElement> getListOfWebElements(Element<? extends IContainerWorker> targetElement, int timeMultiplier) {
        _logger.debug("Ищем без родителя, следующие элементы '{}'", targetElement);
        return getListOfWebElementsInnerImpl(targetElement, null, timeMultiplier);
    }

    @Override
    public List<WebElement> getListOfWebElementsWithinParent(Element<? extends IContainerWorker> targetElement, Element<? extends IContainerWorker> parentElement,
            int timeMultiplier) throws UnavailableParentWebElement {
        _logger.debug("Ищем внутри родителя = '{}', следующий элемент = '{}'", parentElement, targetElement);
        parentElement.getWebElement().orElseThrow(() -> {
            String message =
                    String.format("Exception: У родителя = '%s' отсутствует webElement, внутри котрого планируется осуществлять поиск!", parentElement);
            return new UnavailableParentWebElement(message);
        });
        return getListOfWebElementsInnerImpl(targetElement, parentElement, timeMultiplier);
    }

    /**
     * Реализация поиска элементов(массива элементов).
     * 
     * @param targetElement - целевой Element, для которого будет искаться webElement.
     * @param parentElement - родительский Element внутри которого будет производиться поиск целевого Element.
     * @param timeMultiplier - временной множитель для времени ожидания. Само время ожидания здано при создании данного экземпляра.
     * @return - возвращает список элементов.
     */

    private List<WebElement> getListOfWebElementsInnerImpl(Element<? extends IContainerWorker> targetElement, Element<? extends IContainerWorker> parentElement,
            int timeMultiplier) {
        int multiplier = (timeMultiplier <= maxMultipier) ? timeMultiplier : maxMultipier;
        WebElement webElementOfParent = parentElement.getWebElement().orElse(null);
        List<WebElement> listOfElements = null;
        _driver.manage().timeouts().implicitlyWait(_waitTime * multiplier, TimeUnit.SECONDS);
        if (webElementOfParent != null) {
            listOfElements = webElementOfParent.findElements(targetElement.getBy());
        } else {
            listOfElements = _driver.findElements(targetElement.getBy());
        }
        _driver.manage().timeouts().implicitlyWait(_waitTime, TimeUnit.SECONDS);
        return listOfElements;
    }

    /*
     * TODO: Пердполагается, что здесь будет приниматься functional - который будет специфичен для поиска определенного элемента (т.е. специфичный поиск). Нужно
     * подумать, где его хранить - в определенном Part? А как быть если нужно сделать хитрый поиск при обходе списка элементов Page?
     */

    /*
     * TODO: Подумать тад тем, как искать нестандартные вещи с нестандартным ожиданием - т.е. есть _explicitWait в ISearchAndAnalyzeElement и вот туда как-то
     * нужно передавать реализацию. Опять же, есть момент - если у нас есть список элементов и только один из них обладает определенным хитрым алгоритмом или
     * только один из Part - как быть в таком случае при переборе? Быть может бежать по списку и справшивать какой поиск использовать - если стандартный - то
     * стандартный поиск ищем, если нет то запрашиваем у Element лямбду и применяем ее для поиска через _explicitWait
     */
    private WebElement getWebElementByFunctionInnerImpl(Element<? extends IContainerWorker> targetElement, WebElement parentElement, char timeMultiplier,
            Function<WebDriver, WebElement> functional) {
        _explicitWait.until(functional);
        return null;
    }

    public WebElement getWebElementByFunction(Element<? extends IContainerWorker> targetElement, WebElement parentElement, char timeMultiplier,
            Function<WebDriver, WebElement> functional) {
        return getWebElementByFunctionInnerImpl(targetElement, parentElement, timeMultiplier, functional);
    }

    @Override
    public void setPathToFolderForSaveScreen(Path folderPathForSaveScreen) {
        _folderPathForSaveScreen = folderPathForSaveScreen;
    }

    @Override
    public Path getPathToFolderForSaveScreen() {
        return _folderPathForSaveScreen;
    }

    @Override
    public void takeScreenShotAndSaveItInFolder(String nameOfScreenShot) {
        File srcFile = ((TakesScreenshot) _driver).getScreenshotAs(OutputType.FILE);
        try {
            String resultPathToCopy = _folderPathForSaveScreen.toString() + FileSystems.getDefault().getSeparator() + nameOfScreenShot + ".png";
            _logger.debug("File = '{}' будет скопирован в каталог = '{}'", srcFile.getAbsoluteFile(), resultPathToCopy);
            FileUtils.copyFile(srcFile.getAbsoluteFile(), new File(resultPathToCopy));
        } catch (IOException e) {
            _logger.error("Ошибка при копировании файла", e);
        }
    }

    @Override
    public String getURLOfCurrentPage() {
        String currentUrlOfPage = _driver.getCurrentUrl();
        _logger.debug("currentUrlOfPage = '{}'", currentUrlOfPage);
        return currentUrlOfPage;
    }

    @Override
    public WebDriver getWebDriver() {
        return _driver;
    }

}
