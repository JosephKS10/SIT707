package sit707_tasks;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Joseph Kalayathankal Saji 
 */
public class DateUtilTest {
	
	@Test
	public void testStudentIdentity() {
		String studentId = "S225053039"; 
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Joseph Kalayathankal Saji"; 
		Assert.assertNotNull("Student name is null", studentName);
	}

	/*
	 * EQUIVALENCE CLASS TESTS
	 */

	// Test M1 (31-day month), D4 (31st day), Y2 (Non-leap)
	@Test
	public void testEC_M1_D4_Y2_Increment() {
		DateUtil date = new DateUtil(31, 1, 2023); // Jan 31
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(2, date.getMonth());
	}

	// Test M2 (30-day month), D3 (30th day), Y2 (Non-leap)
	@Test
	public void testEC_M2_D3_Y2_Increment() {
		DateUtil date = new DateUtil(30, 4, 2023); // April 30
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(5, date.getMonth());
	}
	
	// Test M2 (30-day month), D4 (31st day) -> SHOULD FAIL
	@Test(expected = RuntimeException.class)
	public void testEC_M2_D4_Invalid() {
		new DateUtil(31, 4, 2023); // April 31 does not exist
	}

	// Test M3 (Feb), D1 (28th day), Y2 (Non-leap)
	@Test
	public void testEC_M3_D1_Y2_Increment() {
		DateUtil date = new DateUtil(28, 2, 2023); 
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(3, date.getMonth()); // Goes to March 1
	}

	// Test M3 (Feb), D1 (28th day), Y1 (Leap year)
	@Test
	public void testEC_M3_D1_Y1_Increment() {
		DateUtil date = new DateUtil(28, 2, 2024); 
		date.increment();
		Assert.assertEquals(29, date.getDay());
		Assert.assertEquals(2, date.getMonth()); // Goes to Feb 29
	}

	// Test M3 (Feb), D2 (29th day), Y1 (Leap year)
	@Test
	public void testEC_M3_D2_Y1_Increment() {
		DateUtil date = new DateUtil(29, 2, 2024); 
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(3, date.getMonth()); // Goes to March 1
	}

	// Test M3 (Feb), D2 (29th day), Y2 (Non-leap) -> SHOULD FAIL
	@Test(expected = RuntimeException.class)
	public void testEC_M3_D2_Y2_Invalid() {
		new DateUtil(29, 2, 2023); // Feb 29 in a non-leap year
	}

	// Nominal Test combining normal values (D1, M1, Y2)
	@Test
	public void testNominalDate() {
		DateUtil date = new DateUtil(15, 5, 2023);
		date.increment();
		Assert.assertEquals(16, date.getDay());
		Assert.assertEquals(5, date.getMonth());
	}
	
	// Decrement Test: Month boundary (D1, M2, Y2)
	@Test
	public void testEC_M2_D1_Y2_Decrement() {
		DateUtil date = new DateUtil(1, 4, 2023); // April 1
		date.decrement();
		Assert.assertEquals(31, date.getDay());
		Assert.assertEquals(3, date.getMonth()); // Goes to March 31
	}
	
	// Decrement Test: Year boundary (D1, M1(Jan), Y2)
	@Test
	public void testEC_YearBoundary_Decrement() {
		DateUtil date = new DateUtil(1, 1, 2024); // Jan 1
		date.decrement();
		Assert.assertEquals(31, date.getDay());
		Assert.assertEquals(12, date.getMonth()); 
		Assert.assertEquals(2023, date.getYear()); // Goes to Dec 31, 2023
	}
}