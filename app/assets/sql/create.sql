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

CREATE TABLE CITY
  (
    city_id   INTEGER NOT NULL ,
    city_name VARCHAR2 (255) NOT NULL ,
    country_id   INTEGER NOT NULL ,
    state     VARCHAR2 (255) ,
    county    VARCHAR2 (255)
  ) ;
ALTER TABLE CITY ADD CONSTRAINT CITY_PK PRIMARY KEY ( city_id ) ;

CREATE TABLE STREET
  (
    street_id   INTEGER NOT NULL ,
    street_name VARCHAR2(255) NOT NULL ,
    street_no   VARCHAR2(255) ,
    city_id     INTEGER NOT NULL
  ) ;
ALTER TABLE STREET ADD CONSTRAINT STREET_PK PRIMARY KEY ( street_id ) ;

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

CREATE TABLE TRIP
  (
    trip_id             INTEGER NOT NULL ,
    origin_address      INTEGER NOT NULL ,
    destination_address INTEGER NOT NULL ,
    departure_time      DATE NOT NULL ,
    arrival_date        DATE NOT NULL
  ) ;
ALTER TABLE TRIP ADD CONSTRAINT TRIP_PK PRIMARY KEY ( trip_id ) ;

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
    user_info INTEGER NOT NULL
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
    middle_name  VARCHAR2 (255) ,
    last_name    VARCHAR2 (255) ,
    birthdate    DATE ,
    gender       VARCHAR2 (10) NOT NULL,
    email        VARCHAR2 (255) NOT NULL ,
    address      INTEGER NOT NULL
  ) ;
ALTER TABLE USER_INFO ADD CONSTRAINT USER_INFO_PK PRIMARY KEY ( user_info_id );

CREATE TABLE USER_PREFERENCE
  (
    user_preference_id INTEGER NOT NULL ,
    user_id            INTEGER NOT NULL ,
    preference_id      INTEGER NOT NULL
  ) ;
ALTER TABLE USER_PREFERENCE ADD CONSTRAINT USER_PREFERENCE_PK PRIMARY KEY (user_preference_id ) ;

CREATE TABLE USER_TRIP
  (
    user_trip_id INTEGER NOT NULL ,
    user_id      INTEGER NOT NULL ,
    trip_id      INTEGER NOT NULL
  ) ;
ALTER TABLE USER_TRIP ADD CONSTRAINT USER_TRIP_PK PRIMARY KEY ( user_trip_id );

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
ALTER TABLE USER_INFO ADD CONSTRAINT USER_INFO_ADDRESS_FK FOREIGN KEY ( address ) REFERENCES ADDRESS ( address_id ) ;

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