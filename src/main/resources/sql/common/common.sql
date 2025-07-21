-- 공통
CREATE TABLE report (
  report_id bigint(20) DEFAULT NULL,
  user_id bigint(20) DEFAULT NULL,
  report_date timestamp NULL DEFAULT NULL,
  report_content varchar(2000) DEFAULT NULL,
  target_type int(11) DEFAULT NULL,
  target_id bigint(20) DEFAULT NULL,
  report_status enum('PENDING','REVIEWING','APPROVED','REJECTED', 'CLOSED') DEFAULT 'PENDING' NOT NULL

  CONSTRAINT fk_reporter FOREIGN key (user_id) REFERENCES user_detail(user_id)
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

-- 공통
CREATE TABLE file (
   file_id   int   NULL,
   file_uploader   varchar(100)   NULL,
   file_name   varchar(500)   NULL,
   file_path   varchar(100)   NULL,
   target_type   enum('SHOP', 'COMMUNITY', 'INTERIOR', 'MEMBER')   NULL,
   target_id   bigint   NULL
);