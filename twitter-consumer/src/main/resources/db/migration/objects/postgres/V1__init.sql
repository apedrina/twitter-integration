CREATE TABLE audit
(
    id SERIAL NOT NULL ,
    message_id BIGINT NOT NULL,
    harvest_date DATE DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE message
(
    id SERIAL NOT NULL ,
    create_date DATE DEFAULT NULL,
    text VARCHAR,
    author_id BIGINT,
    is_printed VARCHAR(1) DEFAULT '0',
    PRIMARY KEY (id)
);

CREATE TABLE author
(
    id SERIAL NOT NULL ,
    user_id VARCHAR,
    create_date DATE DEFAULT NULL,
    name VARCHAR,
    screen_name VARCHAR,
    PRIMARY KEY (id)
);