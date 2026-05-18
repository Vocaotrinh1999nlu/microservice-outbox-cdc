--liquibase formatted sql

--changeset trinhvo:0
CREATE SCHEMA IF NOT EXISTS order_svc;

--changeset trinhvo:1
CREATE TABLE order_svc.orders (
    id BIGSERIAL PRIMARY KEY,
    customer_email VARCHAR(255),
    total_amount NUMERIC(19, 2),
    status VARCHAR(50)
);
--rollback DROP TABLE orders;

--changeset trinhvo:2
CREATE TABLE order_svc.outbox (
    id BIGSERIAL PRIMARY KEY,
    aggregatetype VARCHAR(255) NOT NULL,
    aggregateid VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    payload TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
--rollback DROP TABLE outbox;
