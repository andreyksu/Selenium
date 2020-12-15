package ru.annikonenkov.common.worker;

import java.util.List;

import org.openqa.selenium.WebDriver;

import ru.annikonenkov.common.registry.IPartRegistry;
import ru.annikonenkov.common.registry.IRegistry;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;
import ru.annikonenkov.common.utils.visitor.IVerifyVisitor;

/**
 * Описывает общие свойства и действия как для страницы, так и для составной части страницы.
 */
public interface IContainerWorker {

    /**
     * Возвращает имя, что задано через {@link IRegistry}
     * 
     * @return
     */
    String getName();

    /**
     * Возвращает описание.<br>
     * Для {@link IPartWorker} - возвращает полное имя начиная от родителя заканчивая текущим IPart.
     * 
     * @return String - возвращает описание.
     */
    String getFullDescription();

    /**
     * Проверяет наличие всех частей на один уровень ниже - (для {@link IPageWorker} или {@link IPartWorker}).<br>
     * Каждый Part должен сам знать как проверить, что он есть(т.е. присутствет).<br>
     * Part описывается Element_ом, что имеет свой путь (CSS или XPath). Т.е. проверка Part означает, его поиск по CSS или XPath.<br>
     * 
     * @return true - если все составные Part (т.е. найдены все {@link IPartWorker}).<br>
     *         false - если не найден хотя-бы один Part (т.е. {@link IPartWorker}).
     */
    boolean isPresentChildParts();

    /**
     * Проверка ведется на всю глубину вниз.<br>
     * В Part - ведется проверка с предварительным использванием метода isPresentChildParts().<br>
     * После выполнения isPresentChildParts() - мы уже знаем какие Part не найдены и вглубь них проверка не будет выполняться.
     * 
     * @return
     */

    boolean isPresentAllSubPartsInFullDepth();

    /**
     * Проверяет наличие всех описанных элементов - (для {@link IPageWorker} или {@link IPartWorker}).<br>
     * Проверка ведется на всю глубину вниз. При этом ведется проверка только по элементам добавленным в коллекцию в рамках класса {@link APartDescriptor}<br>
     * Part описывается элементом что имеет свой путь (Css или XPath). По этой причине Part тоже нужно проверить.<br>
     * 
     * @return true - если все элементы найдены.<br>
     *         false - если не найден хотя-бы - один элемент.
     */

    boolean isPresentAllElementsInFullDepth();

    /**
     * Выполняет проверку заданную в IVerifyVisitor.<br>
     * Перед применением данного метода, необходимо вызвать метод {@link isPresentAllElementsInFullDepth()} - т.е. по сути проинициализировав страницу. После
     * этого можно вызывать текущий метод.
     * 
     * @return true - если проверка заложенная в IVerifyVisitor успешна.<br>
     *         false - если проверка заложенная в IVerifyVisitor неудачна.
     */
    boolean verifyByVisitor(IVerifyVisitor visitor);

    // TODO: Перейти либо на возврат Optional либо при отсутствии генерировать исключение.
    /**
     * Возвращает Part по его PartRegistry.<br>
     * Проверяет всегда вглубь до конца. Можно вызвать для Part - и будет так же проверены ссылки от тек. Part до конца по иерархии.
     * 
     * @param nameOfPart - имя Part.
     * @return
     */
    IPartWorker getPart(IPartRegistry partRegistry);

    /**
     * Возвращает Part при его наличии в Page или его вложенных Part.
     * 
     * @param <T>
     * @param clazz
     * @return
     */
    <T> List<T> getPartByClass(Class<T> clazz);

    /**
     * Добавлеяет Part к текущему контейнеру
     */
    void addPart(IPartWorker partWorker);

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

    /**
     * Возвращает, Registry - что содержит описание текущего контейнера.
     * 
     * @return
     */
    IRegistry getRegistry();
}
