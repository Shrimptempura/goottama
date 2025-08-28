CREATE TABLE IF NOT EXISTS roles (
    roles_id  int  PRIMARY KEY,
    roles_name   varchar(100)  not NULL,
    roles_description   varchar(2000)  not NULL
    );

CREATE TABLE IF NOT EXISTS user_detail (
    user_id   bigint AUTO_INCREMENT PRIMARY KEY,
    user_name   varchar(100)  not NULL,
    user_nickname   varchar(100)  not NULL,
    user_gender   enum('M', 'F')  not NULL,
    user_birth   varchar(100)  not NULL,
    user_created_at   timestamp  default current_timestamp,
    user_tel   varchar(100)  not NULL,
    user_zipcode   varchar(100)  not NULL,
    user_addr   varchar(500)  not NULL,
    user_email   varchar(100)  not NULL,
    user_img   varchar(100)   NULL,
    user_status enum('ACTIVE', 'SUSPENDED', 'DELETED') NULL,
    user_sanctions_until datetime NULL
);

CREATE TABLE IF NOT EXISTS user_login (
    login_id   varchar(100)  primary key,
    user_id   bigint  not NULL,
    roles_id   int   NOT NULL,
    user_password   varchar(100)  not NULL,
    CONSTRAINT fk_user_login_user
        FOREIGN key (user_id) references user_detail(user_id),
    CONSTRAINT fk_user_login_roles
        FOREIGN key (roles_id) references roles(roles_id)
);

CREATE TABLE company_detail (
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

