package sit707_week4;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests functions in LoginForm.
 * @author Joseph Kalayathankal Saji
 */
public class LoginFormTest 
{

	@Test
	public void testStudentIdentity() {
		String studentId = "s225053039";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Joseph Kalayathankal Saji";
		Assert.assertNotNull("Student name is null", studentName);
	}
	
	// Test 1: Username (-), Password (-), ValCode (X)
	@Test
    public void testFailEmptyUsernameAndEmptyPasswordAndDontCareValCode()
    {
		LoginStatus status = LoginForm.login(null, null);
		Assert.assertTrue( status.isLoginSuccess() == false );
    }
	
	// Test 2: Username (-), Password (W), ValCode (X)
	@Test
	public void testFailEmptyUsernameAndWrongPasswordAndDontCareValCode() {
		LoginStatus status = LoginForm.login(null, "wrong_pass");
		Assert.assertTrue( status.isLoginSuccess() == false );
	}

	// Test 3: Username (-), Password (C), ValCode (X)
	@Test
	public void testFailEmptyUsernameAndCorrectPasswordAndDontCareValCode() {
		LoginStatus status = LoginForm.login(null, "ahsan_pass");
		Assert.assertTrue( status.isLoginSuccess() == false );
	}

	// Test 4: Username (W), Password (-), ValCode (X)
	@Test
	public void testFailWrongUsernameAndEmptyPasswordAndDontCareValCode() {
		LoginStatus status = LoginForm.login("wrong_user", null);
		Assert.assertTrue( status.isLoginSuccess() == false );
	}

	// Test 5: Username (W), Password (W), ValCode (X)
	@Test
	public void testFailWrongUsernameAndWrongPasswordAndDontCareValCode() {
		LoginStatus status = LoginForm.login("wrong_user", "wrong_pass");
		Assert.assertTrue( status.isLoginSuccess() == false );
	}

	// Test 6: Username (W), Password (C), ValCode (X)
	@Test
	public void testFailWrongUsernameAndCorrectPasswordAndDontCareValCode() {
		LoginStatus status = LoginForm.login("wrong_user", "ahsan_pass");
		Assert.assertTrue( status.isLoginSuccess() == false );
	}

	// Test 7: Username (C), Password (-), ValCode (X)
	@Test
	public void testFailCorrectUsernameAndEmptyPasswordAndDontCareValCode() {
		LoginStatus status = LoginForm.login("ahsan", null);
		Assert.assertTrue( status.isLoginSuccess() == false );
	}

	// Test 8: Username (C), Password (W), ValCode (X)
	@Test
	public void testFailCorrectUsernameAndWrongPasswordAndDontCareValCode() {
		LoginStatus status = LoginForm.login("ahsan", "wrong_pass");
		Assert.assertTrue( status.isLoginSuccess() == false );
	}

	// Test 9: Username (C), Password (C), ValCode (-)
	@Test
	public void testSuccessLoginButEmptyValidationCodeFails() {
		LoginStatus status = LoginForm.login("ahsan", "ahsan_pass");
		Assert.assertTrue( status.isLoginSuccess() == true );
		// Now test the empty validation code
		boolean isValid = LoginForm.validateCode(null);
		Assert.assertTrue(isValid == false);
	}

	// Test 10: Username (C), Password (C), ValCode (W)
	@Test
	public void testSuccessLoginButWrongValidationCodeFails() {
		LoginStatus status = LoginForm.login("ahsan", "ahsan_pass");
		Assert.assertTrue( status.isLoginSuccess() == true );
		// Now test the wrong validation code
		boolean isValid = LoginForm.validateCode("999999");
		Assert.assertTrue(isValid == false);
	}

	// Test 11: Username (C), Password (C), ValCode (C)
	@Test
	public void testSuccessLoginAndCorrectValidationCodePasses() {
		LoginStatus status = LoginForm.login("ahsan", "ahsan_pass");
		Assert.assertTrue( status.isLoginSuccess() == true );
		// Now test the correct validation code
		boolean isValid = LoginForm.validateCode("123456");
		Assert.assertTrue(isValid == true);
	}
