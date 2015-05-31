--
drop table AIRLINE cascade constraints; 
drop table AIRPORT cascade constraints; 
drop table ADDRESS cascade constraints; 
drop table CITY cascade constraints; 
drop table FRIENDS cascade constraints; 
drop table PREFERENCE cascade constraints; 
drop table TRIP cascade constraints; 
drop table FLIGHT cascade constraints; 
drop table TRIP_FLIGHT cascade constraints; 
drop table USER_INFO cascade constraints; 
drop table USER_PREFERENCE cascade constraints; 
drop table USER_TRIP cascade constraints;
drop table USERS cascade constraints; 
--DROP SEQUENCE users_seq;

CREATE TABLE AIRPORT
 (
    airport_id INTEGER NOT NULL,
    airport_name VARCHAR2 (255) NOT NULL,
    city VARCHAR2 (255) NOT NULL,
    country VARCHAR2 (255) NOT NULL,
    iata_faa_code VARCHAR2 (3),
    icao_code VARCHAR2 (4),
    latitude NUMBER NOT NULL,
    longitude NUMBER NOT NULL,
    altitude NUMBER NOT NULL,
    timezone NUMBER NOT NULL,
    dst VARCHAR2 (1) NOT NULL,
    tzone_db_time VARCHAR2(255) NOT NULL,
    rating_num  INTEGER ,
    rating_sum  INTEGER
  ) ;
ALTER TABLE AIRPORT ADD CONSTRAINT AIRPORT_PK PRIMARY KEY ( airport_id ) ;

CREATE TABLE ADDRESS
  (
    address_id    INTEGER NOT NULL ,
    country_id     INTEGER NOT NULL ,
    city_id      INTEGER NOT NULL ,
    street_id    INTEGER NOT NULL
  ) ;
ALTER TABLE ADDRESS ADD CONSTRAINT ADDRESS_PK PRIMARY KEY ( address_id ) ;

CREATE SEQUENCE address_seq;

CREATE OR REPLACE TRIGGER trigger_address_incr
BEFORE INSERT ON ADDRESS
FOR EACH ROW
  BEGIN
    -- :new.user_id := users_seq.NEXTVAL;
    SELECT address_seq.NEXTVAL
    INTO :new.address_id
    FROM DUAL;
  END;
 /

CREATE TABLE AIRLINE
  (
    airline_id   INTEGER NOT NULL ,
    airline_name VARCHAR2 (255) NOT NULL ,
    airline_alias VARCHAR2 (255) NOT NULL ,
    iata_code VARCHAR2 (2) NOT NULL ,
    icao_code VARCHAR2 (3) NOT NULL ,
    callsign VARCHAR2 (255) NOT NULL ,
    country VARCHAR2 (255) NOT NULL ,
    active VARCHAR2 (1) NOT NULL ,
    rating_num  INTEGER ,
    rating_sum  INTEGER
  ) ;
ALTER TABLE AIRLINE ADD CONSTRAINT AIRLINE_PK PRIMARY KEY ( airline_id ) ;

CREATE TABLE AIRLINE_USER
  (
    airline_id    INTEGER NOT NULL ,
    user_id       INTEGER NOT NULL ,
    rating             INTEGER
  ) ;
ALTER TABLE AIRLINE_USER ADD CONSTRAINT AIRLINE_USER_UQ UNIQUE ( airline_id , user_id ) ;

CREATE TABLE COUNTRY
  ( 
  country_id INTEGER NOT NULL , 
  name VARCHAR2(255)
  ) ;
ALTER TABLE COUNTRY ADD CONSTRAINT COUNTRY_PK PRIMARY KEY ( country_id ) ;

CREATE SEQUENCE country_seq;

CREATE OR REPLACE TRIGGER trigger_country_incr
BEFORE INSERT ON COUNTRY
FOR EACH ROW
  BEGIN
    -- :new.user_id := users_seq.NEXTVAL;
    SELECT country_seq.NEXTVAL
    INTO :new.country_id
    FROM DUAL;
  END;
 /

