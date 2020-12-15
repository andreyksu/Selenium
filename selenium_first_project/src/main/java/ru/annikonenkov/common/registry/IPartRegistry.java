package ru.annikonenkov.common.registry;

import java.util.Optional;

public interface IPartRegistry extends IRegistry {

    public Optional<IPartRegistry> getParentPartRegistry();

    public boolean hasParentPart();

}
