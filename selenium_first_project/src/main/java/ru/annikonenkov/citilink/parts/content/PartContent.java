package ru.annikonenkov.citilink.parts.content;

import ru.annikonenkov.citilink.type.page_and_parts.PartCitilinkRegistry;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IPageWorker;

public class PartContent<T extends IPageWorker> extends APartWorker<T, DescriptorPartContent> {

    public final PartContentAfterSearch<PartContent<T>> PART_CONTENT_AFTER_SEARCH;

    private final static PartCitilinkRegistry _partregistry = PartCitilinkRegistry.PART_CONTENT;

    public PartContent(T owner) {
        super(owner, _partregistry, new DescriptorPartContent(_partregistry.getName()));

        PART_CONTENT_AFTER_SEARCH = new PartContentAfterSearch<>(this);
    }
}
