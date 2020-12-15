package ru.annikonenkov.common.worker;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.exceptions.UnavailableParentElement;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;
import ru.annikonenkov.common.utils.visitor.IVerifyVisitor;

/**
 * Описывает часть страницы. Т.е. можно провести аналогию с div, когда он используется для логической группировки страницы.<br>
 * По сути является центральным звеном - вся работа ведется здесь.
 * 
 * @param <PartDescriptorType> - <b><i>родитель</i></b> для данного Part. Это может быть Page, LMD, или другой Part.
 * @param <P> - <b><i>класс-описание</i></b> для данного Part. Т.е. PartDescriptor - т.е. содержит {@link Element}.
 */

public abstract class APartWorker implements IPartWorker {

    private final static Logger _LOGGER = LogManager.getLogger(APartWorker.class);

    private final String _description;

    private final String _nameForMap;

    private final IContainerWorker _owner;

    /**
     * Коллекция дочерних Part.<br>
     * Сюда помещаются дочерние Part.
     */
    private final Map<IPartRegistry, IPartWorker> _subPartsAsMap = new HashMap<>();

    /**
     * Поле содержит класс-описание (PartDescriptor) для данного Part.
     */
    private final APartDescriptor _partDescriptor;

    private IPartRegistry _partRegistry;

    /**
     * Переменная описывает - множитель для времени ожидания.<br>
     * Т.е. берется время, которое было задано в тесте при создании SearchAndAnalyzeElements - и умножается на этот коэффициент.<br>
     * Т.е. первая переменная являетя общая для теста, а вторая переменная позволяет настраивать время ожидания для конкретного теста.<br>
     * Для методов из абстрактного класса - это поле имеет значение равное 1. Так как в стандартных действиях увеличение времени через множетель, не требуется.
     * Если же для действий тек. класса время заданного в классе SearchAndAnalyzeElements не достаточно, то необходимо увеличить его в SearchAndAnalyzeElements.
     */
    protected final byte timeMultiplier = 1;

    protected APartWorker(IContainerWorker owner, IPartRegistry partRegistry, APartDescriptor relatedPartDescriptor) {
        _owner = owner;
        _partRegistry = partRegistry;
        _description = _partRegistry.getDescription();
        _nameForMap = _partRegistry.getName();
        _partDescriptor = relatedPartDescriptor;
    }

    @Override
    public IPartRegistry getRegistry() {
        return _partRegistry;
    }

    @Override
    public IContainerWorker getOwner() {
        return _owner;
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
        StringBuilder sb = new StringBuilder().append(_owner.getFullDescription()).append("----->").append(_description);
        return sb.toString();
    }

    @Override
    public void addPart(IPartWorker partWorker) {
        if (partWorker == null) {
            _LOGGER.error("В качестве Part передан null. Part добавлен не будет!");
            return;
        }
        IPartRegistry partRegistry = partWorker.getRegistry();
        if (partRegistry.hasParentPart() && (partRegistry.getParentPartRegistry().get() == _partRegistry)) {
            _subPartsAsMap.put(partWorker.getRegistry(), partWorker);
        } else {
            if (partRegistry.hasParentPart()) {
                _LOGGER.error("Переданный Part = '{}' имеет родителя = '{}', что не совпадает с текущим Part. Part не будет добавлен.", partWorker.getName(),
                        partRegistry.getName());
            } else {
                _LOGGER.error("Переданный Part = '{}' не имеет родителя. Part не будет добавлен.", partWorker.getName());
            }

        }
    }

