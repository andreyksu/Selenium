package ru.annikonenkov.pages.mainpage.parts;

import org.openqa.selenium.WebDriver;

import ru.andreyksu.annikonenkov.common.utils.SearchAndAnalyzeElements;
import ru.annikonenkov.pages.IPage;
import ru.annikonenkov.pages.Part;

public class MainNavigation<T extends IPage> extends Part<T> {

    private static String _nameOfCurrentPart = "Навигационная панель";

    SearchAndAnalyzeElements searcher;

    public MainNavigation(WebDriver driver, T owner) {
        super(driver, owner, _nameOfCurrentPart);
        searcher = new SearchAndAnalyzeElements(webDriver);
        initParts();
    }

    private void initParts() {
        addSubPart("categories", new MainNavigationCategories<MainNavigation<T>>(webDriver, this));
        addSubPart("discouenter", new MainNavigationDiscounter<MainNavigation<T>>(webDriver, this));
    }

    @Override
    public boolean isPresentCurrentPart() {
        return searcher.checkWithoutWait(MainNavigationHelper.getCurrentPart(), 2);
    }

    @Override
    public boolean isPresentCurrentElements() {
        boolean result = true;
        for (MainNavigationHelper mnh : MainNavigationHelper.values()) {
            result &= searcher.checkWithoutWait(mnh.getValue(), 2);
        }
        return result;
    }


    
    
}
