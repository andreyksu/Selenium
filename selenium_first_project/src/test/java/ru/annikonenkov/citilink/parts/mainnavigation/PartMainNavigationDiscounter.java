package ru.annikonenkov.citilink.parts.mainnavigation;

import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;

public class PartMainNavigationDiscounter extends APartWorker {

    public PartMainNavigationDiscounter(IContainerWorker owner, IPartRegistry partRegistry, DescriptorPartMainNavigationDiscount partDescriptor) {
        super(owner, partRegistry, partDescriptor);
    }

    public static class PartFabric implements IContainerFabric<PartMainNavigationDiscounter> {

        @Override
        public PartMainNavigationDiscounter getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            PartCitilinkRegistry partRegistry = PartCitilinkRegistry.PART_MAIN_NAVIGATION_DISCOUNTER;
            DescriptorPartMainNavigationDiscount descriptor = new DescriptorPartMainNavigationDiscount(partRegistry.getName());
            return new PartMainNavigationDiscounter(sourceProvider.getParentPart().get(), partRegistry, descriptor);
        }
    }

}
