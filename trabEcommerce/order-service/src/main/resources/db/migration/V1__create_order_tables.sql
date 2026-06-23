CREATE TABLE IF NOT EXISTS orders (
    id               UUID           NOT NULL PRIMARY KEY,
    buyer_id         UUID           NOT NULL,
    delivery_address TEXT           NOT NULL,
    payment_method   VARCHAR(20)    NOT NULL,
    status           VARCHAR(30)    NOT NULL DEFAULT 'PENDENTE',
    subtotal         NUMERIC(14,2)  NOT NULL,
    freight_value    NUMERIC(14,2)  NOT NULL DEFAULT 0,
    total            NUMERIC(14,2)  NOT NULL,
    created_at       TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP      NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS order_items (
    id           SERIAL          NOT NULL PRIMARY KEY,
    order_id     UUID            NOT NULL REFERENCES orders(id),
    product_id   UUID            NOT NULL,
    product_name VARCHAR(255)    NOT NULL,
    unit_price   NUMERIC(14,2)   NOT NULL,
    quantity     INTEGER         NOT NULL CHECK (quantity > 0)
);

CREATE TABLE IF NOT EXISTS saga_log (
    id         UUID        NOT NULL PRIMARY KEY,
    order_id   UUID        NOT NULL,
    step       VARCHAR(50) NOT NULL,
    status     VARCHAR(20) NOT NULL,
    payload    JSONB,
    created_at TIMESTAMP   NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_orders_buyer_id ON orders(buyer_id);
CREATE INDEX IF NOT EXISTS idx_order_items_order_id ON order_items(order_id);
CREATE INDEX IF NOT EXISTS idx_saga_log_order_id ON saga_log(order_id);
