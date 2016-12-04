package com.roufid.tutorial;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.roufid.tutorial.dao.BookDao;
import com.roufid.tutorial.entity.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private BookDao bookDao;

	@Autowired
	private EntityManager entityManager;

	@Test
	public void testShouldCreateBookByNamingStoredProcedure() {

		String bookName = "Design Patterns: Elements of Reusable Object-Oriented Software";
		String authorFirstname = "Gang";
		String authorLastname = "of four";

		StoredProcedureQuery addBookNamedStoredProcedure = entityManager.createNamedStoredProcedureQuery("addBook_sp");
		addBookNamedStoredProcedure.setParameter("bookName", bookName);
		addBookNamedStoredProcedure.setParameter("bookReleaseDate", new Date(), TemporalType.DATE);
		addBookNamedStoredProcedure.setParameter("authorFirstname", authorFirstname);
		addBookNamedStoredProcedure.setParameter("authorLastname", authorLastname);

		// Stored procedure call
		Integer createdBookId = (Integer) addBookNamedStoredProcedure.getSingleResult();

		Assert.assertNotNull(createdBookId);

		Book book = bookDao.findOne(createdBookId);

		Assert.assertEquals(bookName, book.getName());
		Assert.assertEquals(authorFirstname, book.getAuthor().getFirstname());
		Assert.assertEquals(authorLastname, book.getAuthor().getLastname());
	}
	
	@Test
	public void testShouldCreateBookByDynamicStoredProcedure() {
		
		String bookName = "Java Persistence with Hibernate";
		String authorFirstname = "Christian";
		String authorLastname = "Bauer";
		
		// Dynamic stored procedure definition.
		StoredProcedureQuery addBookStoredProcedure = entityManager.createStoredProcedureQuery("addBook");
		addBookStoredProcedure.registerStoredProcedureParameter(1, String.class, ParameterMode.IN); 
		addBookStoredProcedure.registerStoredProcedureParameter(2, Date.class, ParameterMode.IN); 
		addBookStoredProcedure.registerStoredProcedureParameter(3, String.class, ParameterMode.IN); 
		addBookStoredProcedure.registerStoredProcedureParameter(4, String.class, ParameterMode.IN); 
		
		// Setting stored procedure parameters.
		addBookStoredProcedure.setParameter(1, bookName);
		addBookStoredProcedure.setParameter(2, new Date(), TemporalType.DATE);
		addBookStoredProcedure.setParameter(3, authorFirstname);
		addBookStoredProcedure.setParameter(4, authorLastname);
		
		// Stored procedure call
		Integer createdBookId = (Integer) addBookStoredProcedure.getSingleResult();
		
		Assert.assertNotNull(createdBookId);

		Book book = bookDao.findOne(createdBookId);

		Assert.assertEquals(bookName, book.getName());
		Assert.assertEquals(authorFirstname, book.getAuthor().getFirstname());
		Assert.assertEquals(authorLastname, book.getAuthor().getLastname());
	}
	
	@Test
	public void testShouldAddBookUsingSpringJpaProcedure() {
		
		String bookName = "Java Persistence with Hibernate";
		String authorFirstname = "Christian";
		String authorLastname = "Bauer";
		
		Integer createdBookId = bookDao.addBook(bookName, new Date(), authorFirstname, authorLastname);
		
		Assert.assertNotNull(createdBookId);

	}

}
