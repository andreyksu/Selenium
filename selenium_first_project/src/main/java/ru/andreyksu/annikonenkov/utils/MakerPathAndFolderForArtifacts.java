package ru.andreyksu.annikonenkov.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by andrey on 11.06.2017.
 */
//TODO: нужно сделать конструктор, что будет принимать параметры из конфигурационного файла или же из параметров запуска, т.е. не только создавать уникальный кталог и но пользовтаельскй каталог.

public class MakerPathAndFolderForArtifacts {
    public static String _fullPathToFolder;
    private final static Logger logger = LogManager.getLogger(MakerPathAndFolderForArtifacts.class);

    public MakerPathAndFolderForArtifacts() {
        _fullPathToFolder = getFullPathToFolder();
    }

    /**
     * Создает каталог для артефактов, используя параметры из конфигурационного файла path.for.screenshot и delimiter.folder
     * Каталог созадется один на весь запуск.
     *
     * @return В случае успешного создания каталога возвращает путь до созданного каталога, где будут содержаться все ратефакты.
     * @throws Exception выбрасывает ислючение если не удалось создать каталог.
     */
    public String createAndGetPathToFolder() throws Exception {
        innerCreateFolderNIO(_fullPathToFolder);
        return _fullPathToFolder;
    }

    /**
     * Метод для получения уникального имени каталога артефактов.
     *
     * @return возвращает строку каталога, куда будут скалдываться сриншоты.
     */
    private String getFolderForScreen() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss_a");
        Calendar now = Calendar.getInstance();
        return formatter.format(now.getTime());
    }

    /**
     * Используя уникальное имя из метода getFolderForScreen и полученной строки из конфигурационного файла, формируем полный путь к каталогу.
     *
     * @return возвращает полнвй путь до каталога.
     */
    private String getFullPathToFolder() {
        String systemPath = ConfigProperties.getProperties("path.for.screenshot");
        StringBuilder stringBuilder = new StringBuilder(systemPath);
        stringBuilder.append(getFolderForScreen()).append(ConfigProperties.getProperties("delimiter.folder"));
        return stringBuilder.toString();
    }

    /**
     * Используя полный путь до каталога создает каталог для артефактов
     *
     * @param fullPath полный путь до каталога
     * @throws Exception выбрасываем исключение, если каталог не создался.
     */
    private void innerCreateFolder(String fullPath) throws Exception {
        File folder = new File(fullPath);
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                logger.error("Не удалось создать каталог для артефактов");
                throw new Exception("Не удалось создать каталог для артефактов");
            }
        }
    }

    /**
     * Используя полный путь до каталога создает каталог для артефактов использую NIO
     *
     * @param fullPath полный путь до каталога
     * @throws Exception выбрасываем исключение, если каталог не создался.
     */
    private static void innerCreateFolderNIO(String fullPath) throws Exception {
        Path path = Paths.get(fullPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                logger.error("Не удалось создать каталог для артефактов");
                throw new Exception("Не удалось создать каталог для артефактов", e);
            }
        }
    }


}
