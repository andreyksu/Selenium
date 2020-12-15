package ru.annikonenkov.common.utils.visitor;

import ru.annikonenkov.common.worker.ILMDWorker;
import ru.annikonenkov.common.worker.IPageWorker;
import ru.annikonenkov.common.worker.IPartWorker;

public interface IVerifyVisitor {

    public boolean verify(IPartWorker partWorker);

    public boolean verify(ILMDWorker LMDWorker);

    public boolean verify(IPageWorker pageWorker);
}
