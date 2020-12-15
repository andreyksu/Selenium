package ru.annikonenkov.citilink.parts.content;

import org.openqa.selenium.By;

import ru.annikonenkov.common.descriptions.APartDescriptor;
import ru.annikonenkov.common.descriptions.ETypeOfElement;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.worker.IContainerWorker;

public class DescriptionPartContentOnTrolleyPageCalculation extends APartDescriptor {

    /**
     * SUMM_BLOCK
     */
    private final String selectorForSummBlock = "process.process_block .payment_block";

    private final String selectorForSumm = selectorForSummBlock + " " + "ins.num.order_amount_field";

    Element<IContainerWorker> SUMM_BLOCK = new Element<>("SUMM_BLOCK", selectorForSummBlock, By::cssSelector, ETypeOfElement.CONTAINER);

    Element<IContainerWorker> SUMM = new Element<>("SUMM", selectorForSumm, By::cssSelector, ETypeOfElement.TEXT);

    /**
     * PROCESS_BLOCK
     */
    private final String selectotrForProcessBlock = "div.process.process_block form";

    private final String selectorForButtonNextBasketStep = selectotrForProcessBlock + " " + "button.ext-basket-step-button__js.process_order";

    Element<IContainerWorker> PROCESS_BLOCK = new Element<>("PROCESS_BLOCK", selectotrForProcessBlock, By::cssSelector, ETypeOfElement.CONTAINER);

    Element<IContainerWorker> BUTTON_NEXT_BASKET_STEP =
            new Element<>("BUTTON_NEXT_BASKET_STEP", selectorForButtonNextBasketStep, By::cssSelector, ETypeOfElement.BUTTON);

    public DescriptionPartContentOnTrolleyPageCalculation(String partName) {

        super(new Element<>(partName, "aside.order_page_block .aside_inner.cart_details", By::cssSelector, ETypeOfElement.PART));

        addElementToPart(SUMM_BLOCK);

        addElementToPart(PROCESS_BLOCK);

    }

}
