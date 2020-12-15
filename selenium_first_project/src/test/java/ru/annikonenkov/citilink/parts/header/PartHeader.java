package ru.annikonenkov.citilink.parts.header;

import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.source_provider.SourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;

public class PartHeader extends APartWorker {

    private <O extends IContainerWorker, D extends DescriptorPartHeader> PartHeader(O owner, IPartRegistry partRegistry, D partDescriptor) {
        super(owner, partRegistry, partDescriptor);
    }

    public static class PartFabric implements IContainerFabric<PartHeader> {

        @Override
        public PartHeader getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            IPartRegistry partRegistry = PartCitilinkRegistry.PART_HEADER;
            DescriptorPartHeader descriptor = new DescriptorPartHeader(partRegistry.getName());

            String forError = String.format("В качестве ParentPage был передан  'null'! Part = '%s' не будет создан.", partRegistry);
            IContainerWorker iContWorker = sourceProvider.getRootParent().orElseThrow(() -> new CriticalException(forError));

            PartHeader partHeader = new PartHeader(iContWorker, partRegistry, descriptor);

            ISourceProviderForBuildContainer issp = new SourceProviderForBuildContainer(partHeader);

            setParts(partHeader, issp);

            return partHeader;
        }

        private void setParts(PartHeader partHeader, ISourceProviderForBuildContainer sourceProvider) throws CriticalException {

            PartHeaderFirstFloor partHeaderFirstFloor = new PartHeaderFirstFloor.PartFabric().getContainer(sourceProvider);// UPPER_HEADER_PART
            PartHeaderSecondFloor partHeaderSecondFloor = new PartHeaderSecondFloor.PartFabric().getContainer(sourceProvider);// UNDER_HEADER_PART

            partHeader.addPart(partHeaderFirstFloor);
            partHeader.addPart(partHeaderSecondFloor);

        }

    }
}
