--Find all houses in the French Quarter neighborhood and sort the output by the price
(any direction)
SELECT *
FROM House
WHERE h_neighborhood = 'French Quarter'
ORDER BY H_PRICE DESC

-- 2. [2 pts] Find all houses on Bourbon (in the address). Hint: remember the LIKE operator.
SELECT *
FROM HOUSE
WHERE H_ADDRESS LIKE '%Bourbon%'
ORDER BY H_ID ASC; -- this is just for my practice

-- 3. [2 pts] Find all agents who do not have an assistant. (problem)
SELECT *
FROM AGENT
WHERE A_ASSISTANTID IS NULL;


-- 4. [2 pts] Find the names of all buyers who have an upper price amount between $325,000
--and $350,000 (inclusive).
SELECT* 
FROM BUYER
WHERE B_UPPERPRICE BETWEEN 325000 AND 350000;

-- 5. [2 pts] Find all buyers who are represented by the agent A01.  (problem )
SELECT *
FROM BUYER
WHERE AGENTID LIKE '%A01%';


-- 6. [2 pts] Find the minimum and maximum prices for all houses. Use only one query.
SELECT 
MIN(H_PRICE) AS Minimum_Price, 
MAX(H_PRICE) AS Maximum_Price
FROM 
HOUSE;


-- 7. [2 pts] Find the average price for all houses.
SELECT AVG(H_PRICE) AS Average_Price
FROM HOUSE;

-- 8. [4 pts] Find the house(s) that has the highest price. Do not hardcode any prices or other
--values – you must use SQL without assuming you know the current database snapshot.
SELECT *
FROM HOUSE
WHERE H_PRICE = (SELECT MAX(H_PRICE) FROM HOUSE);

-- 9. [4 pts] Find the houses with a price less than the average overall price for all homes + 20%
--(i.e., less than 1.2 * average salary). Do not hardcode any salaries or other values – you must
--use SQL without assuming you know the current database snapshot.

SELECT *
FROM HOUSE
WHERE H_PRICE < (SELECT AVG(H_PRICE) * 1.2 FROM HOUSE);