CREATE TABLE CITY
  (
    city_id   INTEGER NOT NULL ,
    city_name VARCHAR2 (255) NOT NULL ,
    country_id   INTEGER NOT NULL ,
    state     VARCHAR2 (255) ,
    county    VARCHAR2 (255)
  ) ;
ALTER TABLE CITY ADD CONSTRAINT CITY_PK PRIMARY KEY ( city_id ) ;

CREATE SEQUENCE city_seq;

CREATE OR REPLACE TRIGGER trigger_city_incr
BEFORE INSERT ON CITY
FOR EACH ROW
  BEGIN
    -- :new.user_id := users_seq.NEXTVAL;
    SELECT city_seq.NEXTVAL
    INTO :new.city_id
    FROM DUAL;
  END;
 /

CREATE TABLE STREET
  (
    street_id   INTEGER NOT NULL ,
    street_name VARCHAR2(255) NOT NULL ,
    street_no   VARCHAR2(255) ,
    city_id     INTEGER NOT NULL
  ) ;
ALTER TABLE STREET ADD CONSTRAINT STREET_PK PRIMARY KEY ( street_id ) ;

CREATE SEQUENCE street_seq;

CREATE OR REPLACE TRIGGER trigger_street_incr
BEFORE INSERT ON STREET
FOR EACH ROW
  BEGIN
    -- :new.user_id := users_seq.NEXTVAL;
    SELECT street_seq.NEXTVAL
    INTO :new.street_id
    FROM DUAL;
  END;
 /

CREATE TABLE FRIENDS
  (    
  user1 INTEGER NOT NULL ,    
  user2 INTEGER NOT NULL  
  ) ;
ALTER TABLE FRIENDS ADD CONSTRAINT FRIENDS_PK PRIMARY KEY ( user1, user2 ) ;

CREATE TABLE PREFERENCE
  (
    preference_id    INTEGER NOT NULL ,
    preference_type  VARCHAR2 (255) NOT NULL ,
    preference_value VARCHAR2 (255) NOT NULL
  ) ;
ALTER TABLE PREFERENCE ADD CONSTRAINT PREFERENCE_PK PRIMARY KEY ( preference_id) ;

CREATE SEQUENCE preference_seq;

CREATE OR REPLACE TRIGGER trigger_preference_incr
BEFORE INSERT ON PREFERENCE
FOR EACH ROW
  BEGIN
    -- :new.user_id := users_seq.NEXTVAL;
    SELECT preference_seq.NEXTVAL
    INTO :new.preference_id
    FROM DUAL;
  END;
 /

CREATE TABLE TRIP
  (
    trip_id             INTEGER NOT NULL ,
    origin_address      INTEGER NOT NULL ,
    destination_address INTEGER NOT NULL ,
    departure_time      DATE NOT NULL ,
    arrival_date        DATE NOT NULL
  ) ;
ALTER TABLE TRIP ADD CONSTRAINT TRIP_PK PRIMARY KEY ( trip_id ) ;

CREATE SEQUENCE trip_seq;

CREATE OR REPLACE TRIGGER trigger_trip_incr
BEFORE INSERT ON TRIP
FOR EACH ROW
  BEGIN
    -- :new.user_id := users_seq.NEXTVAL;
    SELECT trip_seq.NEXTVAL
    INTO :new.trip_id
    FROM DUAL;
  END;
 /

CREATE TABLE FLIGHT
  (
    flight_id INTEGER NOT NULL ,
    airline_id INTEGER NOT NULL ,
    src_airport INTEGER NOT NULL ,
    dst_airport INTEGER NOT NULL ,
    codeshare VARCHAR2 (1) ,
    stops INTEGER NOT NULL ,
    equipment VARCHAR2 (255)
  ) ;
ALTER TABLE FLIGHT ADD CONSTRAINT FLIGHT_PK PRIMARY KEY ( flight_id ) ;

CREATE TABLE TRIP_FLIGHT
  (
    trip_id        INTEGER NOT NULL ,
    flight_id      INTEGER NOT NULL ,
    flight_date DATE ,
    flight_num  INTEGER
  ) ;
