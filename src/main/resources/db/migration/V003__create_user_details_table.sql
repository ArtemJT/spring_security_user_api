CREATE TABLE IF NOT EXISTS testDB.user_details
(
    id          SERIAL PRIMARY KEY,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    address     VARCHAR(50),
    user_id INT CONSTRAINT details_user_id REFERENCES testdb.users ON UPDATE CASCADE ON DELETE CASCADE
);
