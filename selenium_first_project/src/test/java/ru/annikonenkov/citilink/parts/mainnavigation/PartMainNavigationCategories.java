package ru.annikonenkov.citilink.parts.mainnavigation;

import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;

public class PartMainNavigationCategories extends APartWorker {

    public PartMainNavigationCategories(IContainerWorker owner, IPartRegistry partRegistry, DescriptorPartMainNavigationCategories partDescriptor) {
        super(owner, partRegistry, partDescriptor);
    }

    public static class PartFabric implements IContainerFabric<PartMainNavigationCategories> {

        @Override
        public PartMainNavigationCategories getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            PartCitilinkRegistry partRegistry = PartCitilinkRegistry.PART_MAIN_NAVIGATION_CATEGORIES;
            DescriptorPartMainNavigationCategories descriptor = new DescriptorPartMainNavigationCategories(partRegistry.getName());
            return new PartMainNavigationCategories(sourceProvider.getParentPart().get(), partRegistry, descriptor);
        }

    }

}
