CREATE TABLE user_role(
    id bigint NOT NULL AUTO_INCREMENT,
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES user(id),
    FOREIGN KEY(role_id) REFERENCES role(id)
);

INSERT INTO user_role (id, user_id, role_id) VALUES (1,1,1);