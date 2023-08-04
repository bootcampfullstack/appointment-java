INSERT INTO TBL_PERSON (PHONE,NAME) VALUES ('15 992231122', 'Ana Maria');
INSERT INTO TBL_PERSON (PHONE,NAME) VALUES ('15 923233212', 'Pedro Silva');
INSERT INTO TBL_PERSON (PHONE,NAME) VALUES ('11 902324322', 'Marco Nunes');
INSERT INTO TBL_PERSON (PHONE,NAME) VALUES ('13 999216212', 'Marcelo Silva');
INSERT INTO TBL_PERSON (PHONE,NAME) VALUES ('13 999216212', 'Fernanda Cruz');
INSERT INTO TBL_PERSON (PHONE,NAME) VALUES ('13 999216212', 'Lucia Gomes');
INSERT INTO TBL_PERSON (PHONE,NAME) VALUES ('13 999216212', 'Murilio Aparecido');
INSERT INTO TBL_PERSON (PHONE,NAME) VALUES ('13 999216212', 'João Antunes da Cruz');
INSERT INTO TBL_PERSON (PHONE,NAME) VALUES ('13 999216212', 'Marcos Santos Rosa');

INSERT INTO TBL_CLIENT (DATE_OF_BIRTH, PERSON_ID) VALUES (DATE '2000-08-02', 1);
INSERT INTO TBL_CLIENT (DATE_OF_BIRTH, PERSON_ID) VALUES (DATE '1998-01-22', 2);
INSERT INTO TBL_CLIENT (DATE_OF_BIRTH, PERSON_ID) VALUES (DATE '1998-01-22', 3);

INSERT INTO TBL_PROFESSIONAL (ACTIVE, PERSON_ID) VALUES (TRUE,  4);
INSERT INTO TBL_PROFESSIONAL (ACTIVE, PERSON_ID) VALUES (TRUE,  5);
INSERT INTO TBL_PROFESSIONAL (ACTIVE, PERSON_ID) VALUES (FALSE, 6);

INSERT INTO TBL_USER (EMAIL, PASSWORD, PERSON_ID) VALUES ('murilo@gmail.com','123456',  7);
INSERT INTO TBL_USER (EMAIL, PASSWORD, PERSON_ID) VALUES ('joao@gmail.com','123456',    8);
INSERT INTO TBL_USER (EMAIL, PASSWORD, PERSON_ID) VALUES ('marcos@gmail.com','123456',  9);

INSERT INTO TBL_ROLE (ROLE) VALUES ('ROLE_ADMIN');
INSERT INTO TBL_ROLE (ROLE) VALUES ('ROLE_OPERATOR');

INSERT INTO TBL_USER_ROLE (ROLE_ID, USER_ID) VALUES (1, 7);
INSERT INTO TBL_USER_ROLE (ROLE_ID, USER_ID) VALUES (2, 7);
INSERT INTO TBL_USER_ROLE (ROLE_ID, USER_ID) VALUES (2, 8);
INSERT INTO TBL_USER_ROLE (ROLE_ID, USER_ID) VALUES (2, 9);

INSERT INTO TBL_AREA (NAME) VALUES ('Fisioterapia');
INSERT INTO TBL_AREA (NAME) VALUES ('Terapia Ocupacional');
INSERT INTO TBL_AREA (NAME) VALUES ('Clínica Médica');

INSERT INTO TBL_AREA_PROFESSIONAL (AREA_ID, PROFESSIONAL_ID) VALUES (1,4);
INSERT INTO TBL_AREA_PROFESSIONAL (AREA_ID, PROFESSIONAL_ID) VALUES (1,5);
INSERT INTO TBL_AREA_PROFESSIONAL (AREA_ID, PROFESSIONAL_ID) VALUES (2,5);
INSERT INTO TBL_AREA_PROFESSIONAL (AREA_ID, PROFESSIONAL_ID) VALUES (2,6);
INSERT INTO TBL_AREA_PROFESSIONAL (AREA_ID, PROFESSIONAL_ID) VALUES (3,6);
INSERT INTO TBL_AREA_PROFESSIONAL (AREA_ID, PROFESSIONAL_ID) VALUES (3,4);