    // TODO: Метод полностью совпадает с аналогичным методом в AHightLevelContainer. Нужно вынести в общую часть.
    // TODO: Переделать под Optional.
    @Override
    public IPartWorker getPart(IPartRegistry partRegistry) {
        if (partRegistry == null) {
            _LOGGER.error("В качестве IPartRegistry был передан null, поиск не будет выполнен.");
            return null;
        }
        IPartWorker targetPart = _subPartsAsMap.get(partRegistry);
        if (targetPart == null) {
            Collection<IPartWorker> childParts = _subPartsAsMap.values();
            _LOGGER.debug("В Part = '{}' не смогли найти целевой Part = {}", _nameForMap, partRegistry.getName());
            _LOGGER.debug("В Part = '{}' находится childParts = '{}'", _nameForMap, childParts);
            for (IPartWorker part : childParts) {
                targetPart = part.getPart(partRegistry);
                if (targetPart != null)
                    break;
            }
        }
        return targetPart;
    }

    public <T> List<T> getPartByClass(Class<T> clazz) {
        Collection<IPartWorker> childParts = _subPartsAsMap.values();
        List<T> list = childParts.stream().filter(clazz::isInstance).map(clazz::cast).collect(Collectors.toList());
        if (list.size() == 0) {
            _LOGGER.debug("В Part = '{}' не смогли найти Part = '{}'", _nameForMap, clazz);
            _LOGGER.debug("В Part = '{}' находится childParts = '{}'", _nameForMap, childParts);
            for (IPartWorker childrenPart : childParts) {
                list = childrenPart.getPartByClass(clazz);
                if (list.size() != 0)
                    break;
            }
        }
        return list;
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
    public IContainerWorker getRootContainer() {
        return _owner.getRootContainer();
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
     * Убрал данный код! Он позволял повторно не искать элемент, что ранее был найден.<br>
     * Не подошел, так как после навигации при возврате на эту же страницу - найденный WebElement уже не годится для работы (падает с ошибкой Stale Element
     * Reference Exception). Да и вообще - парни советуют каждый раз искать жлемент, дабы не падать по ошибке StaleElementReferenceException - а эта ошибка
     * возникает довольно часто даже при простой загрузке страницы - к примеру часто бывает как бы двойная загрузка страницы<br>
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
        } catch (UnavailableParentElement e) {
            _LOGGER.error("Ошибка при поиске текущего Part = '{}'. У родительского Part : webElement = null! FullDescription = '{}'", getName(),
                    getFullDescription());
        }
        return resultOfCheck;
    }

    @Override
    public boolean isPresentChildParts() {
        if (isPresentCurrentPart() == false) {
            _LOGGER.error("Проверка дочерних элементов не будет выполнена, так как для тек. Part = '{}' не был найден его webElement! FullDescription = '{}'",
                    getName(), getFullDescription());
            return false;
        }
        Collection<IPartWorker> subParts = _subPartsAsMap.values();
        boolean isPresent = true;
        for (IPartWorker subPart : subParts) {
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
            _LOGGER.error("Проверка всей иерархии Part не будет выполнена, так как для тек. Part = '{}' не был найден его webElement! FullDescription = '{}'",
                    getName(), getFullDescription());
            return false;
        }
        boolean isPresent = isPresentChildParts();
        Collection<IPartWorker> subParts = _subPartsAsMap.values();
        for (IPartWorker subPart : subParts) {
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
            _LOGGER.error("Проверка наличия Element не будет выполнена, так как для тек. Part = '{}' не был найден его webElement! FullDescription = '{}'",
                    getName(), getFullDescription());
            return false;
        }
        boolean resultOfcheck = false;
        Collection<Element<? extends IContainerWorker>> elements = _partDescriptor.getCollectionOfElements();
        try {
            resultOfcheck = checkIsPresentAllElementsInPart(elements);
        } catch (UnavailableParentElement e) {
            _LOGGER.error("Ошибка при поиске элемента. У тек. Part = {} не был проинициализиров его WebElement! FullDescription = '{}'", getName(),
                    getFullDescription());
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
            _LOGGER.error(
                    "Проверка наличия Element по всей иерархии не будет выполнена, так как для тек. Part = '{}' не был найден webElement! FullDescription = '{}'",
                    getName(), getFullDescription());
            return false;
        }
        boolean isPresent = isPresentItsOwnElements();
        Collection<IPartWorker> parts = _subPartsAsMap.values();
        for (IPartWorker iPart : parts) {
            isPresent &= iPart.isPresentAllElementsInFullDepth();
        }
        return isPresent;
    }

