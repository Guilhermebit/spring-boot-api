CREATE TABLE db_api.sch_application.product (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    name TEXT NOT NULL,
    price_in_cents INTEGER NOT NULL,
    active BOOLEAN,
    user_id TEXT,
    CONSTRAINT fk_product_user FOREIGN KEY(user_id) REFERENCES db_api.sch_application.tb_user(id)
)