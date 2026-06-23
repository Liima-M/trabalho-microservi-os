CREATE TABLE IF NOT EXISTS categories (
    id          UUID         NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE IF NOT EXISTS products (
    id          UUID           NOT NULL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    description TEXT           NOT NULL,
    price       NUMERIC(14,2)  NOT NULL CHECK (price >= 0),
    category_id UUID           NOT NULL REFERENCES categories(id),
    seller_id   UUID           NOT NULL,
    active      BOOLEAN        NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP      NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS product_images (
    id         SERIAL  NOT NULL PRIMARY KEY,
    product_id UUID    NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    url        TEXT    NOT NULL,
    position   INTEGER NOT NULL DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_products_category  ON products(category_id);
CREATE INDEX IF NOT EXISTS idx_products_seller    ON products(seller_id);
CREATE INDEX IF NOT EXISTS idx_products_name      ON products USING gin(to_tsvector('portuguese', name));

-- Categorias iniciais (RN20 — apenas gestor cria categorias)
INSERT INTO categories (id, name, description) VALUES
    ('00000000-0000-0000-0000-000000000001', 'Eletrônicos',    'Produtos eletrônicos em geral'),
    ('00000000-0000-0000-0000-000000000002', 'Moda',           'Roupas, calçados e acessórios'),
    ('00000000-0000-0000-0000-000000000003', 'Casa e Jardim',  'Itens para casa e jardim'),
    ('00000000-0000-0000-0000-000000000004', 'Esportes',       'Artigos esportivos'),
    ('00000000-0000-0000-0000-000000000005', 'Livros',         'Livros e publicações')
ON CONFLICT DO NOTHING;
