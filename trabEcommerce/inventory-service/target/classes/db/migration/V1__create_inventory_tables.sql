CREATE TABLE IF NOT EXISTS stocks (
    id                UUID    NOT NULL PRIMARY KEY,
    product_id        UUID    NOT NULL UNIQUE,
    quantity          INTEGER NOT NULL CHECK (quantity >= 0),
    reserved_quantity INTEGER NOT NULL DEFAULT 0 CHECK (reserved_quantity >= 0),
    updated_at        TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS stock_reservations (
    id         UUID        NOT NULL PRIMARY KEY,
    product_id UUID        NOT NULL,
    order_id   UUID        NOT NULL UNIQUE,
    quantity   INTEGER     NOT NULL CHECK (quantity > 0),
    status     VARCHAR(20) NOT NULL CHECK (status IN ('PENDING','CONFIRMED','RELEASED','EXPIRED')),
    created_at TIMESTAMP   NOT NULL DEFAULT NOW(),
    expires_at TIMESTAMP   NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_stock_reservations_order_id  ON stock_reservations(order_id);
CREATE INDEX IF NOT EXISTS idx_stock_reservations_status    ON stock_reservations(status);
CREATE INDEX IF NOT EXISTS idx_stock_reservations_expires   ON stock_reservations(expires_at);
