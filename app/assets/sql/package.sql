CREATE OR REPLACE PACKAGE TRAVLR
IS
  FUNCTION ADD_USER (
    p_email IN VARCHAR2,
    p_username IN VARCHAR2,
    p_password IN VARCHAR2)
  RETURN INTEGER;
  
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
    BEGIN
      
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
      
      RETURN v_return;
    END ADD_USER;
    
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
END TRAVLR;
/
