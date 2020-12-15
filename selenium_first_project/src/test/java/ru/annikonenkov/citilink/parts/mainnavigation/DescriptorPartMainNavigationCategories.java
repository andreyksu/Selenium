package ru.annikonenkov.citilink.parts.mainnavigation;

import org.openqa.selenium.By;

import ru.annikonenkov.citilink.pages.PageMobileCatalog;
import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.worker.IContainerWorker;

public class DescriptorPartMainNavigationCategories extends APartDescriptor {

    public final Element<PageMobileCatalog> PHONES_ELEMENT =
            new Element<>("PHONES_ELEMENT", "li.menu-item_cat_phones span a", By::cssSelector, ETypeOfElement.LINK, new PageMobileCatalog.PageFabric());

    public final Element<IContainerWorker> COMPUTERS_ELEMENT =
            new Element<>("COMPUTERS_ELEMENT", "li.menu-item_cat_computer", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> TV_ELEMENT = new Element<>("TV_ELEMENT", "li.menu-item_cat_tv", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> DOMESTIC_ELEMENT =
            new Element<>("DOMESTIC_ELEMENT", "li.menu-item_cat_domestic_appliances", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> TOOLS_ELEMENT =
            new Element<>("TOOLS_ELEMENT", "li.menu-item_cat_cottage_tools", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> PHOTO_ELEMENT = new Element<>("PHOTO_ELEMENT", "li.menu-item_cat_photo", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> AUTO_ELEMENT = new Element<>("AUTO_ELEMENT", "li.menu-item_cat_auto", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> OFFICE_ELEMENT = new Element<>("OFFICE_ELEMENT", "li.menu-item_cat_office", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> BEAUTY_ELEMENT = new Element<>("BEAUTY_ELEMENT", "li.menu-item_cat_beauty", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> SPORT_ELEMENT =
            new Element<>("SPORT_ELEMENT", "li.menu-item_cat_active_sport", By::cssSelector, ETypeOfElement.LINK);

    public final Element<IContainerWorker> GAME_ELEMETN = new Element<>("GAME_ELEMETN", "li.menu-item_cat_game", By::cssSelector, ETypeOfElement.LINK);

    public DescriptorPartMainNavigationCategories(String partName) {
        super(new Element<>(partName, "menu.menu.menu_categories", By::cssSelector, ETypeOfElement.PART));

        addElementToPart(PHONES_ELEMENT);

        addElementToPart(COMPUTERS_ELEMENT);

        addElementToPart(TV_ELEMENT);

        addElementToPart(DOMESTIC_ELEMENT);

        addElementToPart(TOOLS_ELEMENT);

        addElementToPart(PHOTO_ELEMENT);

        addElementToPart(AUTO_ELEMENT);

        addElementToPart(OFFICE_ELEMENT);

        addElementToPart(BEAUTY_ELEMENT);

        addElementToPart(SPORT_ELEMENT);

        addElementToPart(GAME_ELEMETN);
    }

}
