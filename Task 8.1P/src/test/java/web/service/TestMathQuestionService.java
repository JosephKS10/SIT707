package web.service;

import org.junit.Assert;
import org.junit.Test;

public class TestMathQuestionService {

	// ---------- Q1: Addition ----------

	@Test
	public void q1_addsTwoPositiveIntegers() {
		Assert.assertEquals(3.0, MathQuestionService.q1Addition("1", "2"), 0.0);
	}

	@Test
	public void q1_addsNegativeAndPositive() {
		Assert.assertEquals(-1.0, MathQuestionService.q1Addition("-3", "2"), 0.0);
	}

	@Test
	public void q1_addsDecimals() {
		Assert.assertEquals(3.7, MathQuestionService.q1Addition("1.2", "2.5"), 1e-9);
	}

	@Test
	public void q1_zeroPlusZero() {
		Assert.assertEquals(0.0, MathQuestionService.q1Addition("0", "0"), 0.0);
	}

	@Test
	public void q1_returnsNullWhenFirstEmpty() {
		Assert.assertNull(MathQuestionService.q1Addition("", "2"));
	}

	@Test
	public void q1_returnsNullWhenSecondEmpty() {
		Assert.assertNull(MathQuestionService.q1Addition("1", ""));
	}

	@Test
	public void q1_returnsNullWhenBothEmpty() {
		Assert.assertNull(MathQuestionService.q1Addition("", ""));
	}

	@Test
	public void q1_returnsNullWhenFirstNull() {
		Assert.assertNull(MathQuestionService.q1Addition(null, "2"));
	}

	@Test
	public void q1_returnsNullWhenSecondNull() {
		Assert.assertNull(MathQuestionService.q1Addition("1", null));
	}

	@Test
	public void q1_returnsNullForNonNumericInput() {
		Assert.assertNull(MathQuestionService.q1Addition("abc", "2"));
	}

	@Test
	public void q1_returnsNullForWhitespaceInput() {
		Assert.assertNull(MathQuestionService.q1Addition("   ", "2"));
	}

	// ---------- Q2: Subtraction ----------

	@Test
	public void q2_subtractsTwoPositiveIntegers() {
		Assert.assertEquals(3.0, MathQuestionService.q2Subtraction("5", "2"), 0.0);
	}

	@Test
	public void q2_resultCanBeNegative() {
		Assert.assertEquals(-3.0, MathQuestionService.q2Subtraction("2", "5"), 0.0);
	}

	@Test
	public void q2_subtractsDecimals() {
		Assert.assertEquals(0.5, MathQuestionService.q2Subtraction("1.5", "1.0"), 1e-9);
	}

	@Test
	public void q2_returnsNullWhenEmptyInput() {
		Assert.assertNull(MathQuestionService.q2Subtraction("", "2"));
	}

	@Test
	public void q2_returnsNullWhenNullInput() {
		Assert.assertNull(MathQuestionService.q2Subtraction(null, "2"));
	}

	@Test
	public void q2_returnsNullForNonNumericInput() {
		Assert.assertNull(MathQuestionService.q2Subtraction("five", "2"));
	}

	// ---------- Q3: Multiplication ----------

	@Test
	public void q3_multipliesTwoPositiveIntegers() {
		Assert.assertEquals(12.0, MathQuestionService.q3Multiplication("3", "4"), 0.0);
	}

	@Test
	public void q3_multipliesByZero() {
		Assert.assertEquals(0.0, MathQuestionService.q3Multiplication("7", "0"), 0.0);
	}

	@Test
	public void q3_multipliesNegativeNumbers() {
		Assert.assertEquals(-15.0, MathQuestionService.q3Multiplication("-3", "5"), 0.0);
	}

	@Test
	public void q3_multipliesDecimals() {
		Assert.assertEquals(7.5, MathQuestionService.q3Multiplication("2.5", "3"), 1e-9);
	}

	@Test
	public void q3_returnsNullWhenEmptyInput() {
		Assert.assertNull(MathQuestionService.q3Multiplication("", "5"));
	}

	@Test
	public void q3_returnsNullWhenNullInput() {
		Assert.assertNull(MathQuestionService.q3Multiplication("3", null));
	}

	@Test
	public void q3_returnsNullForNonNumericInput() {
		Assert.assertNull(MathQuestionService.q3Multiplication("three", "4"));
	}
}