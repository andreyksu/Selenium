package ru.andreyksu.annikonenkov.webdrivers.tests.citilinktests.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by andrey on 26.05.17.
 */
public class MPageHelper {
    WebDriver webDriver;

    public By getLogoElementOfPage() {
        return By.cssSelector("a.logo__inner_nonunderline>img[alt='Ситилинк']");
    }

    public By getSearchContainer() {
        return By.cssSelector("div.search_header");
    }

    public By getSearchField() {
        return By.cssSelector("input.search_input");
    }

    public By getSearchButton() {
        return By.cssSelector("button.search_enter_icon");
    }

    public By getContainerOfResultSearch(){
        return  By.cssSelector("div.main_content_inner div.product_category_list");
    }

    public By getItemOfResult(){
        return  By.cssSelector("div[data-list-id='main']");
    }


    @FindBy(css = "div.search_wrapper>input.search_input")
    public WebElement serchFieldInMPage;


    public MPageHelper(WebDriver webDriver) {
        this.webDriver = webDriver;
        //TODO плохой подход предавать this в конструкторе. Т.к. на данный момент объект может быть в невалидном сост. Т.е. как вариант сконструирован не до конца.
        PageFactory.initElements(webDriver, this);
    }
}