ALTER TABLE TRIP_FLIGHT ADD CONSTRAINT TRIP_FLIGHT_PK PRIMARY KEY ( trip_id, flight_id );

CREATE TABLE USERS
  (
    user_id   INTEGER NOT NULL ,
    username  VARCHAR2(100) ,
    email     VARCHAR2(100) ,
    password  VARCHAR2(100) ,
    user_info INTEGER
  ) ;
ALTER TABLE USERS ADD CONSTRAINT USER_PK PRIMARY KEY ( user_id ) ;

CREATE SEQUENCE users_seq;

CREATE OR REPLACE TRIGGER trigger_users_incr
BEFORE INSERT ON USERS
FOR EACH ROW
  BEGIN
    -- :new.user_id := users_seq.NEXTVAL;
    SELECT users_seq.NEXTVAL
    INTO :new.user_id
    FROM DUAL;
  END;
 /

CREATE TABLE USER_INFO
  (
    user_info_id INTEGER NOT NULL ,
    first_name   VARCHAR2 (255) NOT NULL ,
    last_name    VARCHAR2 (255) ,
    birthdate    DATE ,
    gender       VARCHAR2 (10) NOT NULL ,
    hometown_address      INTEGER ,
    current_address       INTEGER
  ) ;
ALTER TABLE USER_INFO ADD CONSTRAINT USER_INFO_PK PRIMARY KEY ( user_info_id );

CREATE SEQUENCE user_info_seq;

CREATE OR REPLACE TRIGGER trigger_user_info_incr
BEFORE INSERT ON USER_INFO
FOR EACH ROW
  BEGIN
    -- :new.user_id := users_seq.NEXTVAL;
    SELECT user_info_seq.NEXTVAL
    INTO :new.user_info_id
    FROM DUAL;
  END;
 /

CREATE TABLE USER_PREFERENCE
  (
    user_preference_id INTEGER NOT NULL ,
    user_id            INTEGER NOT NULL ,
    preference_id      INTEGER NOT NULL
  ) ;
ALTER TABLE USER_PREFERENCE ADD CONSTRAINT USER_PREFERENCE_PK PRIMARY KEY (user_preference_id ) ;

CREATE SEQUENCE user_preference_seq;

CREATE OR REPLACE TRIGGER trigger_user_preference_incr
BEFORE INSERT ON USER_PREFERENCE
FOR EACH ROW
  BEGIN
    -- :new.user_id := users_seq.NEXTVAL;
    SELECT user_preference_seq.NEXTVAL
    INTO :new.user_preference_id
    FROM DUAL;
  END;
 /

CREATE TABLE USER_TRIP
  (
    user_trip_id INTEGER NOT NULL ,
    user_id      INTEGER NOT NULL ,
    trip_id      INTEGER NOT NULL
  ) ;
ALTER TABLE USER_TRIP ADD CONSTRAINT USER_TRIP_PK PRIMARY KEY ( user_trip_id );


CREATE SEQUENCE user_trip_seq;

CREATE OR REPLACE TRIGGER trigger_user_trip_incr
BEFORE INSERT ON USER_TRIP
FOR EACH ROW
  BEGIN
    -- :new.user_id := users_seq.NEXTVAL;
    SELECT user_trip_seq.NEXTVAL
    INTO :new.user_trip_id
    FROM DUAL;
  END;
 /


--Address
ALTER TABLE ADDRESS ADD CONSTRAINT ADDRESS_COUNTRY_FK FOREIGN KEY ( country_id ) REFERENCES COUNTRY ( country_id );
ALTER TABLE ADDRESS ADD CONSTRAINT ADDRESS_CITY_FK FOREIGN KEY ( city_id ) REFERENCES CITY ( city_id );
ALTER TABLE ADDRESS ADD CONSTRAINT ADDRESS_STREET_FK FOREIGN KEY ( street_id ) REFERENCES STREET ( street_id );

--Airline_User
ALTER TABLE AIRLINE_USER ADD CONSTRAINT AIRLINE_USER_AIRLINE_FK FOREIGN KEY ( airline_id ) REFERENCES AIRLINE ( airline_id );
ALTER TABLE AIRLINE_USER ADD CONSTRAINT AIRLINE_USER_USER_FK FOREIGN KEY ( user_id ) REFERENCES USERS( user_id );

