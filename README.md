# YandexMarketTesting
### Описание проекта
Pet-проект по автоматизированному тестированию на языке программирования Java. \
Проект тестирует сайт Яндекс Маркета: навигация по каталогу, установка фильтров,\
поиск товаров на сайте и т.д.
### Стек
Java, Selenium, Allure, Junit
##### Пакеты:
### Структура проекта
- src
    - main
    - test
        - java
          - steps allure Step'ы
            - YandexMarketSteps
          - pages классы веб-страниц (PageObject)
            - YandexMarketHeader
            - YandexMarketCatalogPage
          - utils В директории содержатся утилиты для скриншотирования, описания зависимостей, поставщики данных для параметризированных тестов
            - Assertions
            - DataProvider
            - Properties
            - TestProperties
            - Screenshoter
          - tests Директория для тестов
            - BaseTest
            - YandexMarketTest
        - resources Директория содержит property файлы, результаты тестирования
          - tests.properties
### Запуск программы
### Результаты работы
