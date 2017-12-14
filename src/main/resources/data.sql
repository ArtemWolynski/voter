DELETE FROM users;
DELETE FROM restaurants;


INSERT INTO users (name, email, password, enabled)
    VALUES ('Admin', 'admin@gmail.com', 'admin', 'true');

INSERT INTO users (name, email, password, enabled)
    VALUES ('User', 'user@gmail.com', 'user', 'true');



INSERT INTO restaurants (name)
    VALUES ('Test Restaurant 1');

INSERT INTO restaurants (name)
    VALUES ('Test Restaurant 2');