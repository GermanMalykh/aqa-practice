package data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class StatesAndCities {

    private static final Map<String, List<String>> STATE_TO_CITIES = Map.of(
            "NCR", List.of("Delhi", "Gurgaon", "Noida"),
            "Uttar Pradesh", List.of("Agra", "Lucknow", "Merrut"),
            "Haryana", List.of("Karnal", "Panipat"),
            "Rajasthan", List.of("Jaipur", "Jaiselmer")
    );

    /** Возвращает случайный штат */
    public static String getRandomState() {
        List<String> states = STATE_TO_CITIES.keySet().stream().toList();
        return states.get(ThreadLocalRandom.current().nextInt(states.size()));
    }

    /** Возвращает случайный город для конкретного штата */
    public static String getRandomCityForState(String state) {
        List<String> cities = STATE_TO_CITIES.get(state);
        if (cities == null || cities.isEmpty()) {
            throw new IllegalArgumentException("Неизвестный штат: " + state);
        }
        return cities.get(ThreadLocalRandom.current().nextInt(cities.size()));
    }
}
