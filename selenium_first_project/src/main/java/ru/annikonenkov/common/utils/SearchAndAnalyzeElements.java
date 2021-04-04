package ru.annikonenkov.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.exceptions.UnavailableParentElement;
import ru.annikonenkov.common.worker.IContainerWorker;

public class SearchAndAnalyzeElements implements ISearchAndAnalyzeElement {

    private final static Logger _logger = LogManager.getLogger(SearchAndAnalyzeElements.class);

    private final String messageForIsntWebElement =
            "Exception: У родителя = '%s' его webElement = null !";

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
     * @param waitTime - время ожидания элементов на странице.Задается в тесте - так как тест знает, какое ожидание
     *            необходимо для каждого конкретного случая. Если задается больше чем maxForTimeWait, то будет
     *            установлено значение maxForTimeWait.
     */
    public SearchAndAnalyzeElements(WebDriver driver, int waitTime) {
        _waitTime = (waitTime <= MAX_WAIT_TIME) ? waitTime : MAX_WAIT_TIME;
        _driver = driver;
    }

    private int getMultiplier(int timeMultiplier) {
        return (timeMultiplier <= MAX_MULTIPLIER) ? timeMultiplier : MAX_MULTIPLIER;
    }

    /**
     * Реализация поиска элемента (конкретного элемента).<br>
     * Не использовать метод ля проверки отсутствия элементов. Для этого есть метод {@link getWebElementsImplement(...)}
     * - причина указана в документацииы на метод findElement(...)
     * <p>
     * Может выкинуть StaleElementReferenceException когда будем искать внутри родительского Part, а страница или часть
     * страницы обновлена.
     * 
     * @param targetElement - целевой Element, для которого будет искаться webElement.
     * @param parentElement - родительский Element внутри которого будет производиться поиск целевого Element.
     * @param timeMultiplier - временной множитель для времени ожидания. Само время ожидания здано при создании данного
     *            экземпляра.
     * @return - возвращает WebElement. Если элемент в результате поиска не был найден, то возвращает null.
     */
    private WebElement getWebElementInnerImpl(Element<? extends IContainerWorker> targetElement,
            Element<? extends IContainerWorker> parentElement, int timeMultiplier) {

        int multiplier = getMultiplier(timeMultiplier);

        WebElement firstElementInResultOfSearch = null;

        try {
            _driver.manage().timeouts().implicitlyWait(_waitTime * multiplier, TimeUnit.SECONDS);
            if (parentElement != null) {

                firstElementInResultOfSearch = parentElement.getWebElement()
                        .map((parElem) -> parElem.findElement(targetElement.getBy())).orElse(null);
            } else {
                firstElementInResultOfSearch = _driver.findElement(targetElement.getBy());
            }
        } catch (NoSuchElementException e) {
            _logger.error("Exception: Ошибка при поиске WebElement для Element = '{}'!", targetElement);
            takeScreenShotAndSaveItInFolder(targetElement.toString());
        } finally {
            _driver.manage().timeouts().implicitlyWait(_waitTime, TimeUnit.SECONDS);
        }
        targetElement.setWebElement(firstElementInResultOfSearch);
        return firstElementInResultOfSearch;
    }

    @Override
    public boolean checkIsPresentWebElement(Element<? extends IContainerWorker> targetElement, int timeMultiplier) {
        return getWebElement(targetElement, timeMultiplier) != null ? true : false;
    }

    @Override
    public boolean checkIsPresentWebElementWithInParent(Element<? extends IContainerWorker> targetElement,
            Element<? extends IContainerWorker> parentElement, int timeMultiplier) throws UnavailableParentElement {
        return getWebElementWithinParent(targetElement, parentElement, timeMultiplier) != null ? true : false;
    }

    @Override
    public WebElement getWebElement(Element<? extends IContainerWorker> targetElement, int timeMultiplier) {

        _logger.debug("Ищем без родителя, следующий элемент '{}'", targetElement);
        return getWebElementInnerImpl(targetElement, null, timeMultiplier);
    }

    @Override
    public WebElement getWebElementWithinParent(Element<? extends IContainerWorker> targetElement,
            Element<? extends IContainerWorker> parentElement, int timeMultiplier) throws UnavailableParentElement {

        _logger.debug("Ищем внутри родителя = '{}', следующий элемент = '{}'", parentElement, targetElement);
        if (parentElement == null) {
            throw new UnavailableParentElement("Exception: В качестве родительского Element передан null !");
        }
        parentElement.getWebElement().orElseThrow(
                () -> new UnavailableParentElement(String.format(messageForIsntWebElement, parentElement.toString())));
        return getWebElementInnerImpl(targetElement, parentElement, timeMultiplier);
    }

    /**
     * Реализация поиска элементов(массива элементов).
     * 
     * @param targetElement - целевой Element, для которого будет искаться webElement.
     * @param parentElement - родительский Element внутри которого будет производиться поиск целевого Element.
     * @param timeMultiplier - временной множитель для времени ожидания. Само время ожидания здано при создании данного
     *            экземпляра.
     * @return - возвращает список элементов.
     */

