package ru.annikonenkov.citilink.parts.content;

import org.openqa.selenium.By;

import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.worker.IContainerWorker;

public class DescriptorPartContentAfterSearch extends APartDescriptor {

    public final Element<IContainerWorker> HEADER_OF_SEARCH =
            new Element<>("HEADER_OF_SEARCH", "h2", By::cssSelector, ETypeOfElement.HEADER);

    public final Element<IContainerWorker> SUBNAVIGATION_CONTAINER = new Element<>("SUBNAVIGATION_CONTAINER",
            ".subnavigation_container", By::cssSelector, ETypeOfElement.HEADER);

    /*
     * TODO: Вот как раз, у нас получается есть UNIT_OF_RESULT_LIST которые состоят из элементов из подчастей. Можно все
     * это дело организовать на базе String, как сделано в DescriptorPartHeaderSecondFloor (div.subcategory-product-item
     * + div.subcategory-product-item__body + div.product_name span>a). А можно осуществить поиск по схеме Нашли
     * родителя > Нашли подродителя > Нашли элемент. Но проблема в том, что это нужно помнить/учитывать при написании
     * кода в Part что очень неудобно. Вероятно на базе String решение более предпочтительно. Для этого мне нужно знать
     * только что Part найден и все. Оставил здесь только для примера. В остальных местах буду делать через String
     */

    public final Element<IContainerWorker> CONTAINER_FOR_RESULT = new Element<>("CONTAINER_FOR_RESULT",
            "#content .main_content_inner #subcategoryList .product_category_list", By::cssSelector,
            ETypeOfElement.HEADER);

    // CONTAINER_FOR_RESULT > UNIT_OF_RESULT_LIST
    public final Element<IContainerWorker> UNIT_OF_RESULT_LIST = new Element<>("UNIT_OF_RESULT_LIST",
            "div.subcategory-product-item", By::cssSelector, ETypeOfElement.CONTAINER);

    // UNIT_OF_RESULT_LIST > MAIN_BLOCK
    public final Element<IContainerWorker> MAIN_BLOCK = new Element<>("MAIN_BLOCK",
            "div.subcategory-product-item__body", By::cssSelector, ETypeOfElement.CONTAINER);

    // MAIN_BLOCK > NAME_OF_PRODUCT. Хотя поиск успешен и без родителей.
    public final Element<IContainerWorker> NAME_OF_PRODUCT =
            new Element<>("LIST_OF_PRODUCT", "div.product_name span>a", By::cssSelector, ETypeOfElement.CONTAINER);

    // MAIN_BLOCK > BUTTON_ADD_TO_TROLLY. Хотя поиск успешен и без родителей.
    public final Element<IContainerWorker> BUTTON_ADD_TO_TROLLY = new Element<>("BUTTON_ADD_TO_TROLLY",
            "div.subcategory-product-item__footer div.actions div.buttons button.add_to_cart:not(.not_display)",
            By::cssSelector, ETypeOfElement.BUTTON);

    // MAIN_BLOCK > BUTTON_DEL_FROM_TROLLY Хотя поиск успешен и без родителей.
    public final Element<IContainerWorker> BUTTON_DEL_FROM_TROLLY = new Element<>("BUTTON_DEL_FROM_TROLLY",
            "div.subcategory-product-item__footer div.actions div.remove_from_cart_container:not(.not_display) span.remove_from_cart",
            By::cssSelector, ETypeOfElement.BUTTON);

    // MAIN_BLOCK > LINK_TROLLY_IN_PRODUCT Хотя поиск успешен и без родителей.
    public final Element<IContainerWorker> LINK_TROLLY_IN_PRODUCT = new Element<>("LINK_TROLLY_IN_PRODUCT",
            "div.subcategory-product-item__footer div.actions div.remove_from_cart_container:not(.not_display) a",
            By::cssSelector, ETypeOfElement.BUTTON);

    public DescriptorPartContentAfterSearch(String partName) {
        super(new Element<>(partName, ".main_content_wrapper.search", By::cssSelector, ETypeOfElement.PART));

        addElementToPart(HEADER_OF_SEARCH);

        addElementToPart(SUBNAVIGATION_CONTAINER);

        addElementToPart(CONTAINER_FOR_RESULT);
    }

}
