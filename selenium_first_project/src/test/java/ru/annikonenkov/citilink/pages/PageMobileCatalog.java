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

public class PageMobileCatalog extends APageWorker {

    public PageMobileCatalog(IPageRegistry pageRegistry, ISearchAndAnalyzeElement searcher) throws CriticalException {
        super(pageRegistry, searcher);
    }

    public static class PageFabric implements IContainerFabric<PageMobileCatalog> {

        @Override
        public PageMobileCatalog getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            PageCitilinkRegistry pageRegistry = PageCitilinkRegistry.PAGE_MOBILE_CATALOG;

            ISearchAndAnalyzeElement searcher = sourceProvider.getSearcher()
                    .orElseThrow(() -> new CriticalException("В качестве ISearchAndAnalyzeElement был передан  null! Старница не будет создана."));

            PageMobileCatalog pageMobileCatalog = new PageMobileCatalog(pageRegistry, searcher);

            ISourceProviderForBuildContainer issp = new SourceProviderForBuildContainer(pageMobileCatalog);

            PartHeader partHeader = new PartHeader.PartFabric().getContainer(issp);
            PartMainNavigation partMainNavigation = new PartMainNavigation.PartFabric().getContainer(issp);
            PartContent partContent = new PartContent.PartFabric().getContainer(issp);
            PartFooter partFooter = new PartFooter.PartFabric().getContainer(issp);

            pageMobileCatalog.addPart(partHeader);
            pageMobileCatalog.addPart(partMainNavigation);
            pageMobileCatalog.addPart(partContent);
            pageMobileCatalog.addPart(partFooter);

            return pageMobileCatalog;
        }

    }

}