INSERT INTO TBL_APPOINTMENT_TYPE (TYPE) VALUES ('Particular');
INSERT INTO TBL_APPOINTMENT_TYPE (TYPE) VALUES ('Convênio Prefeitura');
INSERT INTO TBL_APPOINTMENT_TYPE (TYPE) VALUES ('Convênio Plano Azul');

-- SUNDAY = 1, MONDAY = 2, .... SATURDAY = 7
INSERT INTO TBL_WORK_SCHEDULE_ITEM (DAY_OF_WEEK,START_TIME,END_TIME, SLOTS, SLOT_SIZE, PROFESSIONAL_ID)
    VALUES (2,'08:00:00-03','10:00:00-03', 4, 30, 4);


INSERT INTO TBL_WORK_SCHEDULE_ITEM (DAY_OF_WEEK,START_TIME,END_TIME, SLOTS, SLOT_SIZE, PROFESSIONAL_ID)
    VALUES (3,'08:00:00-03','12:00:00-03', 8, 30, 4);

INSERT INTO TBL_WORK_SCHEDULE_ITEM (DAY_OF_WEEK,START_TIME,END_TIME, SLOTS, SLOT_SIZE, PROFESSIONAL_ID) 
    VALUES (3,'14:00:00-03','18:00:00-03', 8, 30, 4);


INSERT INTO TBL_WORK_SCHEDULE_ITEM (DAY_OF_WEEK,START_TIME,END_TIME, SLOTS, SLOT_SIZE, PROFESSIONAL_ID)
    VALUES (2,'08:00:00-03','12:00:00-03', 8, 30, 5);

INSERT INTO TBL_WORK_SCHEDULE_ITEM (DAY_OF_WEEK,START_TIME,END_TIME, SLOTS, SLOT_SIZE, PROFESSIONAL_ID) 
    VALUES (2,'14:00:00-03','18:00:00-03', 8, 30, 5);

INSERT INTO TBL_WORK_SCHEDULE_ITEM (DAY_OF_WEEK,START_TIME,END_TIME, SLOTS, SLOT_SIZE, PROFESSIONAL_ID) 
    VALUES (3,'14:00:00-03','18:00:00-03', 8, 30, 5);


INSERT INTO TBL_APPOINTMENT (DATE, START_TIME, END_TIME, STATUS, COMMENTS, CLIENT_ID, PROFESSIONAL_ID, AREA_ID, APPOINTMENT_TYPE_ID) 
    VALUES ( '2024-04-08 00:00:00-03', '08:00:00-03', '08:30:00-03', 'OPEN', 'Parcelar em 3x', 1, 4, 1, 1);

INSERT INTO TBL_APPOINTMENT (DATE, START_TIME, END_TIME, STATUS, COMMENTS, CLIENT_ID, PROFESSIONAL_ID, AREA_ID, APPOINTMENT_TYPE_ID) 
    VALUES ( '2024-04-08 00:00:00-03', '08:30:00-03', '09:00:00-03', 'OPEN', 'Parcelar em 3x', 1, 4, 1, 1);

INSERT INTO TBL_APPOINTMENT (DATE, START_TIME, END_TIME, STATUS, COMMENTS, CLIENT_ID, PROFESSIONAL_ID, AREA_ID, APPOINTMENT_TYPE_ID) 
    VALUES ( '2024-04-08 00:00:00-03', '09:00:00-03', '09:30:00-03', 'OPEN', 'Parcelar em 3x', 1, 4, 1, 1);

INSERT INTO TBL_APPOINTMENT (DATE, START_TIME, END_TIME, STATUS, COMMENTS, CLIENT_ID, PROFESSIONAL_ID, AREA_ID, APPOINTMENT_TYPE_ID) 
    VALUES ( '2024-04-08 00:00:00-03', '09:30:00-03', '10:00:00-03', 'OPEN', '', 2, 4, 1, 2);

INSERT INTO TBL_APPOINTMENT (DATE, START_TIME, END_TIME, STATUS, COMMENTS, CLIENT_ID, PROFESSIONAL_ID, AREA_ID, APPOINTMENT_TYPE_ID) 
    VALUES ( '2024-04-15 00:00:00-03', '09:30:00-03', '10:00:00-03', 'OPEN', 'Ligar para confirmar', 3, 4, 3, 3);

