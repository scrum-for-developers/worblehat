--liquibase formatted sql

/*
Documentation about an API for searching books https://openlibrary.org/dev/docs/api/search

For example:
https://openlibrary.org/search.json?fields=title,author_name,isbn,publish_year&q=…
*/

-- changeset Andreas_Ebbert-Karroum:add_harry_potter_books

-- Insert Harry Potter books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', '1', '1338878921', 2023),
('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', '1', '1338878921', 2023),
('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', '1', '1338878921', 2023),
('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', '1', '1338878921', 2023),
('Harry Potter and the Chamber of Secrets', 'J.K. Rowling', '1', '133887893X', 2023),
('Harry Potter and the Chamber of Secrets', 'J.K. Rowling', '1', '133887893X', 2023),
('Harry Potter and the Chamber of Secrets', 'J.K. Rowling', '1', '133887893X', 2023),
('Harry Potter and the Chamber of Secrets', 'J.K. Rowling', '1', '133887893X', 2023),
('Harry Potter and the Prisoner of Azkaban', 'J.K. Rowling', '1', '1338878948', 2023),
('Harry Potter and the Prisoner of Azkaban', 'J.K. Rowling', '1', '1338878948', 2023),
('Harry Potter and the Prisoner of Azkaban', 'J.K. Rowling', '1', '1338878948', 2023),
('Harry Potter and the Goblet of Fire', 'J.K. Rowling', '1', '1338878956', 2023),
('Harry Potter and the Goblet of Fire', 'J.K. Rowling', '1', '1338878956', 2023),
('Harry Potter and the Goblet of Fire', 'J.K. Rowling', '1', '1338878956', 2023),
('Harry Potter and the Order of the Phoenix', 'J.K. Rowling', '1', '1338299182', 2023),
('Harry Potter and the Order of the Phoenix', 'J.K. Rowling', '1', '1338299182', 2023),
('Harry Potter and the Half-Blood Prince', 'J.K. Rowling', '1', '1338878972', 2023),
('Harry Potter and the Half-Blood Prince', 'J.K. Rowling', '1', '1338878972', 2023),
('Harry Potter and the Deathly Hallows', 'J.K. Rowling', '1', '1338878980', 2023),
('Harry Potter and the Deathly Hallows', 'J.K. Rowling', '1', '1338878980', 2023);

-- changeset Andreas_Ebbert-Karroum:add_game_of_thrones_books

-- Insert Game of Thrones books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('A Game of Thrones', 'George R.R. Martin', '1', '0553381687', 1996),
('A Clash of Kings', 'George R.R. Martin', '1', '0553381695', 1998),
('A Storm of Swords', 'George R.R. Martin', '1', '0553381709', 2000),
('A Feast for Crows', 'George R.R. Martin', '1', '0553582038', 2005),
('A Dance with Dragons', 'George R.R. Martin', '1', '055338595X', 2011);

-- changeset Andreas_Ebbert-Karroum:add_lord_of_the_rings_books

-- Insert Lord of the Rings books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('The Fellowship of the Ring', 'J.R.R. Tolkien', '1', '0008537720', 1954),
('The Fellowship of the Ring', 'J.R.R. Tolkien', '1', '0008537720', 1954),
('The Fellowship of the Ring', 'J.R.R. Tolkien', '1', '0008537720', 1954),
('The Two Towers', 'J.R.R. Tolkien', '1', '0063270897', 1954),
('The Two Towers', 'J.R.R. Tolkien', '1', '0063270897', 1954),
('The Two Towers', 'J.R.R. Tolkien', '1', '0063270897', 1954),
('The Return of the King', 'J.R.R. Tolkien', '1', '0008537747', 1955),
('The Return of the King', 'J.R.R. Tolkien', '1', '0008537747', 1955),
('The Return of the King', 'J.R.R. Tolkien', '1', '0008537747', 1955);

-- changeset Andreas_Ebbert-Karroum:add_narnia_books

