--liquibase formatted sql

-- changeset Andreas_Ebbert-Karroum:add_harry_potter_books

-- Insert Harry Potter books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('Harry Potter and the Philosopher''s Stone', 'J.K. Rowling', '1', '0747532699', 1997),
('Harry Potter and the Philosopher''s Stone', 'J.K. Rowling', '1', '0747532699', 1997),
('Harry Potter and the Philosopher''s Stone', 'J.K. Rowling', '1', '0747532699', 1997),
('Harry Potter and the Philosopher''s Stone', 'J.K. Rowling', '1', '0747532699', 1997),
('Harry Potter and the Chamber of Secrets', 'J.K. Rowling', '1', '0439064873', 1998),
('Harry Potter and the Chamber of Secrets', 'J.K. Rowling', '1', '0439064873', 1998),
('Harry Potter and the Chamber of Secrets', 'J.K. Rowling', '1', '0439064873', 1998),
('Harry Potter and the Chamber of Secrets', 'J.K. Rowling', '1', '0439064873', 1998),
('Harry Potter and the Prisoner of Azkaban', 'J.K. Rowling', '1', '0439136358', 1999),
('Harry Potter and the Prisoner of Azkaban', 'J.K. Rowling', '1', '0439136358', 1999),
('Harry Potter and the Prisoner of Azkaban', 'J.K. Rowling', '1', '0439136358', 1999),
('Harry Potter and the Goblet of Fire', 'J.K. Rowling', '1', '0439139595', 2000),
('Harry Potter and the Goblet of Fire', 'J.K. Rowling', '1', '0439139595', 2000),
('Harry Potter and the Goblet of Fire', 'J.K. Rowling', '1', '0439139595', 2000),
('Harry Potter and the Order of the Phoenix', 'J.K. Rowling', '1', '0439358071', 2003),
('Harry Potter and the Order of the Phoenix', 'J.K. Rowling', '1', '0439358071', 2003),
('Harry Potter and the Half-Blood Prince', 'J.K. Rowling', '1', '0439785969', 2005),
('Harry Potter and the Half-Blood Prince', 'J.K. Rowling', '1', '0439785969', 2005),
('Harry Potter and the Deathly Hallows', 'J.K. Rowling', '1', '0545139700', 2007),
('Harry Potter and the Deathly Hallows', 'J.K. Rowling', '1', '0545139700', 2007);

-- changeset Andreas_Ebbert-Karroum:add_game_of_thrones_books

-- Insert Game of Thrones books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('A Game of Thrones', 'George R.R. Martin', '1', '0553103540', 1996),
('A Clash of Kings', 'George R.R. Martin', '1', '0553108033', 1998),
('A Storm of Swords', 'George R.R. Martin', '1', '0553106633', 2000),
('A Feast for Crows', 'George R.R. Martin', '1', '0553801507', 2005),
('A Dance with Dragons', 'George R.R. Martin', '1', '0553801477', 2011);

-- changeset Andreas_Ebbert-Karroum:add_lord_of_the_rings_books

-- Insert Lord of the Rings books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('The Fellowship of the Ring', 'J.R.R. Tolkien', '1', '0618346252', 1954),
('The Fellowship of the Ring', 'J.R.R. Tolkien', '1', '0618346252', 1954),
('The Fellowship of the Ring', 'J.R.R. Tolkien', '1', '0618346252', 1954),
('The Two Towers', 'J.R.R. Tolkien', '1', '0618346260', 1954),
('The Two Towers', 'J.R.R. Tolkien', '1', '0618346260', 1954),
('The Two Towers', 'J.R.R. Tolkien', '1', '0618346260', 1954),
('The Return of the King', 'J.R.R. Tolkien', '1', '0618346279', 1955),
('The Return of the King', 'J.R.R. Tolkien', '1', '0618346279', 1955),
('The Return of the King', 'J.R.R. Tolkien', '1', '0618346279', 1955);

-- changeset Andreas_Ebbert-Karroum:add_narnia_books

