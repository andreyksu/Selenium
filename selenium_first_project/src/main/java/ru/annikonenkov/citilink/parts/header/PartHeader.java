package ru.annikonenkov.citilink.parts.header;

import ru.annikonenkov.citilink.type.page_and_parts.PartCitilinkRegistry;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;

public class PartHeader<T extends IContainerWorker> extends APartWorker<T, DescriptorPartHeader> {

    private final static PartCitilinkRegistry _partregistry = PartCitilinkRegistry.PART_HEADER;

    public final PartHeaderFirstFloor<PartHeader<T>> UPPER_HEADER_PART;

    public final PartHeaderSecondFloor<PartHeader<T>> UNDER_HEADER_PART;

    public PartHeader(T owner) {
        super(owner, _partregistry, new DescriptorPartHeader(_partregistry.getName()));

        UPPER_HEADER_PART = new PartHeaderFirstFloor<>(this);
        UNDER_HEADER_PART = new PartHeaderSecondFloor<>(this);

        addSubPart(UPPER_HEADER_PART);
        addSubPart(UNDER_HEADER_PART);
    }
}