-- Insert Narnia books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('The Magician''s Nephew', 'C.S. Lewis', '1', '0064409430', 1955),
('The Lion, the Witch and the Wardrobe', 'C.S. Lewis', '1', '0064409422', 1950),
('The Horse and His Boy', 'C.S. Lewis', '1', '0064409406', 1954),
('Prince Caspian', 'C.S. Lewis', '1', '0064409449', 1951),
('The Voyage of the Dawn Treader', 'C.S. Lewis', '1', '0064409465', 1952),
('The Silver Chair', 'C.S. Lewis', '1', '0064471098', 1953),
('The Last Battle', 'C.S. Lewis', '1', '006447108X', 1956);

-- changeset Andreas_Ebbert-Karroum:add_his_dark_materials_books

-- Insert His Dark Materials books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('Northern Lights (The Golden Compass)', 'Philip Pullman', '1', '0754070751', 1995),
('The Subtle Knife', 'Philip Pullman', '1', '7532738426', 1997),
('The Amber Spyglass', 'Philip Pullman', '1', '8466617299', 2000);

-- changeset Andreas_Ebbert-Karroum:add_artemis_fowl_books

-- Insert Artemis Fowl books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('Artemis Fowl', 'Eoin Colfer', '1', '1855498146', 2001),
('Artemis Fowl', 'Eoin Colfer', '1', '1855498146', 2001),
('Artemis Fowl', 'Eoin Colfer', '1', '1855498146', 2001),
('Artemis Fowl: The Arctic Incident', 'Eoin Colfer', '1', '8497939212', 2002),
('Artemis Fowl: The Eternity Code', 'Eoin Colfer', '1', '1417776609', 2003),
('Artemis Fowl: The Opal Deception', 'Eoin Colfer', '1', '1423124553', 2005),
('Artemis Fowl: The Lost Colony', 'Eoin Colfer', '1', '161383201X', 2006),
('Artemis Fowl: The Time Paradox', 'Eoin Colfer', '1', '0241411718', 2008),
('Artemis Fowl: The Atlantis Complex', 'Eoin Colfer', '1', '0141328045', 2010),
('Artemis Fowl: The Last Guardian', 'Eoin Colfer', '1', '0141346736', 2012);

-- changeset Andreas_Ebbert-Karroum:add_discworld_books

-- Insert Discworld books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('The Colour of Magic', 'Terry Pratchett', '1', '1856953246', 1983),
('Mort', 'Terry Pratchett', '1', '8071975079', 1987),
('Guards! Guards!', 'Terry Pratchett', '1', '3492280684', 1989),
('Reaper Man', 'Terry Pratchett', '1', '1407034766', 1991),
('Sourcery', 'Terry Pratchett', '1', '8497931262', 1988),
('Wyrd Sisters', 'Terry Pratchett', '1', '6055060345', 1988),
('Pyramids', 'Terry Pratchett', '1', '006222574X', 1989),
('Moving Pictures', 'Terry Pratchett', '1', '0061809713', 1990);

-- changeset Andreas_Ebbert-Karroum:add_additional_books

-- Insert additional books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('The Hobbit', 'J.R.R. Tolkien', '1', '0544239555', 1937),
('Dune', 'Frank Herbert', '1', '3453185676', 1965),
('Neuromancer', 'William Gibson', '1', '8585887907', 1984),
('Snow Crash', 'Neal Stephenson', '1', '1586211110', 1992),
-- Classic Fiction
('Pride and Prejudice', 'Jane Austen', '1', '1561381713', 1813),
('1984', 'George Orwell', '1', '014027877X', 1949),
('To Kill a Mockingbird', 'Harper Lee', '1', '0099466260', 1960),
-- Mystery/Thriller
('The Girl with the Dragon Tattoo', 'Stieg Larsson', '1', '0307949494', 2005),
('Gone Girl', 'Gillian Flynn', '1', '0297859404', 2012),
('The Da Vinci Code', 'Dan Brown', '1', '0739353128', 2003),
-- Historical Fiction
('The Book Thief', 'Markus Zusak', '1', '1407036904', 2005),
('All the Light We Cannot See', 'Anthony Doerr', '1', '7508653165', 2014);

