## Задание: Система лояльности

Вы — backend-разработчик в интернет-магазине.  
Бизнес активно растёт, и руководство решило повысить лояльность клиентов с помощью персональных скидок.

**Product Owner** обратился к вам с просьбой реализовать начальную версию системы лояльности.  
Система должна уметь предоставлять покупателям процентную скидку на содержимое их корзины.  
Размер скидки индивидуален для каждого покупателя — аналитики уже подготовили данные по процентам.

---

### Термины

- **Корзина** — список товаров, выбранных покупателем.
- **Покупка** — один товар в корзине. Содержит:
    - `id` товара
    - полную цену
    - конечную цену с учётом скидки
- **Скидка** — персональный процент (целое число), привязанный к покупателю.

---

### Ваша задача

Реализовать компонент новой системы лояльности, который:
- Принимает `id` покупателя и его корзину
- Рассчитывает и применяет скидку к каждому товару
- Возвращает обновлённую корзину, в которой уже учтены персональные скидки




## Environment

 * Java: Amazon Corretto 11
   * 11 is next LTS version after most used 8
   * starting from 2022 Java 11 is the most used version: https://newrelic.com/resources/report/2023-state-of-the-java-ecosystem
   * Amazon is the most popular JDK vendor: https://newrelic.com/resources/report/2023-state-of-the-java-ecosystem

* not-null annotation - jakarta.annotation.Nonnull (should be switched to https://jspecify.dev/ ?)
  * https://www.baeldung.com/java-notnull-method-parameter
  * https://stackoverflow.com/questions/4963300/which-notnull-java-annotation-should-i-use

* Junit5
  * https://junit.org/junit5/docs/current/user-guide/#overview
  * https://www.baeldung.com/junit-5
  * https://www.baeldung.com/junit-5-migration

* Mockito 4
  * https://javadoc.io/doc/org.mockito/mockito-core/4.11.0/org/mockito/Mockito.html
  * It looks like Mockito 5 main idea is to prepare for more recent JDKs (https://github.com/mockito/mockito/releases/tag/v5.0.0) 
  
 