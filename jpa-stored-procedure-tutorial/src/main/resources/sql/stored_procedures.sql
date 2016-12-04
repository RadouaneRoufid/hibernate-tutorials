CREATE OR REPLACE FUNCTION addbook(
	bookname character varying,
	bookreleasedate timestamp without time zone,
	authorfirstname character varying,
	authorlastname character varying)

RETURNS integer AS $BODY$
	
	DECLARE
		authorId int;
		bookId int;

	BEGIN 
		-- select if exist
		SELECT AUTHOR_ID INTO authorId FROM author a WHERE a.FIRSTNAME=authorFirstname AND a.LASTNAME=authorLastname;
		    
		IF NOT FOUND THEN
			-- insert AUTHOR
			SELECT nextval('idauhtor_seq') INTO authorId;
			INSERT INTO author (AUTHOR_ID, FIRSTNAME, LASTNAME) values (authorId, authorFirstname, authorLastname);	
		END IF;	

		-- insert BOOK
		SELECT nextval('idbook_seq') INTO bookId;
		INSERT INTO book (BOOK_ID, NAME, RELEASE_DATE, AUTHOR_ID) VALUES(bookId, bookName, bookReleaseDate, authorId);

		return bookId;

	END; 
$BODY$
LANGUAGE plpgsql VOLATILE