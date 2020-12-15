package ru.annikonenkov.citilink.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class MainPageOfCitilink extends APageWorker {

    private final static Logger _logger = LogManager.getLogger(MainPageOfCitilink.class);

    public MainPageOfCitilink(IPageRegistry pageRegistry, ISearchAndAnalyzeElement searcher) {
        super(pageRegistry, searcher);
    }

    public static class PageFabric implements IContainerFabric<MainPageOfCitilink> {

        @Override
        public MainPageOfCitilink getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            PageCitilinkRegistry pageRegistry = PageCitilinkRegistry.MAIN_PAGE_OF_CITILINK;

            ISearchAndAnalyzeElement searcher = sourceProvider.getSearcher()
                    .orElseThrow(() -> new CriticalException("В качестве ISearchAndAnalyzeElement был передан  null! Старница не будет создана."));

            MainPageOfCitilink mainPageOfCitilink = new MainPageOfCitilink(pageRegistry, searcher);

            ISourceProviderForBuildContainer sourceProviderForPart = new SourceProviderForBuildContainer(mainPageOfCitilink);

            PartHeader partHeader = new PartHeader.PartFabric().getContainer(sourceProviderForPart);
            PartMainNavigation partMainNavigation = new PartMainNavigation.PartFabric().getContainer(sourceProviderForPart);
            PartContent partContent = new PartContent.PartFabric().getContainer(sourceProviderForPart);
            PartFooter partFooter = new PartFooter.PartFabric().getContainer(sourceProviderForPart);

            mainPageOfCitilink.addPart(partHeader);
            mainPageOfCitilink.addPart(partMainNavigation);
            mainPageOfCitilink.addPart(partContent);
            mainPageOfCitilink.addPart(partFooter);

            return mainPageOfCitilink;
        }

    }

}
