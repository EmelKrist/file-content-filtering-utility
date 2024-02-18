import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с аргументами командной строки.
 */
public class CommandLineArgs {

    /**
     * Список входных файлов для фильтрации.
     */
    private List<File> inputFiles = new ArrayList<>();

    /**
     * Путь сохранения файлов с результатами.
     */
    private String outputPath;

    /**
     * Префикс для наименования файлов с результатами.
     */
    private String outputPrefix;

    /**
     * Режим добавления в существующие файлы.
     */
    private boolean appendMode;

    /**
     * Режим вывода краткой статистики.
     */
    private boolean summaryStats;

    /**
     * Режим вывода полной статистики.
     */
    private boolean fullStats;

    /**
     * Метка, показывающая успешность работы с полученными аргументами.
     */
    private boolean successful;

    public CommandLineArgs(String[] commandLineArgs) {
        parseCommandLineArgs(commandLineArgs);
    }

    /**
     * Метод для парсинга аргументов командной строки.
     * Определяет все заданные флаги работы приложения, а также получает входные данные.
     *
     * @param args аргументы командной строки.
     */
    private void parseCommandLineArgs(String[] args) {
        if (args.length == 0) {
            return;
        }

        int i = 0;
        while (i < args.length) {
            switch (args[i]) {
                case "-o" -> outputPath = args[i++ + 1];
                case "-p" -> outputPrefix = args[i++ + 1];
                case "-a" -> appendMode = true;
                case "-s" -> summaryStats = true;
                case "-f" -> fullStats = true;
                default -> {
                    try {
                        String filePath = args[i];
                        String fileExtension = ParseUtils.parseToGetFileExtension(filePath);
                        // если это не поддерживаемый флаг и не файл (не имеет расширения), то аргумент некорректный
                        if (fileExtension == null) {
                            throw new IllegalArgumentException("Некорректный аргумент командной строки: " + filePath);
                        }
                        inputFiles.add(new File(filePath)); // иначе это файл
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
            i++;
        }

        try { // проверка на то, есть ли входные файлы для фильтрации
            if (inputFiles.isEmpty()) {
                throw new IllegalArgumentException("Ошибка! В аргументах запуска утилиты не найдено ни одного входного файла для фильтрации.");
            }
            successful = true;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean isSuccessful() {
        return successful;
    }

    public List<File> getInputFiles() {
        return inputFiles;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getOutputPrefix() {
        return outputPrefix;
    }

    public boolean isAppendMode() {
        return appendMode;
    }

    public boolean isSummaryStats() {
        return summaryStats;
    }

    public boolean isFullStats() {
        return fullStats;
    }
}
