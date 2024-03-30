# Money Transfer App

## Описание проекта
Money Transfer App - это консольное приложение на Java для перевода денежных средств между счетами.

## Требования

### Общие требования
- Java SE 8.0 или выше
- Запрещено использование библиотек типа Lombok и Spring
- Использование системы логирования (например, Log4j)

### Функциональные требования
- При запуске приложение создает несколько экземпляров объекта Account
со случайными значениями ID и значениями money, равными 10 000.
- В приложении запускается несколько независимых потоков, которые выполняют перевод средств между счетами.
- После 30 выполненных транзакций приложение завершает свою работу.

## Структура проекта



MoneyTransferApp/
├── .idea/
├── logs/
│   └── app.log
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── moneytransfer/
│   │   │       ├── model/
│   │   │       │   └── Account.java
│   │   │       └── service/
│   │   │           ├── MoneyTransferApp.java
│   │   │           └── Main.java
│   │   └── resources/
│   │       └── log4j2.xml
│   └── test/
│       └── java/
│           └── moneytransfer/
│               ├── MainTest.java
│               ├── MoneyTransferAppAccountCreationTest.java
│               ├── MoneyTransferAppAdditionalTests.java
│               ├── MoneyTransferAppConcurrencyTest.java
│               └── MoneyTransferAppErrorHandlingTest.java
├── target/
├── .gitignore
└── pom.xml


## Запуск приложения

Для сборки и запуска приложения выполните следующие команды:

```bash
mvn clean install
java -cp target/MoneyTransferApp.jar moneytransfer.Main
```

## Зависимости

Для сборки и запуска проекта необходима Java Development Kit (JDK) версии 8 или выше. 
Также требуется Maven для управления зависимостями и сборкой проекта.

## Лицензия

Этот проект лицензируется под [MIT License](LICENSE).
