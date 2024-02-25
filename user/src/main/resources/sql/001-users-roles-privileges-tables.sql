DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    id       VARCHAR(36) PRIMARY KEY NOT NULL UNIQUE,
    username VARCHAR(25)             NOT NULL UNIQUE,
    email    VARCHAR(255)            NOT NULL UNIQUE,
    password TEXT                    NOT NULL,
    enabled  BOOLEAN
);

DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE roles
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(255) NOT NULL UNIQUE,
    value VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS tokens;
CREATE TABLE tokens
(
    id      VARCHAR(36) PRIMARY KEY NOT NULL UNIQUE,
    user_id VARCHAR(36)  NOT NULL,
    token   TEXT         NOT NULL UNIQUE,
    type    VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO roles (id, name, value)
VALUES (1, 'ROLE_ADMIN', 'Administrateur'),
       (2, 'ROLE_USER', 'Utilisateur');

DROP TABLE IF EXISTS privileges CASCADE;
CREATE TABLE privileges
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    value       VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL
);

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles
(
    user_id VARCHAR(36) NOT NULL,
    role_id INT         NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

DROP TABLE IF EXISTS roles_privileges;
CREATE TABLE roles_privileges
(
    role_id      INT NOT NULL,
    privilege_id INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles (id),
    FOREIGN KEY (privilege_id) REFERENCES privileges (id)
);

INSERT INTO users (id, username, email, password, enabled)
VALUES ('21b6ab22-74dc-11ee-b962-0242ac120002', 'admin', 'admin@clea.com',
        '$2a$10$9TzlAROjyvk4j9X2PSDPaOFotuBxzn64bmn0vJPWQDkh84QnFwpWq', true),
       ('2d4ae552-74dc-11ee-b962-0242ac120002', 'user', 'user@clea.com',
        '$2a$10$WgSr/JmRcIJMaMKUUcsvMezsXQj8kMmkWRteckcL6.4Zl88DFMg3.', true);

INSERT INTO users_roles (user_id, role_id)
VALUES ('21b6ab22-74dc-11ee-b962-0242ac120002', 1),
       ('2d4ae552-74dc-11ee-b962-0242ac120002', 2);