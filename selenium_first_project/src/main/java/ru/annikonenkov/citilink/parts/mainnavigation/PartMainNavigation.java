package ru.annikonenkov.citilink.parts.mainnavigation;

import ru.annikonenkov.citilink.type.page_and_parts.PartCitilinkRegistry;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IPageWorker;

public class PartMainNavigation<T extends IPageWorker> extends APartWorker<T, DescriptorPartMainNavigation> {

    private final static PartCitilinkRegistry _partregistry = PartCitilinkRegistry.PART_MAIN_NAVIGATION;

    public final PartMainNavigationCategories<PartMainNavigation<T>> CATEGORIES_PART;

    public final PartMainNavigationDiscounter<PartMainNavigation<T>> DISCOUENTER_PART;

    public PartMainNavigation(T owner) {
        super(owner, _partregistry, new DescriptorPartMainNavigation(_partregistry.getName()));

        CATEGORIES_PART = new PartMainNavigationCategories<>(this);
        DISCOUENTER_PART = new PartMainNavigationDiscounter<>(this);

        addSubPart(CATEGORIES_PART);
        addSubPart(DISCOUENTER_PART);
    }
}
