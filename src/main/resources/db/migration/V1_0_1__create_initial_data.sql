INSERT INTO TBL_PERSON (PHONE,NAME) VALUES ('15 992231122', 'Admin');

INSERT INTO TBL_USER (EMAIL, PASSWORD, PERSON_ID) VALUES ('admin@abutua.com','123456',  1);

INSERT INTO TBL_ROLE (ROLE) VALUES ('ROLE_ADMIN');
INSERT INTO TBL_ROLE (ROLE) VALUES ('ROLE_OPERATOR');

INSERT INTO TBL_USER_ROLE (ROLE_ID, USER_ID) VALUES (1, 1);
INSERT INTO TBL_USER_ROLE (ROLE_ID, USER_ID) VALUES (2, 1);
