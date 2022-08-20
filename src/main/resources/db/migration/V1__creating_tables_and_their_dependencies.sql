CREATE TABLE company (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(200) NOT NULL
);

CREATE TABLE customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    second_name VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    CHECK(0 <= age and age <= 150)
);

CREATE TABLE skill (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    department VARCHAR(50) NOT NULL,
    skill_level VARCHAR(50) NOT NULL
);

CREATE TABLE developer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    second_name VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    salary DECIMAL(7,2) NOT NULL,
    CHECK(0 <= age and age <= 150),
    CHECK(gender IN('MALE', 'FEMALE')),
    CHECK(0 <= salary)
);

CREATE TABLE developer_skill (
    developer_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    FOREIGN KEY(developer_id) REFERENCES developer(id) ON DELETE CASCADE,
    FOREIGN KEY(skill_id) REFERENCES skill(id) ON DELETE CASCADE
);

CREATE TABLE project (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    company_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    cost DECIMAL(7,2),
    creation_date DATE NOT NULL,
    FOREIGN KEY(company_id) REFERENCES company(id) ON DELETE CASCADE,
    FOREIGN KEY(customer_id) REFERENCES customer(id) ON DELETE CASCADE
);

CREATE TABLE project_developer (
    project_id BIGINT NOT NULL,
    developer_id BIGINT NOT NULL,
    FOREIGN KEY(project_id) REFERENCES project(id) ON DELETE CASCADE,
    FOREIGN KEY(developer_id) REFERENCES developer(id) ON DELETE CASCADE
);