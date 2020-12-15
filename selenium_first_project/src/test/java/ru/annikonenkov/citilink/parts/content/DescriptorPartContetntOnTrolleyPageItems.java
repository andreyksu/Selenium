package ru.annikonenkov.citilink.parts.content;

import org.openqa.selenium.By;

import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.worker.IContainerWorker;

public class DescriptorPartContetntOnTrolleyPageItems extends APartDescriptor {

    Element<IContainerWorker> HEADER = new Element<>("HEADER_ON_PAGE_TROLLY", "h1", By::cssSelector, ETypeOfElement.HEADER);

    /**
     * OrderSteps
     */

    private static final String selectorOfOrderSteps = "ul.order_steps";

    private static final String selectorOfOrederStepsItem = selectorOfOrderSteps + " " + "li";

    Element<IContainerWorker> STEP_OF_ORDER = new Element<>("STEP_OF_ORDER", selectorOfOrderSteps, By::cssSelector, ETypeOfElement.CONTAINER);

    Element<IContainerWorker> ITEMS_OF_ORDER_STEPS = new Element<>("ITEMS_OF_ORDER_STEPS", selectorOfOrederStepsItem, By::cssSelector, ETypeOfElement.TEXT);

    /**
     * Items Container - (product)
     */

    private static final String selectorTableOfItems = ".cart_list.order_page_block table";

    private static final String selectorHeadOfTable = selectorTableOfItems + " " + "thead tr";

    private static final String selectorItemsBlock = selectorTableOfItems + " " + "tbody:not([class*='product_data__gtm-js'])  tr.cart_item";

    Element<IContainerWorker> ITEMS_TABLE = new Element<>("ITEMS_TABLE", selectorTableOfItems, By::cssSelector, ETypeOfElement.CONTAINER);

    Element<IContainerWorker> HEAD_OF_TITLE = new Element<>("HEAD_OF_TITLE", selectorHeadOfTable, By::cssSelector, ETypeOfElement.CONTAINER);

    Element<IContainerWorker> ITEMS_BLOCK = new Element<>("ITEMS_BLOCK", selectorItemsBlock, By::cssSelector, ETypeOfElement.CONTAINER);

    /**
     * Элементы, что принадлежат ITEMS_BLOCK. И должны искаться относительно ITEMS_BLOCK
     */
    private static final String nameOfItem = "td.product_name a";

    Element<IContainerWorker> ITEMS_NAME = new Element<>("ITEMS_NAME", nameOfItem, By::cssSelector, ETypeOfElement.TEXT);

    private static final String removeItem = "td.remove_cell span.remove_from_cart";

    Element<IContainerWorker> REMOVE_BUTTON = new Element<>("REMOVE_BUTTON", removeItem, By::cssSelector, ETypeOfElement.BUTTON);

    public DescriptorPartContetntOnTrolleyPageItems(String partName) {
        super(new Element<>(partName, "#content .main_content_inner", By::cssSelector, ETypeOfElement.PART));
    }

}
