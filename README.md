# Spring Boot Transactional Outbox Pattern with Debezium & Oracle

This project demonstrates the **Transactional Outbox Pattern** using Spring Boot, Oracle Database, Debezium, and Kafka.

## Architecture
1. **Application** saves an `Order` and an `OutboxEvent` in a single Oracle transaction.
2. **Debezium** (Oracle LogMiner adapter) monitors the `OUTBOX` table.
3. **Debezium** sends the events to **Kafka** topics.

## Database Migration
This project uses **Liquibase** for database migrations.
- The master changelog is located at `src/main/resources/db/changelog/db.changelog-master.xml`.
- The SQL migration scripts are in `src/main/resources/db/changelog/changes/`.
- Migrations run automatically when the application starts.

## Prerequisites
- Java 21
- Docker & Docker Compose
- **Oracle DB Note**: Debezium's Oracle connector requires the database to be in `ARCHIVELOG` mode and the user to have specific permissions (LogMiner). The `gvenzl/oracle-free` image in `docker-compose.yml` is pre-configured for these requirements.

## Getting Started

### 1. Start Infrastructure
```bash
docker-compose up -d
```
*Note: It may take 1-2 minutes for Oracle to be fully ready.*

### 2. Register Debezium Connector
```bash
./register-oracle-connector.sh
```

### 3. Run the Spring Boot Application
```bash
./gradlew bootRun
```

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

### 2. Verify Database
Connect to your Oracle DB and check:
```sql
SELECT * FROM ORDERS;
SELECT * FROM OUTBOX;
```

### 3. Verify Kafka Message
```bash
docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic outbox.event.ORDER --from-beginning
```

## Key Configuration
- The `Order` and `OutboxEvent` entities use standard JPA. Oracle might use sequences for IDs.
- Debezium configuration uses `database.connection.adapter: logminer`.
- Topic naming: `outbox.event.ORDER`.
