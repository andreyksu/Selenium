package ru.annikonenkov.citilink.parts.header.lmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.annikonenkov.citilink.registry.LMDCitilinkRgistry;
import ru.annikonenkov.common.exceptions.CriticalException;
import ru.annikonenkov.common.exceptions.UnavailableParentElement;
import ru.annikonenkov.common.fabric.IContainerFabric;
import ru.annikonenkov.common.registry.ILMDRegistry;
import ru.annikonenkov.common.source_provider.ISourceProviderForBuildContainer;
import ru.annikonenkov.common.source_provider.SourceProviderForBuildContainer;
import ru.annikonenkov.common.utils.ISearchAndAnalyzeElement;
import ru.annikonenkov.common.worker.ALMDWorker;
import ru.annikonenkov.common.worker.ILMDWorker;
import ru.annikonenkov.common.worker.IPartWorker;

public class LMDForLogin extends ALMDWorker {

    private final static Logger _logger = LogManager.getLogger(LMDForLogin.class);

    public LMDForLogin(ILMDRegistry lmdRegistry, ISearchAndAnalyzeElement searcher) {
        super(lmdRegistry, searcher);
    }

    public static class LMDFabric implements IContainerFabric<LMDForLogin> {

        @Override
        public LMDForLogin getContainer(ISourceProviderForBuildContainer sourceProvider) throws CriticalException {

            LMDCitilinkRgistry lmdRegistry = LMDCitilinkRgistry.LMD_FOR_LOGIN;

            ISearchAndAnalyzeElement searcher = sourceProvider.getSearcher()
                    .orElseThrow(() -> new CriticalException("В качестве ISearchAndAnalyzeElement был передан  null! LMD не будет создан"));

            IPartWorker parentPart = sourceProvider.getParentPart().get();
            LMDForLogin lmdForLogin = new LMDForLogin(lmdRegistry, searcher);

            try {
                lmdForLogin.setElementAsStartPointForThisLMD(parentPart.getElementForPart());
            } catch (UnavailableParentElement e) {
                _logger.debug("В качестве родительского элемента переден Part у которого Element содержит пустой WebElement ", e);
            }

            ISourceProviderForBuildContainer issp = new SourceProviderForBuildContainer(lmdForLogin);
            setParts(lmdForLogin, issp);

            return lmdForLogin;
        }

        private void setParts(ILMDWorker lmdForLogin, ISourceProviderForBuildContainer sourceProvider) throws CriticalException {
            PartForLMDAuthorization AUTHORIZATION_PART = new PartForLMDAuthorization.PartFabric().getContainer(sourceProvider);
            lmdForLogin.addPart(AUTHORIZATION_PART);
        }
    }

}
