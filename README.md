## Подготовка mySQL (Нужно сделать только в первый раз)

```bash
    sudo apt update
```
```bash
    sudo apt install mysql-server
```
```bash
    sudo mysql -u root -p
```
Создание пользователя (username и password находится в leetcode-java-project/src/main/resources/application.properties)
```bash
    CREATE USER 'username' IDENTIFIED BY 'password';
```
Выдаем все разрешения
```bash
    GRANT ALL PRIVILEGES ON *.* TO 'username' WITH GRANT OPTION;
```
```bash
    FLUSH PRIVILEGES;
```
```bash
  ./gradlew build -U
```

## Как запустить
Заходим в корень репозитория

## Как проверить работу базы данных

```bash
  USE mydb;
```
Чтобы увидеть какие таблицы есть в базе данных:
```bash
  SHOW tables;
```
Чтобы увидеть записи в таблице (например app_user):
```bash
  SELECT * FROM app_user;
```

Так можно проверить, что после каких-то POST действий на странице база действительно меняется (например, после регистрации)

### Сборка

```bash
    ./gradlew build
```

### Запуск

```bash
    ./gradlew bootRun
```

После этого идем на [http://localhost:8080]() и любуемся сайтом

# Описание проекта

