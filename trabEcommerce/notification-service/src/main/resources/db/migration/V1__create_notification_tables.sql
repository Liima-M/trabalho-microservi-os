CREATE TABLE IF NOT EXISTS notifications (
    id         UUID        NOT NULL PRIMARY KEY,
    user_id    UUID        NOT NULL,
    type       VARCHAR(30) NOT NULL,
    title      VARCHAR(255) NOT NULL,
    message    TEXT         NOT NULL,
    sent       BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- Idempotência: garante processamento exatamente uma vez por evento (RN16)
CREATE TABLE IF NOT EXISTS processed_events (
    event_id     VARCHAR(255) NOT NULL PRIMARY KEY,
    processed_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_notifications_user_id ON notifications(user_id);
CREATE INDEX IF NOT EXISTS idx_notifications_created ON notifications(created_at DESC);