--City
ALTER TABLE CITY ADD CONSTRAINT CITY_COUNTRY_FK FOREIGN KEY ( country_id ) REFERENCES COUNTRY ( country_id );

--Street
ALTER TABLE STREET ADD CONSTRAINT STREET_CITY_FK FOREIGN KEY ( city_id ) REFERENCES CITY ( city_id );

--Friends
ALTER TABLE FRIENDS ADD CONSTRAINT FRIENDS1_USER_FK FOREIGN KEY ( user1 ) REFERENCES USERS ( user_id ) ;
ALTER TABLE FRIENDS ADD CONSTRAINT FRIENDS2_USER_FK FOREIGN KEY ( user2 ) REFERENCES USERS ( user_id ) ;

--Trip
ALTER TABLE TRIP ADD CONSTRAINT TRIP_DESTINATION_ADDRESS_FK FOREIGN KEY ( destination_address ) REFERENCES ADDRESS ( address_id ) ;
ALTER TABLE TRIP ADD CONSTRAINT TRIP_ORIGIN_ADDRESS_FK FOREIGN KEY ( origin_address ) REFERENCES ADDRESS ( address_id ) ;

--Flight
ALTER TABLE FLIGHT ADD CONSTRAINT AIRLINE  FOREIGN KEY ( airline_id )  REFERENCES AIRLINE ( airline_id ) ;
ALTER TABLE FLIGHT ADD CONSTRAINT AIRPORT1 FOREIGN KEY ( src_airport ) REFERENCES AIRPORT ( airport_id ) ;
ALTER TABLE FLIGHT ADD CONSTRAINT AIRPORT2 FOREIGN KEY ( dst_airport ) REFERENCES AIRPORT ( airport_id ) ;

--User_Info
ALTER TABLE USER_INFO ADD CONSTRAINT USER_INFO_HOMETOWN_ADDRESS_FK FOREIGN KEY ( hometown_address ) REFERENCES ADDRESS ( address_id ) ;
ALTER TABLE USER_INFO ADD CONSTRAINT USER_INFO_CURRENT_ADDRESS_FK FOREIGN KEY ( current_address ) REFERENCES ADDRESS ( address_id ) ;

--User_Preference
ALTER TABLE USER_PREFERENCE ADD CONSTRAINT USER_PREFERENCE_PREFERENCE_FK FOREIGN KEY ( preference_id ) REFERENCES PREFERENCE ( preference_id ) ;
ALTER TABLE USER_PREFERENCE ADD CONSTRAINT USER_PREFERENCE_USER_FK FOREIGN KEY ( user_id ) REFERENCES USERS ( user_id ) ;

--User_Trip
ALTER TABLE USER_TRIP ADD CONSTRAINT USER_TRIP_TRIP_FK FOREIGN KEY ( trip_id ) REFERENCES TRIP ( trip_id ) ON DELETE CASCADE ;
ALTER TABLE USER_TRIP ADD CONSTRAINT USER_TRIP_USER_FK FOREIGN KEY ( user_id ) REFERENCES USERS ( user_id ) ;

--Users
ALTER TABLE USERS ADD CONSTRAINT USER_USER_INFO_FK FOREIGN KEY ( user_info ) REFERENCES USER_INFO ( user_info_id ) ;

--Trip_Flight
ALTER TABLE TRIP_FLIGHT ADD CONSTRAINT TRIP_FLIGHT_TRIP FOREIGN KEY ( trip_id ) REFERENCES TRIP ( trip_id );
ALTER TABLE TRIP_FLIGHT ADD CONSTRAINT TRIP_FLIGHT_FLIGHT FOREIGN KEY ( flight_id ) REFERENCES FLIGHT ( flight_id );

/*
BEGIN
  FOR c IN (SELECT TABLE_NAME FROM USER_TABLES) 
  LOOP
    EXECUTE IMMEDIATE ('DROP TABLE '||c.table_name||' CASCADE CONSTRAINTS');
  END LOOP;
END;
*/