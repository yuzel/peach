CREATE TABLE database_configurations
(
    id int(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    host varchar(20) NOT NULL,
    port varchar(5) NOT NULL,
    `database` varchar(10) NOT NULL,
    user_name varchar(10) NOT NULL,
    password varchar(20),
    type tinyint(1) NOT NULL COMMENT '1:mysql;2.oracle;',
    created_date timestamp DEFAULT current_timestamp,
    updated_date timestamp DEFAULT current_timestamp on update current_timestamp
);