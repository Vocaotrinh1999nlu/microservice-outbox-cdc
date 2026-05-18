# Spring Boot Transactional Outbox Pattern with Debezium & PostgreSQL

This project demonstrates the **Transactional Outbox Pattern** using Spring Boot, PostgreSQL Database, Debezium, and Kafka.

## Architecture
1. **Application** saves an `Order` and an `OutboxEvent` in a single PostgreSQL transaction.
2. **Debezium** (PostgreSQL pgoutput plugin) monitors the `outbox` table.
3. **Debezium** sends the events to **Kafka** topics.

## Database Migration
This project uses **Liquibase** for database migrations.
- The master changelog is located at `src/main/resources/db/changelog/db.changelog-master.xml`.
- The SQL migration scripts are in `src/main/resources/db/changelog/changes/`.
- Migrations run automatically when the application starts.

## Prerequisites
- Java 21
- Docker & Docker Compose
- **PostgreSQL Note**: Debezium's PostgreSQL connector requires logical replication to be enabled (`wal_level=logical`). This is pre-configured in the `docker-compose.yml`.

## Getting Started

### 1. Start Infrastructure
```bash
docker-compose up -d
```
*Note: Wait for all services (PostgreSQL, Kafka, Debezium) to be healthy.*

### 2. Register Debezium Connector
```bash
./register-postgres-connector.sh
```

### 3. Run the Spring Boot Application
```bash
./gradlew bootRun
```
The application now includes a **Kafka Consumer** (`OrderEventListener`) that logs incoming events from the `outbox.event.ORDER` topic.

### 4. Access Kafka UI
You can visualize Kafka topics and Debezium connectors at [http://localhost:8085](http://localhost:8085).

## Testing the Outbox Pattern

### 1. Create an Order
```bash
curl -X POST http://localhost:8080/api/orders \
-H "Content-Type: application/json" \
-d '{
  "customerEmail": "user@example.com",
  "totalAmount": 150.00
}'
```

### 2. Verify Logs
Check the application logs to see the consumer in action:
```text
Received event from Kafka!
Event Type: ORDER_CREATED
Payload: {"id":1,"customerEmail":"user@example.com","totalAmount":150.00,"status":"CREATED"}
```

### 3. Verify Database
Connect to your PostgreSQL DB and check:
```sql
SELECT * FROM orders;
SELECT * FROM outbox;
```

### 4. Verify Kafka Message via CLI
```bash
docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic outbox.event.ORDER --from-beginning
```

## Key Configuration
- The `Order` and `OutboxEvent` entities use standard JPA.
- Debezium configuration uses `plugin.name: pgoutput`.
- Topic naming: `outbox.event.${routedByValue}`.
