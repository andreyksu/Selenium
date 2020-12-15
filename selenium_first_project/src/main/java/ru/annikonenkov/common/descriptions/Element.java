package ru.annikonenkov.common.descriptions;

import java.util.Optional;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ru.annikonenkov.common.exceptions.UnavailableReturndeContainer;
import ru.annikonenkov.common.exceptions.UnavailableTargetElement;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.worker.IContainerWorker;

/**
 * Представляет собой элемент, который содержится в IPart.<br>
 * Данный класс содержит в себе:
 * <ul>
 * <li>String - Текстовое описание элемента.</li>
 * <li>By - селектор, что описывает данный элемент поиске на странице.</li>
 * <li>WebElement - найденный элемента на странице.</li>
 * <li>Тип элемента - описывает к какому типу отнести данный элемент Ссылка, Кнопка итд</li>
 * <li>Возвращает объект что генерируется при клике на данный элемент - для Link - это Page. Для кнопки это может быть ЛМД(формы заполнения).</li>
 * </ul>
 * 
 * @param <R>
 */
public class Element<T extends IContainerWorker> implements IElement<T> {

    private String _nameOfElement;

    private String _selectorOfElement;

    private Function<String, By> _fSelectorToBy;

    private ETypeOfElement _typeOfElement;

    /**
     * Описывает контейнер, что будет вызван при взаимодействии с данным элементом.<br>
     * Для элемента описывающего Part - будет возвращен сам Part <br>
     * Для Link, если она выполняет переход на новую строку - будет возвращен Page - на который привела данная Link<br>
     * Для Button - это может быть ЛМД (локально-модельное окно) - форма заполнения.
     */
    /*
     * TODO: Вот для кнопки не понятно, когда мы кликнули на кнопку в ЛМД(кнопку подтверждения, которая закрывает ЛМД) - что должно быть возвращено, Снова
     * вызвавшая страничка? Так же вопрос про поиск, вот результат поиска дал, что содержимое контента изменилось до неузнаваемости, что в этом случае?
     */
    /*
     * TODO: А еще может быть так, что Element может породить Part - т.е. всплывающая область/меню при наведении на элемент. Или в случае поиска - Part что
     * писывает контент меняется. И вот здесь видимо стоит это учесть.
     */

    private IContainerFabric<T> _containerFabric;

    private WebElement _webElement = null;

    /**
     * Конструктор элемента.
     * 
     * @param nameOfElement - Текствое наименование/описание текущего элемента.
     * @param selectorOfElement - CSS или XPath cелектор. Должен быть соотнесен с полем function.
     * @param function - Ссылка на на метод вида By::cssSelector/By::xpath - т.е. описывает тип селектора.
     * @param typeOfElement - Тип элемента. Ссылка, Кнопка - описывается на базе действия, что выполняется при клике на данный элемент. Т.е. если
     *            внешне-отображаемая кнопка открывает новую страницу - то это линка.
     * @param containerFabric - Это Page, Part или ModalWindow - т.е. то что пораждает данный элемент в результате клика по нему.
     */
    public Element(String nameOfElement, String selectorOfElement, Function<String, By> function, ETypeOfElement typeOfElement,
            IContainerFabric<T> containerFabric) {
        _nameOfElement = nameOfElement;
        _selectorOfElement = selectorOfElement;
        _fSelectorToBy = function;
        _typeOfElement = typeOfElement;
        _containerFabric = containerFabric;
    }

    public Element(String nameOfElement, String selectorOfElement, Function<String, By> function, ETypeOfElement typeOfElement) {
        _nameOfElement = nameOfElement;
        _selectorOfElement = selectorOfElement;
        _fSelectorToBy = function;
        _typeOfElement = typeOfElement;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return _nameOfElement;
    }

    /**
     * @return Возвращает By для элемента - по сути и есть описание элемента в контексте WebDriver
     */
    @Override
    public By getBy() {
        return _fSelectorToBy.apply(_selectorOfElement);
    }

    /**
     * @return Возвращает тип элемента. Для примера Link, Button или еще что-то.
     */
    @Override
    public ETypeOfElement getTypeOfElement() {
        return _typeOfElement;
    }

    /**
     * @return - boolean содержит ли генерируемый контейнер.
     */
    @Override
    public boolean hasProducedContainer() {
        return _containerFabric == null ? false : true;
    }

    /**
     * @return Возвращает контейнер, что пораждает данный элемент. Может быть null.<br>
     *         Допустим Link пораждает новую страницу - т.е. будет возвращена страница. Для Button - это может быть новое модально окно. Часть кнопок тоже
     *         можгут возвращать новую страницу, допустим авторизация. Часть кнопок возвращает текущий Part или текущий Page.
     * @throws UnavailableReturndeContainer
     */
    @Override
    public Optional<IContainerFabric<T>> getProducedContainer() {
        return Optional.ofNullable(_containerFabric);
    }

    /**
     * @param webElement - найденный элемент на странице. Если элемент не был найден - то сюда помещается null.
     */
    public void setWebElement(WebElement webElement) {
        _webElement = webElement;
    }

    /**
     * @return возвращает webElement, который соответствует найденому элементу.
     * @throws UnavailableTargetElement - генерируется в случае если webElement = null.
     */
    @Override
    public WebElement getWebElementOrThrow() throws UnavailableTargetElement {
        if (_webElement == null) {
            String infoText = String.format("У Element = %s отсуствует значение WebElement!", _nameOfElement);
            throw new UnavailableTargetElement(infoText);
        }
        return _webElement;
    }

    /**
     * @return возвращает webElement, в виде Optional.
     */
    @Override
    public Optional<WebElement> getWebElement() {
        return Optional.ofNullable(_webElement);
    }

    /**
     * @return возвращает Selector в виде строки.
     */
    @Override
    public String getSelectorOfElementAsString() {
        return _selectorOfElement;
    }

}
