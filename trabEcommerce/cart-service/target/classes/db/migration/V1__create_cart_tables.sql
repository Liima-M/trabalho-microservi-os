CREATE TABLE IF NOT EXISTS carts (
    id         UUID      NOT NULL PRIMARY KEY,
    user_id    UUID      NOT NULL UNIQUE,
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS cart_items (
    id           SERIAL         NOT NULL PRIMARY KEY,
    cart_id      UUID           NOT NULL REFERENCES carts(id) ON DELETE CASCADE,
    product_id   UUID           NOT NULL,
    product_name VARCHAR(255)   NOT NULL,
    unit_price   NUMERIC(14,2)  NOT NULL,
    quantity     INTEGER        NOT NULL CHECK (quantity > 0),
    UNIQUE (cart_id, product_id)
);

CREATE INDEX IF NOT EXISTS idx_carts_user_id ON carts(user_id);
