package ru.annikonenkov.citilink.parts.mainnavigation;

import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.source_provider.SourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;

public class PartMainNavigation extends APartWorker {

    public PartMainNavigation(IContainerWorker owner, PartCitilinkRegistry partRegistry, DescriptorPartMainNavigation partDescriptor) throws CriticalException {
        super(owner, partRegistry, partDescriptor);
    }

    public static class PartFabric implements IContainerFabric<PartMainNavigation> {

        @Override
        public PartMainNavigation getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            PartCitilinkRegistry partRegistry = PartCitilinkRegistry.PART_MAIN_NAVIGATION;
            DescriptorPartMainNavigation descriptor = new DescriptorPartMainNavigation(partRegistry.getName());

            PartMainNavigation partMainNavigation = new PartMainNavigation(sourceProvider.getRootParent().get(), partRegistry, descriptor);

            ISourceProviderForBuildContainer issp = new SourceProviderForBuildContainer(partMainNavigation);

            PartMainNavigationCategories partMainNavigationCategories = new PartMainNavigationCategories.PartFabric().getContainer(issp);

            PartMainNavigationDiscounter partMainNavigationDiscounter = new PartMainNavigationDiscounter.PartFabric().getContainer(issp);

            partMainNavigation.addPart(partMainNavigationCategories);
            partMainNavigation.addPart(partMainNavigationDiscounter);

            return partMainNavigation;
        }

    }
}
