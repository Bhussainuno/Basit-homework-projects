
DROP TABLE Dependent;  -- Droping the Dependent table
DROP TABLE House;   -- Drop the House table
DROP TABLE Buyer; -- Drop the Buyer table
DROP TABLE Seller; -- Drop the Seller table
DROP TABLE Agent; -- Drop the Agent table

-- Create AGENT Table
CREATE TABLE Agent (
    A_ID CHAR(4), -- Agent Unique ID
    A_Name VARCHAR(50), --Agent Name 
    A_Phone CHAR(12), -- Agent Phone number 
    A_AssistantID CHAR(4),  -- assistant agent ID which is self-referencing foreign key
    CONSTRAINT AgentPK PRIMARY KEY (A_ID), -- Agent ID primary key constrains
    CONSTRAINT AssistantFK FOREIGN KEY (A_AssistantID) REFERENCES Agent(A_ID) --Forgein key assistant is self referancing 
);
--creating seller table 
CREATE TABLE Seller ( 
    S_SSN CHAR(11), --Buyer ssn number is primary key 
    S_Name VARCHAR(50), --seller name 
    S_Phone CHAR(12),  -- seller number
    CONSTRAINT SellerPK PRIMARY KEY (S_SSN) -- Primary key constraint on seller ssn
);
CREATE TABLE Buyer (
    B_SSN CHAR(11), -- Buyer SSN is Primary key 
    B_Name VARCHAR(50), -- buyer name 
    B_LowerPrice DECIMAL(10, 2), -- LowerPrice range for buyer 
    B_UpperPrice DECIMAL(12, 2), --upperPrice range for Buyer
    AgentID CHAR(4),  -- foreign key referencing agent by AgentID
    CONSTRAINT BuyerPK PRIMARY KEY (B_SSN), -- Primary key constraint on B_SSN
    CONSTRAINT BuyerAgentFK FOREIGN KEY (AgentID) REFERENCES Agent(A_ID) -- Foreign key linking with buyer to agent
);
-- Create HOUSE Table
CREATE TABLE House (
    H_ID CHAR(4), -- House Unique ID as primary key
    H_Address VARCHAR(20), -- House address 
    H_Neighborhood VARCHAR(30), -- Neighborhood where the house is located
    H_Price DECIMAL(10, 2), -- House Price
    H_SquareFeet DECIMAL(12, 2), -- measure of the house in square feet
    SellerSSN CHAR(11), -- Seller(S_SSN) is referencing to Foreign key
    AgentID CHAR(4), -- Foreign key is referencing to Agent(A_ID)
    CONSTRAINT HousePK PRIMARY KEY (H_ID),  -- H_ID as Primary key constraint
    CONSTRAINT HouseSellerFK FOREIGN KEY (SellerSSN) REFERENCES Seller(S_SSN), -- Foreign key connecting house to seller
    CONSTRAINT HouseAgentFK FOREIGN KEY (AgentID) REFERENCES Agent(A_ID) -- Foreign key connecting house to agen
);
-- Create DEPENDENT Table
CREATE TABLE Dependent (
    D_BuyerSSN CHAR(11), -- Buyer(B_SSN) is referencing as Foreign key 
    D_Name VARCHAR(50),  -- Name of the dependent
    D_Age INT, -- Age of the dependent
    CONSTRAINT DependentPK PRIMARY KEY (D_BuyerSSN, D_Name), -- Composite of primary key on D_BuyerSSN and D_Name
    CONSTRAINT DependentBuyerFK FOREIGN KEY (D_BuyerSSN) REFERENCES Buyer(B_SSN) -- Foreign key connecting with dependent to buyer
);

INSERT INTO Agent VALUES('A10', 'Shreya Banerjee', '504-125-4567', NULL);
INSERT INTO Agent VALUES('A02', 'James Wagner', '504-123-5567', NULL);
INSERT INTO Agent VALUES('A08', 'David Pace', '504-124-4567', NULL);
INSERT INTO Agent VALUES('A05', 'Vassil Roussev', '504-323-4567', 'A02');
INSERT INTO Agent VALUES('A04', 'Ben Samuel', '504-123-4667', 'A02');
INSERT INTO Agent VALUES('A07', 'Yasin Nur', '504-123-4597', 'A02');
INSERT INTO Agent VALUES('A03', 'Chris Summa', '504-223-4567', 'A10');
INSERT INTO Agent VALUES('A09', 'Tamjid Hoque', '504-423-4567', 'A10');
INSERT INTO Agent VALUES('A01', 'Kathy Johnson', '504-123-4567', 'A03');
INSERT INTO Agent VALUES('A06', 'Ted Holmberg', '504-123-4767', 'A04');
Commit;

INSERT INTO Buyer VALUES('987-65-4321', 'Will Smith', 200000, 250000, 'A01');
INSERT INTO Buyer VALUES('987-65-4322', 'Margot Robbie', 300000, 325000, 'A04');
INSERT INTO Buyer VALUES('987-65-4323', 'Ryan Gosling', 260000, 310000, 'A07');
INSERT INTO Buyer VALUES('987-65-4324', 'Dwayne Johnson', 180000, 200000, 'A10');
INSERT INTO Buyer VALUES('987-65-4325', 'Emma Stone', 285000, 315000, 'A02');
INSERT INTO Buyer VALUES('987-65-4326', 'Tom Hanks', 320000, 390000, 'A05');
INSERT INTO Buyer VALUES('987-65-4327', 'Idris Elba', 380000, 410000, 'A08');
INSERT INTO Buyer VALUES('987-65-4328', 'Gal Gadot', 210000, 245000, 'A01');
INSERT INTO Buyer VALUES('987-65-4329', 'Will Ferrell', 270000, 310000, 'A02');
INSERT INTO Buyer VALUES('987-64-4321', 'Chris Evans', 365000, 405000, 'A06');
INSERT INTO Buyer VALUES('987-63-4321', 'Jamie Foxx', 375000, 400000, 'A08');
INSERT INTO Buyer VALUES('987-62-4321', 'Tom Hardy', 300000, 330000, 'A03');
Commit;


