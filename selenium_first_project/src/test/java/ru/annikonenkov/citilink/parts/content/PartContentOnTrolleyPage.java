package ru.annikonenkov.citilink.parts.content;

import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.source_provider.SourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;

/*
 * TODO: Целенаправленно делается этот Part - чтобы в Page на задумываеться о каких-то дополнительных subParts. Просто добвил этот Part и все. Опять же, набор
 * subParts может меняться исходя из условий (соответственно, здесь будет обработка этих условий) а не на стороне Page.
 */
public class PartContentOnTrolleyPage extends APartWorker {

    private PartContentOnTrolleyPage(IContainerWorker owner, IPartRegistry partRegistry, APartDescriptor relatedPartDescriptor) {
        super(owner, partRegistry, relatedPartDescriptor);
    }

    public static class PartFabric implements IContainerFabric<PartContentOnTrolleyPage> {

        @Override
        public PartContentOnTrolleyPage getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {

            IPartRegistry partRegistry = PartCitilinkRegistry.TROLLEY_CONTENT;

            DescriptorPartContentOnTrolleyPage descriptor = new DescriptorPartContentOnTrolleyPage(partRegistry.getName());

            String forError = String.format("В качестве ParentPart был передан  'null'! Part = '%s' не будет создан.", partRegistry);
            IContainerWorker iContWorker = sourceProvider.getParentPart().orElseThrow(() -> new CriticalException(forError));

            PartContentOnTrolleyPage trollyContent = new PartContentOnTrolleyPage(iContWorker, partRegistry, descriptor);

            ISourceProviderForBuildContainer sourceProviderForPart = new SourceProviderForBuildContainer(trollyContent);

            PartContentOnTrolleyPageCalculation partContentOnTrollyPageCalculation =
                    new PartContentOnTrolleyPageCalculation.PartFabric().getContainer(sourceProviderForPart);

            PartContetntOnTrolleyPageItems partContetntOnTrollyPageItems = new PartContetntOnTrolleyPageItems.PageFabric().getContainer(sourceProviderForPart);

            trollyContent.addPart(partContentOnTrollyPageCalculation);
            trollyContent.addPart(partContetntOnTrollyPageItems);

            return trollyContent;
        }

    }

}
