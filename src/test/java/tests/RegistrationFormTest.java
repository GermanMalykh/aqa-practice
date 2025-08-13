package tests;

import config.TestBase;
import data.PageConstants;
import data.UserInfo;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.RegistrationFormPage;

@Epic("Формы регистрации")
@Feature("Автоматизация практики")
@Story("Заполнение формы регистрации")
@DisplayName("Форма регистрации")
public class RegistrationFormTest extends TestBase {

    RegistrationFormPage page = new RegistrationFormPage();

    @Test
    @DisplayName("Заполнение формы регистрации с валидацией данных")
    @Description("E2E тест заполнения формы регистрации с проверкой всех полей и валидацией отправленных данных")
    @Owner("QA Team")
    @Tag("web")
    void fillingPracticeForm() {
        page.open()
                .cleanupPageElements()
                .setFirstName(UserInfo.FIRST_NAME)
                .setLastName(UserInfo.LAST_NAME)
                .setEmail(UserInfo.EMAIL)
                .setGender(page.getTestData().getSelectedGender())
                .setPhone(UserInfo.PHONE_NUMBER)
                .setMonth(page.getTestData().getSelectedMonth())
                .setYear(page.getTestData().getSelectedYear())
                .setDay(page.getTestData().getSelectedDay())
                .setSubject(page.getTestData().getSelectedSubject())
                .setHobbies(page.getTestData().getSelectedHobbies())
                .setPicture(PageConstants.UPLOAD_FILE_NAME)
                .setAddress(UserInfo.ADDRESS)
                .setState(page.getTestData().getSelectedState())
                .setCity(page.getTestData().getSelectedCity())
                .submitForm()
                .checkTitle()
                .checkTableData();
    }
}
