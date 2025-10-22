# 📨 Microservices Demo: User Service + Notification Service (Kafka + PostgreSQL)

Проект демонстрирует работу **микросервисной архитектуры на Spring Boot**  
с обменом событиями через **Apache Kafka** и хранением данных в **PostgreSQL**.
## ⚙️ Архитектура

```
            ┌────────────────────┐
            │     user-service   │
            │--------------------│
            │ REST API (CRUD)    │
            │ PostgreSQL         │
            │ Kafka Producer     │
            └────────┬───────────┘
                     │
             🔽  Kafka Topic  🔽
                     │
            ┌────────┴───────────┐
            │notification-service│
            │--------------------│
            │ Kafka Consumer     │
            │ Email Sender (SMTP)│
            └────────────────────┘

````

## 🧩 Сервисы

| Сервис | Порт | Назначение |
|--------|------|-------------|
| **user-service** | 8081 | REST API для управления пользователями. Отправляет события в Kafka. |
| **notification-service** | 8082 | Слушает Kafka и отправляет email-уведомления пользователям. |
| **PostgreSQL** | 5433 | База данных пользователей. |
| **Kafka** | 9092 | Брокер сообщений. |
| **Zookeeper** | 2181 | Координация Kafka. |

---

## 🚀 Запуск проекта

### 1️⃣ Предварительные требования

- Docker ≥ 24  
- Docker Compose ≥ v2  
- Git установлен  
- В корне проекта файл `.env` с email-учёткой:

```bash
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
````

> ⚠️ Если используете Gmail, [создайте App Password](https://myaccount.google.com/apppasswords)
> (нельзя использовать обычный пароль, если включена 2FA).

---

### 2️⃣ Сборка и запуск

```bash
docker compose up --build
```

> Первый запуск может занять 2–4 минуты — Maven соберёт оба сервиса внутри контейнеров.

После успешного запуска:

* `http://localhost:8081` — User Service
* `http://localhost:8082` — Notification Service

---

### 3️⃣ Проверка работы

#### ➕ Создать пользователя

```bash
curl -X POST http://localhost:8081/users \
     -H "Content-Type: application/json" \
     -d '{"name": "Alex", "email": "alex@example.com"}'
```

#### 🗑 Удалить пользователя

```bash
curl -X DELETE http://localhost:8081/users/1
```

При этих операциях:

* `user-service` отправляет событие в Kafka (`user-events`)
* `notification-service` получает сообщение и отправляет письмо

---

## 🧰 Полезные команды

📋 Проверить список топиков Kafka:

```bash
docker exec -it $(docker ps -qf "name=kafka") \
  kafka-topics --bootstrap-server localhost:9093 --list
```

📨 Проверить сообщения:

```bash
docker exec -it $(docker ps -qf "name=kafka") \
  kafka-console-consumer --bootstrap-server localhost:9093 --topic user-events --from-beginning
```

---

## 🧠 Технологический стек

* **Java 21**
* **Spring Boot 3**

    * Spring Web
    * Spring Data JPA
    * Spring Kafka
    * Spring Mail
* **PostgreSQL 16**
* **Apache Kafka 7.6 (Confluent Platform)**
* **Docker Compose**
* **Lombok**
* **Awaitility / GreenMail** (тестирование)

---

## 🧾 Переменные окружения

| Переменная      | Описание                | Пример            |
| --------------- | ----------------------- | ----------------- |
| `MAIL_USERNAME` | Email-адрес отправителя | `mybot@gmail.com` |
| `MAIL_PASSWORD` | Пароль или App Password | `abcd1234xyz`     |

---

## 🧩 Структура проекта

```
project-root/
│
├── common/                  # Общие классы (DTO, события)
│
├── user-service/            # CRUD сервис пользователей
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── notification-service/    # Уведомления по Kafka и email
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── docker-compose.yml
├── pom.xml                  # Родительский pom
└── README.md
```

---

## 🧹 Остановка контейнеров

```bash
docker compose down
```

Для полной очистки:

```bash
docker system prune -f
docker volume prune -f
```

---

## 🧑‍💻 Автор

**Алексей Тен**
Java Backend Developer
Spring Boot • Kafka • PostgreSQL • Docker • CI/CD
📧 [связаться](mailto:aleksey-ten-developer@yandex.ru)

---
