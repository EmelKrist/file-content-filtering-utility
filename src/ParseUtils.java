import java.math.BigInteger;

/**
 * Класс для статических вспомогательных методов на основе парсинга данных.
 */
public class ParseUtils {

    /**
     * Метод для проверки, является ли строка целым числом.
     *
     * @param line строка.
     * @return true/false.
     */
    public static boolean isInteger(String line) {
        try {
            new BigInteger(line);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Метод для проверки, является ли строка вещественным числом.
     *
     * @param line строка.
     * @return true/false.
     */
    public static boolean isFloat(String line) {
        try {
            Double.parseDouble(line);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Метод для получения расширения файла по его пути.
     *
     * @param filePath путь к файлу.
     * @return расширение файла (null, если оно отсутствует).
     */
    public static String parseToGetFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf(".");
        if (dotIndex != -1 && dotIndex < filePath.length() - 1) {
            return filePath.substring(dotIndex + 1);
        }
        return null;
    }
}
