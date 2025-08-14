package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Элементы страницы формы регистрации.
 * Отделяет элементы от логики Page Object
 */
public class PageElements {
    
    // Поля ввода
    public static final SelenideElement
            FIRST_NAME_FIELD = $("#firstName"),
            LAST_NAME_FIELD = $("#lastName"),
            EMAIL_FIELD = $("#userEmail"),
            PHONE_FIELD = $("#userNumber"),
            ADDRESS_FIELD = $("#currentAddress");
    
    // Элементы выбора
    public static final SelenideElement
            GENDER_WRAPPER = $("#genterWrapper"),
            SUBJECTS_INPUT = $("#subjectsInput"),
            HOBBIES_WRAPPER = $("#hobbiesWrapper"),
            PICTURE_UPLOAD = $("#uploadPicture");
    
    // Элементы даты
    public static final SelenideElement
            DATE_INPUT = $("#dateOfBirthInput"),
            MONTH_SELECT = $(".react-datepicker__month-select"),
            YEAR_SELECT = $(".react-datepicker__year-select");
    
    // Элементы географии
    public static final SelenideElement
            STATE_SELECT = $("#state"),
            CITY_SELECT = $("#city");
    
    // Кнопки и заголовки
    public static final SelenideElement
            SUBMIT_BUTTON = $("#submit"),
            SUCCESS_TITLE = $(".modal-title");
    
    // Коллекции элементов
    public static final ElementsCollection
            DAY_OPTIONS = $$(".react-datepicker__day"),
            TABLE_ROWS = $$("table tr");
}
