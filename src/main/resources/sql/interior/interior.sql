-- 인테리어
-- 맞춤 시공 요청 테이블
CREATE TABLE partial_request (
    pr_id   bigint   PRIMARY KEY AUTO_INCREMENT,
    location_id   varchar(500)   NOT NULL,
    user_id   bigint   NOT NULL,
    partial_type   varchar(100)   NOT NULL,
    partial_kind   varchar(100)   NOT NULL,
    partial_area   varchar(100)   NOT NULL,
    partial_addr   varchar(100)   NOT NULL,
    partial_friend   varchar(100)   NOT NULL,

    CONSTRAINT fk_partial_request_location
        FOREIGN KEY (location_id) REFERENCES location(location_id),

    CONSTRAINT fk_partial_request_user
        FOREIGN KEY (user_id) REFERENCES user_detail(user_id)
);

-- 인테리어
-- 업체 팔로우 테이블
-- pk: company_follow_id, fk: company_id, user_id
CREATE TABLE company_follow (
    company_follow_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    company_id   bigint   NOT NULL,
    user_id   bigint   NOT NULL,
    follow_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_company_follow_user
        FOREIGN KEY (user_id) REFERENCES user_detail(user_id),

    CONSTRAINT fk_company_follow_company
        FOREIGN KEY (company_id) REFERENCES company(company_id),

    UNIQUE KEY unique_follow(user_id, company_id)
);

-- 인테리어
-- 업체 좋아요 테이블
CREATE TABLE company_like (
    company_like_id   bigint   PRIMARY KEY AUTO_INCREMENT,
    company_project_id   bigint   NOT NULL,
    user_id   bigint   NOT NULL,

    CONSTRAINT fk_company_like_project
        FOREIGN KEY (company_project_id) REFERENCES  company_project(company_project_id),

    CONSTRAINT fk_company_like_user
        FOREIGN KEY (user_id) REFERENCES user_detail(user_id)
);

-- 회원
-- pk: user_id
-- 인테리어에서 가져다 쓸 fk, 참고용
CREATE TABLE user_detail (
    user_id   bigint   PRIMARY KEY AUTO_INCREMENT,
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


-- 인테리어
-- 업체 포트폴리오(게시글) 테이블
-- pk: company_project_id, fk: company_id, post_id
CREATE TABLE company_project (
    company_project_id   bigint   PRIMARY KEY AUTO_INCREMENT,
    company_id   bigint   NOT NULL,
    post_id   bigint   NOT NULL,

    space_type varchar(100) NOT NULL,
    area_pyeong varchar(100) NOT NULL,
    style varchar(100) NOT NULL,
    construction_detail varchar(100) NOT NULL,

    CONSTRAINT fk_company_project_post
        FOREIGN KEY (post_id) REFERENCES post(post_id),

    CONSTRAINT fk_company_project_company
        FOREIGN KEY (company_id) REFERENCES company(company_id)
);

-- 인테리어
-- 위치 테이블
CREATE TABLE location (
    location_id   BIGINT   PRIMARY KEY AUTO_INCREMENT,
    location_lat   decimal(9, 5)   NULL,
    location_lng   decimal(9, 5)   NULL,
    location_ac   varchar(100)   NULL,
    location_limit   int   NULL
);

-- fk 문법
-- FOREIGN KEY (현재 테이블의 칼럼)
-- REFERENCES 대상 테이블(대상 테이블의 칼럼)

-- 인테리어
-- 업체 테이블
-- pk: company_id, fk: user_id, company_detail_id
CREATE TABLE company (
    company_id   bigint   PRIMARY KEY AUTO_INCREMENT,
    user_id bigint NOT NULL,
    company_detail_id   bigint   NOT NULL,
    location_id   varchar(500)   NOT NULL,
    company_img   varchar(100)   NULL,

    CONSTRAINT fk_company_user
        FOREIGN KEY (user_id) REFERENCES user_detail(user_id),

    CONSTRAINT fk_company_detail
        FOREIGN KEY (company_detail_id) REFERENCES company_detail(company_detail_id),

    CONSTRAINT fk_company_location
        FOREIGN KEY (location_id) REFERENCES location(location_id)
);

-- 인테리어
-- 업체 상세정보 테이블
-- pk: company_detail_id
CREATE TABLE company_detail (
    company_detail_id   bigint   PRIMARY KEY AUTO_INCREMENT,
    company_addr   varchar(500)   NOT NULL ,
    company_field   varchar(100) NOT NULL ,
    company_license   varchar(100)   NOT NULL ,
    company_as   varchar(100)   NOT NULL ,
    company_career   varchar(100)   NOT NULL ,
    company_name   varchar(100)   NOT NULL ,
    company_intro   varchar(500)   NOT NULL,
    company_rate   float   NULL
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

-- 인테리어
-- 공통 리뷰 테이블의 하위 테이블, 리뷰 점수 저장용, 유저 한명당 입력값이므로 점수는 정수형
-- pk: review_id, fk: review_id
CREATE TABLE company_review (
    review_id bigint PRIMARY KEY,
    communication_rate int NOT NULL,
    price_rate int NOT NULL,
    result_rate int NOT NULL,
    schedule_rate int NOT NULL,
    construction_field varchar(100) NOT NULL,
    area_pyeong varchar(100) NOT NULL,
    structure_type varchar(100) NOT NULL,

    CONSTRAINT fk_company_review
        FOREIGN KEY (review_id) REFERENCES review(review_id)
);


-- 인테리어
-- company_review_score에서 얻은 점수를 총합 업체에 대한 평균 점수 저장
-- pk: company_id, fk: company_id
CREATE TABLE company_score_avg (
    company_id bigint PRIMARY KEY,
    avg_communication float NOT NULL,
    avg_price float NOT NULL,
    avg_result float NOT NULL,
    avg_schedule float NOT NULL,
    avg_total_rate float NOT NULL,

    CONSTRAINT fk_company_socre
        FOREIGN KEY (company_id) REFERENCES company(company_id)
);