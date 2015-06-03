SET SERVEROUTPUT ON;
/
DECLARE
  v_returned INTEGER;
  v_user_info USER_INFO_TYPE;
  v_user_hometown ADDRESS_TYPE;
  v_user_current_address ADDRESS_TYPE;
  
  v_user_flight_preferences FLIGHT_PREFERENCES_TYPE;
  v_user_route_preferences ROUTE_PREFERENCES_TYPE;
BEGIN
  --v_returned := TRAVLR.ADD_USER('virgil.barcan@info.uaic.ro', 'virgil.barcan', 'parola');
  --DBMS_OUTPUT.PUT_LINE('Returned by ADD_USER:' || v_returned);
  
  --v_returned := TRAVLR.IS_VALID_USER('virgil.barcan', 'Parola');
  --DBMS_OUTPUT.PUT_LINE('Returned by IS_VALID_USER:' || v_returned);
  
  --v_returned := TRAVLR.IS_VALID_USER('virgill.barcan@info.uaic.ro', 'virgil.barcan', 'parola');
  --DBMS_OUTPUT.PUT_LINE('Returned by IS_VALID_USER:' || v_returned);
  
  --v_returned := TRAVLR.ADD_USER_INFO('Barcan', 'Virgil-Gheorghe', '1994-04-30', 'male', 'virgil.barcan');
  --DBMS_OUTPUT.PUT_LINE('Returned by ADD_USER_USER_INFO:' || v_returned);
  
  --v_returned := TRAVLR.GET_STREET_ID('Soseaua Pacurari', 'Iasi', 'Romania');
  --DBMS_OUTPUT.PUT_LINE('Returned by GET_STREET_ID:' || v_returned);
  
  --v_returned := TRAVLR.GET_ADDRESS_ID('Romania', 'Romania', 'Iasi', 'Iasi', 'Soseaua Pacurari', '16');
  --DBMS_OUTPUT.PUT_LINE('Returned by GET_ADDRESS_ID:' || v_returned);
 
  --v_returned := TRAVLR.ADD_USER_HOMETOWN('Romania', 'Romania', 'Iasi', 'Iasi', 'Soseaua Pacurari', '16', 'virgil.barcan');
  --DBMS_OUTPUT.PUT_LINE('Returned by ADD_USER_HOMETOWN:' || v_returned);
  
  --v_returned := TRAVLR.ADD_USER_CURRENT_ADDRESS('Romania', 'Romania', 'Iasi', 'Iasi', 'Soseaua Pacurari', '16', 'virgil.barcan');
  --DBMS_OUTPUT.PUT_LINE('Returned by ADD_USER_CURRENT_ADDRESS:' || v_returned);
  
  --v_returned := TRAVLR.EXISTS_COUNTRY('Romania');
  --DBMS_OUTPUT.PUT_LINE('Returned by EXISTS_COUNTRY:' || v_returned);
  
  --v_returned := TRAVLR.EXISTS_STREET('Soseaua Pacurari', '16', 'Romania', 'Iasi', 'Romania', 'Iasi');
  --DBMS_OUTPUT.PUT_LINE('Returned by EXISTS_STREET:' || v_returned);
  
  --v_returned := TRAVLR.EXISTS_ADDRESS('Romania', 'Romania', 'Iasi', 'Iasi', 'Soseaua Pacurari', '16');
  --DBMS_OUTPUT.PUT_LINE('Returned by EXISTS_ADDRESS:' || v_returned);
  
  --v_returned := TRAVLR.ADD_ADDRESS('Romania', 'Romania', 'Iasi', 'Iasi', 'Soseaua Pacurari', '16');
  --DBMS_OUTPUT.PUT_LINE('Returned by ADD_ADDRESS:' || v_returned);
  
  --v_returned := TRAVLR.EXISTS_STREET('Romania', 'Romania', 'Iasi', 'Iasi', 'Soseaua Pacurari', '16');
  --DBMS_OUTPUT.PUT_LINE('Returned by EXISTS_ADDRESS:' || v_returned);
  
  --v_returned := TRAVLR.ADD_USER_HOMETOWN('Romania', 'Romania', 'Iasi', 'Iasi', 'Soseaua Pacurari', '16', 'virgil.barcan');
  --DBMS_OUTPUT.PUT_LINE('Returned by ADD_USER_HOMETOWN:' || v_returned);
  
  v_returned := TRAVLR.ADD_USER_CURRENT_ADDRESS('Romania', 'Romania', 'Iasi', 'Iasi', 'Soseaua Pacurari', '26', 'virgil.barcan');
  DBMS_OUTPUT.PUT_LINE('Returned by ADD_USER_CURRENT_ADDRESS:' || v_returned);
