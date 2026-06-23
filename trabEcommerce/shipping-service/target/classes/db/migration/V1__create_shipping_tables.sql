CREATE TABLE IF NOT EXISTS shipments (
    id                   UUID           NOT NULL PRIMARY KEY,
    order_id             UUID           NOT NULL UNIQUE,
    tracking_code        VARCHAR(50)    NOT NULL UNIQUE,
    destination_zip_code VARCHAR(10)    NOT NULL,
    weight               NUMERIC(8,3)   NOT NULL,
    freight_value        NUMERIC(14,2)  NOT NULL,
    status               VARCHAR(20)    NOT NULL DEFAULT 'POSTADO',
    created_at           TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at           TIMESTAMP      NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_shipments_tracking_code ON shipments(tracking_code);
CREATE INDEX IF NOT EXISTS idx_shipments_order_id      ON shipments(order_id);
