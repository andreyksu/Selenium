package ru.annikonenkov.citilink.pages;

import ru.annikonenkov.citilink.parts.content.PartContent;
import ru.annikonenkov.citilink.parts.footer.PartFooter;
import ru.annikonenkov.citilink.parts.header.PartHeader;
import ru.annikonenkov.citilink.parts.mainnavigation.PartMainNavigation;
import ru.annikonenkov.citilink.registry.PageCitilinkRegistry;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.IPageRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.source_provider.SourceProviderForBuildContainer;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;
import ru.annikonenkov.common.worker.APageWorker;

public class TrolleyPage extends APageWorker {

    public TrolleyPage(IPageRegistry pageRegistry, ISearchAndAnalyzeElement searcher) {
        super(pageRegistry, searcher);
    }

    public static class PageFabric implements IContainerFabric<TrolleyPage> {

        @Override
        public TrolleyPage getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            PageCitilinkRegistry pageRegistry = PageCitilinkRegistry.TROLLEY_PAGE;

            ISearchAndAnalyzeElement searcher = sourceProvider.getSearcher()
                    .orElseThrow(() -> new CriticalException("В качестве ISearchAndAnalyzeElement был передан  null! Старница не будет создана."));

            TrolleyPage trolleyPage = new TrolleyPage(pageRegistry, searcher);

            ISourceProviderForBuildContainer sourceProviderForPart = new SourceProviderForBuildContainer(trolleyPage);

            PartHeader partHeader = new PartHeader.PartFabric().getContainer(sourceProviderForPart);
            PartMainNavigation partMainNavigation = new PartMainNavigation.PartFabric().getContainer(sourceProviderForPart);
            PartContent partContent = new PartContent.PartFabric().getContainer(sourceProviderForPart);
            PartFooter partFooter = new PartFooter.PartFabric().getContainer(sourceProviderForPart);

            trolleyPage.addPart(partHeader);
            trolleyPage.addPart(partMainNavigation);
            trolleyPage.addPart(partContent);
            trolleyPage.addPart(partFooter);

            return trolleyPage;

        }

    }

}
