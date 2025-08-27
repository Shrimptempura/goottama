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

