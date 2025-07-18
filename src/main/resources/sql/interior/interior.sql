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
    cp_intro varchar(100) NULL,
    area_pyeong varchar(100) NULL,
    style varchar(100) NULL,
    construction_detail varchar(100) NULL
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
    company_field   varchar(100) NULL,
    company_license   varchar(100)   NULL,
    company_as   varchar(100)   NULL,
    company_career   varchar(100)   NULL,
    company_name   varchar(100)   NULL,
    company_rate   int   NULL,
    company_letter   varchar(500)   NULL
);

-- 인테리어
-- 공통 리뷰 테이블의 하위 테이블, 리뷰 점수 저장용
CREATE TABLE company_review (
    review_id bigint NOT NULL,
    communication_rate int NOT NULL,
    price_rate int NOT NULL,
    result_rate int NOT NULL,
    schedule_rate int NOT NULL,
    construction_field varchar(100) NOT NULL,
    area_pyeong varchar(100) NOT NULL,
    structure_type varchar(100) NOT NULL
);


-- 인테리어
-- company_review_score에서 얻은 점수를 총합 업체에 대한 평균 점수 저장
CREATE TABLE company_score_avg (
    company_id bigint NOT NULL,
    avg_communication float NOT NULL,
    avg_price float NOT NULL,
    avg_result float NOT NULL,
    avg_schedule float NOT NULL,
    avg_total_rate float NOT NULL
);