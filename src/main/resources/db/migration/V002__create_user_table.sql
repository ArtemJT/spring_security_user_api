CREATE TABLE IF NOT EXISTS testDB.users
(
    id         SERIAL PRIMARY KEY,
    user_name  VARCHAR(255) NOT NULL UNIQUE,
    pass       VARCHAR(120) NOT NULL
);
