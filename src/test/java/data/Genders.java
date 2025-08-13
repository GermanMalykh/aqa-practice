package data;

import java.util.concurrent.ThreadLocalRandom;

public enum Genders {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String value;

    Genders(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Возвращает случайное название гендера
     */
    public static String randomGender() {
        Genders[] genders = values();
        int randomIndex = ThreadLocalRandom.current().nextInt(genders.length);
        return genders[randomIndex].getValue();
    }
}
