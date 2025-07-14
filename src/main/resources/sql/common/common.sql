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

-- 공통
CREATE TABLE comments (
   comment_id   bigint   NULL,
   user_id   bigint   NULL,
   comment_content   varchar(500)   NULL,
   comment_date   timestamp   NULL,
   target_id   int   NULL,
   tartet_type   enum('SHOP', 'COMMUNITY', 'INTERIOR')   NULL
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