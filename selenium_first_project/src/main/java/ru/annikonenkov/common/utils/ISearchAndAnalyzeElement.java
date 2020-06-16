package ru.annikonenkov.common.utils;

import java.nio.file.Path;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.exceptions.UnavailableParentWebElement;
import ru.annikonenkov.common.worker.IContainerWorker;

public interface ISearchAndAnalyzeElement {

    public final int maxTimeWait = 60;

    public final int maxMultipier = 5;

    /**
     * Проверяет наличие элемента на странице. Проверка ведется без учета родителя.
     * 
     * @param targetElement - целевой элемент, что планируется искать.
     * @param timeMultiplier - множитель времени.
     * @return
     */
    public boolean checkIsPresentWebElement(Element<? extends IContainerWorker> targetElement, int timeMultiplier);

    /**
     * Проверяет наличие элемента на странице. Проверка ведется с учетом родителя.
     * 
     * @param targetElement - целевой элмент, что планируется к поиску.
     * @param parentElement - родительский элемент в рамках которого планируется производить поиск.
     * @param timeMultiplier - множитель к основному времени ожидания.
     * @return
     */
    public boolean checkIsPresentWebElementWithInParent(Element<? extends IContainerWorker> targetElement,
            Element<? extends IContainerWorker> parentElement, int timeMultiplier) throws UnavailableParentWebElement;

    /**
     * Возвращает WebElement - представляющий из себя элемент веб страницы.<br>
     * Не использовать метод для проерки отсутствия элемента (т.е. когда не должно быть элемента на странице). Для этих
     * целей использовать метод {@link getWebElements(...)}
     * 
     * @param targetElement - целевой элемент для поиска. Ищет без учета родительского элемента.
     * @param timeMultiplier - Коэффициент для времени. Т.е. умножается заданное время ожидания на данный коэффициэнт.
     * @return - возвращает найденный элемент. Может вернуть Null - если элемент не найден.
     */

    public WebElement getWebElement(Element<? extends IContainerWorker> targetElement, int timeMultiplier);

    /**
     * Возвращает WebElement - представляющий собой элемент веб страницы. Ищет с учетом родительского элемента.<br>
     * Не использовать метод для проверки отсутствия элемента. Для этих целей использовать метод
     * {@link getWebElementsWithinParent(...)}
     * 
     * @param targetElement - целевой элемент для поиска.
     * @param parentElement - родительский элемент (ранее найденный элемент). Может быть null - тогда будет вестись писк
     *            без учета родительского элемента.
     * @param timeMultiplier - Коэффициент для времени. Т.е. умножается заданное время ожидания на данный коэффициэнт.
     * @return - возвращает найденный элемент. Может вернуть Null - если элемент не найден.
     */
    public WebElement getWebElementWithinParent(Element<? extends IContainerWorker> targetElement,
            Element<? extends IContainerWorker> parentElement, int timeMultiplier) throws UnavailableParentWebElement;

    /** */
    public List<WebElement> getListOfWebElements(Element<? extends IContainerWorker> targetElement, int timeMultiplier);

    public List<WebElement> getListOfWebElementsWithinParent(Element<? extends IContainerWorker> targetElement,
            Element<? extends IContainerWorker> parentElement, int timeMultiplier) throws UnavailableParentWebElement;

    /**
     * Устанавливает пусть до каталога в котором будут сохранться артефакты теста(скриншоты, логи).
     * 
     * @param pathToFolder - пусть до каталога.
     */
    public void setPathToFolderForSaveScreen(Path pathToFolder);

    /**
     * Возвращает путь до каталога, в котором будут сохраняться артефакты теста(скриншоты, логи).
     * 
     * @return Path - пусть до каталога.
     */
    public Path getPathToFolderForSaveScreen();

    /**
     * Делает скриншот - и размещает его в каталог теста. Для того - чтобы узнать место где будет сохранен скриншот
     * нужно вопользоваться методоом
     * 
     * @see ISearchAndAnalyzeElement#getPathToFolderForSaveScreen()
     * @param nameOfScreenShot - Имя файла - имя с котороым файл будет схранён в файловой системе.
     */
    public void takeScreenShotAndSaveItInFolder(String nameOfScreenShot);

    /**
     * Возвращает URL страницы.
     * 
     * @return - URL страницы.
     */
    public String getURLOfCurrentPage();

    /**
     * Возвращает webDriver
     * 
     * @return - webDriver - экземпляр веб дарйвера.
     */
    public WebDriver getWebDriver();

}
