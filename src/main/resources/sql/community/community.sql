
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

-- 회원
CREATE TABLE user_detail (
   user_id   bigint   NULL,
   user_name   varchar(100)   NULL,
   user_nickname   varchar(100)   NULL,
   user_gender   enum('M', 'F')   NULL,
   user_birth   varchar(100)   NULL,
   user_created_at   timestamp   NULL,
   user_tel   varchar(100)   NULL,
   user_zipcode   varchar(100)   NULL,
   user_addr   varchar(500)   NULL,
   user_email   varchar(100)   NULL,
   user_img   varchar(100)   NULL
);

-- 쇼핑
CREATE TABLE category (
   category_id   int   NOT NULL,
   category_main   varchar(100)   NULL,
   category_sub   varchar(100)   NULL
);


-- 공통
CREATE TABLE report (
   report_id   bigint   NULL,
   user_id   bigint   NULL,
   report_date   timestamp   NULL,
   report_content   varchar(2000)   NULL,
   target_type   int   NULL,
   target_id   bigint   NOT NULL,
   report_status   enum('SHOP', 'COMMUNITY', 'INTERIOR', 'ADMIN')   NULL
);

-- 관리자
CREATE TABLE system_log (
   system_log_id   bigint   NOT NULL,
   system_log_level   varchar(100)   NULL,
   system_log_message   varchar(2000)   NULL,
   system_log_occured_at   timestamp   NULL,
   system_log_stack_trace   varchar(2000)   NULL
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

-- 공통
CREATE TABLE comments (
   comment_id   bigint   NULL,
   user_id   bigint   NULL,
   comment_content   varchar(500)   NULL,
   comment_date   timestamp   NULL,
   target_id   int   NULL,
   tartet_type   enum('SHOP', 'COMMUNITY', 'INTERIOR')   NULL
);

-- 관리자
CREATE TABLE user_activity (
   user_activity_id   bigint   NOT NULL,
   user_id   bigint   NULL,
   user_activity_type   varchar(100)   NULL,
   user_activity_tiem   timestamp   NULL,
   user_activity_target   varchar(100)   NULL,
   user_activity_details   varchar(2000)   NULL
);


-- 회원
CREATE TABLE email_verification (
   email_verification_id   bigint   NULL,
   user_email   varchar(100)   NULL,
   verification_code   varchar(100)   NULL,
   is_verified   boolean   NULL,
   created_at   timestamp   NULL,
   expires_at   timestamp   NULL
);

-- 회원
CREATE TABLE withdrawal_member (
   withdraw_id   bigint   NULL,
   withdrawal_reason_id   int   NULL,
   user_id   bigint   NULL,
   withdrawal_date   timestamp   NULL
);

-- 관리자
CREATE TABLE notices (
   notices_id   int   NOT NULL,
   notices_title   varchar(500)   NULL,
   notices_is_pinned   boolean   NULL,
   notices_created_at   timestamp   NULL,
   notices_file_path   varchar(100)   NULL,
   notices_content   varchar(2000)   NULL
);

-- 인테리어
CREATE TABLE partial_request (
   pr_id   bigint   NULL,
   location_id   varchar(500)   NOT NULL,
   user_id   bigint   NULL,
   partial_type   varchar(100)   NULL,
   partial_kind   varchar(100)   NULL,
   partial_area   varchar(100)   NULL,
   partial_addr   varchar(100)   NULL,
   partial_friend   varchar(100)   NULL
);

-- 인테리어
CREATE TABLE company_follow (
   company_follow_id   int   NULL,
   company_detail_id   bigint   NULL,
   user_id   bigint   NULL,
   company_follow_at   varchar(100)   NULL,
   company_share_at   varchar(100)   NULL
);

-- 회원
CREATE TABLE withdrawal_reason (
   withdrawal_reason_id   int   NULL,
   withdrawal_reason   varchar(500)   NULL
);

-- 회원
CREATE TABLE inquiry_way (
   inquiry_way_id   int   NOT NULL,
   inquiry_way   varchar(100)   NOT NULL
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

-- 공통
CREATE TABLE post (
   post_id   bigint   NULL,
   user_id   bigint   NULL,
   post_title   varchar(500)   NULL,
   post_content   varchar(2000)   NULL,
   post_date   timestamp   NULL,
   post_count   int   NULL,
   post_like_count   int   NULL,
   post_img   varchar(100)   NULL,
   target_type   enum('COMMUNITY', 'INTERIOR')   NULL,
   target_id   int   NULL
);

-- 회원
CREATE TABLE roles (
   roles_id   int   NOT NULL,
   roles_name   varchar(100)   NULL,
   roles_description   varchar(2000)   NULL
);

-- 인테리어
CREATE TABLE company_project (
   company_project_id   bigint   NOT NULL,
   company_id   bigint   NOT NULL,
   post_id   bigint   NULL,
   cp_like_count   int   NULL,
   cp_share   varchar(500)   NULL
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

-- 인테리어
CREATE TABLE location (
   location_id   varchar(500)   NOT NULL,
   location_lat   decimal(9, 5)   NULL,
   location_lng   decimal(9, 5)   NULL,
   location_ac   varchar(100)   NULL,
   location_limit   int   NULL
);

-- 회원
CREATE TABLE inquiry (
   inquiry_id   int   NOT NULL,
   user_id   bigint   NULL,
   inquiry_way_id   int   NOT NULL,
   user_inquiry   varchar(2000)   NULL,
   contact_value   varchar(100)   NULL,
   created_at   timestamp   NULL
);

-- 관리자
CREATE TABLE sanctions (
   sanctions_id   int   NOT NULL,
   user_id   bigint   NULL,
   sanctions_types   varchar(100)   NULL,
   sanctions_start_date   timestamp   NULL,
   sanctions_end_date   timestamp   NULL,
   sanctions_reason   varchar(2000)   NULL,
   admin_account_id   int   NULL,
   sanctions_created_at   timestamp   NULL
);

-- 인테리어
CREATE TABLE company (
   company_id   bigint   NOT NULL,
   company_detail_id   bigint   NULL,
   location_id   varchar(500)   NOT NULL,
   company_img   varchar(100)   NULL
);


-- 커뮤니티
CREATE TABLE community_comment (
   comment_id   bigint   NULL,
   number_of_likes   int   NULL,
   dislike_number   int   NULL
);

-- 인테리어
CREATE TABLE company_like (
   company_like_id   bigint   NOT NULL,
   company_project_id   bigint   NOT NULL,
   user_id   bigint   NULL
);

-- 관리자
CREATE TABLE admin_actions (
   admin_actions_id   int   NOT NULL,
   admin_actions_type   varchar(100)   NULL,
   admin_actions_target   varchar(100)   NULL,
   admin_actions_time   timestamp   NULL,
   admin_actions_details   varchar(2000)   NULL
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

-- 공통
CREATE TABLE review (
   review_id   bigint   NULL,
   user_id   bigint   NULL,
   review_title   varchar(500)   NULL,
   review_content   varchar(2000)   NULL,
   review_count   int   NULL,
   review_date   timestamp   NULL,
   review_modify   timestamp   NULL,
   review_img   varchar(100)   NULL,
   target_id   int   NULL,
   target_type   enum('SHOP', 'COMMUNITY', 'INTERIOR')   NULL
);

-- 회원
CREATE TABLE user_login (
   login_id   varchar(100)   NULL,
   user_id   bigint   NULL,
   roles_id   int   NOT NULL,
   user_password   varchar(100)   NULL
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

-- 인테리어
CREATE TABLE company_detail (
   company_detail_id   bigint   NULL,
   company_addr   varchar(500)   NULL,
   company_field   varchar(100)   NULL,
   company_license   varchar(100)   NULL,
   company_as   varchar(100)   NULL,
   company_career   varchar(100)   NULL,
   company_name   varchar(100)   NULL,
   company_rate   int   NULL,
   company_letter   varchar(500)   NULL
);

-- 공통
CREATE TABLE file (
   file_id   int   NULL,
   file_uploader   varchar(100)   NULL,
   file_name   varchar(500)   NULL,
   file_path   varchar(100)   NULL,
   target_type   enum('SHOP', 'COMMUNITY', 'INTERIOR', 'MEMBER')   NULL,
   target_id   bigint   NULL
);

/*
ALTER TABLE product ADD CONSTRAINT PK_PRODUCT PRIMARY KEY (
   product_id
);

ALTER TABLE user_detail ADD CONSTRAINT PK_USER_DETAIL PRIMARY KEY (
   user_id
);

ALTER TABLE category ADD CONSTRAINT PK_CATEGORY PRIMARY KEY (
   category_id
);

ALTER TABLE report ADD CONSTRAINT PK_REPORT PRIMARY KEY (
   report_id
);

ALTER TABLE system_log ADD CONSTRAINT PK_SYSTEM_LOG PRIMARY KEY (
   system_log_id
);

ALTER TABLE payment ADD CONSTRAINT PK_PAYMENT PRIMARY KEY (
   payment_id
);

ALTER TABLE comments ADD CONSTRAINT PK_COMMENTS PRIMARY KEY (
   comment_id
);

ALTER TABLE user_activity ADD CONSTRAINT PK_USER_ACTIVITY PRIMARY KEY (
   user_activity_id
);

ALTER TABLE email_verification ADD CONSTRAINT PK_EMAIL_VERIFICATION PRIMARY KEY (
   email_verification_id
);

ALTER TABLE withdrawal_member ADD CONSTRAINT PK_WITHDRAWAL_MEMBER PRIMARY KEY (
   withdraw_id
);

ALTER TABLE notices ADD CONSTRAINT PK_NOTICES PRIMARY KEY (
   notices_id
);

ALTER TABLE partial_request ADD CONSTRAINT PK_PARTIAL_REQUEST PRIMARY KEY (
   pr_id
);

ALTER TABLE company_follow ADD CONSTRAINT PK_COMPANY_FOLLOW PRIMARY KEY (
   company_follow_id
);

ALTER TABLE withdrawal _reason ADD CONSTRAINT PK_WITHDRAWAL _REASON PRIMARY KEY (
   withdrawal _reason_id
);

ALTER TABLE inquiry_way ADD CONSTRAINT PK_INQUIRY_WAY PRIMARY KEY (
   inquiry way_id
);

ALTER TABLE cart ADD CONSTRAINT PK_CART PRIMARY KEY (
   cart_id
);

ALTER TABLE deliver ADD CONSTRAINT PK_DELIVER PRIMARY KEY (
   deliver_id
);

ALTER TABLE post ADD CONSTRAINT PK_POST PRIMARY KEY (
   post_id
);

ALTER TABLE roles ADD CONSTRAINT PK_ROLES PRIMARY KEY (
   roles_id
);

ALTER TABLE company_project ADD CONSTRAINT PK_COMPANY_PROJECT PRIMARY KEY (
   company_project_id
);

ALTER TABLE orders ADD CONSTRAINT PK_ORDERS PRIMARY KEY (
   order_id
);

ALTER TABLE product_img ADD CONSTRAINT PK_PRODUCT_IMG PRIMARY KEY (
   product_img_id
);

ALTER TABLE location ADD CONSTRAINT PK_LOCATION PRIMARY KEY (
   location_id
);

ALTER TABLE inquiry ADD CONSTRAINT PK_INQUIRY PRIMARY KEY (
   inquiry_id
);

ALTER TABLE sanctions ADD CONSTRAINT PK_SANCTIONS PRIMARY KEY (
   sanctions_id
);

ALTER TABLE company ADD CONSTRAINT PK_COMPANY PRIMARY KEY (
   company_id
);

ALTER TABLE company_like ADD CONSTRAINT PK_COMPANY_LIKE PRIMARY KEY (
   company_like_id
);

ALTER TABLE admin_actions ADD CONSTRAINT PK_ADMIN_ACTIONS PRIMARY KEY (
   admin_actions_id
);

ALTER TABLE product_inquiry ADD CONSTRAINT PK_PRODUCT_INQUIRY PRIMARY KEY (
   pinquiry_id
);

ALTER TABLE review ADD CONSTRAINT PK_REVIEW PRIMARY KEY (
   review_id
);

ALTER TABLE user_login ADD CONSTRAINT PK_USER_LOGIN PRIMARY KEY (
   login_id
);

ALTER TABLE orders_products ADD CONSTRAINT PK_ORDERS_PRODUCTS PRIMARY KEY (
   po_id
);

ALTER TABLE company_detail ADD CONSTRAINT PK_COMPANY_DETAIL PRIMARY KEY (
   company_detail_id
);

ALTER TABLE file ADD CONSTRAINT PK_FILE PRIMARY KEY (
   file_id
);
*/
