# Настройка GitHub Pages

## Вариант 1: Использование вашего рабочего workflow (рекомендуется)

У вас уже есть рабочий workflow `test-runner.yml`, который использует проверенный подход:

1. **Автоматический запуск:** При push в main ветку или вручную через `workflow_dispatch`
2. **Использование Selenoid:** Ваш workflow использует `Xotabu4/selenoid-github-action@v2`
3. **История отчетов:** Сохраняет историю тестов в gh-pages ветке
4. **Проверенный деплой:** Использует `peaceiris/actions-gh-pages@v2`

### Настройка для вашего workflow:

1. Перейдите в настройки репозитория: `Settings` → `Pages`
2. В разделе `Source` выберите `Deploy from a branch`
3. В поле `Branch` выберите `gh-pages`
4. В поле `Folder` оставьте `/(root)`
5. Нажмите `Save`

## Вариант 2: Современный подход с GitHub Pages Actions

Если хотите использовать современный подход:

1. **Права доступа:** В `Settings` → `Actions` → `General` выберите `Read and write permissions`
2. **Workflow:** Используйте `allure.yml` workflow
3. **Автоматический деплой:** Отчеты будут публиковаться автоматически

## Шаг 2: Проверить права доступа

1. В настройках репозитория перейдите в `Settings` → `Actions` → `General`
2. В разделе `Workflow permissions` выберите `Read and write permissions`
3. Поставьте галочку `Allow GitHub Actions to create and approve pull requests`
4. Нажмите `Save`

## Шаг 3: Запустить workflow

### Для вашего workflow:
1. Перейдите в `Actions` → `Test Runner`
2. Нажмите `Run workflow` → `Run workflow`
3. Или просто сделайте push в main ветку

### Для современного workflow:
1. Сделайте push в main ветку
2. GitHub Actions автоматически запустится

## Шаг 4: Проверить результат

1. Перейдите по ссылке: https://germanmalykh.github.io/aqa-practice/
2. Должны отобразиться отчеты Allure с историей

## Troubleshooting

### Ошибка 403: Permission denied

**Решение:** Проверьте настройки прав доступа в `Settings` → `Actions` → `General`

### Страница не загружается

**Решение:** Подождите несколько минут после успешного выполнения workflow

### Отчеты не отображаются

**Решение:** Проверьте, что тесты прошли успешно и отчеты Allure сгенерировались

### Проблемы с Selenoid

**Решение:** Ваш workflow автоматически настраивает Selenoid через `Xotabu4/selenoid-github-action@v2`

## Преимущества вашего workflow

✅ **Проверенный годами** - работает 3+ года  
✅ **История отчетов** - сохраняет все предыдущие результаты  
✅ **Selenoid** - стабильный WebDriver в CI/CD  
✅ **Простой деплой** - использует проверенные actions  
✅ **Автоматизация** - запускается при каждом push
