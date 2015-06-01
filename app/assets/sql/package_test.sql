SET SERVEROUTPUT ON;
/
DECLARE
  v_returned INTEGER;
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
  
  v_returned := TRAVLR.ADD_USER_HOMETOWN('Romania', 'Romania', 'Iasi', 'Iasi', 'Soseaua Pacurari', '16', 'virgil.barcan');
  DBMS_OUTPUT.PUT_LINE('Returned by ADD_USER_HOMETOWN:' || v_returned);
  
  v_returned := TRAVLR.ADD_USER_CURRENT_ADDRESS('Romania', 'Romania', 'Iasi', 'Iasi', 'Soseaua Pacurari', '16', 'virgil.barcan');
  DBMS_OUTPUT.PUT_LINE('Returned by ADD_USER_CURRENT_ADDRESS:' || v_returned);
  
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
*/