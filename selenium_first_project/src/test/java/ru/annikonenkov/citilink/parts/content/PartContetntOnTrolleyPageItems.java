package ru.annikonenkov.citilink.parts.content;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.exceptions.UnavailableParentElement;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;

public class PartContetntOnTrolleyPageItems extends APartWorker {

    private final static Logger _logger = LogManager.getLogger(PartContetntOnTrolleyPageItems.class);

    private DescriptorPartContetntOnTrolleyPageItems _partDescriptor;

    private PartContetntOnTrolleyPageItems(IContainerWorker owner, IPartRegistry partRegistry,
            DescriptorPartContetntOnTrolleyPageItems relatedPartDescriptor) {
        super(owner, partRegistry, relatedPartDescriptor);
        _partDescriptor = relatedPartDescriptor;
    }

    public static class PageFabric implements IContainerFabric<PartContetntOnTrolleyPageItems> {

        @Override
        public PartContetntOnTrolleyPageItems getContainer(ISourceProviderForBuildContainer sourceProvider)
                throws CriticalException {
            IPartRegistry partRegistry = PartCitilinkRegistry.ITEMS_CONTENT_ON_TROLLY_PAGE;

            DescriptorPartContetntOnTrolleyPageItems descriptor =
                    new DescriptorPartContetntOnTrolleyPageItems(partRegistry.getName());

            String forError = String.format("В качестве ParentPart был передан  'null'! Part = '%s' не будет создан.",
                    partRegistry);
            IContainerWorker iContWorker =
                    sourceProvider.getParentPart().orElseThrow(() -> new CriticalException(forError));

            PartContetntOnTrolleyPageItems partContetntOnTrollyPageItems =
                    new PartContetntOnTrolleyPageItems(iContWorker, partRegistry, descriptor);

            return partContetntOnTrollyPageItems;
        }

    }

    // TODO: Нахожу только первый элемент. А по хорошему вероятно возвращать List.
    public WebElement searchItemOnBasketPage(String nameOfItem) throws UnavailableParentElement {
        WebElement targetWebElement = null;

        List<WebElement> listItemsBlock = getSearcherAndAnalуzerElements().getListOfWebElementsWithinParent(
                _partDescriptor.ITEMS_BLOCK, _partDescriptor.getElementForCurrentPart(), 1);

        for (WebElement itemBlock : listItemsBlock) {
            _logger.debug("Ищем в блоке = '{}' целевой элемент", itemBlock);
            List<WebElement> elementsWithName = itemBlock.findElements(_partDescriptor.ITEMS_NAME.getBy());
            if (elementsWithName.size() < 1)
                continue;
            // Обход не нужен т.к. имя одно.
            String name = elementsWithName.get(0).getText();
            if (name.contains(nameOfItem)) {
                _logger.debug("Успех! Нашли целевой элемент с содержимым = '{}'", name);
                targetWebElement = itemBlock;
                break;
            }
        }
        return targetWebElement;
    }

    public boolean removeItemFromBasketPage(String nameOfItem) throws UnavailableParentElement {
        _logger.debug("Перед удалением, предварительно осуществим поиск товара с именем = {}", nameOfItem);
        WebElement targetElement = searchItemOnBasketPage(nameOfItem);
        if (targetElement == null) {
            _logger.warn("Элемент/товар для удаления так не не был найден! Удаление не будет произведено");
            return false;
        }

        Actions builder = new Actions(getSearcherAndAnalуzerElements().getWebDriver());
        builder.moveToElement(targetElement).perform();
        WebElement elementForRemove = targetElement.findElement(_partDescriptor.REMOVE_BUTTON.getBy());
        builder.moveToElement(elementForRemove).click().perform();

        //TODO: Для таких целей нежно добавить работу еще и через ???
        boolean res1 = getSearcherAndAnalуzerElements().verifyThatTargetElementDoesntPresent(
                _partDescriptor.ITEMS_BLOCK, _partDescriptor.getElementForCurrentPart(), 1);
        _logger.debug("-----Результат проверки отсутсвия элемента = {}", res1);

        return isPresentItemOnBasketPage(nameOfItem);
    }

    public boolean isPresentItemOnBasketPage(String nameOfItem) throws UnavailableParentElement {
        if (searchItemOnBasketPage(nameOfItem) != null)
            return true;
        return false;

    }

}
