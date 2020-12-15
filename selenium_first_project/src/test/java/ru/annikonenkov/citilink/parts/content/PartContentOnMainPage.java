package ru.annikonenkov.citilink.parts.content;

import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;

public class PartContentOnMainPage extends APartWorker {

    private PartContentOnMainPage(IContainerWorker owner, IPartRegistry partRegistry, DescriptorPartContentOnMainPage relatedPartDescriptor) {
        super(owner, partRegistry, relatedPartDescriptor);
    }

    public static class PartFabric implements IContainerFabric<PartContentOnMainPage> {

        @Override
        public PartContentOnMainPage getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            IPartRegistry partRegistry = PartCitilinkRegistry.PART_CONTENT_AFTER_START_PAGE;
            DescriptorPartContentOnMainPage descriptor = new DescriptorPartContentOnMainPage(partRegistry.getName());

            IContainerWorker iContWorker =
                    sourceProvider.getParentPart().orElseThrow(() -> new CriticalException("В качестве ParentPart был передан  'null'! Part не будет создан."));

            PartContentOnMainPage mainContent = new PartContentOnMainPage(iContWorker, partRegistry, descriptor);

            return mainContent;
        }
        // TODO: Этот Part так же будет состоять из множества вложенных Part (там много сложных Part). Т.е. по аналогии с PartContent.
    }
}
