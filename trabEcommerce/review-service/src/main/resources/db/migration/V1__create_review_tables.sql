-- Lado COMMAND do CQRS (PostgreSQL)
CREATE TABLE IF NOT EXISTS reviews (
    id         UUID        NOT NULL PRIMARY KEY,
    product_id UUID        NOT NULL,
    buyer_id   UUID        NOT NULL,
    rating     SMALLINT    NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment    TEXT        NOT NULL CHECK (length(comment) BETWEEN 10 AND 500),
    created_at TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP   NOT NULL DEFAULT NOW(),
    UNIQUE (product_id, buyer_id)
);

-- Tabela de idempotência para eventos consumidos (RN16)
CREATE TABLE IF NOT EXISTS processed_events (
    event_id     VARCHAR(255) NOT NULL PRIMARY KEY,
    processed_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_reviews_product_id ON reviews(product_id);
CREATE INDEX IF NOT EXISTS idx_reviews_buyer_id   ON reviews(buyer_id);
