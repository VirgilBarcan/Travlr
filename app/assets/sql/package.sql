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
  This function is used to check if an user given by his username/email is valid
  We allow this because we have users created via FB and G+
  */
  FUNCTION IS_VALID_USER_EXTERNAL (
    p_usernameEmail IN VARCHAR2)
  RETURN INTEGER;
  
  /**
  This function is used to check if a new user can be added (the new user having the given email, username, password)
  */
  FUNCTION IS_VALID_USER (
    p_email IN VARCHAR2,
    p_username IN VARCHAR2,
    p_password IN VARCHAR2) 
  RETURN INTEGER;
  
  FUNCTION GET_USER_ID (
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION GET_USER_INFO_ID (
    p_user_id IN INTEGER
  ) RETURN INTEGER;
  
  FUNCTION GET_COUNTRY_ID (
    p_country_name IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION GET_CITY_ID (
    p_city_name IN VARCHAR2,
    p_country_name IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION GET_STREET_ID (
    p_street_name IN VARCHAR2,
    p_city_name IN VARCHAR2,
    p_country_name IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION ADD_USER_INFO (
    p_first_name IN VARCHAR2,
    p_last_name  IN VARCHAR2,
    p_birthdate  IN VARCHAR2,
    p_gender     IN VARCHAR2,
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION ADD_COUNTRY (
    p_country_name IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION ADD_CITY (
    p_city_name IN VARCHAR2,
    p_country_name IN VARCHAR2,
    p_state IN VARCHAR2,
    p_county IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION ADD_STREET (
    p_street_name IN VARCHAR2,
    p_street_no IN VARCHAR2,
    p_country IN VARCHAR2,
    p_city_name IN VARCHAR2,
    p_state IN VARCHAR2,
    p_county IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION ADD_ADDRESS (
    p_country IN VARCHAR2,
    p_state   IN VARCHAR2,
    p_county  IN VARCHAR2,
    p_locality IN VARCHAR2,
    p_street_name IN VARCHAR2,
    p_street_no   IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION EXISTS_COUNTRY (
    p_country_name IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION EXISTS_CITY (
    p_city_name IN VARCHAR2,
    p_country_name IN VARCHAR2,
    p_state IN VARCHAR2,
    p_county IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION EXISTS_STREET (
    p_street_name IN VARCHAR2,
    p_street_no IN VARCHAR2,
    p_country IN VARCHAR2,
    p_city_name IN VARCHAR2,
    p_state IN VARCHAR2,
    p_county IN VARCHAR2
  ) RETURN INTEGER;
  
  FUNCTION EXISTS_ADDRESS (
    p_country IN VARCHAR2,
    p_state   IN VARCHAR2,
    p_county  IN VARCHAR2,
    p_locality IN VARCHAR2,
    p_street_name IN VARCHAR2,
    p_street_no   IN VARCHAR2
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
          COMMIT;
          INSERT INTO USERS(email, username, password) VALUES (p_email, p_username, p_password);
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
      
      COMMIT;
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

  FUNCTION IS_VALID_USER_EXTERNAL (
    p_usernameEmail IN VARCHAR2)
  RETURN INTEGER
  IS
    v_return INTEGER;
    v_email VARCHAR2(255);
    v_username VARCHAR2(255);
    BEGIN
      v_email := NULL;
      v_username := NULL;
      
      BEGIN
        --check if there exists an user with these fields
        SELECT email, username
        INTO v_email, v_username
        FROM USERS
        WHERE (email = p_usernameEmail OR username = p_usernameEmail);
      EXCEPTION
        WHEN OTHERS THEN
          v_return := 1;
      END;
      
      IF v_email IS NOT NULL AND v_username IS NOT NULL THEN
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
   
  FUNCTION GET_USER_ID (
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_user_id INTEGER;
    BEGIN
      BEGIN
        SELECT user_id
        INTO v_user_id
        FROM USERS
        WHERE username = p_user_identifier OR email = p_user_identifier;
      EXCEPTION
        WHEN OTHERS THEN
          v_user_id := -1;
      END;
      RETURN v_user_id;
    END;
  
  FUNCTION GET_USER_INFO_ID (
    p_user_id IN INTEGER
  ) RETURN INTEGER
  IS
    v_user_info_id INTEGER;
    BEGIN
      SELECT user_info
      INTO v_user_info_id
      FROM USERS
      WHERE user_id = p_user_id;
      
      RETURN v_user_info_id;
    END;
  
   
  FUNCTION ADD_USER_INFO (
    p_first_name IN VARCHAR2,
    p_last_name  IN VARCHAR2,
    p_birthdate  IN VARCHAR2,
    p_gender     IN VARCHAR2,
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_user_id INTEGER;
    v_birthdate DATE;
    v_gender VARCHAR2(40);
    v_user_info_id INTEGER;
    v_return INTEGER;
    v_has_info INTEGER;
    BEGIN
      --get the user id
      v_user_id := GET_USER_ID(p_user_identifier);
      v_birthdate := TO_DATE(p_birthdate, 'yyyy-mm-dd');
      v_gender := LOWER(p_gender);
      
      SELECT user_info
      INTO v_has_info
      FROM USERS
      WHERE user_id = v_user_id;
   
      IF v_has_info IS NULL THEN --the user hasn't inserted the info until now, insert new
        BEGIN
          COMMIT;
          --first create the new user_info
          INSERT INTO USER_INFO(first_name, last_name, birthdate, gender)
          VALUES(p_first_name, p_last_name, v_birthdate, v_gender);
          
          --get the user_info_id of the newly created record
          SELECT user_info_id
          INTO v_user_info_id
          FROM USER_INFO
          WHERE first_name = p_first_name AND
                last_name  = p_last_name  AND
                birthdate  = v_birthdate  AND
                gender     = p_gender;
          
          --then update the user record
          UPDATE USERS SET user_info = v_user_info_id
          WHERE user_id = v_user_id;
          v_return := 0;
        EXCEPTION
          WHEN OTHERS THEN
            ROLLBACK;
            v_return := 1;
        END;
      ELSE --the user has inserted this data already, only update now
        BEGIN
          COMMIT;
          
          --update user info with the new data
          UPDATE USER_INFO
          SET first_name = p_first_name,
              last_name = p_last_name,
              birthdate = v_birthdate,
              gender = v_gender
          WHERE user_info_id = v_has_info;
          
          v_return := 0;
        EXCEPTION
          WHEN OTHERS THEN
            ROLLBACK;
            v_return := 1;
        END;
      END IF;
      COMMIT;
      RETURN v_return;
    END;

  FUNCTION GET_COUNTRY_ID (
    p_country_name IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_country_id INTEGER;
    BEGIN
      BEGIN
        SELECT country_id
        INTO v_country_id
        FROM COUNTRY
        WHERE name = p_country_name;
      EXCEPTION
        WHEN OTHERS THEN
          v_country_id := -1;
      END;
      
      RETURN v_country_id;
    END;
  
  FUNCTION GET_CITY_ID (
    p_city_name IN VARCHAR2,
    p_country_name IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_city_id INTEGER;
    v_country_id INTEGER;
    BEGIN
      v_country_id := GET_COUNTRY_ID(p_country_name);
    
      BEGIN
        SELECT city_id
        INTO v_city_id
        FROM CITY
        WHERE city_name = p_city_name AND country_id = v_country_id;
      EXCEPTION
        WHEN OTHERS THEN
          v_city_id := -1;
      END; 
      
      RETURN v_city_id;
    END;
  
  FUNCTION GET_STREET_ID (
    p_street_name IN VARCHAR2,
    p_city_name IN VARCHAR2,
    p_country_name IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_street_id INTEGER;
    v_city_id INTEGER;
    BEGIN
      v_city_id := GET_CITY_ID(p_city_name, p_country_name);
      
      BEGIN
      SELECT street_id
        INTO v_street_id
        FROM STREET
        WHERE city_id = v_city_id;
      EXCEPTION
        WHEN OTHERS THEN
          v_street_id := -1;
      END;
      
      RETURN v_street_id;
    END;

  FUNCTION GET_ADDRESS_ID (
    p_country IN VARCHAR2,
    p_state   IN VARCHAR2,
    p_county  IN VARCHAR2,
    p_locality IN VARCHAR2,
    p_street_name IN VARCHAR2,
    p_street_no   IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_country_id INTEGER;
    v_city_id INTEGER;
    v_street_id INTEGER;
    v_address_id INTEGER;
    BEGIN
      v_country_id := GET_COUNTRY_ID(p_country);
      v_city_id := GET_CITY_ID(p_locality, p_country);
      v_street_id := GET_STREET_ID(p_street_name, p_locality, p_country);
      
      BEGIN
        SELECT address_id
        INTO v_address_id
        FROM ADDRESS
        WHERE country_id = v_country_id AND city_id = v_city_id AND street_id = v_street_id;
      EXCEPTION
        WHEN OTHERS THEN
          v_address_id := -1;
      END;      
      RETURN v_address_id;
    END;

  FUNCTION ADD_COUNTRY (
    p_country_name IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_exists INTEGER;
    v_return INTEGER;
    BEGIN
      --check if the country already exists
      v_exists := EXISTS_COUNTRY(p_country_name);
      
      IF v_exists = 1 THEN
        --address exists
        v_return := 1;
      ELSE
        --address does not exist yet
        BEGIN
          COMMIT;
          
          INSERT INTO COUNTRY(name)
          VALUES(p_country_name);
          v_return := 1;
        EXCEPTION
          WHEN OTHERS THEN
            v_return := 0;
        END;
      END IF;
      
      RETURN v_return;
    END;
  
  FUNCTION ADD_CITY (
    p_city_name IN VARCHAR2,
    p_country_name IN VARCHAR2,
    p_state IN VARCHAR2,
    p_county IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_country INTEGER;
    v_return INTEGER;
    v_exists INTEGER;
    v_country_id INTEGER;
    BEGIN
      --check if the country already exists
      v_exists := EXISTS_CITY(p_city_name, p_country_name, p_state, p_county);
      
      IF v_exists = 1 THEN
        --address exists
        v_return := 1;
      ELSE
        --address does not exist yet
        BEGIN
          --find country_id
          v_country := EXISTS_COUNTRY(p_country_name);
          
          IF v_country = 0 THEN
            --country does not exist, add it
            v_return := ADD_COUNTRY(p_country_name);
            
            v_country_id := GET_COUNTRY_ID(p_country_name);
            
            BEGIN
              COMMIT;
              
              INSERT INTO CITY(city_name, country_id, state, county)
              VALUES(p_city_name, v_country_id, p_state, p_county);
              v_return := 1;
            EXCEPTION
              WHEN OTHERS THEN
                v_return := 0;
            END;
          ELSE
            --country exists
            v_country_id := GET_COUNTRY_ID(p_country_name);
            
            BEGIN
            INSERT INTO CITY(city_name, country_id, state, county)
            VALUES(p_city_name, v_country_id, p_state, p_county);
            v_return := 1;
            EXCEPTION
              WHEN OTHERS THEN
                v_return := 0;
            END;
          END IF;
        END;
      END IF;
      
      RETURN v_return;
    END;
  
  FUNCTION ADD_STREET (
    p_street_name IN VARCHAR2,
    p_street_no IN VARCHAR2,
    p_country IN VARCHAR2,
    p_city_name IN VARCHAR2,
    p_state IN VARCHAR2,
    p_county IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_return INTEGER;
    v_city_id INTEGER;
    v_exists INTEGER;
    BEGIN
      --check if the country already exists
      v_exists := EXISTS_STREET(p_street_name, p_street_no, p_country, p_city_name, p_state, p_county);
      
      IF v_exists = 1 THEN
        --street exists
        v_return := 1;
      ELSE
        --street does not exist yet
        --add it to
        BEGIN
          v_city_id := GET_CITY_ID(p_city_name, p_country);
          COMMIT;
          INSERT INTO STREET(street_name, street_no, city_id)
          VALUES(p_street_name, p_street_no, v_city_id);
          v_return := 1;
        EXCEPTION
          WHEN OTHERS THEN
            ROLLBACK;
            v_return := 0;
        END;
      END IF;
      
      RETURN v_return;
    END;

  FUNCTION ADD_ADDRESS (
    p_country IN VARCHAR2,
    p_state   IN VARCHAR2,
    p_county  IN VARCHAR2,
    p_locality IN VARCHAR2,
    p_street_name IN VARCHAR2,
    p_street_no   IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_return INTEGER;
    v_exists INTEGER;
    v_country_id INTEGER;
    v_city_id INTEGER;
    v_street_id INTEGER;
    BEGIN
      v_return := 0;
      
      --check if the address doesn't already exist
      v_exists := EXISTS_ADDRESS(p_country, p_state, p_county, p_locality, p_street_name, p_street_no);
      
      IF v_exists = 1 THEN
        --address exists
        v_return := 1;
      ELSE
        --address does not exist yet
        BEGIN
          --find country_id, city_id, street_id
          --if these don't exist, create them
          
          --find country_id
          v_country_id := GET_COUNTRY_ID(p_country);
          
          IF v_country_id = -1 THEN
            --country does not exist
            --create the country
            v_country_id := ADD_COUNTRY(p_country);
            
            --get the id of the newly created country
            v_country_id := GET_COUNTRY_ID(p_country);
          END IF;
          
          --find city_id
          v_city_id := GET_CITY_ID(p_locality, p_country);
          IF v_city_id = -1 THEN
            --city does not exist
            --create the city
            v_city_id := ADD_CITY(p_locality, p_country, p_state, p_county);
            --get the id of the newly created city
            v_city_id := GET_CITY_ID(p_locality, p_country);
          END IF;
          
          --find street_id
          v_street_id := GET_STREET_ID(p_street_name, p_locality, p_country);
          IF v_street_id = -1 THEN
            --street does not exist
            --create the street
            v_street_id := ADD_STREET(p_street_name, p_street_no, p_country, p_locality, p_state, p_county);
            --get the id of the newly created street
            v_street_id := GET_STREET_ID(p_street_name, p_locality, p_country);
          END IF;
          COMMIT;
          BEGIN
            INSERT INTO ADDRESS(country_id, city_id, street_id)
            VALUES(v_country_id, v_city_id, v_street_id);
            v_return := 1;
          EXCEPTION
            WHEN OTHERS THEN
              v_return := 0;
          END;
        END;
      END IF;
      
      RETURN v_return;
    END;

  FUNCTION EXISTS_COUNTRY (
    p_country_name IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_country_name VARCHAR2(255);
    v_return INTEGER;
    BEGIN
      v_country_name := NULL;
      
      BEGIN
        SELECT name
        INTO v_country_name
        FROM COUNTRY;
        
        IF v_country_name IS NOT NULL THEN
          v_return := 1;
        ELSE
          v_return := 0;
        END IF;
      EXCEPTION
        WHEN OTHERS THEN
          v_return := 0;
      END;
      
      RETURN v_return;
    END;
  
  FUNCTION EXISTS_CITY (
    p_city_name IN VARCHAR2,
    p_country_name IN VARCHAR2,
    p_state IN VARCHAR2,
    p_county IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_city_name VARCHAR2(255);
    v_return INTEGER;
    BEGIN
      v_city_name := NULL;
      
      BEGIN
        SELECT city_name
        INTO v_city_name
        FROM CITY
        WHERE state = p_state AND county = p_county;
        
        IF v_city_name IS NOT NULL THEN
          v_return := 1;
        ELSE
          v_return := 0;
        END IF;
      EXCEPTION
        WHEN OTHERS THEN
          v_return := 0;
      END;
      
      RETURN v_return;
    END;
  
  FUNCTION EXISTS_STREET (
    p_street_name IN VARCHAR2,
    p_street_no IN VARCHAR2,
    p_country IN VARCHAR2,
    p_city_name IN VARCHAR2,
    p_state IN VARCHAR2,
    p_county IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_street_name VARCHAR2(255);
    v_street_no VARCHAR2(255);
    v_city_id INTEGER;
    v_return INTEGER;
    BEGIN
      v_street_name := NULL;
      v_street_no := NULL;
      
      BEGIN
        v_city_id := GET_CITY_ID(p_city_name, p_country);
        
        SELECT street_name, street_no
        INTO v_street_name, v_street_no
        FROM STREET
        WHERE city_id = v_city_id;
        
        IF v_street_name IS NOT NULL AND v_street_no IS NOT NULL THEN
          v_return := 1;
        ELSE
          v_return := 0;
        END IF;
      EXCEPTION
        WHEN OTHERS THEN
          v_return := 0;
      END;      
      RETURN v_return;
      
    END;
  
  FUNCTION EXISTS_ADDRESS (
    p_country IN VARCHAR2,
    p_state   IN VARCHAR2,
    p_county  IN VARCHAR2,
    p_locality IN VARCHAR2,
    p_street_name IN VARCHAR2,
    p_street_no   IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_country VARCHAR2(255);
    v_state VARCHAR2(255);
    v_county VARCHAR2(255);
    v_locality VARCHAR2(255);
    v_street_name VARCHAR2(255);
    v_street_no INTEGER;
    v_return INTEGER;
    BEGIN
      v_country := null;
      v_state := null;
      v_county := null;
      v_locality := null;
      v_street_name := null;
      v_street_no := null;
      v_return := 0;
      
      BEGIN
        SELECT CO.name, CI.state, CI.county, CI.city_name, ST.street_name, ST.street_no
        INTO v_country, v_state, v_county, v_locality, v_street_name, v_street_no
        FROM COUNTRY CO, CITY CI, STREET ST, ADDRESS A
        WHERE A.country_id = CO.country_id AND
              A.city_id = CI.city_id AND
              A.street_id = ST.street_id AND
              CO.country_id = CI.country_id AND 
              CI.city_id = ST.city_id;
        
        IF v_country IS NOT NULL AND
           v_state IS NOT NULL AND
           v_county IS NOT NULL AND
           v_locality IS NOT NULL AND
           v_street_name IS NOT NULL AND
           v_street_no IS NOT NULL
        THEN
          v_return := 1;  
        END IF;
      EXCEPTION
        WHEN OTHERS THEN
          v_return := 0;
      END;
      
      RETURN v_return;
    END;

  FUNCTION ADD_USER_HOMETOWN (
    p_country IN VARCHAR2,
    p_state   IN VARCHAR2,
    p_county  IN VARCHAR2,
    p_locality IN VARCHAR2,
    p_street_name IN VARCHAR2,
    p_street_no   IN VARCHAR2,
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_address_id INTEGER;
    v_user_id INTEGER;
    v_user_info_id INTEGER;
    v_returned INTEGER;
    BEGIN
      --get the id of the address
      v_address_id := GET_ADDRESS_ID(p_country, p_state, p_county, p_locality, p_street_name, p_street_no);
      
      IF v_address_id = -1 THEN --address does not exist
        --create the address
        v_address_id := ADD_ADDRESS(p_country, p_state, p_county, p_locality, p_street_name, p_street_no);
        
        --get the id of the newly created address
        v_address_id := GET_ADDRESS_ID(p_country, p_state, p_county, p_locality, p_street_name, p_street_no);
      END IF;
      
      --get the id of the user
      v_user_id := GET_USER_ID(p_user_identifier);
      IF v_user_id != -1 THEN --user does exist
        --get the user info id
        v_user_info_id := GET_USER_INFO_ID(v_user_id);
        
        BEGIN
          COMMIT;
          --insert the address into the user record
          UPDATE USER_INFO SET hometown_address = v_address_id WHERE user_info_id = v_user_info_id;
          v_returned := 0;
        EXCEPTION
          WHEN OTHERS THEN
            ROLLBACK;
            v_returned := 1;
        END;
      ELSE
        v_returned := 1;
      END IF;          
      
      RETURN v_returned;
    END;
  
  FUNCTION ADD_USER_CURRENT_ADDRESS (
    p_country IN VARCHAR2,
    p_state   IN VARCHAR2,
    p_county  IN VARCHAR2,
    p_locality IN VARCHAR2,
    p_street_name IN VARCHAR2,
    p_street_no   IN VARCHAR2,
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER
  IS
    v_address_id INTEGER;
    v_user_id INTEGER;
    v_user_info_id INTEGER;
    v_returned INTEGER;
    BEGIN
      --get the id of the address
      v_address_id := GET_ADDRESS_ID(p_country, p_state, p_county, p_locality, p_street_name, p_street_no);
      
      IF v_address_id = -1 THEN --address does not exist
        --create the address
        v_address_id := ADD_ADDRESS(p_country, p_state, p_county, p_locality, p_street_name, p_street_no);
        
        --get the id of the newly created address
        v_address_id := GET_ADDRESS_ID(p_country, p_state, p_county, p_locality, p_street_name, p_street_no);
      END IF;
      
      --get the id of the user
      v_user_id := GET_USER_ID(p_user_identifier);
      IF v_user_id != -1 THEN --user does exist
        --get the user info id
        v_user_info_id := GET_USER_INFO_ID(v_user_id);
        
        BEGIN
          COMMIT;
          --insert the address into the user record
          UPDATE USER_INFO SET current_address = v_address_id WHERE user_info_id = v_user_info_id;
          v_returned := 0;
        EXCEPTION
          WHEN OTHERS THEN
            ROLLBACK;
            v_returned := 1;
        END;
      ELSE
        v_returned := 1;
      END IF;          
      
      RETURN v_returned;
    END;
  
  FUNCTION ADD_USER_AIRLINE (
    p_airline_name  IN VARCHAR2,
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER
  IS
    BEGIN
      null;
    END;

  FUNCTION ADD_USER_FLIGHT_PREFERENCES (
    p_night_flights IN INTEGER,
    p_stopovers     IN INTEGER,
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER
  IS
    BEGIN
      null;
    END;

  FUNCTION ADD_USER_ROUTE_PREFERENCES (
    p_cheapese     IN INTEGER,
    p_shortest     IN INTEGER,
    p_most_friends_seen IN INTEGER,
    p_user_identifier IN VARCHAR2
  ) RETURN INTEGER
  IS
    BEGIN
      null;
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