-- Insert Narnia books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('The Lion, the Witch and the Wardrobe', 'C.S. Lewis', '1', '0060764899', 1950),
('Prince Caspian', 'C.S. Lewis', '1', '0060764929', 1951),
('The Horse and His Boy', 'C.S. Lewis', '1', '0060764945', 1954),
('The Silver Chair', 'C.S. Lewis', '1', '006076497X', 1953),
('The Voyage of the Dawn Treader', 'C.S. Lewis', '1', '0060764899', 1952),
('The Magician''s Nephew', 'C.S. Lewis', '1', '0060764902', 1955),
('The Last Battle', 'C.S. Lewis', '1', '0060764902', 1956);

-- changeset Andreas_Ebbert-Karroum:add_his_dark_materials_books

-- Insert His Dark Materials books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('Northern Lights (The Golden Compass)', 'Philip Pullman', '1', '0440418321', 1995),
('The Subtle Knife', 'Philip Pullman', '1', '044041833X', 1997),
('The Amber Spyglass', 'Philip Pullman', '1', '0440418569', 2000);

-- changeset Andreas_Ebbert-Karroum:add_a_song_of_ice_and_fire_books

-- Insert A Song of Ice and Fire books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('A Game of Thrones', 'George R.R. Martin', '1', '0553103540', 1996),
('A Game of Thrones', 'George R.R. Martin', '1', '0553103540', 1996),
('A Clash of Kings', 'George R.R. Martin', '1', '0553108033', 1998),
('A Storm of Swords', 'George R.R. Martin', '1', '0553106633', 2000),
('A Feast for Crows', 'George R.R. Martin', '1', '0553801507', 2005),
('A Dance with Dragons', 'George R.R. Martin', '1', '0553801477', 2011);

-- changeset Andreas_Ebbert-Karroum:add_artemis_fowl_books

-- Insert Artemis Fowl books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('Artemis Fowl', 'Eoin Colfer', '1', '0786856288', 2001),
('Artemis Fowl', 'Eoin Colfer', '1', '0786856288', 2001),
('Artemis Fowl', 'Eoin Colfer', '1', '0786856288', 2001),
('Artemis Fowl: The Arctic Incident', 'Eoin Colfer', '1', '0786856296', 2002),
('Artemis Fowl: The Eternity Code', 'Eoin Colfer', '1', '0786819146', 2003),
('Artemis Fowl: The Opal Deception', 'Eoin Colfer', '1', '0786852894', 2005),
('Artemis Fowl: The Lost Colony', 'Eoin Colfer', '1', '0786856288', 2006),
('Artemis Fowl: The Time Paradox', 'Eoin Colfer', '1', '1423108366', 2008),
('Artemis Fowl: The Atlantis Complex', 'Eoin Colfer', '1', '1423128192', 2010),
('Artemis Fowl: The Last Guardian', 'Eoin Colfer', '1', '1423161630', 2012);

-- changeset Andreas_Ebbert-Karroum:add_discworld_books

-- Insert Discworld books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('The Color of Magic', 'Terry Pratchett', '1', '0062225677', 1983),
('Mort', 'Terry Pratchett', '1', '0552131066', 1987),
('Guards! Guards!', 'Terry Pratchett', '1', '0062225757', 1989),
('Reaper Man', 'Terry Pratchett', '1', '0062225757', 1991),
('Sourcery', 'Terry Pratchett', '1', '0062225730', 1988),
('Wyrd Sisters', 'Terry Pratchett', '1', '006222579X', 1988),
('Pyramids', 'Terry Pratchett', '1', '0062225765', 1989),
('Moving Pictures', 'Terry Pratchett', '1', '0062225781', 1990);

-- changeset Andreas_Ebbert-Karroum:add_additional_books

