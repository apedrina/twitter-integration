CREATE TABLE audit
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    message_id BIGINT NOT NULL,
    harvest_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE message
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    text VARCHAR(300),
    is_printed VARCHAR(1) DEFAULT '0',
    author_id BIGINT
);

CREATE TABLE author
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    user_id VARCHAR,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name VARCHAR,
    screen_name VARCHAR
);