DROP TABLE user_roles IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE menu IF EXISTS;
DROP TABLE restaurants IF EXISTS;

CREATE TABLE  users
(
  id INTEGER GENERATED BY DEFAULT AS IDENTITY,
  name VARCHAR(100),
  email VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  enabled BOOLEAN DEFAULT TRUE,
);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role VARCHAR(50),
--   CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
  id INTEGER GENERATED BY DEFAULT AS IDENTITY,
  name VARCHAR(255),
  score INTEGER NULL_TO_DEFAULT
);

CREATE TABLE menu
(
  id INTEGER GENERATED BY DEFAULT AS IDENTITY,
  name VARCHAR(255),
  price LONGBLOB,
  restaurant_id INTEGER NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE
)