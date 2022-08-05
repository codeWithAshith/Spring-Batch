CREATE TABLE person
(
    id integer not null,
    name varchar(255) not null,
    location varchar(255),
    birth_date timestamp,
    primary key(id)
);

INSERT INTO PERSON (id, name, location, birth_date) VALUES (101, 'Ashith', 'Delhi', NOW() );
INSERT INTO PERSON (id, name, location, birth_date) VALUES (102, 'Ashith', 'Delhi', NOW() );