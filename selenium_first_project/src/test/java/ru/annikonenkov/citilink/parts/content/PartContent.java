package ru.annikonenkov.citilink.parts.content;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.annikonenkov.citilink.registry.PageCitilinkRegistry;
import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.registry.IRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.source_provider.SourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;
import ru.annikonenkov.common.worker.IPartWorker;

public class PartContent extends APartWorker {

    private final static Logger _logger = LogManager.getLogger(PartContent.class);

    public PartContent(IContainerWorker owner, IPartRegistry partRegistry, DescriptorPartContent partDescriptor) {
        super(owner, partRegistry, partDescriptor);
    }

    public static class PartFabric implements IContainerFabric<PartContent> {

        @Override
        public PartContent getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {

            IPartRegistry partRegistry = PartCitilinkRegistry.PART_CONTENT;
            DescriptorPartContent descriptor = new DescriptorPartContent(partRegistry.getName());

            String forError = String.format("В качестве ParentPage был передан  'null'! Part = '%s' не будет создан.", partRegistry);
            IContainerWorker iContWorker = sourceProvider.getRootParent().orElseThrow(() -> new CriticalException(forError));

            PartContent partContent = new PartContent(iContWorker, partRegistry, descriptor);

            ISourceProviderForBuildContainer issp = new SourceProviderForBuildContainer(partContent);

            setParts(partContent, issp);

            return partContent;
        }

        /**
         * Исходя из корневого контейнера Page/LMD выбирает какие дочерние Part будут добавлены.
         * <p>
         * Есть два подхода. Делать настроку в каждом конкретном Part - т.е. как здесь. Либо выносить набор/формирование Part в каждый Page.<br>
         * 1) В первом случае при каждом новом Page - нужно добавлять/править логику в существующих Part.<br>
         * 2) Во втором случае, буду иметь дублирование (в каждом Page). Дублирование настроек.
         * 
         * @param partWorker
         * @param sourceProvider
         * @throws CriticalException
         */
        private void setParts(IPartWorker partWorker, ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            IContainerWorker containerWorker = partWorker.getOwner();
            IRegistry registry = containerWorker.getRegistry();
            String name = registry.getName();

            PageCitilinkRegistry registryOfRootContainer = PageCitilinkRegistry.valueOf(name);

            switch (registryOfRootContainer) {
            case MAIN_PAGE_OF_CITILINK:
            case PAGE_MOBILE_CATALOG:
                _logger.debug("Будет добавлен Part = 'PAGE_MOBILE_CATALOG/MAIN_PAGE_OF_CITILINK'");
                PartContentOnMainPage mainContent = new PartContentOnMainPage.PartFabric().getContainer(sourceProvider);
                partWorker.addPart(mainContent);
                break;
            case MAIN_PAGE_OF_CITILINK_AFTER_SEARCH:
                _logger.debug("Будет добавлен Part = 'MAIN_PAGE_OF_CITILINK_AFTER_SEARCH'");
                PartContentAfterSearch partContentAfterSearch = new PartContentAfterSearch.PartFabric().getContainer(sourceProvider);
                partWorker.addPart(partContentAfterSearch);
                break;
            case TROLLEY_PAGE:
                _logger.debug("Будет добавлен Part = 'TROLLEY_PAGE'");
                PartContentOnTrolleyPage trolleyContent = new PartContentOnTrolleyPage.PartFabric().getContainer(sourceProvider);
                partWorker.addPart(trolleyContent);
            default:
                _logger.debug("Переданый корневой контейнер = '{}' не известен! Текущий парт ='{}' будет пустым (т.е. без дочерних Part)",
                        registryOfRootContainer.getName(), partWorker.getName());
                break;
            }
        }
    }
}
