CREATE TABLE report (
  report_id bigint(20) DEFAULT NULL,
  user_id bigint(20) DEFAULT NULL,
  report_date timestamp NULL DEFAULT NULL,
  report_content varchar(2000) DEFAULT NULL,
  target_type int(11) DEFAULT NULL,
  target_id bigint(20) DEFAULT NULL,
  report_status enum('PENDING','REVIEWING','APPROVED','REJECTED', 'CLOSED') DEFAULT 'PENDING' NOT NULL,

  CONSTRAINT fk_reporter FOREIGN key (user_id) REFERENCES user_detail(user_id)
);

-- 공통
-- pk: comment_id, fk: user_id
CREATE TABLE comments (
    comment_id   bigint  PRIMARY KEY AUTO_INCREMENT,
    user_id   bigint   NOT NULL,
    comment_content   varchar(500)   NULL,
    comment_date   timestamp   DEFAULT CURRENT_TIMESTAMP,
    target_id   BIGINT  NOT  NULL,
    target_type   enum('SHOP', 'COMMUNITY', 'INTERIOR')   NULL,

    CONSTRAINT fk_comments_user FOREIGN KEY (user_id) REFERENCES user_detail(user_id)
);

-- 공통
-- pk: post_id, fk: user_id
-- enum: COMMUNITY, INTERIOR
-- post_img: 1장이면 ok, 여러장이면 file 테이블의 target_id,type으로 처리
CREATE TABLE post (
    post_id   BIGINT   PRIMARY KEY AUTO_INCREMENT,
    user_id   BIGINT   NOT NULL ,
    post_title   varchar(500)   NOT NULL ,
    post_content   varchar(2000)   NULL,
    post_date   timestamp DEFAULT CURRENT_TIMESTAMP,
    post_count   INT DEFAULT 0,
    post_like_count   INT DEFAULT 0,
    post_img   varchar(100)   NULL,
    target_type   enum('COMMUNITY', 'INTERIOR')   NOT NULL,
    target_id   BIGINT   NULL,

    CONSTRAINT fk_post_user
        FOREIGN KEY (user_id) REFERENCES user_detail(user_id)
);

-- 공통
-- pk: review_id, fk: user_id
CREATE TABLE review (
    review_id   bigint   PRIMARY KEY AUTO_INCREMENT,
    user_id   bigint   NOT NULL,
    review_title   varchar(500)   NULL,
    review_content   varchar(2000)   NULL,
    review_count   INT DEFAULT 0,
    review_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    review_modify   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    review_img   varchar(100)   NULL,
    target_id   BIGINT   NOT NULL,
    target_type   enum('SHOP', 'COMMUNITY', 'INTERIOR')   NOT NULL,

    CONSTRAINT fk_review_user
        FOREIGN KEY (user_id) REFERENCES user_detail(user_id)
);

-- 공통
CREATE TABLE file (
    file_id   BIGINT   PRIMARY KEY AUTO_INCREMENT,
    file_uploader   varchar(100) NOT NULL ,
    file_name   varchar(500)  NOT NULL,
    file_path   varchar(100)  NOT NULL,
    target_type    enum('COMMUNITY', 'INTERIOR','MEMBER','SHOP','ADMIN')   NOT NULL,
    target_id   BIGINT  NOT NULL
);