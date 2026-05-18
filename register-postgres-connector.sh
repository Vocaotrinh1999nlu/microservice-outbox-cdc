#!/bin/bash

# Delete existing connector if it exists
curl -s -X DELETE http://localhost:8083/connectors/outbox-connector

echo "Registering outbox-connector..."
curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://localhost:8083/connectors/ -d '{
  "name": "outbox-connector",
  "config": {
    "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
    "tasks.max": "1",
    "database.hostname": "postgres",
    "database.port": "5432",
    "database.user": "postgres",
    "database.password": "postgres",
    "database.dbname": "postgres",
    "topic.prefix": "pg_server",
    "plugin.name": "pgoutput",
    "slot.name": "debezium_outbox",
    "table.include.list": "order_svc.outbox",
    "transforms": "outbox",
    "transforms.outbox.type": "io.debezium.transforms.outbox.EventRouter",
    "transforms.outbox.route.topic.replacement": "outbox.event.${routedByValue}",
    "transforms.outbox.table.fields.additional.placement": "type:header:eventType"
  }
}'
