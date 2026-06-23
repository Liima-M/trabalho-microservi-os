CREATE TABLE IF NOT EXISTS payments (
    id                 UUID           NOT NULL PRIMARY KEY,
    order_id           UUID           NOT NULL UNIQUE,
    amount             NUMERIC(14,2)  NOT NULL,
    method             VARCHAR(20)    NOT NULL,
    status             VARCHAR(20)    NOT NULL DEFAULT 'PENDING',
    external_reference TEXT,
    created_at         TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at         TIMESTAMP      NOT NULL DEFAULT NOW()
);

-- Tabela de idempotência para evitar processamento duplicado (RNF14, RN16)
CREATE TABLE IF NOT EXISTS processed_events (
    event_id   VARCHAR(255) NOT NULL PRIMARY KEY,
    processed_at TIMESTAMP  NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_payments_order_id ON payments(order_id);
CREATE INDEX IF NOT EXISTS idx_payments_status   ON payments(status);
