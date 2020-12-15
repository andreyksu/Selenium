package ru.annikonenkov.citilink.parts.header;

import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;

public class PartHeaderFirstFloor extends APartWorker {

    private PartHeaderFirstFloor(IContainerWorker owner, IPartRegistry partRegistry, DescriptorPartHeaderFirstFloor partDescriptor) {
        super(owner, partRegistry, partDescriptor);
    }

    public static class PartFabric implements IContainerFabric<PartHeaderFirstFloor> {

        @Override
        public PartHeaderFirstFloor getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            PartCitilinkRegistry partRegistry = PartCitilinkRegistry.UPPER_PART_HEADER;
            DescriptorPartHeaderFirstFloor descriptor = new DescriptorPartHeaderFirstFloor(partRegistry.getName());
            return new PartHeaderFirstFloor(sourceProvider.getParentPart().get(), partRegistry, descriptor);
        }

    }
}
