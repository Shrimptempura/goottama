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

CREATE TABLE email_verification (
   email_verification_id   bigint   NULL,
   user_email   varchar(100)   NULL,
   verification_code   varchar(100)   NULL,
   is_verified   boolean   NULL,
   created_at   timestamp   NULL,
   expires_at   timestamp   NULL
);

CREATE TABLE withdrawal_member (
   withdraw_id   bigint   NULL,
   withdrawal_reason_id   int   NULL,
   user_id   bigint   NULL,
   withdrawal_date   timestamp   NULL
);

CREATE TABLE withdrawal_reason (
   withdrawal_reason_id   int   NULL,
   withdrawal_reason   varchar(500)   NULL
);

CREATE TABLE inquiry_way (
   inquiry_way_id   int   NOT NULL,
   inquiry_way   varchar(100)   NOT NULL
);

CREATE TABLE roles (
   roles_id   int   NOT NULL,
   roles_name   varchar(100)   NULL,
   roles_description   varchar(2000)   NULL
);

CREATE TABLE inquiry (
   inquiry_id   int   NOT NULL,
   user_id   bigint   NULL,
   inquiry_way_id   int   NOT NULL,
   user_inquiry   varchar(2000)   NULL,
   contact_value   varchar(100)   NULL,
   created_at   timestamp   NULL
);

CREATE TABLE user_login (
   login_id   varchar(100)   NULL,
   user_id   bigint   NULL,
   roles_id   int   NOT NULL,
   user_password   varchar(100)   NULL
);

