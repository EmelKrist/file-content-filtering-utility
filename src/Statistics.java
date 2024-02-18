import java.math.BigInteger;

/**
 * Класс для работы со статистикой.
 */
public class Statistics {

    /**
     * Количество целых чисел.
     */
    private int intCount;

    /**
     * Макс. целое число.
     */
    private BigInteger intMax;

    /**
     * Мин. целое число.
     */
    private BigInteger intMin;

    /**
     * Сумма целых чисел.
     */
    private BigInteger intSum;

    /**
     * Количество вещественных чисел.
     */
    private int floatCount;

    /**
     * Макс. вещественное число.
     */
    private double floatMax = Double.MIN_VALUE;

    /**
     * Мин. вещественное число.
     */
    private double floatMin = Double.MAX_VALUE;

    /**
     * Сумма вещественных чисел.
     */
    private double floatSum;

    /**
     * Количество строк.
     */
    private int stringCount;

    /**
     * Макс. длина строки.
     */
    private int stringMaxLength = Integer.MIN_VALUE;

    /**
     * Мин. длина строки.
     */
    private int stringMinLength = Integer.MAX_VALUE;

    /**
     * Метка, показывающая, является ли данная статистика полной.
     */
    private final boolean isFullStats;

    public Statistics(boolean isFullStats) {
        this.isFullStats = isFullStats;
    }


    /**
     * Метод для обновления статистики в соответствии с прочитанными данными.
     *
     * @param line прочитанная строка с типом данных.
     */
    public void updateStatistics(String line) {
        if (ParseUtils.isInteger(line)) {
            intCount++;
            if (isFullStats) {
                // используем BigInteger, чтобы корректно работать с целыми числами любого размера
                BigInteger bigValue = new BigInteger(line);
                if (intMax == null || bigValue.compareTo(intMax) > 0) {
                    intMax = bigValue;
                }

                if (intMin == null || bigValue.compareTo(intMin) < 0) {
                    intMin = bigValue;
                }

                if (intSum == null) intSum = new BigInteger("0");
                intSum = intSum.add(bigValue);
            }
        } else if (ParseUtils.isFloat(line)) {
            floatCount++;
            if (isFullStats) {
                // используем double, чтобы корректно работать с вещ. числами любого размера
                double floatValue = Double.parseDouble(line);
                floatMax = Math.max(floatMax, floatValue);
                floatMin = Math.min(floatMin, floatValue);
                floatSum += floatValue;
            }
        } else {
            stringCount++;
            if (isFullStats) {
                int lineLength = line.length();
                stringMaxLength = Math.max(stringMaxLength, lineLength);
                stringMinLength = Math.min(stringMinLength, lineLength);
            }
        }
    }

    /**
     * Метод для получения полной статистики.
     *
     * @return строка с полной статистикой.
     */
    public String getFullStats() {
        StringBuilder sb = new StringBuilder("\nПолная статистика: \n");

        sb.append("\nЦелые числа \n").append("Кол-во: ").append(intCount).append("\n");
        if (intCount > 0) {
            sb.append("Макс.: ").append(intMax).append("\n")
                    .append("Мин.: ").append(intMin).append("\n")
                    .append("Сумма: ").append(intSum).append("\n");
        }

        sb.append("\nВещественные числа \n").append("Кол-во: ").append(floatCount).append("\n");
        if (floatCount > 0) {
            sb.append("Макс.: ").append(floatMax).append("\n")
                    .append("Мин.: ").append(floatMin).append("\n")
                    .append("Сумма: ").append(floatSum).append("\n");
        }

        sb.append("\nСтроки \n").append("Кол-во: ").append(stringCount).append("\n");
        if (stringCount > 0) {
            sb.append("Макс. длина: ").append(stringMaxLength).append("\n")
                    .append("Мин. длина: ").append(stringMinLength).append("\n");
        }

        return sb.toString();
    }

    /**
     * Метод ля получения краткой статистики.
     *
     * @return строка с краткой статистикой.
     */
    public String getSummaryStats() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nКраткая статистика: \n")
                .append("Кол-во целых чисел: ").append(intCount).append("\n")
                .append("Кол-во вещественных чисел: ").append(floatCount).append("\n")
                .append("Кол-во строк: ").append(stringCount).append("\n");

        return sb.toString();
    }

}
