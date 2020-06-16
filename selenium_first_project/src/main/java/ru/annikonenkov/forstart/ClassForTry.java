package ru.annikonenkov.forstart;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.annikonenkov.pages.IPage;
import ru.annikonenkov.pages.mainpage.MainPageOfCitilink;

public class ClassForTry {

    static long durationForExplicetWait = 10;

    private static WebDriver webDriver = null;

    private static By by =
            new By.ByCssSelector("div.main-navigation menu.menu_categories li.menu-item a[data-category-id='207']");

    private By by1 = new By.ByCssSelector("div.main-navigation a[class='link_side-menu'][data-category-id='1108']");

    private static void sutUpWebDriver() {
        System.setProperty("webdriver.chrome.driver", "/opt/webDrivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        webDriver = new ChromeDriver(options);
        webDriver.manage().window().maximize();

        Navigation nav = webDriver.navigate();
    }

    public static void main(String[] args) {       

        sutUpWebDriver();
        webDriver.get("https://www.citilink.ru/");

        webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        try {

            WebDriverWait explicetWait = new WebDriverWait(webDriver, Duration.ofSeconds(durationForExplicetWait));
            Function<WebDriver, List<WebElement>> function = (webDriver) -> {
                List<WebElement> listOfElements = webDriver.findElements(by);
                System.out.println(listOfElements.size());
                System.out.println("Еще ждем");
                return listOfElements.size() > 0 ? listOfElements : null;
            };
            List<WebElement> fromSearch = explicetWait.until(function);
            if (fromSearch != null) {
                for (WebElement elem : fromSearch) {
                    System.out.println(String.format("Положение элемента = %s", elem.getLocation().toString()));
                }
            } else {
                System.out.println(String.format("Получили null"));
            }
            webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

            WebElement element = webDriver.findElement(by);
            System.out.println(element.getAttribute("class"));

            Actions action = new Actions(webDriver);
            action.moveToElement(element, 4, 4).build().perform();

            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            // Object strOfTitle = js.executeScript("return document.title");
            Object strOfTitle = js.executeScript("console.log('Привет')");
            // System.out.println(strOfTitle.getClass().getCanonicalName());
            // System.out.println(strOfTitle.toString());

            Thread thread = Thread.currentThread();
            thread.sleep(50000);

        } catch (Exception e) {
            System.out.println("!!!!!Ошибка была во время поиска");
            e.printStackTrace();
        } finally {
            webDriver.quit();

        }

    }

}
