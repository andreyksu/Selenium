package ru.annikonenkov.common.source_provider;

import java.util.Optional;

import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;
import ru.annikonenkov.common.worker.IHightLevelContainer;
import ru.annikonenkov.common.worker.IPartWorker;

public class SourceProviderForBuildContainer implements ISourceProviderForBuildContainer {

    private ISearchAndAnalyzeElement _searcher;

    private IHightLevelContainer _parentContainer;

    private IPartWorker _parentPart;

    public SourceProviderForBuildContainer(ISearchAndAnalyzeElement searcher) {
        _searcher = searcher;
    }

    public SourceProviderForBuildContainer(IHightLevelContainer parentContainer) {
        _parentContainer = parentContainer;
    }

    public SourceProviderForBuildContainer(IPartWorker parentPart) {
        _parentPart = parentPart;
    }

    public SourceProviderForBuildContainer(ISearchAndAnalyzeElement searcher, IHightLevelContainer parentContainer) {
        _searcher = searcher;
        _parentContainer = parentContainer;
    }

    public SourceProviderForBuildContainer(ISearchAndAnalyzeElement searcher, IPartWorker parentPart) {
        _searcher = searcher;
        _parentPart = parentPart;
    }

    public SourceProviderForBuildContainer(ISearchAndAnalyzeElement searcher, IHightLevelContainer parentContainer, IPartWorker parentPart) {
        _searcher = searcher;
        _parentContainer = parentContainer;
        _parentPart = parentPart;
    }

    @Override
    public Optional<ISearchAndAnalyzeElement> getSearcher() {
        return Optional.ofNullable(_searcher);
    }

    @Override
    public Optional<IHightLevelContainer> getRootParent() {
        return Optional.ofNullable(_parentContainer);
    }

    @Override
    public Optional<IPartWorker> getParentPart() {
        return Optional.ofNullable(_parentPart);
    }
}
