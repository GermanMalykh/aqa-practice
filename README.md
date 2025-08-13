# 🚀 AQA Practice - Автоматизация тестирования

Профессиональный фреймворк для автоматизации тестирования веб-приложений, построенный на современных технологиях и лучших практиках.

## 📋 Содержание

- [Описание проекта](#описание-проекта)
- [Технологии](#технологии)
- [Архитектура](#архитектура)
- [Установка и настройка](#установка-и-настройка)
- [Запуск тестов](#запуск-тестов)
- [Структура проекта](#структура-проекта)
- [Отчеты](#отчеты)
- [CI/CD](#cicd)
- [Лучшие практики](#лучшие-практики)

## 🎯 Описание проекта

Этот проект демонстрирует автоматизацию тестирования формы регистрации на сайте [DemoQA](https://demoqa.com). Фреймворк построен с использованием современных подходов к автоматизации тестирования и следует принципам чистого кода.

### ✨ Особенности

- **Page Object Model** - чистое разделение логики и элементов
- **Fluent Interface** - читаемые цепочки методов
- **Allure Reporting** - детальные отчеты с прикреплениями
- **Thread-safe** - безопасная генерация тестовых данных
- **Error Handling** - надежная обработка ошибок
- **Logging** - подробное логирование всех операций

## 🛠 Технологии

| Компонент | Версия | Описание |
|-----------|--------|----------|
| **Java** | 21 | Современный язык программирования |
| **Gradle** | 8.x | Система сборки |
| **JUnit 5** | 5.x | Фреймворк тестирования |
| **Selenide** | 6.x | Wrapper над Selenium WebDriver |
| **Allure** | 2.x | Система отчетности |
| **SLF4J** | 2.x | Логирование |
| **ChromeDriver** | Auto | Автоматическое управление драйверами |

## 🏗 Архитектура

### Принципы проектирования

- **Single Responsibility Principle** - каждый класс отвечает за одну задачу
- **Separation of Concerns** - разделение ответственности между слоями
- **Dependency Injection** - внедрение зависимостей
- **Fluent Interface** - цепочки методов для читаемости

### Слои архитектуры

```
┌─────────────────────────────────────────────────────────────┐
│                    Tests Layer                              │
│              (RegistrationFormTest)                         │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                   Pages Layer                               │
│         (RegistrationFormPage + PageElements)               │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                   Data Layer                                │
│    (TestDataProvider, Constants, Enums, UserInfo)          │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                  Utils Layer                                │
│           (Attach, AllureEnv, Dates)                       │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                 Config Layer                                │
│                    (TestBase)                               │
└─────────────────────────────────────────────────────────────┘
```

## 🚀 Установка и настройка

### Предварительные требования

- Java 21 или выше
- Gradle 8.x или выше
- Chrome браузер

### Клонирование репозитория

```bash
git clone <repository-url>
cd aqa-practice
```

### Проверка окружения

```bash
java -version
gradle -version
```

## 🧪 Запуск тестов

### Запуск всех тестов

```bash
./gradlew test
```

### Запуск с генерацией Allure отчета

```bash
./gradlew test allureReport
```

### Запуск с определенными тегами

```bash
./gradlew test --tests "*RegistrationFormTest*"
```

### Запуск в headless режиме

```bash
./gradlew test -Dheadless=true
```

### Запуск на удаленном Selenoid

```bash
./gradlew test -DremoteUrl=https://selenoid.autotests.cloud/wd/hub
```

## 📁 Структура проекта

```
src/
├── test/
│   ├── java/
│   │   ├── config/           # Конфигурация тестов
│   │   │   └── TestBase.java # Базовый класс для всех тестов
│   │   ├── data/             # Тестовые данные и константы
│   │   │   ├── TestDataProvider.java    # Провайдер тестовых данных
│   │   │   ├── PageConstants.java       # Константы страницы
│   │   │   ├── TableColumnName.java     # Названия колонок таблицы
│   │   │   ├── UserInfo.java            # Статические данные пользователя
│   │   │   ├── Genders.java             # Enum гендеров
│   │   │   ├── Subjects.java            # Enum предметов
│   │   │   ├── Hobbies.java             # Enum хобби
│   │   │   └── StatesAndCities.java     # Штаты и города
│   │   ├── pages/            # Page Objects
│   │   │   ├── RegistrationFormPage.java # Основная страница
│   │   │   └── PageElements.java        # Элементы страницы
│   │   ├── tests/             # Тестовые классы
│   │   │   └── RegistrationFormTest.java # Основной тест
│   │   └── utils/             # Утилиты
│   │       ├── Attach.java               # Прикрепление файлов к Allure
│   │       ├── AllureEnv.java            # Настройка окружения Allure
│   │       └── Dates.java                # Работа с датами
│   └── resources/             # Ресурсы
│       └── tools_qa.jpeg     # Тестовый файл для загрузки
```

## 📊 Отчеты

### Allure Report

После выполнения тестов:

```bash
./gradlew allureServe
```

Откроется локальный сервер с отчетом в браузере.

### Структура отчета

- **Overview** - общая статистика
- **Behaviors** - группировка по Epic/Feature/Story
- **Suites** - группировка по тестовым классам
- **Graphs** - графики и диаграммы
- **Timeline** - временная шкала выполнения
- **Categories** - группировка по статусам
- **Suites** - детальная информация о тестах

### Прикрепления

Каждый тест автоматически прикрепляет:
- 📸 Скриншот последнего состояния
- 📄 Исходный код страницы
- 🌐 HTML страницы
- 📝 Логи консоли браузера
- 🎥 Видео (при удаленном запуске)

## 🔄 CI/CD

### GitHub Actions

```yaml
name: Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'
      - name: Run tests
        run: ./gradlew test
      - name: Generate Allure report
        run: ./gradlew allureReport
      - name: Upload Allure results
        uses: actions/upload-artifact@v3
        with:
          name: allure-results
          path: build/allure-results/
```

### Jenkins Pipeline

```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Report') {
            steps {
                sh './gradlew allureReport'
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'build/allure-results']]
                ])
            }
        }
    }
}
```

## 📚 Лучшие практики

### 1. Page Object Model

```java
public class RegistrationFormPage {
    public RegistrationFormPage setFirstName(String firstName) {
        PageElements.FIRST_NAME_FIELD.setValue(firstName);
        return this; // Fluent Interface
    }
}
```

### 2. Генерация тестовых данных

```java
public class TestDataProvider {
    public void initializeTestData() {
        this.selectedGender = randomGender();
        this.selectedYear = Dates.randomYear();
        // Уникальные данные для каждого теста
    }
}
```

### 3. Обработка ошибок

```java
try {
    return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
} catch (Exception e) {
    logger.error("Ошибка при создании скриншота: {}", e.getMessage(), e);
    return new byte[0]; // Graceful degradation
}
```

### 4. Allure аннотации

```java
@Step("Заполнить поле имени: {firstName}")
@Epic("Формы регистрации")
@Feature("Автоматизация практики")
@Story("Заполнение формы регистрации")
@Owner("QA Team")
@Tag("smoke")
```

## 🔧 Конфигурация

### Системные свойства

| Свойство | Описание | По умолчанию |
|----------|----------|--------------|
| `browser` | Браузер для тестов | `chrome` |
| `headless` | Headless режим | `false` |
| `remoteUrl` | URL удаленного Selenoid | - |
| `browserVersion` | Версия браузера | `latest` |

### Примеры использования

```bash
# Запуск в Firefox
./gradlew test -Dbrowser=firefox

# Запуск в headless режиме
./gradlew test -Dheadless=true

# Запуск на удаленном Selenoid
./gradlew test -DremoteUrl=https://selenoid.autotests.cloud/wd/hub
```

## 🐛 Troubleshooting

### Частые проблемы

#### 1. ChromeDriver не найден

```bash
# Автоматическое управление драйверами включено по умолчанию
# Если проблема остается, проверьте версию Chrome
google-chrome --version
```

#### 2. Тесты падают с ошибкой "Element not found"

- Проверьте селекторы в `PageElements.java`
- Убедитесь, что страница полностью загружена
- Проверьте, не изменился ли UI приложения

#### 3. Allure отчет не генерируется

```bash
# Очистите build папку
./gradlew clean

# Запустите тесты заново
./gradlew test allureReport
```

## 📄 Лицензия

Этот проект предназначен для образовательных целей и демонстрации лучших практик автоматизации тестирования.
