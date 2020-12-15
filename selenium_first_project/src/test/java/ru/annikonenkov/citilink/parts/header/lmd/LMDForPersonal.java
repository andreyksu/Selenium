package ru.annikonenkov.citilink.parts.header.lmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.annikonenkov.citilink.registry.LMDCitilinkRgistry;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.exceptions.UnavailableParentElement;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.ILMDRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;
import ru.annikonenkov.common.worker.ALMDWorker;
import ru.annikonenkov.common.worker.IPartWorker;

public class LMDForPersonal extends ALMDWorker {

    private final static Logger _logger = LogManager.getLogger(LMDForPersonal.class);

    public LMDForPersonal(ILMDRegistry lmdRegistry, ISearchAndAnalyzeElement searcher) {
        super(lmdRegistry, searcher);
    }

    public static class LMDFabric implements IContainerFabric<LMDForPersonal> {

        @Override
        public LMDForPersonal getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            ISearchAndAnalyzeElement searcher = sourceProvider.getSearcher()
                    .orElseThrow(() -> new CriticalException("В качестве ISearchAndAnalyzeElement был передан  null! LMD не будет создан"));

            IPartWorker parentPart = sourceProvider.getParentPart().get();
            LMDForPersonal lmdForLogin = new LMDForPersonal(LMDCitilinkRgistry.LMD_FOR_PERSONAL_INFO, searcher);

            try {
                lmdForLogin.setElementAsStartPointForThisLMD(parentPart.getElementForPart());
            } catch (UnavailableParentElement e) {
                _logger.debug("В качестве родительского элемента переден Part у которого Element содержит пустой WebElement ", e);
            }
            return lmdForLogin;
        }
    }

}
