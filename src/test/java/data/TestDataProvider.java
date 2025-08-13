package data;

import utils.Dates;

import static data.Genders.randomGender;
import static data.Hobbies.randomHobbies;
import static data.StatesAndCities.getRandomCityForState;
import static data.StatesAndCities.getRandomState;
import static data.Subjects.randomSubject;

/**
 * Провайдер тестовых данных для формы регистрации.
 * Генерирует уникальные данные для каждого теста
 */
public class TestDataProvider {
    
    // Тестовые данные - генерируются для каждого теста
    private String selectedGender;
    private String selectedYear;
    private String selectedMonth;
    private String selectedDay;
    private String selectedSubject;
    private String selectedHobbies;
    private String selectedState;
    private String selectedCity;

    /**
     * Инициализация тестовых данных
     */
    public void initializeTestData() {
        this.selectedGender = randomGender();
        this.selectedYear = Dates.randomYear();
        this.selectedMonth = Dates.randomMonth();
        this.selectedDay = Dates.randomDayOfMonth(this.selectedYear, this.selectedMonth);
        this.selectedSubject = randomSubject();
        this.selectedHobbies = randomHobbies();
        this.selectedState = getRandomState();
        this.selectedCity = getRandomCityForState(this.selectedState);
    }

    // Геттеры для доступа к тестовым данным
    public String getSelectedGender() { return selectedGender; }
    public String getSelectedYear() { return selectedYear; }
    public String getSelectedMonth() { return selectedMonth; }
    public String getSelectedDay() { return selectedDay; }
    public String getSelectedSubject() { return selectedSubject; }
    public String getSelectedHobbies() { return selectedHobbies; }
    public String getSelectedState() { return selectedState; }
    public String getSelectedCity() { return selectedCity; }
}