    private List<WebElement> getListOfWebElementsInnerImpl(Element<? extends IContainerWorker> targetElement,
            Element<? extends IContainerWorker> parentElement, int timeMultiplier) {

        int multiplier = getMultiplier(timeMultiplier);

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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WebElement> getListOfWebElements(Element<? extends IContainerWorker> targetElement,
            int timeMultiplier) {

        _logger.debug("Ищем без родителя, следующие элементы '{}'", targetElement);
        return getListOfWebElementsInnerImpl(targetElement, null, timeMultiplier);
    }

    @Override
    public List<WebElement> getListOfWebElementsWithinParent(Element<? extends IContainerWorker> targetElement,
            Element<? extends IContainerWorker> parentElement, int timeMultiplier) throws UnavailableParentElement {

        _logger.debug("Ищем внутри родителя = '{}', следующий элемент = '{}'", parentElement, targetElement);
        parentElement.getWebElement().orElseThrow(() -> {
            String message = String.format(messageForIsntWebElement, parentElement);
            return new UnavailableParentElement(message);
        });
        return getListOfWebElementsInnerImpl(targetElement, parentElement, timeMultiplier);
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    /**
     * Поиск элементов через полученную Function.<br>
     * ExcpicitWait
     * 
     * @param targetElement
     * @param parentElement
     * @param timeMultiplier
     * @param functional
     * @return
     */
    public List<WebElement> getWebElementByFunctionInnerImpl(int timeMultiplier,
            Function<WebDriver, List<WebElement>> functional) {

        List<WebElement> resultList = null;

        int multiplier = getMultiplier(timeMultiplier);

        _explicitWait = new WebDriverWait(_driver, Duration.ofSeconds(_waitTime * multiplier));

        _driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        try {
            resultList = _explicitWait.until(functional);
        } catch (TimeoutException e) {
            _logger.error("Переданное условие поиска так и не выполнено! Выходим в ExplicitWait по TimeOut.");
        } finally {
            _driver.manage().timeouts().implicitlyWait(_waitTime, TimeUnit.SECONDS);
        }
        return resultList;
    }

    /**
     * Проверяет, что элемента больше нет на странице.
     * <p>
     * В Function возвращает null для продолженя поиска. См. доку на until(...); <br>
     * <p>
     * {@inheritDoc}
     */

    // TODO: Возможно вынести в отдельный провайдер функций. Функция отсутствия элмента, присутствия итд.
    private Function<WebDriver, List<WebElement>> functionForWaitEraseElement(
            Element<? extends IContainerWorker> targetElem, Element<? extends IContainerWorker> parentElem)
            throws UnavailableParentElement {

        Function<WebDriver, List<WebElement>> function = null;
        Optional<Element<? extends IContainerWorker>> optional = Optional.ofNullable(parentElem);
        boolean isPresetnWebElement = optional.isPresent();

        String message = String.format(messageForIsntWebElement, parentElem);

        WebElement parWebElem = optional.flatMap((parElem) -> parElem.getWebElement())
                .orElseThrow(() -> new UnavailableParentElement(message));

        if (isPresetnWebElement) {
            function = (webDriver) -> {
                List<WebElement> parentWebElements = parWebElem.findElements(targetElem.getBy());
                if (parentWebElements.size() != 0) {
                    return null;
                }
                return parentWebElements;
            };
        } else {
            function = (webDriver) -> {
                List<WebElement> parentWebElements = webDriver.findElements(targetElem.getBy());
                if (parentWebElements.size() != 0) {
                    return null;
                }
                return parentWebElements;
            };
        }
        return function;
    }

    /**
     * Проверяет, что элемента больше нет на странице.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public boolean verifyThatTargetElementDoesntPresent(Element<? extends IContainerWorker> targetElem,
            Element<? extends IContainerWorker> parentElem, int timeMultiplier) throws UnavailableParentElement {

        List<WebElement> list =
                getWebElementByFunctionInnerImpl(timeMultiplier, functionForWaitEraseElement(targetElem, parentElem));
        if (list != null && list.size() == 0) {
            _logger.info("Успех! Не смогли найти объект, что должен ОТСУТСТВОВАТЬ на странице!");
            return true;
        }
        return false;
    }

    @Override
    public List<WebElement> getWebElementByFunction(Element<? extends IContainerWorker> targetElement,
            Element<? extends IContainerWorker> parentElement, int timeMultiplier) throws UnavailableParentElement {
        _logger.debug("Ищем внутри родителя = '{}', следующий элемент = '{}'", parentElement, targetElement);
        if (parentElement == null) {
            throw new UnavailableParentElement("Exception: В качестве родительского Element передан null !");
        }
        parentElement.getWebElement().orElseThrow(
                () -> new UnavailableParentElement(String.format(messageForIsntWebElement, parentElement)));

        Function<WebDriver, List<WebElement>> functional = null;
        // return getWebElementByFunctionInnerImpl(timeMultiplier, functional);
        return null;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

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
            String resultPathToCopy = _folderPathForSaveScreen.toString() + FileSystems.getDefault().getSeparator()
                    + nameOfScreenShot + ".png";
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
