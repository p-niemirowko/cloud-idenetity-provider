CREATE TABLE app_user_roles
(
    app_user_id UUID NOT NULL,
    roles       VARCHAR(255)
);

CREATE TABLE users
(
    id       UUID NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE app_user_roles
    ADD CONSTRAINT fk_appuser_roles_on_app_user FOREIGN KEY (app_user_id) REFERENCES users (id);