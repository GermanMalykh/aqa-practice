package data;

import java.util.concurrent.ThreadLocalRandom;

public enum Subjects {
    HINDI("Hindi"),
    ENGLISH("English"),
    MATHS("Maths"),
    PHYSICS("Physics"),
    CHEMISTRY("Chemistry"),
    BIOLOGY("Biology"),
    COMPUTER_SCIENCE("Computer Science"),
    COMMERCE("Commerce"),
    ACCOUNTING("Accounting"),
    ECONOMICS("Economics"),
    ARTS("Arts"),
    SOCIAL_STUDIES("Social Studies"),
    HISTORY("History"),
    CIVICS("Civics");

    private final String value;

    Subjects(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Возвращает случайное название предмета
     */
    public static String randomSubject() {
        Subjects[] subjects = values();
        int randomIndex = ThreadLocalRandom.current().nextInt(subjects.length);
        return subjects[randomIndex].getValue();
    }

}
