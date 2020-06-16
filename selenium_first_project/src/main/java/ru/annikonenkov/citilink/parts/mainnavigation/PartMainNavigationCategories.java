package ru.annikonenkov.citilink.parts.mainnavigation;

import ru.annikonenkov.citilink.type.page_and_parts.PartCitilinkRegistry;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IPartWorker;

public class PartMainNavigationCategories<T extends IPartWorker<?>> extends APartWorker<T, DescriptorPartMainNavigationCategories> {

    private final static PartCitilinkRegistry _partregistry = PartCitilinkRegistry.PART_MAIN_NAVIGATION_CATEGORIES;

    public PartMainNavigationCategories(T owner) {
        super(owner, _partregistry, new DescriptorPartMainNavigationCategories(_partregistry.getName()));
    }

}
