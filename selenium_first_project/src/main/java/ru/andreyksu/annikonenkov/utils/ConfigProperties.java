package ru.andreyksu.annikonenkov.utils;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Created by andrey on 18.05.17.
 */
public class ConfigProperties {
    private static Properties PROPERTIES;
    private final static Logger logger = LogManager.getLogger(ConfigProperties.class);

    //TODO: нкжно продумать в части статик инициализации. Вероятно не очено хорошая реализация
    static {
        String confFile = "config.properties";
        PROPERTIES = new Properties();
        URL props = ClassLoader.getSystemResource(confFile);
        try {
            PROPERTIES.load(props.openStream());
        } catch (IOException e) {
            logger.error("Ошибка при загрузке файл конфигурации", e);
        }
    }

    /**
     * Метод для получения значения по ключу
     *
     * @param key ключ для которого будет получено значение.
     * @return возвращает значение ключа
     */
    public static String getProperties(String key) {
        return PROPERTIES.getProperty(key);
    }

    /**
     * Метод для установке значения по ключу
     *
     * @param key   ключ для которого будет установлено значение
     * @param value значение ключа
     */
    public static void setProperies(String key, String value) {
        PROPERTIES.setProperty(key, value);
    }

    /**
     * Добавил метод но еще не использовал. Нужно тестировать данный метод. Сделал его как private. Как проверю, сделаю его public
     *
     * @param key
     * @return
     * @throws IOException
     */
    private String getProperties1(String key) throws IOException {
        String confFile = "config.properties";
        String value = null;
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            inputStream = getClass().getClassLoader().getResourceAsStream(confFile);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + confFile + "' not found in the classpath");
            }

            Date time = new Date(System.currentTimeMillis());

            value = prop.getProperty(key);

        } catch (Exception e) {
            logger.error("Ошибка при загрузке файл конфигурации", e);
        } finally {
            inputStream.close();
        }
        return value;
    }

    /**
     * Добавил метод но еще не использовал. Нужно тестировать данный метод. Сделал его как private. Как проверю, сделаю его public
     *
     * @param key
     * @return
     * @throws IOException
     */
    private static String getProperties2(String key) throws IOException {
        String confFile = "config.properties";
        String value = null;
        FileInputStream fileInput = null;
        File file = null;
        try {
            file = new File(confFile);
            fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);

            value = properties.getProperty(key);

//			Enumeration enuKeys = properties.keys();
//			while (enuKeys.hasMoreElements()) {
//				String key = (String) enuKeys.nextElement();
//				String value = properties.getProperty(key);
//				System.out.println(key + ": " + value);
//			}
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        } finally {
            fileInput.close();
        }
        return value;
    }
}
