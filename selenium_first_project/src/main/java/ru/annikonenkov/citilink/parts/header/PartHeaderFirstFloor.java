package ru.annikonenkov.citilink.parts.header;

import ru.annikonenkov.citilink.type.page_and_parts.PartCitilinkRegistry;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IPartWorker;

public class PartHeaderFirstFloor<T extends IPartWorker<?>> extends APartWorker<T, DescriptorPartHeaderFirstFloor> {

    private final static PartCitilinkRegistry _partregistry = PartCitilinkRegistry.UPPER_PART_HEADER;

    public PartHeaderFirstFloor(T owner) {
        super(owner, _partregistry, new DescriptorPartHeaderFirstFloor(_partregistry.getName()));
    }
}
