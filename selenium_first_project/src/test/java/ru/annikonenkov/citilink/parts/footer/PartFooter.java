package ru.annikonenkov.citilink.parts.footer;

import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;

public class PartFooter extends APartWorker {

    public PartFooter(IContainerWorker owner, IPartRegistry partRegistry, DescriptorPartFooter partDescriptor) {
        super(owner, partRegistry, partDescriptor);
    }

    public static class PartFabric implements IContainerFabric<PartFooter> {

        @Override
        public PartFooter getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            PartCitilinkRegistry partRegistry = PartCitilinkRegistry.PART_FOOTER;
            DescriptorPartFooter descriptor = new DescriptorPartFooter(partRegistry.getName());
            IContainerWorker iContWorker = sourceProvider.getRootParent()
                    .orElseThrow(() -> new CriticalException("В качестве ParentContainer был передан  null! Part не будет создан."));

            return new PartFooter(iContWorker, partRegistry, descriptor);
        }

    }
}
