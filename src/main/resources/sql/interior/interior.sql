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

-- 인테리어
CREATE TABLE company_project (
   company_project_id   bigint   NOT NULL,
   company_id   bigint   NOT NULL,
   post_id   bigint   NULL,
   cp_like_count   int   NULL,
   cp_share   varchar(500)   NULL
);

-- 인테리어
CREATE TABLE location (
   location_id   varchar(500)   NOT NULL,
   location_lat   decimal(9, 5)   NULL,
   location_lng   decimal(9, 5)   NULL,
   location_ac   varchar(100)   NULL,
   location_limit   int   NULL
);

-- 인테리어
CREATE TABLE company (
   company_id   bigint   NOT NULL,
   company_detail_id   bigint   NULL,
   location_id   varchar(500)   NOT NULL,
   company_img   varchar(100)   NULL
);

-- 인테리어
CREATE TABLE company_like (
   company_like_id   bigint   NOT NULL,
   company_project_id   bigint   NOT NULL,
   user_id   bigint   NULL
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