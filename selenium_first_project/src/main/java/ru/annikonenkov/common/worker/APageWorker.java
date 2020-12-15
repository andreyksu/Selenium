package ru.annikonenkov.common.worker;

import ru.annikonenkov.common.registry.IPageRegistry;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;

/**
 * Описывает страницу.<br>
 * Страницей является все, что имеет свой конкретный адрес. И осуществляя переход на этот адрес - мы получаем данную страницу. <br>
 * Несколько по др. обстоят дела с SPA - когда у нас может быть у приложения одна лишь страница - но постоянно меняется состав Part при работе с приложением.
 * Здесь вопрос как с этим работать - обновлять каждый раз состав Part на определенном Page. Или не смотря на том, что URL один - создавать новый Page?
 */
public abstract class APageWorker extends AHightLevelContainer implements IPageWorker {

    private final String _urlOfPage;

    private IPageRegistry _pageRegistry;

    public APageWorker(IPageRegistry pageRegistry, ISearchAndAnalyzeElement searcher) {
        super(pageRegistry, searcher);
        _pageRegistry = pageRegistry;
        _urlOfPage = _pageRegistry.getURL();
    }

    @Override
    public String getURLOfPage() {
        return _urlOfPage;
    }

    @Override
    public IPageWorker openPage() {
        getWebDriver().get(_urlOfPage);
        return this;
    }

    @Override
    public IPageRegistry getRegistry() {
        return _pageRegistry;
    }
}
