CREATE TABLE IF NOT EXISTS testDB."user"
(
    id         SERIAL PRIMARY KEY,
    user_name  VARCHAR(255) NOT NULL UNIQUE,
    pass       VARCHAR(120) NOT NULL,
    user_details_id INT,
    FOREIGN KEY (user_details_id) REFERENCES testDB.user_details (id) ON DELETE CASCADE ON UPDATE CASCADE
);
