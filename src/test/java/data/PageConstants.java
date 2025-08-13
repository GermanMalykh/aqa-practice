package data;

/**
 * Константы страницы формы регистрации
 */
public class PageConstants {
    
    // URL страницы
    public static final String PRACTICE_FORM_URL = "/automation-practice-form";
    
    // Текст для проверок
    public static final String SUCCESS_TITLE = "Thanks for submitting the form";
    
    // Файлы
    public static final String UPLOAD_FILE_NAME = "tools_qa.jpeg";
    
    // JavaScript для очистки страницы
    public static final String PAGE_CLEANUP_SCRIPT = "document.querySelectorAll('" +
            "#fixedban, " +
            "#RightSide_Advertisement, " +
            "#google_center_div, " +
            "footer').forEach(e=>e.remove())";
    
    // HTML атрибуты
    public static final String ARIA_LABEL_ATTRIBUTE = "aria-label";
}