END;  
  --v_user_info := TRAVLR.GET_USER_INFO('virgil.barcan');
  --DBMS_OUTPUT.PUT_LINE('UserInfo: ' || v_user_info.first_name || ' ' || v_user_info.last_name || ' ' || v_user_info.birthdate || ' ' || v_user_info.gender);
  
  --v_user_hometown := TRAVLR.GET_USER_HOMETOWN('virgil.barcan');
  --DBMS_OUTPUT.PUT_LINE('UserHometown: ' || v_user_hometown.country || ' ' ||
  --                     v_user_hometown.state || ' ' || v_user_hometown.county || ' ' || 
  --                     v_user_hometown.locality || ' ' || v_user_hometown.street_name || ' ' ||
  --                     v_user_hometown.street_no);
                       
  --v_returned := TRAVLR.ADD_USER_HOMETOWN('Romania', 'Romania', 'Neamt', 'Vadurele, Cindesti', 'Soseaua Principala', '56', 'virgil.barcan');
  --DBMS_OUTPUT.PUT_LINE('Returned by ADD_USER_HOMETOWN:' || v_returned);

/*
  v_user_hometown := TRAVLR.GET_USER_HOMETOWN('virgil.barcan');
  DBMS_OUTPUT.PUT_LINE('UserHometown: ' || v_user_hometown.country || ' ' ||
                       v_user_hometown.state || ' ' || v_user_hometown.county || ' ' || 
                       v_user_hometown.locality || ' ' || v_user_hometown.street_name || ' ' ||
                       v_user_hometown.street_no);
  
  v_user_current_address := TRAVLR.GET_USER_CURRENT_ADDRESS('virgil.barcan');
  DBMS_OUTPUT.PUT_LINE('UserCurrentAddress: ' || v_user_current_address.country || ' ' ||
                       v_user_current_address.state || ' ' || v_user_current_address.county || ' ' || 
                       v_user_current_address.locality || ' ' || v_user_current_address.street_name || ' ' ||
                       v_user_current_address.street_no);                     
                      
  
  v_returned := TRAVLR.ADD_USER_FLIGHT_PREFERENCES('yes', 'yes', 'virgil.barcan');
  DBMS_OUTPUT.PUT_LINE('Returned by ADD_USER_FLIGHT_PREFERENCES: ' || v_returned);
  
  v_user_flight_preferences := TRAVLR.GET_USER_FLIGHT_PREFERENCES('virgil.barcan');
  DBMS_OUTPUT.PUT_LINE('Returned by GET_USER_FLIGHT_PREFERENCES: ' || v_user_flight_preferences.night_flights || ' ' || v_user_flight_preferences.stopovers_flights);
  
  v_returned := TRAVLR.ADD_USER_ROUTE_PREFERENCES('yes', 'yes', 'yes', 'virgil.barcan');
  DBMS_OUTPUT.PUT_LINE('Returned by ADD_USER_ROUTE_PREFERENCES: ' || v_returned);
  
  v_user_route_preferences := TRAVLR.GET_USER_ROUTE_PREFERENCES('virgil.barcan');
  DBMS_OUTPUT.PUT_LINE('Returned by GET_USER_ROUTE_PREFERENCES: ' || v_user_route_preferences.cheapest_route || ' ' || v_user_route_preferences.shortest_route || ' ' || v_user_route_preferences.most_friends_seen_route);
END;

/*
-- Select full address of a place
SELECT CO.name, CI.state, CI.county, CI.city_name, ST.street_name, ST.street_no
FROM COUNTRY CO, CITY CI, STREET ST, ADDRESS A
WHERE A.country_id = CO.country_id AND
      A.city_id = CI.city_id AND
      A.street_id = ST.street_id AND
      CO.country_id = CI.country_id AND 
      CI.city_id = ST.city_id;
        
-- Select hometown address for a specified user
SELECT CO.name, CI.state, CI.county, CI.city_name, ST.street_name, ST.street_no
FROM COUNTRY CO, CITY CI, STREET ST, ADDRESS A, USER_INFO UI, USERS U
WHERE U.user_info = UI.user_info_id AND
      (U.email = 'abc' OR U.username = 'abc') AND
      UI.hometown_address = A.address_id AND
      A.country_id = CO.country_id AND
      A.city_id = CI.city_id AND
      A.street_id = ST.street_id AND
      CO.country_id = CI.country_id AND 
      CI.city_id = ST.city_id;

-- Get user info for a specified user
SELECT UI.first_name, UI.last_name, UI.birthdate, UI.gender
FROM USERS U, USER_INFO UI
WHERE U.user_info = UI.user_info_id AND
      (U.email = 'ghitza94' OR
       U.username = 'ghitza94');
       
*/