package data;

import java.util.concurrent.ThreadLocalRandom;

public enum Hobbies {
    SPORTS("Sports"),
    READING("Reading"),
    MUSIC("Music");

    private final String value;

    Hobbies(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Возвращает случайное название хобби
     */
    public static String randomHobbies() {
        Hobbies[] hobbies = values();
        int randomIndex = ThreadLocalRandom.current().nextInt(hobbies.length);
        return hobbies[randomIndex].getValue();
    }
}
