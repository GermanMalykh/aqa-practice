package pages;

import com.codeborne.selenide.Selenide;
import data.PageConstants;
import data.TestDataProvider;
import data.TableColumnName;
import data.UserInfo;
import io.qameta.allure.Step;

import java.util.Map;

import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

/**
 * Page Object для формы регистрации.
 * Содержит только логику работы со страницей
 */
public class RegistrationFormPage {

    private final TestDataProvider testData;

    public RegistrationFormPage() {
        this.testData = new TestDataProvider();
    }

    @Step("Удалить лишние элементы страницы")
    public RegistrationFormPage cleanupPageElements() {
        executeJavaScript(PageConstants.PAGE_CLEANUP_SCRIPT);
        return this;
    }

    @Step("Открыть форму регистрации")
    public RegistrationFormPage open() {
        Selenide.open(PageConstants.PRACTICE_FORM_URL);
        testData.initializeTestData();
        return this;
    }

    @Step("Заполнить поле имени: {firstName}")
    public RegistrationFormPage setFirstName(String firstName) {
        PageElements.FIRST_NAME_FIELD.setValue(firstName);
        return this;
    }

    @Step("Заполнить поле фамилии: {lastName}")
    public RegistrationFormPage setLastName(String lastName) {
        PageElements.LAST_NAME_FIELD.setValue(lastName);
        return this;
    }

    @Step("Заполнить поле email: {email}")
    public RegistrationFormPage setEmail(String email) {
        PageElements.EMAIL_FIELD.setValue(email);
        return this;
    }

    @Step("Выбрать гендер: {gender}")
    public RegistrationFormPage setGender(String gender) {
        PageElements.GENDER_WRAPPER.$(byText(gender)).click();
        return this;
    }

    @Step("Заполнить поле телефона: {phoneNumber}")
    public RegistrationFormPage setPhone(String phoneNumber) {
        PageElements.PHONE_FIELD.setValue(phoneNumber);
        return this;
    }

    @Step("Выбрать месяц: {month}")
    public RegistrationFormPage setMonth(String month) {
        PageElements.DATE_INPUT.click();
        PageElements.MONTH_SELECT.selectOption(month);
        return this;
    }

    @Step("Выбрать год: {year}")
    public RegistrationFormPage setYear(String year) {
        PageElements.YEAR_SELECT.selectOption(year);
        return this;
    }

    @Step("Выбрать день: {day}")
    public RegistrationFormPage setDay(String day) {
        PageElements.DAY_OPTIONS.filterBy(attributeMatching(PageConstants.ARIA_LABEL_ATTRIBUTE, ".*" + testData.getSelectedMonth() + ".*"))
                .findBy(exactText(day)).click();
        return this;
    }

    @Step("Выбрать предмет: {subject}")
    public RegistrationFormPage setSubject(String subject) {
        PageElements.SUBJECTS_INPUT.setValue(subject).pressEnter();
        return this;
    }

    @Step("Выбрать хобби: {hobbies}")
    public RegistrationFormPage setHobbies(String hobbies) {
        PageElements.HOBBIES_WRAPPER.$(byText(hobbies)).click();
        return this;
    }

    @Step("Загрузить файл: {fileName}")
    public RegistrationFormPage setPicture(String fileName) {
        PageElements.PICTURE_UPLOAD.uploadFromClasspath(fileName);
        return this;
    }

    @Step("Заполнить поле адреса: {address}")
    public RegistrationFormPage setAddress(String address) {
        PageElements.ADDRESS_FIELD.setValue(address);
        return this;
    }

    @Step("Выбрать штат: {state}")
    public RegistrationFormPage setState(String state) {
        PageElements.STATE_SELECT.click();
        $(byText(state)).click();
        return this;
    }

    @Step("Выбрать город: {city}")
    public RegistrationFormPage setCity(String city) {
        PageElements.CITY_SELECT.click();
        $(byText(city)).click();
        return this;
    }

    @Step("Отправить форму")
    public RegistrationFormPage submitForm() {
        PageElements.SUBMIT_BUTTON.click();
        return this;
    }

    @Step("Проверить заголовок успеха")
    public RegistrationFormPage checkTitle() {
        PageElements.SUCCESS_TITLE.shouldHave(text(PageConstants.SUCCESS_TITLE));
        return this;
    }

    @Step("Проверить данные в таблице")
    public RegistrationFormPage checkTableData() {
        Map<String, String> expectedData = Map.of(
                TableColumnName.FULL_NAME, UserInfo.FIRST_NAME + " " + UserInfo.LAST_NAME,
                TableColumnName.MAIL, UserInfo.EMAIL,
                TableColumnName.GENDER, testData.getSelectedGender(),
                TableColumnName.MOBILE, UserInfo.PHONE_NUMBER,
                TableColumnName.DATE, testData.getSelectedDay() + " " + testData.getSelectedMonth() + "," + testData.getSelectedYear(),
                TableColumnName.SUBJECT, testData.getSelectedSubject(),
                TableColumnName.HOBBY, testData.getSelectedHobbies(),
                TableColumnName.PICTURE, PageConstants.UPLOAD_FILE_NAME,
                TableColumnName.ADDRESS, UserInfo.ADDRESS,
                TableColumnName.STATE_CITY, testData.getSelectedState() + " " + testData.getSelectedCity()
        );

        expectedData.forEach((key, value) ->
                PageElements.TABLE_ROWS.findBy(text(key))
                        .$$("td").get(1)
                        .shouldHave(text(value))
        );
        return this;
    }

    // Геттер для доступа к тестовым данным (полезно для отладки)
    public TestDataProvider getTestData() {
        return testData;
    }
}
