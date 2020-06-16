package ru.annikonenkov.common.worker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import ru.annikonenkov.common.registry.PageRegistry;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;

/**
 * Описывает страницу.<br>
 * Страницей является все, что имеет свой конкретный адрес. И осуществляя переход на этот адрес - мы получаем данную страницу. <br>
 * Несколько по др. обстоят дела с SPA - когда у нас может быть у приложения одна лишь страница - но постоянно меняется состав Part при работе с приложением.
 * Здесь вопрос как с этим работать - обновлять каждый раз состав Part на определенном Page. Или не смотря на том, что URL один - создавать новый Page?
 */
public abstract class APageWorker implements IPageWorker {

    private final String _descriptionOfPage;

    private final String _urlOfPage;

    private final Map<String, IPartWorker<? extends IPageWorker>> _partsAsMap = new HashMap<>();

    private ISearchAndAnalyzeElement _searcher;

    private PageRegistry _pageRegistry;

    public APageWorker(PageRegistry pageRegistry, ISearchAndAnalyzeElement searcher) {
        _pageRegistry = pageRegistry;
        _descriptionOfPage = _pageRegistry.getDescription();
        _urlOfPage = _pageRegistry.getURL();
        _searcher = searcher;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return _pageRegistry.getName();
    }

    @Override
    public String getFullDescription() {
        return _descriptionOfPage;
    }

    @Override
    public Map<String, IPartWorker<? extends IPageWorker>> getPartsOfPageAsMap() {
        return _partsAsMap;
    }

    @Override
    public IPartWorker<? extends IPageWorker> getPartByName(String nameOfPart) {
        return _partsAsMap.get(nameOfPart);
    }

    @Override
    public void addPartToPage(IPartWorker<? extends IPageWorker> part) {
        _partsAsMap.put(part.toString(), part);
    }

    @Override
    public String getURLOfPage() {
        return _urlOfPage;
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
    public IPageWorker openPage() {
        getWebDriver().get(_urlOfPage);
        return this;
    }

    @Override
    public boolean isPresentChildParts() {
        Collection<IPartWorker<? extends IPageWorker>> childParts = _partsAsMap.values();
        boolean isPresent = true;
        for (IPartWorker<? extends IPageWorker> childPart : childParts) {
            isPresent &= childPart.isPresentCurrentPart();
        }
        return isPresent;
    }

    @Override
    public boolean isPresentAllSubPartsInFullDepth() {
        Collection<IPartWorker<? extends IPageWorker>> childParts = _partsAsMap.values();
        boolean isPresent = isPresentChildParts();
        for (IPartWorker<? extends IPageWorker> childPart : childParts) {
            isPresent &= childPart.isPresentAllSubPartsInFullDepth();
        }
        return isPresent;
    }

    @Override
    public boolean isPresentAllElementsInFullDepth() {
        Collection<IPartWorker<? extends IPageWorker>> childParts = _partsAsMap.values();
        boolean isPresent = true;
        for (IPartWorker<? extends IPageWorker> childPart : childParts) {
            isPresent &= childPart.isPresentAllElementsInFullDepth();
        }
        return isPresent;
    }

    @Override
    public boolean verifyLinks() {
        Collection<IPartWorker<? extends IPageWorker>> childParts = _partsAsMap.values();
        boolean isWorkLinks = true;
        for (IPartWorker<? extends IPageWorker> childPart : childParts) {
            isWorkLinks &= childPart.verifyLinks();
        }
        return isWorkLinks;
    }

    @Override
    public IContainerWorker getRootContainer() {
        return this;
    }

}
