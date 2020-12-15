package ru.annikonenkov.common.registry;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import ru.annikonenkov.common.worker.IPartWorker;

@Retention(RUNTIME)
@Target(FIELD)
public @interface TypeInfo {

    Class<? extends IPartWorker> value();
}
