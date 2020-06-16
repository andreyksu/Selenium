package ru.annikonenkov.citilink.parts.header.lmd;

import ru.annikonenkov.citilink.type.page_and_parts.LMDCitilinkRgistry;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;
import ru.annikonenkov.common.worker.ALMDWorker;

public class LMDForPersonal extends ALMDWorker {

    private final static LMDCitilinkRgistry _lmdRegistry = LMDCitilinkRgistry.LMD_FOR_PERSONAL_INFO;

    public LMDForPersonal(ISearchAndAnalyzeElement searcher) {
        super(_lmdRegistry, searcher);
    }

}
