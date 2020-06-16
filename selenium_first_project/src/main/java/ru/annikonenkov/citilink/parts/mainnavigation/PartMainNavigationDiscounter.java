package ru.annikonenkov.citilink.parts.mainnavigation;

import ru.annikonenkov.citilink.type.page_and_parts.PartCitilinkRegistry;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IPartWorker;

public class PartMainNavigationDiscounter<T extends IPartWorker<?>> extends APartWorker<T, DescriptorPartMainNavigationDiscount> {

    private final static PartCitilinkRegistry _partregistry = PartCitilinkRegistry.PART_MAIN_NAVIGATION_DISCOUNTER;

    public PartMainNavigationDiscounter(T owner) {
        super(owner, _partregistry, new DescriptorPartMainNavigationDiscount(_partregistry.getName()));
    }

}
