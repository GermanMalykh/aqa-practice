package utils;

import java.time.YearMonth;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Dates {

    private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

    private static final List<String> YEARS = IntStream.rangeClosed(1900, 2100)
            .mapToObj(String::valueOf)
            .toList();

    /** Случайный год в виде строки */
    public static String randomYear() {
        int randomIndex = ThreadLocalRandom.current().nextInt(YEARS.size());
        return YEARS.get(randomIndex);
    }

    /** Случайный месяц */
    public static String randomMonth() {
        return MONTHS[ThreadLocalRandom.current().nextInt(MONTHS.length)];
    }

    /** Случайный день месяца с учётом високосности */
    public static String randomDayOfMonth(String yearString, String monthName) {
        int year = Integer.parseInt(yearString);
        int month = getMonthNumber(monthName);

        int maxDay = YearMonth.of(year, month).lengthOfMonth();
        int day = ThreadLocalRandom.current().nextInt(1, maxDay + 1);
        return String.valueOf(day);
    }

    /** Получить номер месяца по названию */
    private static int getMonthNumber(String monthName) {
        for (int i = 0; i < MONTHS.length; i++) {
            if (MONTHS[i].equals(monthName)) {
                return i + 1; // Месяцы в Java начинаются с 1
            }
        }
        return 1; // По умолчанию январь
    }
}
