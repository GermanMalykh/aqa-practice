# AQA Practice Project

Проект для практики автоматизированного тестирования с использованием Selenide, JUnit 5 и Allure.

## Структура проекта

- `src/test/java/` - тестовые классы
- `src/test/resources/` - конфигурационные файлы
- `src/test/java/pages/` - Page Object модели
- `src/test/java/data/` - тестовые данные
- `src/test/java/utils/` - утилитарные классы

## Запуск тестов

### Локально
```bash
./gradlew clean test
```

### В CI/CD
Тесты автоматически запускаются при push в ветку main/master.

## Генерация отчетов

### Allure отчет
```bash
./gradlew allureReport
```

### Очистка результатов тестов
```bash
./gradlew cleanReports
```

### Принудительное обновление отчетов
```bash
./gradlew forceUpdateReports
```

### Просмотр отчета локально
```bash
./gradlew allureServe
```

## Решение проблем

### Проблема с кэшированием GitHub Pages
Если отчеты не обновляются на [https://germanmalykh.github.io/aqa-practice/](https://germanmalykh.github.io/aqa-practice/):

#### Автоматическое решение:
1. Запустите workflow вручную через GitHub Actions (вкладка Actions → Test → Run workflow)
2. Дождитесь завершения всех шагов
3. Подождите 5-10 минут для применения изменений

#### Ручное решение:
1. Принудительно обновите страницу (Ctrl+F5 или Cmd+Shift+R)
2. Очистите кэш браузера (Ctrl+Shift+Delete)
3. Откройте страницу в режиме инкогнито

#### Проверка обновлений:
1. Перейдите в ветку `gh-pages` репозитория
2. Проверьте наличие файла `.nojekyll`
3. Убедитесь, что отчеты имеют актуальные даты

### Проблема с артефактами
Если возникает ошибка "Artifact not found for name: allure-results":
- Это нормально для первого запуска
- Артефакт создается только при успешном выполнении тестов
- В последующих запусках проблема должна исчезнуть

## Конфигурация

### Selenide
Настройки находятся в `src/test/resources/selenide-ci.properties`

### Allure
Настройки находятся в `build.gradle`

## CI/CD

Проект настроен для автоматического деплоя отчетов на GitHub Pages через GitHub Actions.

### Workflow файл
`.github/workflows/test.yml`

### Основные шаги
1. Запуск Selenoid
2. Выполнение тестов
3. Генерация Allure отчета
4. Деплой на GitHub Pages
5. Обновление ветки gh-pages
