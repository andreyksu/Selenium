package ru.annikonenkov.citilink.parts.content;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import ru.annikonenkov.citilink.type.page_and_parts.PartCitilinkRegistry;
import ru.annikonenkov.common.exceptions.UnavailableParentWebElement;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IPartWorker;

public class PartContentAfterSearch<T extends IPartWorker<?>> extends APartWorker<T, DescriptorPartContentAfterSearch> {

    private final static PartCitilinkRegistry _partregistry = PartCitilinkRegistry.PART_CONTENT_AFTER_SEARCH;

    private final static Logger _logger = LogManager.getLogger(PartContentAfterSearch.class);

    public PartContentAfterSearch(T owner) {
        super(owner, _partregistry, new DescriptorPartContentAfterSearch(_partregistry.getName()));
    }

    public boolean isPresentTargetElement(String str) throws UnavailableParentWebElement {
        var elementOfPart = partDescriptor.getElementForCurrentPart();
        List<WebElement> webElems = getSearcherAndAnalуzerElements().getListOfWebElementsWithinParent(partDescriptor.LIST_OF_PRODUCT, elementOfPart, 1);
        for (WebElement webElem : webElems) {
            String text = webElem.getText();
            _logger.debug("Текст найденного элемента = '{}'", text);
            if (text.contains(str)) {
                _logger.debug("Нашли элемент у которого текст равен = '{}' и подходит под условия", text);
                // return webElem;
                return true;
            } else {
                _logger.debug("Элемент не соответствует маске писка. Текст равен = '{}'", text);
            }
        }
        return false;
    }

    public boolean isPresentExpectedCount(int str) {

        return true;
    }

}
