CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL,
    created_date DATETIME,
    is_account_non_expired BIT NOT NULL,
    is_account_non_locked BIT NOT NULL,
    is_credentials_non_expired BIT NOT NULL,
    is_enabled BIT NOT NULL
);

CREATE TABLE notes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    summary VARCHAR(1000),
    content clob,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);