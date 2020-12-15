package ru.annikonenkov.common.source_provider;

import java.util.Optional;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;
import ru.annikonenkov.common.worker.IHightLevelContainer;
import ru.annikonenkov.common.worker.IPartWorker;

/**
 * Источник данных необходимых для создания Page, Part и LMD.<br>
 * Для каждого контейнера данный объект заполняется поределенным образом.
 */
public interface ISourceProviderForBuildContainer {

    Optional<ISearchAndAnalyzeElement> getSearcher();

    /**
     * Возвращает корневой Контейнер в качестве родителя - как правило корнем является Page или LMD.<br>
     * Данный метод необходим для верхнеуровневого Part.
     */
    Optional<IHightLevelContainer> getRootParent();

    /**
     * Возвращаем родительский Part<br>
     * Необходим для дочернего Part.
     */
    Optional<IPartWorker> getParentPart();

}
