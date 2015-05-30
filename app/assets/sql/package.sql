CREATE OR REPLACE PACKAGE TRAVLR
IS
  FUNCTION ADD_USER (
    p_email IN VARCHAR2,
    p_username IN VARCHAR2,
    p_password IN VARCHAR2)
  RETURN INTEGER;
  
  
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
END TRAVLR;