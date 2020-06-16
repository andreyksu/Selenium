package ru.annikonenkov.common.worker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.exceptions.UnavailableParentWebElement;
import ru.annikonenkov.common.registry.PartRegistry;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;

/**
 * Описывает часть страницы. Т.е. можно провести аналогию с div, когда он используется для логической группировки страницы.<br>
 * По сути является центральным звеном - вся работа ведется здесь.
 * 
 * @param <T> - <b><i>родитель</i></b> для данного Part. Это может быть Page, LMD, или другой Part.
 * @param <P> - <b><i>класс-описание</i></b> для данного Part. Т.е. PartDescriptor - т.е. содержит {@link Element}.
 */

public abstract class APartWorker<T extends IContainerWorker, P extends APartDescriptor> implements IPartWorker<T> {

    private final static Logger _logger = LogManager.getLogger(APartWorker.class);

    private final String _descriptionOfPart;

    private final String _nameForMap;

    private final T _owner;

    /**
     * Коллекция дочерних Part.<br>
     * Сюда помещаются дочерние Part.
     */
    private final Map<String, IPartWorker<? extends IPartWorker<?>>> _subPartsAsMap = new HashMap<>();

    /**
     * Поле содержит класс-описание (PartDescriptor) для данного Part.
     */
    protected final P partDescriptor;

    private PartRegistry _partRegistry;

    /**
     * Переменная описывает - множитель времени ожидания.<br>
     * Т.е. берется стандартное время, которое должно быть задано в тесте - и умножается на этот коэффициент.<br>
     * Т.е. первая переменная являетя общая для теста - когда мы говорим о медленных машинах - мы увеличиваем время ожидания.<br>
     * Но при этом еще каждый метод в Part должен знать если ему требуется больше заданного времени (он может воспользоваться множителем.<br>
     * Для методов из абстрактного класса - это поле яляется единицей - так как в стандартных действиях увеличение времени по отношению к заданному, не
     * требуется.
     */
    protected final byte timeMultiplier = 1;

    public APartWorker(T owner, PartRegistry partRegistry, P relatedPartDescriptor) {
        _owner = owner;
        _partRegistry = partRegistry;
        _descriptionOfPart = _partRegistry.getDescription();
        _nameForMap = _partRegistry.getName();
        partDescriptor = relatedPartDescriptor;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return _nameForMap;
    }

    @Override
    public String getFullDescription() {
        StringBuilder sb = new StringBuilder().append(_owner.getFullDescription()).append("----->").append(_descriptionOfPart);
        return sb.toString();
    }

    @Override
    public T getOwner() {
        return _owner;
    }

    @Override
    public ISearchAndAnalyzeElement getSearcherAndAnalуzerElements() {
        return _owner.getSearcherAndAnalуzerElements();
    }

    @Override
    public WebDriver getWebDriver() {
        return _owner.getWebDriver();
    }

    @Override
    public IPartWorker<? extends IContainerWorker> getPartByName(String nameOfPart) {
        return _subPartsAsMap.get(nameOfPart);
    }

    @Override
    public <K extends IPartWorker<?>> void addSubPart(IPartWorker<K> part) {
        _subPartsAsMap.put(part.toString(), part);
    }

    /**
     * {@inheritDoc}<br>
     * <br>
     * Секцию:
     * 
     * <pre>
     * {@code
     * if (webElementRepresentsCurrentPart != null)
     *          return true;
     * }
     * </pre>
     * 
     * Убрал! Не подошла, так как после навигации при возврате на эту же страницу - элемент этот уже не годится для работы (падает с ошибкой Stale Element
     * Reference Exception).<br>
     * - При каждом вызове этого метода, происходит поиск элемента текущего Part - все по той же причине, что и выше.<br>
     * - Необходимо для случая, когда возвращаемся на эту же страницу через функцию - "Вернуться обратно".
     * <p>
     * Т.е. найденный элемент принадлежит определенному инстансу открытой странице - после перезагрузки страницы уже другой инстранс.
     * <p>
     * В случае, если будет вызван данный метод без предварительной инициализации родителя - то будет обрабатано исключени - UnavailableParentElement. И
     * результат будет возвращен равный fals.
     */
    @Override
    public boolean isPresentCurrentPart() {
        boolean resultOfCheck = false;
        try {
            resultOfCheck = getElementForPart().getWebElement().isPresent();
        } catch (UnavailableParentWebElement e) {
            _logger.error("Ошибка при поиске текущего Part. У родительского Part = '{}': webElement = null", getFullDescription());
        }
        return resultOfCheck;
    }

    @Override
    public boolean isPresentChildParts() {
        if (isPresentCurrentPart() == false) {
            _logger.error("Проверка дочерхних элементов не будет выполнена, так как для тек. Part = '{}' не был найден его webElement!", getFullDescription());
            return false;
        }
        Collection<IPartWorker<? extends IPartWorker<?>>> subParts = _subPartsAsMap.values();
        boolean isPresent = true;
        for (IPartWorker<? extends IPartWorker<?>> subPart : subParts) {
            isPresent &= subPart.isPresentCurrentPart();
        }
        return isPresent;
    }