    /**
     * Метод, что выполняет проверку по всей иерархии. То что проверяем и как проверяем обеспечивается полученным объектом.
     */
    // TODO: Передалть метод. Должен получать Объект, что будет знать что и как проверить. Будет дергать конкретные методы что дочтупно в IPartWorker.
    @Override
    public boolean verifyByVisitor(IVerifyVisitor visitor) {
        if (isPresentCurrentPart() == false)
            return false;
        boolean isPositive = true;
        isPositive = visitor.verify(this);

        Collection<IPartWorker> colls = _subPartsAsMap.values();
        for (IPartWorker coll : colls) {
            isPositive &= coll.verifyByVisitor(visitor);
        }
        return isPositive;
    }

    @Override
    public Element<? extends IContainerWorker> getElementForPart() throws UnavailableParentElement {
        return getElementForPart(true);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Нужно учесть, что с таким подходом (см. первую проверку) при наличии у всех Part - webElement = null - мы доберемся до самого корня т.е. Page или до Part
     * у которого есть Element. И после этого пойдем вниз.<br>
     * И если верхний элемент так и не удастся найдит webElement то будет возбуждено UnavailableParentElement.
     */
    @Override
    public Element<? extends IContainerWorker> getElementForPart(boolean doNewSearch) throws UnavailableParentElement {
        Element<? extends IContainerWorker> elementForCurrentPart = _partDescriptor.getElementForCurrentPart();

        if (elementForCurrentPart.getWebElement().isPresent() && !doNewSearch) {
            return elementForCurrentPart;
        }
        IContainerWorker own = getOwner();
        Element<? extends IContainerWorker> parentElement = null;
        if (own instanceof IPartWorker) {
            parentElement = ((IPartWorker) own).getElementForPart(false);
            getSearcherAndAnalуzerElements().getWebElementWithinParent(elementForCurrentPart, parentElement, timeMultiplier);
        } else if ((own instanceof ALMDWorker) && ((ALMDWorker) own).getElementAsStartPointForThisLMD() != null) {
            parentElement = ((ALMDWorker) own).getElementAsStartPointForThisLMD();
            getSearcherAndAnalуzerElements().getWebElementWithinParent(elementForCurrentPart, parentElement, timeMultiplier);
        } else {
            getSearcherAndAnalуzerElements().getWebElement(elementForCurrentPart, timeMultiplier);
        }
        if (elementForCurrentPart.getWebElement().isEmpty())
            _LOGGER.error("Для текущего Part = '{}' НЕ найден описывающей его - webElement! FullDescription = '{}'", getName(), getFullDescription());
        else {
            _LOGGER.debug("Для текущего Part = '{}' найден описывающей его - webElement! FullDescription = '{}'", getName(), getFullDescription());
        }
        return elementForCurrentPart;
    }

    /**
     * Проверяет элементы на присутствие в текущем Part
     * 
     * @param elements - коллекция элементов текущего Part
     * @return - результат проверки наличия элементов в текущем Part.
     */
    private boolean checkIsPresentAllElementsInPart(Collection<Element<? extends IContainerWorker>> elements) throws UnavailableParentElement {
        boolean result = true;
        for (Element<? extends IContainerWorker> element : elements) {
            result &=
                    getSearcherAndAnalуzerElements().checkIsPresentWebElementWithInParent(element, _partDescriptor.getElementForCurrentPart(), timeMultiplier);
            if (!result) {
                _LOGGER.error("В Part = '{}' НЕ  найден Element = '{}'", getName(), element.toString());
            } else {
                _LOGGER.debug("В Part = '{}' найден Element = '{}'", getName(), element.toString());
            }
        }
        return result;
    }

}
