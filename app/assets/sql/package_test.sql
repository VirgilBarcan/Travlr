SET SERVEROUTPUT ON;
/
DECLARE
  v_returned INTEGER;
BEGIN
  --v_returned := TRAVLR.ADD_USER('virgil.barcan@info.uaic.ro', 'virgil.barcan', 'parola');
  --DBMS_OUTPUT.PUT_LINE('Returned by ADD_USER:' || v_returned);
  
  v_returned := TRAVLR.IS_VALID_USER('virgil.barcan', 'parola');
  DBMS_OUTPUT.PUT_LINE('Returned by IS_VALID_USER:' || v_returned);
  
  --v_returned := TRAVLR.IS_VALID_USER('virgill.barcan@info.uaic.ro', 'virgil.barcan', 'parola');
  --DBMS_OUTPUT.PUT_LINE('Returned by IS_VALID_USER:' || v_returned);
  
  v_returned := TRAVLR.ADD_USER_INFO('Barcan', 'Virgil-Gheorghe', '30-04-1994', 'male', 'virgil.barcan');
  DBMS_OUTPUT.PUT_LINE('Returned by ADD_USER_USER_INFO:' || v_returned);
END;


/*
-- Select full address of a place
SELECT CO.name, CI.state, CI.county, CI.city_name, ST.street_name, ST.street_no
FROM COUNTRY CO, CITY CI, STREET ST
WHERE CO.country_id = CI.country_id AND CI.city_id = ST.city_id;
*/