-- changeset Andreas_Ebbert-Karroum:add_scrum_and_agile_books

-- Insert Scrum and Agile development books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('Agile Estimating and Planning', 'Mike Cohn', '1', '0132364352', 2005),
('Clean Code: A Handbook of Agile Software Craftsmanship', 'Robert C. Martin', '1', '8328302349', 2008),
('The Agile Samurai: How Agile Masters Deliver Great Software', 'Jonathan Rasmusson', '1', '1934356581', 2010),
('User Stories Applied: For Agile Software Development', 'Mike Cohn', '1', '0321205685', 2004),
('Agile Testing: A Practical Guide for Testers and Agile Teams', 'Lisa Crispin and Janet Gregory', '1', '0321534468', 2009),
('Scrum Mastery: From Good to Great Servant-Leadership', 'Geoff Watts', '1', '0957587406', 2013),
('Kanban: Successful Evolutionary Change for Your Technology Business', 'David J. Anderson', '1', '0984521402', 2010);

-- changeset Andreas_Ebbert-Karroum:add_spring_and_spring_boot_books

-- Insert Spring and Spring Boot books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('Spring in Action', 'Craig Walls', '4', '1932394354', 2014),
('Spring Boot in Action', 'Craig Walls', '1', '1617292540', 2015),
('Mastering Spring Boot 2.0', 'Dinesh Rajput', '2', '1787127567', 2018),
('Spring Microservices in Action', 'John Carnell', '1', '1617293989', 2017),
('Effective Java: Third Edition', 'Joshua Bloch', '3', '0134686047', 2017),
('Spring Recipes: A Problem-Solution Approach', 'Gary Mak', '2', '1430259086', 2014),
('Spring Security in Action', 'Laurentiu Spilca', '1', '1617297739', 2018),
('Kotlin in Action', 'Svetlana Isakova', '1', '1617293296', 2017);

-- changeset Andreas_Ebbert-Karroum:add_tdd_and_atdd_books

-- Insert TDD and ATDD books
INSERT INTO book (title, author, edition, isbn, year_of_publication) VALUES
('Test-Driven Development by Example', 'Kent Beck', '1', '8131715957', 2002),
('Growing Object-Oriented Software, Guided by Tests', 'Steve Freeman and Nat Pryce', '1', '0321699750', 2009),
('Growing Object-Oriented Software, Guided by Tests', 'Steve Freeman and Nat Pryce', '1', '0321699750', 2009),
('Growing Object-Oriented Software, Guided by Tests', 'Steve Freeman and Nat Pryce', '1', '0321699750', 2009),
('Agile Testing: A Practical Guide for Testers and Agile Teams', 'Lisa Crispin and Janet Gregory', '1', '0321534468', 2009),
('Specification by Example: How Successful Teams Deliver the Right Software', 'Gojko Adzic', '1', '1617290084', 2011),
('Specification by Example: How Successful Teams Deliver the Right Software', 'Gojko Adzic', '1', '1617290084', 2011),
('Specification by Example: How Successful Teams Deliver the Right Software', 'Gojko Adzic', '1', '1617290084', 2011),
('The Cucumber Book: Behaviour-Driven Development for Testers and Developers', 'Matt Wynne and Aslak Hellesøy', '1', '1934356808', 2012),
('The Cucumber for Java Book: Behaviour-Driven Development for Testers and Developers', 'Seb Rose, Matt Wynne, Aslak Hellesoy', '1', '1941222293', 2015),
('Effective Unit Testing: A guide for Java developers', 'Lasse Koskela', '1', '1935182579', 2013);
