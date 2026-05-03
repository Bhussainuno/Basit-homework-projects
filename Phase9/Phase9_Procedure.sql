-- Task 1. PL/SQL Procedures
CREATE OR REPLACE PROCEDURE HouseReport(buyer_SSN IN VARCHAR2) 
AS
    -- Set the cursor to choose homes according to the buyer's SSN and budget.
    CURSOR house_cursor IS
        SELECT H.ID, H.ADDRESS, H.NEIGHBORHOOD, H.PRICE
        FROM HOUSE H
        JOIN BUYER B ON H.SELLERSSN = B.SSN
        WHERE B.SSN = buyer_SSN
          AND H.PRICE BETWEEN B.LOWERPRICE AND B.UPPERPRICE;

    total_price NUMBER := 0;
    house_count NUMBER := 0;
    buyer_name VARCHAR2(100);
    lower_price NUMBER;
    upper_price NUMBER;
BEGIN
    -- Fetch the buyer's name and price range
    BEGIN
        SELECT NAME, LOWERPRICE, UPPERPRICE INTO buyer_name, lower_price, upper_price 
        FROM BUYER WHERE SSN = buyer_SSN;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            buyer_name := 'Unknown Buyer';
            lower_price := 0;
            upper_price := 0;
            DBMS_OUTPUT.PUT_LINE('No buyer found with SSN: ' || buyer_SSN);
    END;

    -- The buyer's name appears in the report header.
    DBMS_OUTPUT.PUT_LINE('Report for ' || buyer_SSN || ': ' || buyer_name);

    -- House details header
    DBMS_OUTPUT.PUT_LINE(RPAD('HouseID', 10) || RPAD('Address', 30) || RPAD('Neighborhood', 20) || 'Price');
    DBMS_OUTPUT.PUT_LINE('------------------------------------------------------------------------');

    -- To obtain house details, loop through the cursor.
    FOR house IN house_cursor LOOP
        DBMS_OUTPUT.PUT_LINE(
            RPAD(house.ID, 10) ||  
            RPAD(house.ADDRESS, 30) ||  
            RPAD(house.NEIGHBORHOOD, 20) || 
            '$' || TO_CHAR(house.PRICE, '999999')
        );
        
        total_price := total_price + house.PRICE;
        house_count := house_count + 1;
    END LOOP;

    -- Show a message if there were no houses found (i.e., if house_count = 0).
    IF house_count = 0 
    THEN
        DBMS_OUTPUT.PUT_LINE('No available houses!');
    ELSE
        -- Determine the average price and show it.
        DBMS_OUTPUT.PUT_LINE('Avg Price: $' || TO_CHAR(total_price / house_count, '999999.00'));
    END IF;
END;
/


call HouseReport('987-65-4322');
call HouseReport('987-65-4323');
call HouseReport('987-65-4324');

