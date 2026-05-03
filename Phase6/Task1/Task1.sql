
--1. Retrieve the names of all agents who are listing at least one house. Make sure to remove duplicates.

SELECT A.A_NAME
FROM AGENT A
JOIN HOUSE H ON A.A_ID = H.agentid
GROUP BY A.A_NAME;

--2. For each neighborhood, list the neighborhood and the average house price in that
--neighborhood.


SELECT H_NEIGHBORHOOD, AVG(H_PRICE) AS average_price
FROM house
GROUP BY H_NEIGHBORHOOD;


--3. Retrieve the names of all agents who are not listing any houses.

SELECT A_NAME
FROM AGENT A
LEFT JOIN HOUSE H ON a.A_ID = H.agentid
WHERE h.H_ID IS NULL;



--4. Retrieve the average house price for houses being sold by Katy Perry. You must use the
--string 'Katy Perry' and not hardcode the SSN value

SELECT AVG(h.H_PRICE) AS avg_price
FROM HOUSE h
JOIN SELLER s ON h.SELLERSSN = s.S_SSN
WHERE s.S_NAME = 'Katy Perry';


