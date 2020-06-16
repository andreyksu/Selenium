package ru.annikonenkov.citilink.parts.content;

import org.openqa.selenium.By;

import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.worker.IContainerWorker;

public class DescriptorPartContentAfterSearch extends APartDescriptor {

    public final Element<IContainerWorker> HEADER_OF_SEARCH = new Element<>("HEADER_OF_SEARCH", "h2", By::cssSelector, ETypeOfElement.HEADER);

    public final Element<IContainerWorker> SUBNAVIGATION_CONTAINER =
            new Element<>("SUBNAVIGATION_CONTAINER", ".subnavigation_container", By::cssSelector, ETypeOfElement.HEADER);

    public final Element<IContainerWorker> CONTAINER_FOR_RESULT = new Element<>("CONTAINER_FOR_RESULT",
            "#content .main_content_inner #subcategoryList .product_category_list", By::cssSelector, ETypeOfElement.HEADER);

    public final Element<IContainerWorker> LIST_OF_PRODUCT =
            new Element<>("LIST_OF_PRODUCT", "div.subcategory-product-item__body div.product_name span>a", By::cssSelector, ETypeOfElement.CONTAINER);

    public DescriptorPartContentAfterSearch(String partName) {
        super(new Element<IContainerWorker>(partName, ".main_content_wrapper.search", By::cssSelector, ETypeOfElement.PART));

        addElementToPart(HEADER_OF_SEARCH);

        addElementToPart(SUBNAVIGATION_CONTAINER);

        addElementToPart(CONTAINER_FOR_RESULT);
    }

}
