import java.io.*;
import java.util.List;

/**
 * Класс утилиты для фильтрации содержимого файлов.
 */
public class FileContentFilteringUtility {

    /**
     * Объект для записи в файл с целыми числами.
     */
    private FileWriter intWriter;

    /**
     * Объект для записи в файл с вещ. числами.
     */
    private FileWriter floatWriter;

    /**
     * Объект для записи в файл со строками.
     */
    private FileWriter stringWriter;


    /**
     * Мето для реализации процесса фильтрации.
     *
     * @param cmdArgs аргументы командной строки.
     */
    public void run(String[] cmdArgs) {
        CommandLineArgs commandLineArgs = new CommandLineArgs(cmdArgs);
        // если аргументы не обработаны успешно
        if (!commandLineArgs.isSuccessful()) {
            System.err.println(Messages.utilityLaunchCommandMessage + ", где \n" + Messages.argsInfoMessage);
            return;
        }

        // инициализируем переменные аргументов командной строки
        List<File> inputFiles = commandLineArgs.getInputFiles();
        String outputPath = commandLineArgs.getOutputPath();
        String outputPrefix = commandLineArgs.getOutputPrefix();
        boolean isAppendMode = commandLineArgs.isAppendMode();
        boolean isSummaryStats = commandLineArgs.isSummaryStats();
        boolean isFullStats = commandLineArgs.isFullStats();
        // кол-во файлов, которые существуют и доступны для чтения
        int existFilesCount = inputFiles.size();
        // объект для ведения статистики
        Statistics statistics = new Statistics(isFullStats);

        // читаем каждый файл
        for (File inputFile : inputFiles) {
            String fileName = inputFile.getName();
            String line;
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                while ((line = reader.readLine()) != null) {
                    if (line.isBlank()) continue;
                    // фильтруем полученную строку по типу и обновляем статистику, если требуется
                    filterLine(line, outputPath, outputPrefix, isAppendMode);
                    if (isSummaryStats || isFullStats) {
                        statistics.updateStatistics(line);
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("Файл " + fileName + " не найден!");
                // если файл не прочитан, то уменьшаем их счетчик
                existFilesCount--;
            } catch (IOException e) {
                System.err.println("Ошибка чтения/записи файла " + fileName + ": " + e.getMessage());
                existFilesCount--;
            }
        }

        try {
            // закрываем потоки, если нужно
            if (intWriter != null) intWriter.close();
            if (floatWriter != null) floatWriter.close();
            if (stringWriter != null) stringWriter.close();
        } catch (IOException e) {
            System.err.println("Произошла ошибка закрытия потоков записи: " + e.getMessage());
        }

        // если сколько-то файлов прочитано, опционально выводим статистику
        if (existFilesCount > 0) {
            if (isFullStats) {
                System.out.println(statistics.getFullStats());
                return;
            }

            if (isSummaryStats) {
                System.out.println(statistics.getSummaryStats());
            }
        } else {
            System.err.println("Фильтрация невозможна, так как ни одного файла не было прочитано корректно.");
        }
    }


    /**
     * Метод для фильтрации строки по типу.
     * Записывает в файл данные, для хранения типа которых он предназначен.
     *
     * @param line         строка с данными.
     * @param outputPath   путь файла с результатами.
     * @param outputPrefix префикс названия файла с результатами.
     * @param isAppendMode режим добавления в сущ. файлы.
     * @throws IOException выбрасывает исключения в случае ошибки записи в файл.
     */
    private void filterLine(String line, String outputPath, String outputPrefix, boolean isAppendMode) throws IOException {
        if (outputPath == null) outputPath = "";
        if (outputPrefix == null) outputPrefix = "";

        if (ParseUtils.isInteger(line)) {
            // если выходной файл еще не сущ., создаем его
            if (intWriter == null) {
                intWriter = new FileWriter(outputPath + outputPrefix + "int.txt", isAppendMode);
            }
            intWriter.write(line + "\n");
            intWriter.flush(); // сохр. изменения
        } else if (ParseUtils.isFloat(line)) {
            if (floatWriter == null) {
                floatWriter = new FileWriter(outputPath + outputPrefix + "float.txt", isAppendMode);
            }
            floatWriter.write(line + "\n");
            floatWriter.flush();
        } else {
            if (stringWriter == null) {
                stringWriter = new FileWriter(outputPath + outputPrefix + "string.txt", isAppendMode);
            }
            stringWriter.write(line + "\n");
            stringWriter.flush();
        }
    }
}
