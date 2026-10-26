# Практическая работа №1. Приложение с типовой архитектурой JakartaEE
## Технологии

- Jakarta EE 10
- Java 17
- Eclipse GlassFish 7
- PostgreSQL
- Maven

## Архитектура

- **JPA/data:** сущности `Author`, `Book` и `BookRepository` для доступа к данным.
- **Бизнес-слой:** stateless session EJB `BookService`.
- **Веб-слой:** Servlet `BookServlet` с HTML-интерфейсом.

Схема базы данных и начальные данные находятся в [`sql/01-library.sql`](sql/01-library.sql). Настройки JPA - в `src/main/resources/META-INF/persistence.xml`.
