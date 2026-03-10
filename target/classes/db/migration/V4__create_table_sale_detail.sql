CREATE TABLE IF NOT EXISTS public.sale_detail (
    id BIGSERIAL PRIMARY KEY,
    sale_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER,
    price DOUBLE PRECISION,
    CONSTRAINT fk_sale_detail_sale FOREIGN KEY (sale_id) REFERENCES public.sale(id) ON DELETE CASCADE,
    CONSTRAINT fk_sale_detail_product FOREIGN KEY (product_id) REFERENCES public.product(id)
);

