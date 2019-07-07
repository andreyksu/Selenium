package ru.andreyksu.annikonenkov.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by andrey on 21.05.17.
 */
public class MakerScreenShot {
    private static final Logger logger = LogManager.getLogger(MakerScreenShot.class);

    /**
     * Метод для снятия скриншота. Если снять скриншот не удалось через WebDriver, то скриншот снимается средствами Java.\\n
     * Средствами WebDriver может не сняться скриншот когда происходят зависания с браузером итд.
     *
     * @param webDriver      инстанс webDriver
     * @param nameScreenShot имя скриншота.
     */
    public static void makeScreenShot(WebDriver webDriver, String nameScreenShot) {
        try {
            File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(file, new File(ConfigProperties.getProperties("path.to.ArtifactFolder") + nameScreenShot + ".png"));
        } catch (IOException e) {
            logger.error("Через webdriver cкриншот снять не удалось.", e);
            makeScreenShot1(nameScreenShot);
        }
    }

    /**
     * Метод для снятия скриншота средствами java.
     *
     * @param nameScreenShot имя скриншота.
     */

    public static void makeScreenShot1(String nameScreenShot) {
        try {
            logger.info("Пробуем сделать через средства Java");
            Robot robot = new Robot();
            BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(screenShot, "JPG", new File(ConfigProperties.getProperties("path.to.ArtifactFolder") + nameScreenShot + ".jpg"));
            logger.info("Пробуем сделать через средства Java");
        } catch (Exception e) {
            logger.error("Не удалось снять скриншот средствами Java" ,e);

        }
    }
}