-- Insert additional books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
-- Fantasy
('The Hobbit', 'J.R.R. Tolkien', '1', '0618260307', 1937),
('The Lion, the Witch and the Wardrobe', 'C.S. Lewis', '1', '0060764899', 1950),
-- Science Fiction
('Dune', 'Frank Herbert', '1', '0441013597', 1965),
('Neuromancer', 'William Gibson', '1', '0441569595', 1984),
('Snow Crash', 'Neal Stephenson', '1', '0553380958', 1992),
-- Classic Fiction
('Pride and Prejudice', 'Jane Austen', '1', '0141439513', 1813),
('1984', 'George Orwell', '1', '0451524934', 1949),
('To Kill a Mockingbird', 'Harper Lee', '1', '0061120081', 1960),
-- Mystery/Thriller
('The Girl with the Dragon Tattoo', 'Stieg Larsson', '1', '0307454541', 2005),
('Gone Girl', 'Gillian Flynn', '1', '030758836X', 2012),
('The Da Vinci Code', 'Dan Brown', '1', '0385504209', 2003),
-- Historical Fiction
('The Book Thief', 'Markus Zusak', '1', '0375842209', 2005),
('All the Light We Cannot See', 'Anthony Doerr', '1', '1476746583', 2014);

-- changeset Andreas_Ebbert-Karroum:add_scrum_and_agile_books

-- Insert Scrum and Agile development books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('Agile Estimating and Planning', 'Mike Cohn', '1', '0131479415', 2005),
('Clean Code: A Handbook of Agile Software Craftsmanship', 'Robert C. Martin', '1', '0132350882', 2008),
('The Agile Samurai: How Agile Masters Deliver Great Software', 'Jonathan Rasmusson', '1', '1934356581', 2010),
('User Stories Applied: For Agile Software Development', 'Mike Cohn', '1', '0321205685', 2004),
('Agile Testing: A Practical Guide for Testers and Agile Teams', 'Lisa Crispin and Janet Gregory', '1', '0321534468', 2009),
('Scrum Mastery: From Good to Great Servant-Leadership', 'Geoff Watts', '1', '0957587406', 2013),
('Kanban: Successful Evolutionary Change for Your Technology Business', 'David J. Anderson', '1', '0984521402', 2010);

-- changeset Andreas_Ebbert-Karroum:add_spring_and_spring_boot_books

-- Insert Spring and Spring Boot books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('Spring in Action', 'Craig Walls', '4', '161729120X', 2014),
('Spring Boot in Action', 'Craig Walls', '1', '1617292540', 2015),
('Pro Spring 5', 'Iuliana Cosmina and Rob Harrop', '5', '1484228077', 2017),
('Mastering Spring Boot 2.0', 'Dinesh Rajput', '2', '9388511894', 2018),
('Spring Microservices in Action', 'John Carnell', '1', '1617293989', 2017),
('Effective Java: Third Edition', 'Joshua Bloch', '3', '0134685997', 2017),
('Spring Recipes: A Problem-Solution Approach', 'Gary Mak', '2', '1430260148', 2014),
('Spring Security in Action', 'Laurentiu Spilca', '1', '1617295124', 2018);

-- changeset Andreas_Ebbert-Karroum:add_tdd_and_atdd_books

-- Insert TDD and ATDD books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('Test-Driven Development by Example', 'Kent Beck', '1', '0321146530', 2002),
('The Art of Unit Testing: With Examples in .NET', 'Roy Osherove', '2', '1617290890', 2013),
('Growing Object-Oriented Software, Guided by Tests', 'Steve Freeman and Nat Pryce', '1', '0321503627', 2009),
('Growing Object-Oriented Software, Guided by Tests', 'Steve Freeman and Nat Pryce', '1', '0321503627', 2009),
('Growing Object-Oriented Software, Guided by Tests', 'Steve Freeman and Nat Pryce', '1', '0321503627', 2009),
('Agile Testing: A Practical Guide for Testers and Agile Teams', 'Lisa Crispin and Janet Gregory', '1', '0321534468', 2009),
('Specification by Example: How Successful Teams Deliver the Right Software', 'Gojko Adzic', '1', '1617290084', 2011),
('Specification by Example: How Successful Teams Deliver the Right Software', 'Gojko Adzic', '1', '1617290084', 2011),
('Specification by Example: How Successful Teams Deliver the Right Software', 'Gojko Adzic', '1', '1617290084', 2011),
('The Cucumber Book: Behaviour-Driven Development for Testers and Developers', 'Matt Wynne and Aslak Hellesøy', '1', '1934356808', 2012),
('Effective Unit Testing: A guide for Java developers', 'Lasse Koskela', '1', '1935182579', 2013);
