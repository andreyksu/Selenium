package ru.annikonenkov.citilink.parts.content;

import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;

public class PartContentOnTrolleyPageCalculation extends APartWorker {

    public PartContentOnTrolleyPageCalculation(IContainerWorker owner, IPartRegistry partRegistry,
            DescriptionPartContentOnTrolleyPageCalculation partDescriptor) {
        super(owner, partRegistry, partDescriptor);
    }

    public static class PartFabric implements IContainerFabric<PartContentOnTrolleyPageCalculation> {

        @Override
        public PartContentOnTrolleyPageCalculation getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            IPartRegistry partRegistry = PartCitilinkRegistry.CALCULATION_CONTENT_ON_TROLLY_PAGE;

            DescriptionPartContentOnTrolleyPageCalculation descriptor = new DescriptionPartContentOnTrolleyPageCalculation(partRegistry.getName());

            String forError = String.format("В качестве ParentPart был передан  'null'! Part = '%s' не будет создан.", partRegistry);
            IContainerWorker iContWorker = sourceProvider.getParentPart().orElseThrow(() -> new CriticalException(forError));

            PartContentOnTrolleyPageCalculation partContentOnTrollyPageCalculation =
                    new PartContentOnTrolleyPageCalculation(iContWorker, partRegistry, descriptor);

            return partContentOnTrollyPageCalculation;
        }

    }

}
