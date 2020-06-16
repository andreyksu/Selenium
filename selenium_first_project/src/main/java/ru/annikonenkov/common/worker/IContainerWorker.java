package ru.annikonenkov.common.worker;

import org.openqa.selenium.WebDriver;

import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;

/**
 * Описывает общие свойства и действия как для страницы, так и для составной части страницы.
 */
public interface IContainerWorker {

    String getName();

    /**
     * Возвращает текущее имя для для {@link IPageWorker} или {@link IPartWorker}.<br>
     * Для {@link IPartWorker} - возвращает полное имя начиная от родителя заканчивая текущим IPart.
     * 
     * @return String - возвращает имя старницы или имя составной части({@link IPartWorker})
     */
    String getFullDescription();

    /**
     * Проверяет наличие всех частей на один уровень ниже - (для {@link IPageWorker} или {@link IPartWorker}).<br>
     * Каждый парт сам знает как проверить, что он есть.<br>
     * 
     * @return true - если все составные части найдены (т.е. найдены все {@link IPartWorker}).<br>
     *         false - если не найдена хотя-бы одна составная часть (т.е. {@link IPartWorker}).
     */
    boolean isPresentChildParts();

    /**
     * Проверка ведется на всю глубину вниз.<br>
     * В Part - ведется проверка с предварительным использванием метода isPresentChildParts().
     * 
     * @return
     */

    boolean isPresentAllSubPartsInFullDepth();

    /**
     * Проверяет наличие всех описанных элементов - (для {@link IPageWorker} или {@link IPartWorker}).<br>
     * Проверка ведется на всю глубину вниз. При этом ведется проверка только по элементам добавленным в коллекцию.
     * 
     * @return true - если все элементы найдены.<br>
     *         false - если не найден хотя-бы - один элемент.
     */

    boolean isPresentAllElementsInFullDepth();

    /**
     * Проверяет, работу всех ссылок. Переходит по ссылке, ждет пока откроется страница, проверяет основные элементы на странице.<br>
     * Перед применением данного метода, необходимо вызвать метод {@link isPresentAllElementsInFullDepth()} - т.е. по сути проверить какие элементы найдены а
     * какие нет. После этого можно вызывать текущий метод.
     * 
     * @return true - если все переходы по ссылкам сработали и открылась необходимая страница.<br>
     *         false - если часть переходов не сработало.
     */
    boolean verifyLinks();

    /**
     * Возвращает Part по его имени. Имя задается при добавлении Part в Container в частности(Part, Page)<br>
     * Проверяет всегда вглубь до конца. Можно вызвать для Part - и будет так же проверены ссылки от тек. Part до конца по иерархии.
     * 
     * @param nameOfPart - имя Part.
     * @return
     */
    IPartWorker<? extends IContainerWorker> getPartByName(String nameOfPart);

    /**
     * Возвращает текущий инстанс класса предназначенного для поиска и анализа элементов.
     * 
     * @return инстанс класса, что был задан при создании Page.
     */
    ISearchAndAnalyzeElement getSearcherAndAnalуzerElements();

    /**
     * Возвращает webDriver - с которым был иницилизирован Page.
     * 
     * @return webDriver - конкрентый экземпляр драйвера.
     */
    WebDriver getWebDriver();

    /**
     * Возвращает родительский контейнер. Т.е. Page/LMD - которому принадлежит данный Part.<br>
     * Вернет Page/LMD - вне зависимости от уровня вложенности текущего Part.
     * 
     * @return - родительский Page/LMD - что содержит текущий Part.
     */
    IContainerWorker getRootContainer();
}
