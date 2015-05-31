CREATE OR REPLACE PACKAGE TRAVLR
IS
  /**
  This function is used to add a new user to the DB
  */
  FUNCTION ADD_USER (
    p_email IN VARCHAR2,
    p_username IN VARCHAR2,
    p_password IN VARCHAR2)
  RETURN INTEGER;
  
  /**
  This function is used to check if an user given by his username/email and password is valid
  */
  FUNCTION IS_VALID_USER (
    p_usernameEmail IN VARCHAR2,
    p_password IN VARCHAR2)
  RETURN INTEGER;
  
  /**
  This function is used to check if a new user can be added (the new user having the given email, username, password)
  */
  FUNCTION IS_VALID_USER (
    p_email IN VARCHAR2,
    p_username IN VARCHAR2,
    p_password IN VARCHAR2) 
  RETURN INTEGER;
  
  FUNCTION ADD_USER_INFO (
    p_first_name IN VARCHAR2,
    p_last_name  IN VARCHAR2,
    p_birthdate  IN VARCHAR2,
    p_gender     IN VARCHAR2,
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION ADD_USER_HOMETOWN (
    p_country IN VARCHAR2,
    p_state   IN VARCHAR2,
    p_county  IN VARCHAR2,
    p_locality IN VARCHAR2,
    p_street_name IN VARCHAR2,
    p_street_no   IN VARCHAR2,
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION ADD_USER_CURRENT_ADDRESS (
    p_country IN VARCHAR2,
    p_state   IN VARCHAR2,
    p_county  IN VARCHAR2,
    p_locality IN VARCHAR2,
    p_street_name IN VARCHAR2,
    p_street_no   IN VARCHAR2,
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION GET_ADDRESS_ID (
    p_country IN VARCHAR2,
    p_state   IN VARCHAR2,
    p_county  IN VARCHAR2,
    p_locality IN VARCHAR2,
    p_street_name IN VARCHAR2,
    p_street_no   IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION ADD_USER_AIRLINE (
    p_airline_name  IN VARCHAR2,
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER;

  FUNCTION ADD_USER_FLIGHT_PREFERENCES (
    p_night_flights IN INTEGER,
    p_stopovers     IN INTEGER,
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER;

  FUNCTION ADD_USER_ROUTE_PREFERENCES (
    p_cheapese     IN INTEGER,
    p_shortest     IN INTEGER,
    p_most_friends_seen IN INTEGER,
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER;
  
  
    procedure addAirport(airport_id AIRPORT.airport_id%TYPE,
                        airport_name AIRPORT.airport_name%TYPE,
                        city AIRPORT.city%TYPE,
                        country AIRPORT.country%TYPE,
                        iata_faa_code AIRPORT.iata_faa_code%TYPE,
                        icao_code AIRPORT.icao_code%TYPE,
                        latitude AIRPORT.latitude%TYPE,
                        longitude AIRPORT.longitude%TYPE,
                        altitude AIRPORT.altitude%TYPE,
                        timezone AIRPORT.timezone%TYPE,
                        dst AIRPORT.dst%TYPE,
                        tzone_db_time AIRPORT.tzone_db_time%TYPE);
    procedure addAirline(airline_id AIRLINE.airline_id%TYPE,
                        airline_name AIRLINE.airline_name%TYPE,
                        airline_alias AIRLINE.airline_alias%TYPE,
                        iata_code AIRLINE.iata_code%TYPE,
                        icao_code AIRLINE.icao_code%TYPE,
                        callsign AIRLINE.callsign%TYPE,
                        country AIRLINE.country%TYPE,
                        active AIRLINE.active%TYPE);
    procedure addFlights(flight_id FLIGHT.flight_id%TYPE,
                        airline_id FLIGHT.airline_id%TYPE,
                        src_airport FLIGHT.src_airport%TYPE,
                        dst_airport FLIGHT.dst_airport%TYPE,
                        codeshare FLIGHT.codeshare%TYPE,
                        stops FLIGHT.stops%TYPE,
                        equipment FLIGHT.equipment%TYPE);
    function isTable(tableName Varchar2) return Integer;
  
END TRAVLR;
/

CREATE OR REPLACE PACKAGE BODY TRAVLR
IS
 FUNCTION ADD_USER (
    p_email IN VARCHAR2,
    p_username IN VARCHAR2,
    p_password IN VARCHAR2)
  RETURN INTEGER
  IS
    v_return INTEGER := 1;
    v_exists INTEGER;
    BEGIN
      --check to see if user isn't already created
      v_exists := IS_VALID_USER(p_email, p_username, p_password);
      
      IF v_exists = 0 THEN
        --the user doesn't exist
        BEGIN
          INSERT INTO USERS(email, username, password) VALUES (p_email, p_username, p_password);
          COMMIT;
          --data inserted successfully
          v_return := 0;
        EXCEPTION  
          WHEN OTHERS THEN
            ROLLBACK;
            v_return := 1;
        END;
      ELSE
        --the user exists
        v_return := 2;
      END IF;
      
      RETURN v_return;
    END ADD_USER;

  FUNCTION IS_VALID_USER (
    p_usernameEmail IN VARCHAR2,
    p_password IN VARCHAR2)
  RETURN INTEGER
  IS
    v_return INTEGER;
    v_email VARCHAR2(255);
    v_username VARCHAR2(255);
    v_password VARCHAR2(255);
    BEGIN
      v_email := NULL;
      v_username := NULL;
      v_password := NULL;
      
      BEGIN
        --check if there exists an user with these fields
        SELECT email, username, password
        INTO v_email, v_username, v_password
        FROM USERS
        WHERE (email = p_usernameEmail OR username = p_usernameEmail) AND password = p_password;
      EXCEPTION
        WHEN OTHERS THEN
          v_return := 1;
      END;
      
      IF v_email IS NOT NULL AND v_username IS NOT NULL AND v_password IS NOT NULL THEN
        --good! the user exists and has the given credentials
        v_return := 0;
      ELSE
        --bad! the user does not exist
        v_return := 1;
      END IF;
      RETURN v_return;
    END;

  FUNCTION IS_VALID_USER (
    p_email IN VARCHAR2,
    p_username IN VARCHAR2,
    p_password IN VARCHAR2)
  RETURN INTEGER
  IS
    v_return INTEGER;
    v_email VARCHAR2(255);
    v_username VARCHAR2(255);
    BEGIN
      v_email := NULL;
      v_username := NULL;
      
      BEGIN
        --check if the email is used
        SELECT email
        INTO v_email
        FROM USERS
        WHERE email = p_email;
        
        --check if the username is used
        SELECT username
        INTO v_username
        FROM USERS
        WHERE username = p_username;
      EXCEPTION
        WHEN OTHERS THEN
          v_return := 0;
      END;
      
      IF v_email IS NOT NULL THEN
        --bad! the email is used
        v_return := 2;
      ELSIF v_username IS NOT NULL THEN
        --bad! the username is used
        v_return := 1;
      ELSE
        --good! both the email and the username are available
        v_return := 0;
      END IF;
      
      RETURN v_return;
    END;
   
    procedure addAirport(airport_id AIRPORT.airport_id%TYPE,
                        airport_name AIRPORT.airport_name%TYPE,
                        city AIRPORT.city%TYPE,
                        country AIRPORT.country%TYPE,
                        iata_faa_code AIRPORT.iata_faa_code%TYPE,
                        icao_code AIRPORT.icao_code%TYPE,
                        latitude AIRPORT.latitude%TYPE,
                        longitude AIRPORT.longitude%TYPE,
                        altitude AIRPORT.altitude%TYPE,
                        timezone AIRPORT.timezone%TYPE,
                        dst AIRPORT.dst%TYPE,
                        tzone_db_time AIRPORT.tzone_db_time%TYPE) as
    begin
        insert into AIRPORT values(airport_id, airport_name, city, country,
                                    iata_faa_code, icao_code, latitude,
                                    longitude, altitude, timezone, dst,
                                    tzone_db_time, 0, 0);
        exception
            when others then
                begin
                    rollback;
                    return;
                end;
        commit;
    end;
    procedure addAirline(airline_id AIRLINE.airline_id%TYPE,
                        airline_name AIRLINE.airline_name%TYPE,
                        airline_alias AIRLINE.airline_alias%TYPE,
                        iata_code AIRLINE.iata_code%TYPE,
                        icao_code AIRLINE.icao_code%TYPE,
                        callsign AIRLINE.callsign%TYPE,
                        country AIRLINE.country%TYPE,
                        active AIRLINE.active%TYPE) as
    begin
        insert into AIRLINE values(airline_id, airline_name, airline_alias,
                                    iata_code, icao_code, callsign,
                                    country, active, 0, 0);
        exception
            when others then
                begin
                    rollback;
                    return;
                end;
        commit;
    end;
    procedure addFlights(flight_id FLIGHT.flight_id%TYPE,
                        airline_id FLIGHT.airline_id%TYPE,
                        src_airport FLIGHT.src_airport%TYPE,
                        dst_airport FLIGHT.dst_airport%TYPE,
                        codeshare FLIGHT.codeshare%TYPE,
                        stops FLIGHT.stops%TYPE,
                        equipment FLIGHT.equipment%TYPE) as
    begin
        insert into FLIGHT values(flight_id, airline_id, src_airport,
                                    dst_airport, codeshare, stops, equipment);
        exception
            when others then
                begin
                    rollback;
                    return;
                end;
        commit;
    end;
    function isTable(tableName Varchar2) return Integer as
        cnt Integer;
    begin
        select count(*) into cnt from user_tables where upper(table_name)=upper(tableName);
        return cnt;
    end;
END TRAVLR;
/
