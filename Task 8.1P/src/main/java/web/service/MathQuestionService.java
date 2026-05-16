package web.service;

public class MathQuestionService {

	/**
	 * Q1: Addition. Returns null if either input is empty, null, or non-numeric.
	 */
	public static Double q1Addition(String number1, String number2) {
		if (isBlank(number1) || isBlank(number2)) {
			return null;
		}
		try {
			return Double.valueOf(number1) + Double.valueOf(number2);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Q2: Subtraction (number1 - number2). Returns null if invalid.
	 */
	public static Double q2Subtraction(String number1, String number2) {
		if (isBlank(number1) || isBlank(number2)) {
			return null;
		}
		try {
			return Double.valueOf(number1) - Double.valueOf(number2);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Q3: Multiplication. Returns null if invalid.
	 */
	public static Double q3Multiplication(String number1, String number2) {
		if (isBlank(number1) || isBlank(number2)) {
			return null;
		}
		try {
			return Double.valueOf(number1) * Double.valueOf(number2);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private static boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}
}