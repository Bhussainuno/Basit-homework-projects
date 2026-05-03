
-- 1. Find the names of all agents who James Wagner directly assists. Note: You must use the
--name. Do not hardcode the agent ID.

SELECT A_NAME
FROM AGENT
WHERE A_ASSISTANTID = (SELECT A_ID FROM AGENT WHERE A_NAME = 'James Wagner' );

-- 2. Find the houses that are within Ryan Gosling's price range (inclusive).
SELECT H_ADDRESS, H_PRICE
FROM House
JOIN Buyer ON H_PRICE BETWEEN B_LOWERPRICE AND B_UPPERPRICE
WHERE B_NAME = 'Ryan Gosling';


-- 3. Find the agents names that are listing the houses within Ryan Gosling's price range.
SELECT DISTINCT A_NAME
FROM Agent
JOIN House ON A_ID = AGENTID
JOIN Buyer ON H_PRICE BETWEEN B_LOWERPRICE AND B_UPPERPRICE
WHERE B_NAME = 'Ryan Gosling';

-- 4. For each housing neighborhood, list the neighborhood, the number of houses in that
--For each housing neighborhood, list the neighborhood, the number of houses in that
SELECT DISTINCT H_NEIGHBORHOOD, 
    COUNT(*) AS NumberOfHouses,
    COUNT(DISTINCT A_ID) AS NumberOfAgents
FROM House
JOIN Agent ON AGENTID = A_ID
GROUP BY H_NEIGHBORHOOD;

-- 5. Retrieve the names of all buyers who have 2 or more dependents.

SELECT B_NAME
FROM Buyer
JOIN Dependent ON B_SSN = D_BUYERSSN
GROUP BY B_NAME
HAVING COUNT(D_NAME) >= 2;

-- 6. Find all sellers where the total price of the houses they are selling is greater than $500,000.

SELECT S_NAME
FROM Seller
JOIN House ON S_SSN = SELLERSSN
GROUP BY S_NAME
HAVING SUM(H_PRICE) > 500000;

-- 7. Find all houses where either a) the price per square feet is less than the average price per
--square feet and the neighborhood is the Central Business District or b) the price is less than
--$200,000 and the neighborhood is the French Quarter. Use a single query.
SELECT *
FROM House
WHERE (H_NEIGHBORHOOD = 'Central Business District' 
       AND H_PRICE < (SELECT AVG(H_PRICE / H_SQUAREFEET) 
                      FROM House 
                      WHERE H_NEIGHBORHOOD = 'Central Business District'))
   OR (H_NEIGHBORHOOD = 'French Quarter' 
       AND H_PRICE < 200000);

-- 8. This problem requires you to use the regular expression function REGEXP_LIKE that we
--discussed in class.
-- Find all addresses that have an even street number (e.g., return 102 Bourbon, but not 101
--Bourbon). Use a single regular expression.
SELECT *
FROM House
WHERE REGEXP_LIKE(H_ADDRESS, '^[02468][0-9]* .*');



