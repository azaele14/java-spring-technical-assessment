CREATE TABLE IF NOT EXISTS public.sale (
    id BIGSERIAL PRIMARY KEY,
    date DATE,
    status VARCHAR(255),
    total DOUBLE PRECISION,
    store_id BIGINT,
    CONSTRAINT fk_sale_store FOREIGN KEY (store_id) REFERENCES public.store(id)
);

