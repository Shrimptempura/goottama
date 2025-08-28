CREATE TABLE IF NOT EXISTS company_detail (
    company_detail_id   bigint   PRIMARY KEY AUTO_INCREMENT,
    company_name   varchar(100)   NOT NULL ,
    company_addr   varchar(500)   NOT NULL ,
    company_field   varchar(100) NOT NULL ,
    company_license   varchar(100)   NOT NULL ,
    company_as   varchar(100)   NOT NULL ,
    company_career   varchar(100)   NOT NULL ,
    company_intro   varchar(500)   NOT NULL,
    company_rate   float   NULL
);

CREATE TABLE location (
    location_id   BIGINT   PRIMARY KEY AUTO_INCREMENT,
    location_addr varchar(500) NOT NULL,
    location_lat   decimal(9, 5)   NULL,
    location_lng   decimal(9, 5)   NULL,
    location_ac   varchar(100)   NULL,
    location_limit   int   default 2000
);

CREATE TABLE company (
    company_id   bigint   PRIMARY KEY AUTO_INCREMENT,
    user_id bigint NOT NULL,
    company_detail_id   bigint   NOT NULL,
    location_id   bigint   NOT NULL,
    is_deleted TINYINT(1) DEFAULT 0 NOT NULL,

    CONSTRAINT fk_company_user
        FOREIGN KEY (user_id) REFERENCES user_detail(user_id),

    CONSTRAINT fk_company_detail
        FOREIGN KEY (company_detail_id) REFERENCES company_detail(company_detail_id),

    CONSTRAINT fk_company_location
        FOREIGN KEY (location_id) REFERENCES location(location_id)
);