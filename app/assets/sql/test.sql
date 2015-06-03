SET SERVEROUTPUT ON;

DECLARE
  v_p POST_TYPE;
BEGIN
  v_p := TRAVLR.GET_POST(1, 2);
  if v_p is null
  then
    dbms_output.put_line('is null');
  end if;
  dbms_output.put_line(v_p.message);
END;
/