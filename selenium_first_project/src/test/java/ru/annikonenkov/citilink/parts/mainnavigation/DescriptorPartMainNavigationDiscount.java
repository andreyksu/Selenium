package ru.annikonenkov.citilink.parts.mainnavigation;

import org.openqa.selenium.By;

import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.worker.IContainerWorker;

public class DescriptorPartMainNavigationDiscount extends APartDescriptor {

    public final Element<IContainerWorker> SERVICES_ELEMENT =
            new Element<>("SERVICES_ELEMENT", ".//span/span[contains(., 'Сервисы и услуги')]", By::xpath, ETypeOfElement.LINK);

    public final Element<IContainerWorker> COMMODITY_ELEMETN =
            new Element<>("COMMODITY_ELEMETN", ".//span/span[contains(., 'Уцененные товары')]", By::xpath, ETypeOfElement.LINK);

    public final Element<IContainerWorker> SERTIFICAT_ELEMENT =
            new Element<>("SERTIFICAT_ELEMENT", ".//span/span[contains(., 'Подарочные сертификаты')]", By::xpath, ETypeOfElement.LINK);

    public DescriptorPartMainNavigationDiscount(String partName) {
        super(new Element<>(partName, "menu.menu.menu_discount", By::cssSelector, ETypeOfElement.PART));

        addElementToPart(SERVICES_ELEMENT);
        addElementToPart(COMMODITY_ELEMETN);
        addElementToPart(SERTIFICAT_ELEMENT);
    }

}
