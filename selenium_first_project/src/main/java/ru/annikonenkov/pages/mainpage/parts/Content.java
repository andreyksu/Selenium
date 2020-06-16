package ru.annikonenkov.pages.mainpage.parts;

import org.openqa.selenium.WebDriver;

import ru.annikonenkov.pages.IContainer;
import ru.annikonenkov.pages.Part;

public class Content<T extends IContainer> extends Part<T> {

    private static String _nameOfCurrentPart = "Контент";

    public Content(WebDriver driver, T owner) {
        super(driver, owner, _nameOfCurrentPart);
    }

    @Override
    public boolean isPresentCurrentPart() {
        return false;
    }

    @Override
    public boolean isPresentCurrentElements() {
        return false;
    }

}