    /**
     * {@inheritDoc}<br>
     * <br>
     * Проверку {@code isPresent == false} <br>
     * Убрал! Так как если один из subPart отсутствует, проверим хотя бы остальные.<br>
     * Проверка себя, выполняется несколко раз. По сути три раза для каждого дочернего Part.
     * <ul>
     * В этом методе два раза!
     * <li>Первый раз у родитля при вызове isPresentChildParts()</li>
     * <li>Второй раз, когда мы уже спустились на этот Part при вызове isPresentCurrentPart()</li>
     * <li>И снова методе isPresentChildParts() когда уже спустились на тек. Part</li>
     * </ul>
     * По сути на уже проинициализированной странице это не составит проблем. Но позволит избежать проблем при обновлении или перезагрузке страницы. Хотя нужно
     * еще проанализировать.
     */
    @Override
    public boolean isPresentAllSubPartsInFullDepth() {
        if (isPresentCurrentPart() == false) {
            _logger.error("Проверка всей иерархии Part не будет выполнена, так как для тек. Part = '{}' не был найден его webElement!", getFullDescription());
            return false;
        }
        boolean isPresent = isPresentChildParts();
        Collection<IPartWorker<? extends IPartWorker<?>>> subParts = _subPartsAsMap.values();
        for (IPartWorker<? extends IPartWorker<?>> subPart : subParts) {
            isPresent &= subPart.isPresentAllSubPartsInFullDepth();
        }
        return isPresent;
    }

    /**
     * {@inheritDoc}
     * <p>
     * По сути метод может быть вызван самостоятельно - т.е. когда Part порождается в результате клика на Кнопку, а не в рамках создания страницы.
     */

    @Override
    public boolean isPresentItsOwnElements() {
        if (isPresentCurrentPart() == false) {
            _logger.error("Проверка наличия Element не будет выполнена, так как для тек. Part = '{}' не был найден его webElement!", getFullDescription());
            return false;
        }
        boolean resultOfcheck = false;
        Collection<Element<? extends IContainerWorker>> elements = partDescriptor.getCollectionOfElements();
        try {
            resultOfcheck = checkIsPresentAllElementsInPart(elements);
        } catch (UnavailableParentWebElement e) {
            _logger.error("Ошибка при поиске элемента. У тек. Part = {} не был проинициализиров его WebElement", getFullDescription());
        }
        return resultOfcheck;
    }

    /**
     * {@inheritDoc}<br>
     * <br>
     * Проверку {@code isPresent == false} <br>
     * Убрал! Так как если в текущем Part нет какого-то элемента, то проверим во вложенных Part наличие элементов.
     */
    @Override
    public boolean isPresentAllElementsInFullDepth() {
        if (isPresentCurrentPart() == false) {
            _logger.error("Проверка наличия Element по всей иерархии не будет выполнена, так как для тек. Part = '{}' не был найден webElement!",
                    getFullDescription());
            return false;
        }
        boolean isPresent = isPresentItsOwnElements();
        Collection<IPartWorker<? extends IPartWorker<?>>> parts = _subPartsAsMap.values();
        for (IPartWorker<? extends IPartWorker<?>> iPart : parts) {
            isPresent &= iPart.isPresentAllElementsInFullDepth();
        }
        return isPresent;
    }

    @Override
    public IContainerWorker getRootContainer() {
        return _owner.getRootContainer();
    }

    /**
     * Метод, что непосредственно проверят работу ссылки.<br>
     * <ul>
     * <li>Предварительно проверят, найден ли WeBElement ссылки и является ли элемент ссылкой.</li>
     * <li>Кликае по ссылке.</li>
     * <li>Ждет открытия страницы! Проверят на открывшейся странице все элементы (т.е. параллельно инициализирует все элементы)</li>
     * <li>Проверяет URL. Туда ли привела Link</li>
     * <li>После возврата на исходную страницу, снова производим инициализацию элементов исходной страницы. Необходимо из за особоенностой работы WebDriver</li>
     * </ul>
     * 
     * @param elements - коллекция всех элементов, что задекларированы в PartDescriptor текущего Part.
     * @return - результат проверки всех Link на текущем Part.
     */
    private boolean doWorkConcreteLinkOnPart(Collection<Element<? extends IContainerWorker>> elements) {
        boolean isWorkLinks = true;
        for (Element<? extends IContainerWorker> elem : elements) {
            if (elem.getTypeOfElement() == ETypeOfElement.LINK) {
                Optional<WebElement> webElem = elem.getWebElement();
                if (webElem.isEmpty()) {
                    _logger.error("На Part - '{}', элемент-ссылка - '{}' видимо не былa найден, так как её WebElement == null. Пропускаем.",
                            getFullDescription(), elem);
                    continue;
                }
                if (!elem.hasProducedContainer()) {
                    _logger.warn(
                            "На Part - '{}', для элемента-ссылки - '{}' не определен возвращаемый Container, по этой прчине переход по данной ссылке будет пропущен.",
                            getFullDescription(), elem);
                    continue;
                }

                _logger.info("Element для ссылка был найден. Имеет возвращаемый контейнер. Кликаем по ссылке, для перехода на страницу!");

                // TODO: Пока оставил так. Т.к. реализация была до перехода на Optioanl. Позже вернуться и переделать.
                webElem.get().click();
                IContainerWorker container = elem.getProducedContainer(getSearcherAndAnalуzerElements()).get();

                _logger.info("Проверяем у все элементы у полученного Container = '{}' в результате перехода по ссылки!", container.getFullDescription());
                isWorkLinks &= container.isPresentAllElementsInFullDepth();

                if (container instanceof IPageWorker) {
                    _logger.debug("На Page = '{}', Element - '{}' возвращает страницу. Проверим ее URL", getFullDescription(), elem);
                    IPageWorker ipage = (IPageWorker) container;
                    String URLOfPage = ipage.getURLOfPage();
                    String returnedURL = getSearcherAndAnalуzerElements().getURLOfCurrentPage();
                    isWorkLinks &= URLOfPage.equals(returnedURL);
                    if (!URLOfPage.equals(returnedURL)) {
                        _logger.error("Адреса страниц не совпадают Ожидаем - '{}', Получили - '{}'", URLOfPage, returnedURL);
                    }
                } else {
                    _logger.debug("Возвращенный контейнер не является Page. А является = '{}'. Проверка характерная для Page выполнена не будет",
                            container.getClass());
                }

            } else {
                _logger.debug("Проверяемый элемент = '{}' имеет тип = '{}'. Пропускаем проверку свойственную для Link", elem, elem.getTypeOfElement());
            }
            getSearcherAndAnalуzerElements().getWebDriver().navigate().back();
            IPageWorker page = (IPageWorker) getRootContainer();
            page.isPresentAllElementsInFullDepth();
        }
        return isWorkLinks;

    }