INSERT INTO Dependent VALUES('987-65-4324', 'Alice', 12);
INSERT INTO Dependent VALUES('987-65-4324', 'Bob', 10);
INSERT INTO Dependent VALUES('987-65-4325', 'Alice', 5);
INSERT INTO Dependent VALUES('987-65-4325', 'Bob', 7);
INSERT INTO Dependent VALUES('987-65-4325', 'Cindy', 11);
INSERT INTO Dependent VALUES('987-65-4329', 'Alan', 6);
INSERT INTO Dependent VALUES('987-64-4321', 'Betty', 2);
Commit;

INSERT INTO Seller VALUES('123-45-6789', 'Taylor Swift', '504-987-6543');
INSERT INTO Seller VALUES('223-45-6789', 'Bruno Mars', '504-187-6543');
INSERT INTO Seller VALUES('323-45-6789', 'Justin Bieber', '504-287-6543');
INSERT INTO Seller VALUES('423-45-6789', 'Adele', '504-387-6543');
INSERT INTO Seller VALUES('523-45-6789', 'Lady Gaga', '504-487-6543');
INSERT INTO Seller VALUES('623-45-6789', 'Katy Perry', '504-587-6543');
INSERT INTO Seller VALUES('723-45-6789', 'Rihanna', '504-687-6543');
INSERT INTO Seller VALUES('823-45-6789', 'Snoop Dogg', '504-787-6543');
INSERT INTO Seller VALUES('923-45-6789', 'Ariana Grande', '504-887-6543');
INSERT INTO Seller VALUES('123-45-6780', 'Alicia Keys', '504-987-6541');
INSERT INTO Seller VALUES('123-45-6781', 'Post Malone', '504-987-6542');
INSERT INTO Seller VALUES('123-45-6782', 'Beyonce', '504-987-6544');
INSERT INTO Seller VALUES('123-45-6783', 'Britney Spears', '504-987-6545');
Commit;


INSERT INTO House VALUES('H01', '101 Bourbon', 'French Quarter', 225000, 1200, '123-45-6789', 'A01');
INSERT INTO House VALUES('H02', '201 Bourbon', 'French Quarter', 230000, 1400, '223-45-6789', 'A03');
INSERT INTO House VALUES('H03', '301 Bourbon', 'French Quarter', 280000, 1800, '423-45-6789', 'A03');
INSERT INTO House VALUES('H04', '123 Royal', 'French Quarter', 250000, 1750, '623-45-6789', 'A01');
INSERT INTO House VALUES('H05', '1500 Sugar Bowl', 'Central Business District', 400000, 10000, '123-45-6789', 'A02');
INSERT INTO House VALUES('H06', '1501 Dave Dixon', 'Central Business District', 350000, 8000, '823-45-6789', 'A04');
INSERT INTO House VALUES('H07', '1000 Carrollton', 'Mid City', 310000, 2000, '623-45-6789', 'A06');
INSERT INTO House VALUES('H08', '1200 City Park', 'Mid City', 327000, 2100, '123-45-6780', 'A01');
INSERT INTO House VALUES('H09', '400 Poydras', 'Central Business District', 381000, 1400, '123-45-6783', 'A06');
INSERT INTO House VALUES('H10', '101 Chartres', 'French Quarter', 299000, 900, '623-45-6789', 'A09');
INSERT INTO House VALUES('H11', '456 Royal', 'French Quarter', 500000, 2500, '523-45-6789', 'A01');
INSERT INTO House VALUES('H12', '601 Bourbon', 'French Quarter', 110000, 600, '223-45-6789', 'A02');
INSERT INTO House VALUES('H13', '1100 Carrollton', 'Mid City', 510000, 2100, '123-45-6781', 'A09');
INSERT INTO House VALUES('H14', '1300 City Park', 'Mid City', 270000, 1400, '323-45-6789', 'A03');
INSERT INTO House VALUES('H15', '1000 City Park', 'Mid City', 240000, 1400, '123-45-6782', 'A05');
INSERT INTO House VALUES('H16', '401 Bourbon', 'French Quarter', 100000, 500, '123-45-6789', 'A04');
INSERT INTO House VALUES('H17', '1200 Carrollton', 'Mid City', 395000, 2400, '123-45-6783', 'A02');
INSERT INTO House VALUES('H18', '501 Bourbon', 'French Quarter', 400000, 1900, '423-45-6789', 'A05');
INSERT INTO House VALUES('H19', '500 Poydras', 'Central Business District', 333000, 1500, '623-45-6789', 'A05');
INSERT INTO House VALUES('H20', '600 Poydras', 'Central Business District', 335000, 1400, '723-45-6789', 'A01');
Commit;

SELECT 'Agent: ' || COUNT(*) AS Cnt FROM Agent
UNION
SELECT 'Buyer: ' || COUNT(*) AS Cnt FROM Buyer
UNION
SELECT 'Dependent: ' || COUNT(*) AS Cnt FROM Dependent
UNION
SELECT 'House: ' || COUNT(*) AS Cnt FROM House
UNION
SELECT 'Seller: ' || COUNT(*) AS Cnt FROM Seller;


