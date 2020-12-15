package ru.annikonenkov.common.fabric;

import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.IContainerWorker;

public interface IContainerFabric<T extends IContainerWorker> {

    T getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException;

}
