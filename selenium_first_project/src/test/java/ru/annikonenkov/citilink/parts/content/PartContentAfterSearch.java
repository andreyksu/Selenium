package ru.annikonenkov.citilink.parts.content;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.annikonenkov.citilink.registry.PartCitilinkRegistry;
import ru.annikonenkov.common.descriptions.Element;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.exceptions.UnavailableParentElement;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.worker.APartWorker;
import ru.annikonenkov.common.worker.IContainerWorker;

/*
 * TODO: Нужено обратить внимение, что Part с результатом поиска может быть разным. Если товар найден один единственный.
 * Если торва в разных категориях найден. И результат что описан текущим Part, когда в одной категории но списком).
 */
public class PartContentAfterSearch extends APartWorker {

    private DescriptorPartContentAfterSearch _partDescriptor;

    private final static Logger _logger = LogManager.getLogger(PartContentAfterSearch.class);

    public PartContentAfterSearch(IContainerWorker owner, IPartRegistry partRegistry,
            DescriptorPartContentAfterSearch partDescriptor) {
        super(owner, partRegistry, partDescriptor);
        _partDescriptor = partDescriptor;
    }

    public static class PartFabric implements IContainerFabric<PartContentAfterSearch> {

        @Override
        public PartContentAfterSearch getContainer(ISourceProviderForBuildContainer sourceProvider)
                throws CriticalException {
            PartCitilinkRegistry partRegistry = PartCitilinkRegistry.PART_CONTENT_AFTER_SEARCH;
            DescriptorPartContentAfterSearch descriptor = new DescriptorPartContentAfterSearch(partRegistry.getName());
            IContainerWorker iContWorker = sourceProvider.getParentPart().orElseThrow(
                    () -> new CriticalException("В качестве ParentPart был передан  'null'! Part не будет создан."));

            PartContentAfterSearch partContentAfterSearch =
                    new PartContentAfterSearch(iContWorker, partRegistry, descriptor);

            return partContentAfterSearch;
        }
    }

    public Optional<WebElement> getTargetProductIfPresent(String str) throws UnavailableParentElement {

        var elementForPart = _partDescriptor.getElementForCurrentPart();

        List<WebElement> webElements = getSearcherAndAnalуzerElements()
                .getListOfWebElementsWithinParent(_partDescriptor.UNIT_OF_RESULT_LIST, elementForPart, 1);

        WebElement weOfCuerrentBlock = null;

        for (WebElement webElement : webElements) {
            // TODO: Может быть исключение.
            WebElement elementWithNameOfProduct = webElement.findElement(_partDescriptor.NAME_OF_PRODUCT.getBy());
            String text = elementWithNameOfProduct.getText();
            _logger.debug("Текст найденного элемента = '{}'", text);
            if (text.contains(str)) {
                weOfCuerrentBlock = webElement;
                _logger.debug("Нашли элемент у которого текст равен = '{}' и подходит под условия", text);
                break;
            } else {
                _logger.debug("Элемент не соответствует маске поиска. Текст равен = '{}'", text);
            }
        }

        return Optional.ofNullable(weOfCuerrentBlock);
    }

    /*
     * TODO: Переделать (уйти от findElement и перенести в ISearchAndAnalyzeElement)! Подумать над добавлением метода в
     * ISearchAndAnalyzeElement что будет принимать в качестве родительского элемента кроме Element и еще WebElement.
     */
    /*
     * TODO: Подумать над некоторым объектом, что будет описывать товары, что найдены в результате поиска.
     */
    /*
     * TODO: Подумать над поиском наименования, а после нахождения наименования найти соответствующего родителя.
     */
    /*
     * TODO: Подумать над тем, что если элемент уже в корзине (т.е. если элемент добавить в корзину нет) то возможно
     * элемент уже в корзине и нужно это проверить.
     */
    public Optional<WebElement> addToTrolley(String str) throws UnavailableParentElement {

        Optional<WebElement> goal = getTargetProductIfPresent(str);

        if (goal.isPresent()) {
            WebElement buttonForAddToTrolly = goal.get().findElement(_partDescriptor.BUTTON_ADD_TO_TROLLY.getBy());
            buttonForAddToTrolly.click();
        } else {
            _logger.error("Элемент/продукт не найден. Соответственно добавлен в корзину не будет");
        }
        return goal;

    }

    public Optional<WebElement> getButtonForSpecificElement(WebElement webElementOfProduct,
            Element<IContainerWorker> elem) {

        _logger.debug("Начинаем искать блок содержащий кнопку и ссылку 'В корзине X' для целевого товара");

        Function<WebDriver, List<WebElement>> function = (webDriver) -> {
            List<WebElement> listOfElements =
                    webElementOfProduct.findElements(_partDescriptor.LINK_TROLLY_IN_PRODUCT.getBy());
            if (listOfElements.size() < 1) {
                return null;
            }
            return listOfElements;
        };

        List<WebElement> resultList = getSearcherAndAnalуzerElements().getWebElementByFunctionInnerImpl(2, function);
        int size = resultList.size();
        _logger.debug("В результате поиска элемена = {}, найдено = '{}' элементов", elem, size);

        if (size < 1) {
            return Optional.empty();
        }
        return Optional.ofNullable(resultList.get(0));
    }

    public Optional<WebElement> getLinkToBasket(WebElement webElementOfProduct) {
        return getButtonForSpecificElement(webElementOfProduct, _partDescriptor.LINK_TROLLY_IN_PRODUCT);
    }

    public Optional<WebElement> getDeleteButtonFromBasket(WebElement webElementOfProduct) {
        return getButtonForSpecificElement(webElementOfProduct, _partDescriptor.BUTTON_DEL_FROM_TROLLY);
    }

}
