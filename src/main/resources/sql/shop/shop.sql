-- 쇼핑
CREATE TABLE product (
   product_id   bigint   NOT NULL,
   user_id   bigint   NULL,
   category_id   int   NOT NULL,
   product_name   varchar(500)   NULL,
   product_price   int   NULL,
   product_discountrate   decimal(5,2)   NULL,
   product_mall_name   varchar(500)   NULL,
   product_madein   varchar(100)   NULL,
   product_release   timestamp   NULL,
   product_as_manager_phone   varchar(100)   NULL,
   product_type   varchar(100)   NULL,
   product_color   varchar(100)   NULL,
   product_istoday   varchar(100)   NULL,
   product_date   timestamp   NULL
);

-- 쇼핑
CREATE TABLE payment (
   payment_id   bigint   NOT NULL,
   order_id   bigint   NOT NULL,
   payment_type   varchar(100)   NULL,
   payment_date   timestamp   NULL,
   payment_status   varchar(100)   NULL,
   payment_price   int   NULL
);

-- 쇼핑
CREATE TABLE cart (
   cart_id   int   NOT NULL,
   user_id   bigint   NULL,
   product_id   bigint   NOT NULL,
   cart_quantity   int   NULL,
   cart_date   timestamp   NULL
);

-- 쇼핑
CREATE TABLE deliver (
   deliver_id   bigint   NOT NULL,
   order_id   bigint   NOT NULL,
   deliver_name   varchar(100)   NULL,
   deliver_person   varchar(100)   NULL,
   deliver_recipient_phone   varchar(100)   NULL,
   deliver_loc   varchar(500)   NULL,
   deliver_detail_loc   varchar(100)   NULL,
   deliver_request   varchar(100)   NULL,
   deliver_status   varchar(100)   NULL,
   deliver_date   timestamp   NULL
);

-- 쇼핑
CREATE TABLE orders (
   order_id   bigint   NOT NULL,
   user_id   bigint   NULL,
   order_date   timestamp   NULL,
   order_status   varchar(100)   NULL,
   order_totalprice   int   NULL
);

-- 쇼핑
CREATE TABLE product_img (
   product_img_id   bigint   NOT NULL,
   product_id   bigint   NOT NULL,
   product_imgurl   varchar(200)   NULL,
   product_img_type   varchar(100)  NULL
);

-- 쇼핑
CREATE TABLE product_inquiry (
   pinquiry_id   int   NOT NULL,
   product_id   bigint   NOT NULL,
   pinquiry_content   varchar(2000)   NULL,
   pinquiry_date   timestamp   NULL,
   pinquiry_group   int   NULL,
   pinquiry_step   int   NULL,
   pinquiry_indent   int   NULL
);

-- 쇼핑
CREATE TABLE orders_products (
   po_id   bigint   NOT NULL,
   order_id   bigint   NOT NULL,
   product_id   bigint   NOT NULL,
   op_quantity   int   NULL,
   op_price   int   NULL,
   op_date   timestamp   NULL,
   op_status   varchar(100)   NULL,
   op_totalprice   int   NULL
);

-- 쇼핑
CREATE TABLE category (
   category_id   int   NOT NULL,
   category_main   varchar(100)   NULL,
   category_sub   varchar(100)   NULL
);

