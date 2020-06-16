package ru.annikonenkov.citilink.parts.header.lmd;

import ru.annikonenkov.citilink.type.page_and_parts.LMDCitilinkRgistry;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;
import ru.annikonenkov.common.worker.ALMDWorker;

public class LMDForLogin extends ALMDWorker {

    private final static LMDCitilinkRgistry _lmdRegistry = LMDCitilinkRgistry.LMD_FOR_LOGIN;

    public final PartForLMDAuthorization<LMDForLogin> AUTHORIZATION_PART;

    public LMDForLogin(ISearchAndAnalyzeElement searcher) {
        super(_lmdRegistry, searcher);

        AUTHORIZATION_PART = new PartForLMDAuthorization<>(this);

        addPartToLMD(AUTHORIZATION_PART);
    }

}
