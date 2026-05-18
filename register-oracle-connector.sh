#!/bin/bash

curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://localhost:8083/connectors/ -d '{
  "name": "outbox-connector",
  "config": {
    "connector.class": "io.debezium.connector.oracle.OracleConnector",
    "tasks.max": "1",
    "database.hostname": "oracle",
    "database.port": "1521",
    "database.user": "system",
    "database.password": "oracle_password",
    "database.dbname": "FREEPDB1",
    "database.pdb.name": "FREEPDB1",
    "topic.prefix": "ora_server",
    "schema.history.internal.kafka.bootstrap.servers": "kafka:29092",
    "schema.history.internal.kafka.topic": "schema-changes.outbox",
    "table.include.list": "SYSTEM.OUTBOX",
    "database.connection.adapter": "logminer",
    "transforms": "outbox",
    "transforms.outbox.type": "io.debezium.transforms.outbox.EventRouter",
    "transforms.outbox.route.topic.replacement": "outbox.event.${routedByValue}",
    "transforms.outbox.table.fields.additional.placement": "TYPE:header:eventType"
  }
}'
