package ru.annikonenkov.citilink.parts.footer;

import ru.annikonenkov.citilink.type.page_and_parts.PartCitilinkRegistry;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IPageWorker;

public class PartFooter<T extends IPageWorker> extends APartWorker<T, DescriptorPartFooter> {

    private final static PartCitilinkRegistry _partregistry = PartCitilinkRegistry.PART_FOOTER;

    public PartFooter(T owner) {
        super(owner, _partregistry, new DescriptorPartFooter(_partregistry.getName()));
    }
}
