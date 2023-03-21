CREATE TABLE IF NOT EXISTS "user_details"
(
    id          SERIAL PRIMARY KEY,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    address     VARCHAR(50)
);
