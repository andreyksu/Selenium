package ru.annikonenkov.common.worker;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.registry.IRegistry;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;
import ru.annikonenkov.common.utils.visitor.IVerifyVisitor;

public abstract class AHightLevelContainer implements IHightLevelContainer {

    private final static Logger _LOGGER = LogManager.getLogger(AHightLevelContainer.class);

    private final String _description;

    private final String _name;

    private final ISearchAndAnalyzeElement _searcher;

    private IRegistry _registry;

    private final Map<IPartRegistry, IPartWorker> _partsAsMap = new HashMap<>();

    protected AHightLevelContainer(IRegistry registry, ISearchAndAnalyzeElement searcher) {
        _registry = registry;
        _description = _registry.getDescription();
        _name = _registry.getName();
        _searcher = searcher;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public String getFullDescription() {
        return _description;
    }

    @Override
    public void addPart(IPartWorker partWorker) {
        if (partWorker == null) {
            _LOGGER.error("В качестве Part передан null. Part добавлен не будет");
            return;
        }
        IPartRegistry partRegistry = partWorker.getRegistry();
        if (partRegistry.hasParentPart()) {
            _LOGGER.error("Передан Part = '{}' с родителем Part = '{}'! Переданный Part не будет добавлен к текущему Container = {}", partRegistry.getName(),
                    partRegistry.getParentPartRegistry().get().getName(), _name);
            return;
        }
        _partsAsMap.put(partWorker.getRegistry(), partWorker);
    }

    @Override
    public ISearchAndAnalyzeElement getSearcherAndAnalуzerElements() {
        return _searcher;
    }

    @Override
    public WebDriver getWebDriver() {
        return _searcher.getWebDriver();
    }

    @Override
    public IContainerWorker getRootContainer() {
        return this;
    }

    // TODO: Метод полностью совпадает с аналогичным методом в APartWorker. Нужно вынести в общую часть.
    // TODO: Переделать под Optional.
    // TODO: А еще, по сути Page или Part могут иметь несколько одинаковых Part (что мы получим в этом случае? Будет логическая ошибка)
    @Override
    public IPartWorker getPart(IPartRegistry partRegistry) {
        if (partRegistry == null) {
            _LOGGER.error("В качестве IPartRegistry был передан null, поиск не будет выполнен.");
            return null;
        }
        IPartWorker targetPart = _partsAsMap.get(partRegistry);
        if (targetPart == null) {
            Collection<IPartWorker> childParts = _partsAsMap.values();
            _LOGGER.debug("В Page = '{}' не смогли найти Part = '{}'", _name, partRegistry);
            _LOGGER.debug("В Page = '{}' находятся childParts = '{}'", _name, childParts);
            for (IPartWorker part : childParts) {
                targetPart = part.getPart(partRegistry);
                if (targetPart != null)
                    break;
            }
        }
        return targetPart;
    }

    // TODO: Тоже полностью совпадает с APartWorker. Тоже подумать над выносом в общую часть.
    @Override
    public <T> List<T> getPartByClass(Class<T> clazz) {
        Collection<IPartWorker> childParts = _partsAsMap.values();
        List<T> list = childParts.stream().filter(clazz::isInstance).map(clazz::cast).collect(Collectors.toList());
        if (list.size() == 0) {
            _LOGGER.debug("В Page = '{}' не смогли найти Part = '{}'", _name, clazz);
            _LOGGER.debug("В Page = '{}' находятся childParts = '{}'", _name, childParts);
            for (IPartWorker childrenPart : childParts) {
                list = childrenPart.getPartByClass(clazz);
                if (list.size() != 0)
                    break;
            }
        }
        return list;
    }

    @Override
    public boolean isPresentChildParts() {
        Collection<IPartWorker> childParts = _partsAsMap.values();
        boolean isPresent = true;
        for (IPartWorker childPart : childParts) {
            isPresent &= childPart.isPresentCurrentPart();
        }
        return isPresent;
    }

    @Override
    public boolean isPresentAllSubPartsInFullDepth() {
        Collection<IPartWorker> childParts = _partsAsMap.values();
        boolean isPresent = isPresentChildParts();
        for (IPartWorker childPart : childParts) {
            isPresent &= childPart.isPresentAllSubPartsInFullDepth();
        }
        return isPresent;
    }

    @Override
    public boolean isPresentAllElementsInFullDepth() {
        Collection<IPartWorker> childParts = _partsAsMap.values();
        boolean isPresent = true;
        for (IPartWorker childPart : childParts) {
            isPresent &= childPart.isPresentAllElementsInFullDepth();
        }
        return isPresent;
    }

    // TODO: Должен быть переопределен в LMD и Page (т.к. visitor расчитан на прием конекретных классов).
    @Override
    public boolean verifyByVisitor(IVerifyVisitor visitor) {
        return false;
    }

}
