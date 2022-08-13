CREATE TABLE company (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(200) NOT NULL
);

CREATE TABLE customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    second_name VARCHAR(50) NOT NULL,
    age INT,
    CHECK(0 <= age and age <= 150)
);

CREATE TABLE skill (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    department VARCHAR(50),
    skill_level VARCHAR(50)
);