    @Override
    public boolean verifyLinks() {
        if (isPresentCurrentPart() == false)
            return false;
        boolean isWorkLinks = true;
        Collection<Element<? extends IContainerWorker>> elements = partDescriptor.getCollectionOfElements();
        isWorkLinks = doWorkConcreteLinkOnPart(elements);

        Collection<IPartWorker<? extends IPartWorker<?>>> colls = _subPartsAsMap.values();
        for (IPartWorker<? extends IPartWorker<?>> coll : colls) {
            isWorkLinks &= coll.verifyLinks();
        }
        return isWorkLinks;
    }

    @Override
    public Element<? extends IContainerWorker> getElementForPart() throws UnavailableParentWebElement {
        return getElementForPart(true);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Нужно учесть, что с таким подходом (см. первую проверку) при наличии у всех Part - webElement = null - мы доберемся до самого корня. И если где-то
     * наверху так и не удастся найдит webElelent - мы попадем на UnavailableParentElement
     */
    @Override
    public Element<? extends IContainerWorker> getElementForPart(boolean doNewSearch) throws UnavailableParentWebElement {
        if (partDescriptor.getElementForCurrentPart().getWebElement().isPresent() && !doNewSearch) {
            return partDescriptor.getElementForCurrentPart();
        }
        T own = getOwner();
        Element<? extends IContainerWorker> parentElement = null;
        if (own instanceof APartWorker) {
            parentElement = ((APartWorker<?, ?>) own).getElementForPart(false);
            getSearcherAndAnalуzerElements().getWebElementWithinParent(partDescriptor.getElementForCurrentPart(), parentElement, timeMultiplier);
        } else if ((own instanceof ALMDWorker) && ((ALMDWorker) own).getElementAsStartPointForThisLMD() != null) {
            parentElement = ((ALMDWorker) own).getElementAsStartPointForThisLMD();
            getSearcherAndAnalуzerElements().getWebElementWithinParent(partDescriptor.getElementForCurrentPart(), parentElement, timeMultiplier);
        } else {
            getSearcherAndAnalуzerElements().getWebElement(partDescriptor.getElementForCurrentPart(), timeMultiplier);
        }
        if (partDescriptor.getElementForCurrentPart().getWebElement().isEmpty())
            _logger.error("Для текущего Part = '{}' НЕ найден описывающей его - webElement", getFullDescription());
        else {
            _logger.info("Для текущего Part = '{}' найден описывающей его webElement", getFullDescription());
        }
        return partDescriptor.getElementForCurrentPart();
    }

    /**
     * Проверяет элементы на присутствие в текущем Part
     * 
     * @param elements - коллекция элементов текущего Part
     * @return - результат проверки наличия элементов в текущем Part.
     */
    private boolean checkIsPresentAllElementsInPart(Collection<Element<? extends IContainerWorker>> elements) throws UnavailableParentWebElement {
        boolean result = true;
        for (Element<? extends IContainerWorker> element : elements) {
            result &= getSearcherAndAnalуzerElements().checkIsPresentWebElementWithInParent(element, partDescriptor.getElementForCurrentPart(), timeMultiplier);
            if (!result) {
                _logger.error("В Part = '{}' НЕ  найден Element = '{}'", getFullDescription(), element.toString());
            } else {
                _logger.info("В Part = '{}' найден Element = '{}'", getFullDescription(), element.toString());
            }
        }
        return result;
    }

}
